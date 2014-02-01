package booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import prices.Days;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;

public class BookingManager implements IDatabaseFunctions {

	private static Booking bok;
	
	public BookingManager() {
		
		
	}

	public void create_booking(String attendee_ref) {
		
		try {
			
			bok = new Booking();
			bok.setBooker(attendee_ref);
			
			// check for a booking with a booker 
			if (DatabaseManager.does_entry_exist("bookings", "booker", bok.getBooker())) {
				add_entry(bok);
			}
			
			System.out.println("Booking created...");
			
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
			
			remove_entry(booking_ref);
			
			System.out.println("Booking removed...");
			
		} catch (SQLException ex) {
			ErrorLog.printError("Remove booking failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
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
	public void print_booking_details(Booking booking) {
		
		try {
			
			DatabaseManager.print_results("Booking Details", DatabaseManager.search_database("bookings", "ref", booking.getRef()));
			DatabaseManager.print_results("", null);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Search for a booking in the database
	 * @param column String column to search in
	 * @param data String data to search for
	 */
	public void search_for_booking(String column, String data) {
		
		try {
			
			DatabaseManager.search_database("bookings", column, data);
			
		} catch (SQLException e) {
			ErrorLog.printError(e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void get_all_attendees_attached(String attendee_ref) {
		
		try {
			
			ResultSet rs = DatabaseManager.search_database("attendees", "ref", attendee_ref);
			
			while (rs.next()) {
				// add to group
			}
			
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

	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("INSERT INTO attendees (ref, valid_day) "
				+ "VALUES(ref_auto.nextval, '" + bok.getValid_Day().ordinal() + "')");
		
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
		
		
		stat.executeUpdate("UPDATE bookings SET valid_day='" + bok.getValid_Day() 
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
					+ "(ref varchar(10), valid_day varchar(20),"
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
			bok.setValid_Day(Days.valueOf(result.getString("valid_day")));
		
			return bok;
			
		}
		
		return null;
	}

	
}
