package accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import booking.Booking;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;
import festival.Festival;

public class ChildManager implements IDatabaseFunctions {

	private Attendee att;
	
	public void add_child(Attendee attend) {
		
		try {
			
			add_entry(attend);
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not add child to the database!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void search_for_child(String column, String data) {
		
		try {
			
			DatabaseManager.print_results("Child Search Result", DatabaseManager.search_database("children", column, data));
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not find child!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		int count = DatabaseManager.count_items("attendees");
		if (count <= Festival.MAX_ATTENDEES) {
			
			Attendee att = (Attendee)data;
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO children (ref, first_name, last_name, age, booking) "
					+ "VALUES(ref_child_auto.nextval, '" + att.getFirst_Name() + "', '" + att.getLast_Name()
					+ "', '" + att.getAge() + "', '" + att.getBooking() + "')");
			
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
		
		stat.executeUpdate("UPDATE attendees SET first_name='" + att.getFirst_Name() + "', last_name="
				+ att.getLast_Name() + "', age='"
				+ Integer.toString(att.getAge())
				+ "', booking='" + att.getBooking() + "' WHERE ref=" + att.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("children")) {
			
			ErrorLog.printInfo("Table 'children' already exists");
			
		} else {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE children "
					+ "(ref varchar(10), first_name varchar(100), last_name varchar(100), age int"
					+ ", booking varchar(10),"
					+ "PRIMARY KEY (ref))");
			
			stat.execute("CREATE SEQUENCE ref_child_auto START WITH 1"
					+ " INCREMENT BY 1 NOMAXVALUE");
			
			stat.close();
			
			System.out.println("CREATE children DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("children")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE children");
			
			stat.execute("DROP SEQUENCE ref_child_auto");
			
			stat.close();
			
			System.out.println("DROP children DONE");
			
		}
	
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		Attendee att = new Attendee();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM children WHERE ref=" + ref);
		
		if (result.next()) {
			
			att.setRef(result.getString("ref"));
			att.setFirst_Name(result.getString("first_name"));
			att.setLast_Name(result.getString("last_name"));
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
