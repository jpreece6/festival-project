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
	
	public void create_attendee(String name, int age, String email_address) {
		
		// Create new attendee object
		Attendee att = new Attendee(name, age, email_address);
		
		// Create an empty booking to init the database column
		Booking b = new Booking();
		att.setBooking(b.getRef());
		
		try {

			if (att.getAge() <= 12) {
				
				// do something if attendee is under 12
				
			} else {
			
				add_entry(att);
			
			}
			
			System.out.println("Create attendee successful...");
			
		} catch (SQLException e) {
			ErrorLog.printError("Create attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void search_for_attendee(String column, String data) {
		
		try {
			
			DatabaseManager.print_results("Attendee Search Result", DatabaseManager.search_database("attendees", column, data));
			
		} catch (SQLException e) {
			ErrorLog.printError("Search for attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}

	
	public void remove_attendee(Attendee att) {
		
		try {
			
			remove_entry(att.getRef());
			
			System.out.println("Remove attemdee successful...");
			
		} catch (SQLException e) {
			ErrorLog.printError("Remove attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
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
					+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking() + "')");
			
			stat.close();
			return true;
		}
		
		ErrorLog.printInfo("No available attendee spaces");
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
		
		Booking b = new Booking();
		att.setBooking(b.getRef());
		att.setBooking("1");
		att.toString();
		
		stat.executeUpdate("UPDATE attendees SET name='" + att.getName() + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking() + "' WHERE ref=" + att.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("attendees")) {
			
			ErrorLog.printInfo("Table 'attendees' already exists");
			
		} else {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE attendees "
					+ "(ref varchar(10), name varchar(100), age int, email_address varchar(100), booking varchar(10),"
					+ "PRIMARY KEY (ref))");
			
			stat.execute("CREATE SEQUENCE ref_auto START WITH 1"
					+ " INCREMENT BY 1 NOMAXVALUE");
			
			stat.close();
			
			System.out.println("CREATE attendees DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("attendees")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE attendees");
			
			stat.execute("DROP SEQUENCE ref_auto");
			
			stat.close();
			
			System.out.println("DROP attendees DONE");
			
		}
	
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
				
				Booking bok = new Booking();
				bok.setRef(result.getString("booking"));
				att.setBooking(bok.getRef());
				
			}
		
			return att;
			
		}
		
		return null;
	}
}
