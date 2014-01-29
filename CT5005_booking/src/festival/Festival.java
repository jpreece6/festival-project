package festival;

import java.sql.SQLException;

import menus.StaffMenu;
import booking.BookingManager;
import database.DatabaseManager;

public class Festival {

	private static final String APP_NAME = "Festival Booking";
	private static final String VERSION = "0.1";
	
	public static final int MAX_ATTENDEES = 4000;
	public static final int MAX_TENTS = 400;
	
	public static void main(String[] args) {

		new Festival();
		
	}
	
	public Festival() {
		
		System.out.println(APP_NAME + " " + VERSION);
		
		DatabaseManager mgr = new DatabaseManager("bin/database/database.properties");
		BookingManager bok = new BookingManager();
		
		try {
			DatabaseManager.createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		StaffMenu.display_menu();
		
	}

}
