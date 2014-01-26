package prices;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import accounts.Attendee;
import booking.Booking;
import database.DatabaseManager;
import database.IDatabaseFunctions;

public class PricesManager implements IDatabaseFunctions {
	
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
			
		Statement stat = DatabaseManager.getConnection().createStatement();
			/*
		stat.executeUpdate("INSERT INTO prices (type, price) "
				+ "VALUES(, '" + att.getName() 
				+ "', '" + att.getAge() + "', '" + att.getEmailAddress() + "', '" + att.getBooking().getRef() + "')");
		
		stat.close();

		
		System.out.println("No available booking space");*/
		return false;
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM attendees WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Attendee att = (Attendee)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		att.toString();
		
		stat.executeUpdate("UPDATE attendees SET name='" + att.getName() + "', age='"
				+ Integer.toString(att.getAge()) + "', email_address='" + att.getEmailAddress() 
				+ "', booking='" + att.getBooking().getRef() + "' WHERE ref=" + att.getRef());
		
		stat.close();
		
	}
	
	@Override
	public void  search_database(String column, String data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM attendees WHERE " + column + "='" + data + "'");
		
		DatabaseManager.print_results(rs);
		
		stat.close();
		rs.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE prices "
				+ "(type varchar(20), price varchar(10),"
				+ "PRIMARY KEY (type))");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE attendees");
		
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
