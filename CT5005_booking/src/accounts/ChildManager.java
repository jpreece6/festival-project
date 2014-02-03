/**
 * @author Joshua Preece
 * @version 0.2
 * @description ChildManager handles the database requests for attendees under the age of 12
 */

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
	
	/**
	 * Adds a child attendee to the database
	 * @param attend Attendee object containing the attendees details
	 */
	public void add_child(Attendee attend) {
		
		try {
			
			// Check if the database does not have the maximum number of attendees
			int count = (DatabaseManager.count_items("attendees") + DatabaseManager.count_items("children"));
			if (count <= Festival.MAX_ATTENDEES) {
			
				// Ensure that the booking is not already assigned maximum
				if (DatabaseManager.count_specific_items("children", "booking", attend.getBooking()) < 2) {
					
					add_entry(attend);
				
				} else {
					
					ErrorLog.printInfo("Booking already has the maximum number of assigned children");
					
				}
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not add child to the database!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Removes a child entry from the children's table
	 * @param attendee_ref String ref of child to remove
	 */
	public void remove_child(String attendee_ref) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("children", "ref", attendee_ref)) {
				
				remove_entry(attendee_ref);
				
			} else {
				
				ErrorLog.printInfo("Could not find child to remove. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not delete child!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Updates a child entry in the database
	 * @param child Attendee object
	 */
	public void update_child(Attendee child) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("children", "ref", child.getRef())) {
				
				update_entry(child);
				
			} else {
				
				ErrorLog.printInfo("Could not find child to update. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not update child!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
	}
	
	/**
	 * Search for a child in the children's table
	 * @param column String column to search
	 * @param data String data to search for
	 */
	public void search_for_child(String column, String data) {
		
		try {
			
			DatabaseManager.print_results("Child Search Result", DatabaseManager.search_database("children", column, data));
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not find child!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Get the number of children that are assigned to this booking
	 * @param book Booking to count
	 * @return Integer number of children
	 */
	public int get_number_of_children(Booking book) {
		
		try {
			
			ResultSet rs = DatabaseManager.search_database("children", "booking", book.getRef());
			
			int count = 0;
			while (rs.next()) {
				
				count++;
				
			}

			return count;
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not retrieve booking's attendee count!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return 0;
		}
		 
	}
	
	@Override
	public void add_entry(Object data) throws SQLException {
			
		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.executeUpdate("INSERT INTO children (ref, first_name, last_name, age, booking) "
				+ "VALUES('" + att.getRef() + "', '" + att.getFirst_Name() + "', '" + att.getLast_Name()
				+ "', '" + att.getAge() + "', '" + att.getBooking() + "')");
			
		stat.close();
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM children WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE children SET ref='" + att.getRef() + "', first_name='" + att.getFirst_Name() 
				+ "', last_name='" + att.getLast_Name() + "', age='" + Integer.toString(att.getAge())
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
			
			stat.close();
			
			System.out.println("CREATE children DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("children")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE children");
			
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
