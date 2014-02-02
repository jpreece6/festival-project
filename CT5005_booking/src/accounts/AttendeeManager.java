/**
 * @author Joshua Preece
 * @description Class handles all database communication for the attendees table and functions 
 * @version 0.6
 */

package accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;
import festival.Festival;

public class AttendeeManager implements IDatabaseFunctions {
	
	
	
	public void create_attendee(String first_name, String last_name, int age, String email_address) {
		
		// Create new attendee object
		Attendee att = new Attendee(first_name, last_name, age, email_address);
		
		// Create set an empty booking for now
		att.setBooking("null");
		
		try {

			// Check if the database does not have the maximum number of attendees
			if (DatabaseManager.count_items("attendees") <= Festival.MAX_ATTENDEES) {
				
				if (att.getAge() <= 12) {
					
					ErrorLog.printInfo("Attendee under 12 years old or younger will be added to the childrens table for health and safety");
					ChildManager cmg = new ChildManager();
					cmg.add_child(att);
					
				} else {
				
					add_entry(att);
				
				}
				
			}
			
			System.out.println("Create attendee successful...");
			
		} catch (SQLException e) {
			ErrorLog.printError("Create attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void search_for_attendee(String column, String data) {
		
		try {
			
			// Check to see if the database is not empty
			if (DatabaseManager.count_items("attendees") > 0) {
				
				DatabaseManager.print_results("Attendee Search Result", DatabaseManager.search_database("attendees", column, data));
			
			} else {
				
				ErrorLog.printInfo("No items found in the database, because the database is empty!");
				
			}
			
		} catch (SQLException e) {
			ErrorLog.printError("Search for attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}

	
	public void remove_attendee(String attendee_ref) {
		
		try {
			
			// Check if the attendee exists in the database before removing (pre-condition)
			if (DatabaseManager.does_entry_exist("attendees", "ref", attendee_ref)) {
				
				remove_entry(attendee_ref);
				System.out.println("Remove attemdee successful...");
			
			} else {
				
				System.out.println("Attendee does not exist. Please check the Ref!");
				
			}
			
		} catch (SQLException e) {
			ErrorLog.printError("Remove attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void update_attendee(String attendee_ref) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("attendees", "ref", attendee_ref)) {
				
				
				update_attendee(attendee_ref);
				
			} else {
				
				ErrorLog.printInfo("Could not find attendee. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not update attendee!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}

	/**
	 * @Pre-Conditions The attendees must not have the maximum number of attendees in this case 4000.
	 */
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		// Add both the number of attendees and children to get the total number of attendees currently registered
		int count = (DatabaseManager.count_items("attendees") + DatabaseManager.count_items("children"));
		if (count <= Festival.MAX_ATTENDEES) {
			
			Attendee att = (Attendee)data;
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO attendees (ref, first_name, last_name, age, email_address, booking) "
					+ "VALUES(ref_auto.nextval, '" + att.getFirst_Name() + "', '" + att.getLast_Name()
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
		
		// delete entry only if it exists
		if (DatabaseManager.does_entry_exist("attendees", "ref", ref)) {
			
			stat.execute("DELETE FROM attendees WHERE ref=" + ref);

		}
		
		stat.close();
		
	}
	
	/**
	 * @Pre-Conditions Check if the booking assigned to the attendee, 
	 * ensure it does not already have the maximum number of attendees assigned.
	 */
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Attendee att = (Attendee)data;
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		// validate that a booking does not already have the max number of attendees
		if (DatabaseManager.count_specific_items("attendees", "booking", att.getBooking()) <= 4) {
			
			stat.executeUpdate("UPDATE attendees SET first_name='" + att.getFirst_Name() + "', last_name'" 
					+ att.getLast_Name() + "', age='"
					+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
					+ "', booking='" + att.getBooking() + "' WHERE ref=" + att.getRef());
		
		} else {
			
			ErrorLog.printInfo("Booking already has the maximum number of attendees assigned");
		}
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		// Ensure table does not exist before creating it
		if (DatabaseManager.does_table_exist("attendees")) {
			
			ErrorLog.printInfo("Table 'attendees' already exists");
			
		} else {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE attendees "
					+ "(ref varchar(10), first_name varchar(100), last_name varchar(100), age int, "
					+ "email_address varchar(100), booking varchar(10),"
					+ "PRIMARY KEY (ref))");
			
			stat.execute("CREATE SEQUENCE ref_auto START WITH 1"
					+ " INCREMENT BY 1 NOMAXVALUE");
			
			stat.close();
			
			System.out.println("CREATE attendees DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		// Ensure that the database exists before
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
			att.setFirst_Name(result.getString("first_name"));
			att.setLast_Name(result.getString("last_name"));
			att.setAge(result.getInt("age"));
			att.setEmailAddress(result.getString("email_address"));
			att.setBooking(result.getString("booking"));
		
			return att;
			
		}
		
		return null;
	}
}
