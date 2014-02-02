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
	
	public BookingManager() {
		
		
	}

	public void create_booking(String attendee_ref, Price_Entry price_entry) {
		
		try {
			
			bok = new Booking();
			bok.setBooker(attendee_ref);
			bok.setValid_Day(price_entry);
			
			// Check if the attendee exists
			if (DatabaseManager.does_entry_exist("attendees", "ref", attendee_ref)) {
				
				// check for a booking with a booker 
				if (DatabaseManager.does_entry_exist("bookings", "booker", bok.getBooker()) == false) {
					
					add_entry(bok);
					// TODO Update booker booking --
					System.out.println("Booking created...");
					
				} else {
					
					System.out.println("Attendee is already has a booking...");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find attendee. Please create an attendee or check the ref");
				
			}
			
			
			
		} catch (SQLException ex) {
			ErrorLog.printError("Add booking failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Delete a booking from the database
	 * @param booking_ref String ref of the booking to remove
	 */
	public void delete_booking(String booking_ref) {

		try {
			
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
	
	public void edit_booking(Booking book) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("bookings", "ref", book.getRef())) {
				
				update_entry(book);
				System.out.println("Booking updated!");
				
			} else {
				
				System.out.println("Could not find booking to edit. Please check Ref!");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not edit booking!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public Booking getBooking(String booker_ref) {
		
		try {
			
			return (Booking)get_item(booker_ref);
			
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
		
		try {
			
			return DatabaseManager.does_entry_exist("bookings", "ref", booking_ref);
			
		} catch (SQLException ex) {
			ErrorLog.printError("Check for booking exists failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return false;
		}
		
	}
	
	/**
	 * Prints the details of a booking stored in the database
	 * @param booking 
	 */
	/*public void print_booking_details(Booking booking) {
		
		try {
			
			DatabaseManager.print_results("Booking Details", DatabaseManager.search_database("bookings", "ref", booking.getRef()));
			DatabaseManager.print_results("", null);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}*/
	
	/**
	 * Search for a booking in the database
	 * @param column String column to search in
	 * @param data String data to search for
	 */
	public void search_for_booking(String column, String data) {
		
		try {
			
			DatabaseManager.print_results("Booking Search Results", DatabaseManager.search_database("bookings", column, data));
			
		} catch (SQLException e) {
			ErrorLog.printError(e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void print_all_attendees_attached(String booking_ref) {
		
		try {
			
			DatabaseManager.print_results("Booking Attendees", DatabaseManager.search_database("attendees", "booking", booking_ref));
			DatabaseManager.print_results("Booking Children", DatabaseManager.search_database("children", "booking", booking_ref));
			
			
		} catch (SQLException ex) {
			ErrorLog.printError("Get all attendees failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public int get_number_of_attendees(Booking book) {
		
		bok = book;
		
		try {
			
			ResultSet rs = DatabaseManager.search_database("attendees", "booking", bok.getBooker());
			
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
	public String get_total_cost(String booking_ref) {
		
		try {
			
			int total = 0;
			String valid = "";
			ResultSet rs;
			
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				rs = DatabaseManager.search_database("bookings", "ref", booking_ref);
				
				if (rs.next()) {
					
					valid = rs.getString("valid_day");
					
				}
				
				if (valid.isEmpty() == false) {
					
					rs = DatabaseManager.search_database("prices", "type", valid);
					
					if (rs.next()) {
						
						total += Integer.parseInt(rs.getString("price"));
						
					}
						
					if (DatabaseManager.does_entry_exist("tents", "booking", booking_ref)) {
						
						rs = DatabaseManager.search_database("prices", "type", "TENTS");
						
						if (rs.next()) {
							
							total += Integer.parseInt(rs.getString("price"));
							return Integer.toString(total);
							
						}
						
					} else {
						
						return Integer.toString(total);
						
					}
					
				}
				
				ErrorLog.printInfo("Could not find booking valid day. Please check ref.");
				return "?";
				
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref.");
				return "?";
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not calculate and retrieve the total cost!\n" + ex.getMessage(),	ErrorLog.SEVERITY_MEDIUM);
			return "Error";
		}
		
	}

	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("INSERT INTO bookings (ref, valid_day, booker) "
				+ "VALUES(ref_book_auto.nextval, '" + bok.getValid_Day().toString() + "', '" + bok.getBooker() + "')");
		
		stat.close();
		return true;

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
		
		
		stat.executeUpdate("UPDATE bookings SET valid_day='" + bok.getValid_Day().toString() + "', booker='" + bok.getBooker()
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
			
			stat.execute("CREATE SEQUENCE ref_book_auto START WITH 1"
					+ " INCREMENT BY 1 NOMAXVALUE");
			
			stat.close();
			
			System.out.println("CREATE bookings DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("bookings")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE bookings");
			
			stat.execute("DROP SEQUENCE ref_book_auto");
			
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
			
			bok.setRef(result.getString("ref"));
			bok.setValid_Day(Price_Entry.valueOf(result.getString("valid_day")));
			bok.setBooker(result.getString("booker"));
		
			return bok;
			
		}
		
		return null;
	}

	
}
