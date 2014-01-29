package booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import accounts.Attendee;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;
import festival.Festival;

public class BookingManager implements IDatabaseFunctions {

	private static Booking bok;
	
	public BookingManager() {
		
		
	}
	
	public boolean create_booking() {
		
		return false;
		
	}
	
	public boolean delete_booking() {

		return false;
		
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

		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		/*
		stat.executeUpdate("INSERT INTO attendees (ref, name, age, email_address, booking) "
				+ "VALUES(ref_auto.nextval, '" + att.getName() 
				+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking().getRef() + "')");
		*/
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
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		/*
		stat.executeUpdate("UPDATE bookings SET name='" + bok.g + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking().getRef() + "' WHERE ref=" + att.getRef());
		*/
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
	
		Attendee att = new Attendee();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM booking WHERE ref=" + ref);
		
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
