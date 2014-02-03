/**
 * @author Joshua Preece
 * @version 0.6
 * Class handles all database communication for the attendees table and functions 
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

	/**
	 * Create and add a new attendee to the database
	 * @param att Attendee object to save to the database
	 * @return Attendee pass this back to the caller so they have access to the attendees
	 * assigned ref
	 */
	public Attendee create_attendee(Attendee att) {
		
		// Create set an empty booking for now
		att.setBooking("null");
		
		String ref = "";
		
		try {

			// Check if the database does not have the maximum number of attendees
			int count = (DatabaseManager.count_items("attendees") + DatabaseManager.count_items("children"));
			if (count <= Festival.MAX_ATTENDEES) {
				
				// Generate an set a new ref for this new attendee
				ref = DatabaseManager.generate_random_id();
				att.setRef(ref);
				
				// Check is the attendee 12 years old or younger if so add them to the
				// childrens database instead of the attendees database to better identify them
				if (att.getAge() <= 12) {
					
					ErrorLog.printInfo("Attendee under 12 years old or younger will be added to the childrens table for health and safety");
					ChildManager cmg = new ChildManager();
					cmg.add_child(att);
					
				} else {
				
					add_entry(att);
				
				}
				
			}
			
			System.out.println("Create attendee successful...");
			return att;
			
		} catch (SQLException e) {
			ErrorLog.printError("Create attendee failed!\n" + e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return null;
		}
		
	}
	
	/**
	 * Search for a specific attendees with specific information
	 * @param column String column/category to search e.g first_name,
	 * last_name, age, email_address and booking_ref
	 * @param data String data to search for
	 */
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

	/**
	 * Remove an attendee from the database
	 * @param attendee_ref String ref of the attendee to be removed
	 */
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
	
	/**
	 * Update an attendee's information
	 * @param att attendee_ref Attendee attendee to update
	 */
	public void update_attendee(Attendee att) {
		
		try {
			
			// Check to see if the attendee exists in the database and then update
			if (DatabaseManager.does_entry_exist("attendees", "ref", att.getRef())) {
				
				// validate that a booking does not already have the max number of attendees
				if (DatabaseManager.count_specific_items("attendees", "booking", att.getBooking()) < Festival.BOOKING_MAX_ATTENDEES) {
					
					update_entry(att);
				
				} else {
					
					ErrorLog.printInfo("Booking already has the maximum number of assigned attendees");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find attendee. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not update attendee!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Retrieve an attendee from the database
	 * @param attendee_ref String attendee ref to retrieve
	 * @return Attendee object containing the results
	 */
	public Attendee get_attendee(String attendee_ref) {
		
		try {
			
			// Check to see if the attendee exists in the database
			if (DatabaseManager.does_entry_exist("attendees", "ref", attendee_ref)) {
				
				return (Attendee)get_item(attendee_ref);
				
			} else {
				
				ErrorLog.printInfo("Could not find attendee. Please check ref");
				return null;
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not retireve attendee. Please check ref\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return null;
		}
		
	}

	@Override
	public void add_entry(Object data) throws SQLException {
			
		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		// Insert a new record into the database
		stat.executeUpdate("INSERT INTO attendees (ref, first_name, last_name, age, email_address, booking) "
				+ "VALUES('" + att.getRef() + "', '" + att.getFirst_Name() + "', '" + att.getLast_Name()
				+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking() + "')");

		stat.close();
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
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Attendee att = (Attendee)data;
		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.executeUpdate("UPDATE attendees SET ref='" + att.getRef() + "', first_name='" + att.getFirst_Name() 
				+ "', last_name='" + att.getLast_Name() + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking() + "' WHERE ref=" + att.getRef());
		
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
			
			// Assign the results to a new attendee object to be returned
			att.setRef(result.getString("ref"));
			att.setFirst_Name(result.getString("first_name"));
			att.setLast_Name(result.getString("last_name"));
			att.setAge(result.getInt("age"));
			att.setEmailAddress(result.getString("email_address"));
			att.setBooking(result.getString("booking"));
		
			return att;
			
		}
		
		// Return null if nothing found
		return null;
	}
}
