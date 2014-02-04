/**
 * @author Joshua Preece
 * @version 0.6
 * This is the main entry point to the program. This is a festival system which allows
 * attendees and staff members to book tickets to a festival.
 */

package festival;

import java.sql.SQLException;

import menus.MainMenu;
import menus.StaffMenu;
import booking.BookingManager;
import database.DatabaseManager;

public class Festival {

	private static final String APP_NAME = "Festival Booking";
	private static final String VERSION = "0.6";
	
	public static final int MAX_ATTENDEES = 2000;
	public static final int MAX_TENTS = 400;
	public static final int BOOKING_MAX_ATTENDEES = 4;
	public static final int BOOKING_MAX_CHILDREN = 2;
	public static final int BOOKING_MAX_TENTS = 2;
	
	public static void main(String[] args) {

		new Festival();
		
	}
	
	public Festival() {
		
		System.out.println(APP_NAME + " " + VERSION);
		
		// Get the database credentials from a properties file
		DatabaseManager mgr = new DatabaseManager("bin/database/database.properties");
		
		try {
			
			// Connect to the database
			DatabaseManager.createConnection();
			
		} catch (SQLException e) {
			ErrorLog.printError("Could not connect to the remote database. Please check you connection.\n"
					+ e.getMessage(), ErrorLog.SEVERITY_CRITICAL);
		}
	
		// Start the main menu
		MainMenu.display_menu();
		
	}

}
