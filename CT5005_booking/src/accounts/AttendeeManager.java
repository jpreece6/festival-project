package accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import booking.Booking;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;
import festival.Festival;

public class AttendeeManager implements IDatabaseFunctions {
	
	public AttendeeManager() {
		
	}
	
	public void create_attendee(String name, int age, String email_address) {
		
		// Create new attendee object
		Attendee att = new Attendee(name, age, email_address);
		
		Booking b = new Booking(att);
		att.setBooking(b);
		
		try {

			add_entry(att);
			
		} catch (SQLException e) {
			ErrorLog.printError("Cannot add attendee to database at this time.", ErrorLog.SEVERITY_MEDIUM);
		}
		
	}

	
	public void remove_attendee(Attendee att) {
		
		try {
			
			remove_entry(att.getRef());
			
		} catch (SQLException e) {
			ErrorLog.printError("Cannot remove attendee from the database at this time.", ErrorLog.SEVERITY_MEDIUM);
		}
		
	}

	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		int count = DatabaseManager.count_items("attendees");
		if (count <= Festival.MAX_ATTENDEES) {
			
			Attendee att = (Attendee)data;
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO attendees (ref, name, age, email_address, booking) "
					+ "VALUES(ref_auto.nextval, '" + att.getName() 
					+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking().getRef() + "')");
			
			stat.close();
			return true;
		}
		
		System.out.println("No available booking space");
		return false;
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM attendees WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		att.toString();
		
		stat.executeUpdate("UPDATE attendees SET name='" + att.getName() + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking().getRef() + "' WHERE ref=" + att.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void  search_database(String column, String data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM attendees WHERE " + column + "='" + data + "'");
		
		DatabaseManager.print_results(rs);
		
		stat.close();
		rs.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE attendees "
				+ "(ref varchar(10), name varchar(100), age int, email_address varchar(100), booking varchar(10),"
				+ "PRIMARY KEY (ref))");
		
		stat.execute("CREATE SEQUENCE ref_auto START WITH 1"
				+ " INCREMENT BY 1 NOMAXVALUE");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE attendees");
		
		stat.execute("DROP SEQUENCE ref_auto");
		
		stat.close();
	
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		Attendee att = new Attendee();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM attendees WHERE ref=" + ref);
		
		if (result.next()) {
			
			att.setRef(result.getString("ref"));
			att.setName(result.getString("name"));
			att.setAge(result.getInt("age"));
			att.setEmailAddress(result.getString("email_address"));
			
			if (result.getString("booking") != null) {
				
				Booking bok = new Booking(att);
				bok.setRef(result.getString("booking"));
				att.setBooking(bok);
				
			}
		
			return att;
			
		}
		
		return null;
	}
}
