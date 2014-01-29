package tents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;
import festival.Festival;

public class TentManager implements IDatabaseFunctions {

	private static Tent tnt;
	
	public TentManager() {
		
		//tnt = new Tent();
		
	}
	
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		tnt = (Tent)data;
		
		int count = DatabaseManager.count_items("tents");
		if (count <= Festival.MAX_TENTS) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
				
			stat.executeUpdate("INSERT INTO tents (space_no, booking) "
					+ "VALUES(ref_tent_auto.nextval, '" + tnt.get_booking_ref() + "')");
			
			stat.close();
			return true;
		}
		
		ErrorLog.printInfo("No available tent spaces");
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
		
		tnt = (Tent)data;
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE tents SET booking='" + tnt.get_booking_ref() + "', age='"
				 + "' WHERE space_no=" + tnt.get_space_no());
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("CREATE TABLE tents "
				+ "(space_no varchar(4), booking varchar(10),"
				+ "PRIMARY KEY (ref))");
		
		stat.execute("CREATE SEQUENCE ref_tent_auto START WITH 1"
				+ " INCREMENT BY 1 NOMAXVALUE");
		
		stat.close();
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.execute("DROP TABLE tents");
		
		stat.execute("DROP SEQUENCE ref_tent_auto");
		
		stat.close();
	
	}
	
	@Override
	public Object get_item(String space_no) throws SQLException {
	
		tnt = new Tent();
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM tents WHERE space_no=" + space_no);
		
		if (result.next()) {
			
			tnt.set_space_no(result.getString("space_no"));
			tnt.set_booking_ref(result.getString("booking"));
		
			return tnt;
			
		}
		
		ErrorLog.printInfo("Could not find tent in database");
		return null;
	}

}
