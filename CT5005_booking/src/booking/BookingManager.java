/**
 * @author Joshua Preece
 * @version 1.5
 * Handles the database functions for the bookings table
 */
package booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import prices.Price_Entry;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;

public class BookingManager implements IDatabaseFunctions {

	private static Booking bok;

	/**
	 * Create a new booking in the database
	 * @param attendee_ref String attendee ref to set as booker
	 * @param price_entry String price entry of this booking
	 * @return Booking 
	 */
	public Booking create_booking(String attendee_ref, Price_Entry price_entry) {
		
		try {
			
			String ref = "";
			
			assert attendee_ref.isEmpty() == false : "Attendee ref not set";
			
			bok = new Booking();
			bok.setBooker(attendee_ref);
			bok.setValid_Day(price_entry);
			
			// Check if the attendee exists
			if (DatabaseManager.does_entry_exist("attendees", "ref", attendee_ref)) {
				
				ref = DatabaseManager.generate_random_id();
				bok.setRef(ref);
				
				// check for a booking with a booker 
				if (DatabaseManager.does_entry_exist("bookings", "booker", bok.getBooker()) == false) {
					
					add_entry(bok);

					System.out.println("Booking created...");
					return bok;
					
				} else {
					
					System.out.println("Attendee is already has a booking...");
					return null;
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find attendee. Please create an attendee or check the ref");
				return null;
				
			}
			
			
			
		} catch (SQLException ex) {
			ErrorLog.printError("Add booking failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return null;
		}
		
	}
	
	/**
	 * Delete a booking from the database
	 * @param booking_ref String ref of the booking to remove
	 */
	public void delete_booking(String booking_ref) {

		try {
			
			assert booking_ref.isEmpty() == false : "Could not find booking ref to delete booking";
			
			// Check if the booking exists before removing it
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				remove_entry(booking_ref);
				System.out.println("Booking removed...");
				
			} else {
				
				System.out.println("Booking does not exist. Please check the booking Ref!");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Remove booking failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Edit a booking in the database
	 * @param book Booking object which contains all the changes
	 */
	public void edit_booking(Booking book) {
		
		try {
			
			assert bok != null : "Was not assigned booking to edit";
			
			// Check booking exists before updating
			if (DatabaseManager.does_entry_exist("bookings", "ref", book.getRef())) {
				
				// TODO check if booker COMPARE
				update_entry(book);
				System.out.println("Booking updated!");
				
			} else {
				
				System.out.println("Could not find booking to edit. Please check Ref!");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not edit booking!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Retrieve a booking from the database
	 * @param booking_ref String booking to retrieve
	 * @return Booking object
	 */
	public Booking getBooking(String booking_ref) {
		
		assert booking_ref.isEmpty() == false : "Booking ref not provided to get booking";
		
		try {
			
			// Check booking exists before retrieving
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				return (Booking)get_item(booking_ref);
			
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref");
				return null;
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not retrieve booking. Please check ref\n" + ex.getMessage(), ErrorLog.SEVERITY_LOW);
			return null;
		}
	}
	
	/**
	 * Check if booking exists
	 * @param booking_ref String ref of booking to check for
	 * @return Returns true if if booking is found in the database false if not
	 */
	public boolean does_booking_exist(String booking_ref) {
		
		assert booking_ref.isEmpty() == false : "No booking ref given to check for existance";
		
		try {
			
			return DatabaseManager.does_entry_exist("bookings", "ref", booking_ref);
			
		} catch (SQLException ex) {
			ErrorLog.printError("Check for booking exists failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return false;
		}
		
	}
	
	/**
	 * Search for a booking within the database
	 * @param column String column to search in
	 * @param data String data to search for
	 */
	public void search_for_booking(String column, String data) {
		
		assert column.isEmpty() == false : "No column given";
		assert data.isEmpty() == false : "No query given";
		
		try {
			
			DatabaseManager.print_results("Booking Search Results", DatabaseManager.search_database("bookings", column, data));
			
		} catch (SQLException e) {
			ErrorLog.printError(e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Print all attendees that are assigned to this booking
	 * @param booking_ref String booking to print from
	 */
	public void print_all_attendees_attached(String booking_ref) {
		
		assert booking_ref.isEmpty() == false : "No booking ref given to print attendees";
		
		try {
			
			DatabaseManager.print_results("Booking Attendees", DatabaseManager.search_database("attendees", "booking", booking_ref));
			DatabaseManager.print_results("Booking Children", DatabaseManager.search_database("children", "booking", booking_ref));
			
			
		} catch (SQLException ex) {
			ErrorLog.printError("Get all attendees failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Get the number of attendees that are assigned to this booking
	 * @param book Booking to count
	 * @return Integer number of attendees
	 */
	public int get_number_of_attendees(Booking book) {
		
		assert book != null : "No booking given to get number of attendees";
		
		try {
			
			ResultSet rs = DatabaseManager.search_database("attendees", "booking", book.getRef());
			
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
	
	/**
	 * Returns the total cost of a booking
	 * @param booking_ref String booking ref to calculate cost for
	 * @return String cost
	 */
	public int get_total_cost(String booking_ref) {
		
		assert booking_ref.isEmpty() == false : "No booking ref given for calculate cost";
		
		try {
			
			int total = 0;
			String valid = "";
			ResultSet rs;
			ResultSet valid_rs;
			ResultSet tent_rs;
			ResultSet cal_rs;
			
			// Does the booking exist
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				rs = DatabaseManager.search_database("bookings", "ref", booking_ref);
				
				// Get the bookings valid_day/Price_type
				if (rs.next()) {
					
					valid = rs.getString("valid_day");
					
				}
				
				// Make sure we we're able to get the valid_day/Price_type
				if (valid.isEmpty() == false) {
					
					valid_rs = DatabaseManager.search_database("prices", "type", valid);
					
					// Get the price of the valid_day/Price_type
					if (valid_rs.next()) {
						
						// Update total with the new price
						total += Integer.parseInt(valid_rs.getString("price"));
						

						tent_rs = DatabaseManager.search_database("prices", "type", "TENTS");
						
						// Get 
						if (tent_rs.next()) {
							
							cal_rs = DatabaseManager.search_database("tents", "booking", booking_ref);
							
							// If this booking has tents get their price and add to the total
							while (cal_rs.next()) {
								
								total += Integer.parseInt(tent_rs.getString("price"));
								
							}
							
							// Return the total cost
							return total;
							
						} else {
							ErrorLog.printError("Could not find prices for tents. Please check prices.", ErrorLog.SEVERITY_MEDIUM);
							return 0;
							
						}
						
					} else {
						ErrorLog.printInfo("Could not find booking valid day. Please check ref.");
						return total;
					}
					
				} else {
					
					ErrorLog.printInfo("Could not find booking valid day. Please check ref.");
					return 0;
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref.");
				return 0;
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not calculate and retrieve the total cost!\n" + ex.getMessage(),	ErrorLog.SEVERITY_MEDIUM);
			return 0;
		}
		
	}

	@Override
	public void add_entry(Object data) throws SQLException {
		
		bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("INSERT INTO bookings (ref, valid_day, booker) "
				+ "VALUES('" + bok.getRef() + "', '" + bok.getValid_Day().toString() + "', '" + bok.getBooker() + "')");
		
		stat.close();

	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM bookings WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE bookings SET ref='" + bok.getRef() + "', valid_day='" + bok.getValid_Day().toString() + "', booker='" + bok.getBooker()
				+ "' WHERE ref=" + bok.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("bookings")) {
			
			ErrorLog.printInfo("Table 'bookings' already exists");
			
		} else {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE bookings "
					+ "(ref varchar(10), valid_day varchar(20), booker varchar(10),"
					+ "PRIMARY KEY (ref))");
			
			stat.close();
			
			System.out.println("CREATE bookings DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("bookings")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE bookings");
			
			stat.close();
			
			System.out.println("DROP bookings DONE...");
			
		}
	
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		bok = new Booking();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM bookings WHERE ref=" + ref);
		
		if (result.next()) {
			
			// Assign the results to a new booking object to be returned
			bok.setRef(result.getString("ref"));
			bok.setValid_Day(Price_Entry.valueOf(result.getString("valid_day")));
			bok.setBooker(result.getString("booker"));
		
			return bok;
			
		}
		
		return null;
	}

	
}
