package booking;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import accounts.Attendee;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.Festival;

public class BookingManager implements IDatabaseFunctions {

	public BookingManager() {
		
		
	}
	
	public boolean create_booking() {
		
		return false;
		
	}
	
	public boolean delete_booking() {

		return false;
		
	}

	@Override
	public void add_entry(Object data) throws SQLException {
		
		int count = count_items();
		if (count <= Festival.MAX_ATTENDEES) {
			
			Booking bok = (Booking)data;
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO bookings (ref, booker, cost) "
					+ "VALUES(ref_book_auto.nextval, '" + bok.getBooker() 
					+ "', 10)");
			
			stat.close();
		}
		
		System.out.println("No available booking space");
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM bookings WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Booking bok = (Booking)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE bookings SET VALUES(" + bok.getBooker() + ", "
				+ "10)");
		
		stat.close();
	}
	
	@Override
	public void search_database(String column, String data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM bookings WHERE " + column + "='" + data + "'");
		
		print_results(rs);
		
		stat.close();
		rs.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE bookings "
				+ "(ref varchar(10), booker varchar(100), cost int,"
				+ "PRIMARY KEY (ref), FOREIGN KEY (booker) REFERENCES attendees(ref))");
		
		stat.execute("CREATE SEQUENCE ref_book_auto START WITH 1"
				+ " INCREMENT BY 1 NOMAXVALUE");
		
		stat.close();
		
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE bookings");
		
		stat.execute("DROP SEQUENCE ref_book_auto");
		
		stat.close();
	}
	
	@Override
	public int count_items() throws  SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM bookings");
		
		if (rs.next()) {
			
			return rs.getInt(1);
			
		}
		
		return 0;
		
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		//Booking bok = new Booking();
		
		
		return null;
		
	}
	
	@Override
	public void print_results(ResultSet result) throws SQLException {
		
		ResultSetMetaData meta = result.getMetaData();
		
		System.out.println("-- Results --");
		final String FORMAT = "%-20s";
		
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			
			System.out.format(FORMAT, meta.getColumnName(i));
		
		}
		
		while (result.next()) {
			
			System.out.println();
			
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.format(FORMAT, result.getString(i));
			}
			
		}
		
		System.out.println();
		
	}

	
}
