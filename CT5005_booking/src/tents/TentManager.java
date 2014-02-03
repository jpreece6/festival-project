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
		
		
	}
	
	public void add_tent(String booking_ref) {
		
		try {
			
			tnt = new Tent();
			
			// Ensure that a booking exists
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				// Ensure that the booking does not have more than 2 tents already
				if (DatabaseManager.count_specific_items("tents", "booking", booking_ref) < 2) {
					
					if (DatabaseManager.count_items("tents") <= Festival.MAX_TENTS) {
						
						tnt.set_booking_ref(booking_ref);
						add_entry(tnt);
					
					} else {
						
						ErrorLog.printInfo("Maximum number of tents allocated");
						
					}
					
				} else {
					
					ErrorLog.printInfo("Booking already has the maximum number of assigned tents");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Add tent failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void remove_tent(String space_no) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("tents", "space_no", space_no)) {
			
				remove_entry(space_no);
			
			} else {
				
				ErrorLog.printInfo("Could not find tent space. Please check space number");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Remove tent failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void remove_all_tents(String booking_ref) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
				
				ResultSet rs = DatabaseManager.search_database("tents", "booking", booking_ref);
				
				while (rs.next()) {
					
					remove_tent(rs.getString("space_no"));
					
				}
				
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not delete all tents!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void list_tents(String booking_ref) {
		
		try {

			if (DatabaseManager.does_entry_exist("bookings", "ref", booking_ref)) {
			
				DatabaseManager.print_results("Tent List Result", DatabaseManager.search_database("tents", "booking", booking_ref));
			
			} else {
				
				ErrorLog.printInfo("Could not find booking. Please check ref");
				
			}
		} catch (SQLException ex) {
			ErrorLog.printError("Print tents failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public int get_number_of_tents(String booking_ref){
		
		try {
			
			if (DatabaseManager.does_entry_exist("tents", "booking", booking_ref)) {
				
				return DatabaseManager.count_specific_items("tents", "booking", booking_ref);
				
			} else {
				
				return 0;
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not count number of tents!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return 0;
		}
		
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
			
		stat.execute("DELETE FROM tents WHERE space_no=" + space);

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
		
		if (DatabaseManager.does_table_exist("tents")) {
			
			ErrorLog.printInfo("Table 'tents' already exists");
			
		} else {
		
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE tents "
					+ "(space_no varchar(4), booking varchar(10),"
					+ "PRIMARY KEY (space_no))");
			
			stat.execute("CREATE SEQUENCE ref_tent_auto START WITH 1"
					+ " INCREMENT BY 1 NOMAXVALUE");
			
			stat.close();
			
			System.out.println("CREATE tents DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("tents")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE tents");
			
			stat.execute("DROP SEQUENCE ref_tent_auto");
			
			stat.close();
			
			System.out.println("DROP tents DONE...");
			
		}
	
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
