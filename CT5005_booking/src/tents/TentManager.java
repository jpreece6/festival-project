package tents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import accounts.Attendee;
import booking.Booking;
import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.Festival;

public class TentManager implements IDatabaseFunctions {

	private static Tent tnt;
	
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		int count = DatabaseManager.count_items("tents");
		if (count <= Festival.MAX_TENTS) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			/*	
			stat.executeUpdate("INSERT INTO attendees (space_no, booking) "
					+ "VALUES(ref_auto.nextval, '" + att.getName() 
					+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking().getRef() + "')");
			*/
			stat.close();
			return true;
		}
		
		System.out.println("No available booking space");
		return false;
	}

	@Override
	public void remove_entry(String space) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM tents WHERE sapce_no=" + space);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		/*
		stat.executeUpdate("UPDATE attendees SET name='" + att.getName() + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking().getRef() + "' WHERE ref=" + att.getRef());
		*/
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE tents "
				+ "(space_no varchar(4), booking varchar(10),"
				+ "PRIMARY KEY (ref))");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE prices");
		
		stat.close();
	
	}
	
	@Override
	public Object get_item(String ref) throws SQLException {
	
		Attendee att = new Attendee();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM attendees WHERE ref=" + ref);
		
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
