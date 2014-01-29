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
	
	public void create_booking() {
		
		try {
			
			bok = new Booking();
			add_entry(bok);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void delete_booking(String booking_ref) {

		try {
			
			remove_entry(booking_ref);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void print_booking_details() {
		
		
		
	}
	
	public void search_for_booking(String column, String data) {
		
		try {
			
			DatabaseManager.search_database("bookings", column, data);
			
		} catch (SQLException e) {
			ErrorLog.printError(e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
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
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE bookings "
				+ "(ref varchar(10), valid_day varchar(20),"
				+ "PRIMARY KEY (ref))");
		
		stat.execute("CREATE SEQUENCE ref_book_auto START WITH 1"
				+ " INCREMENT BY 1 NOMAXVALUE");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE booking");
		
		stat.execute("DROP SEQUENCE ref_book_auto");
		
		stat.close();
	
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
