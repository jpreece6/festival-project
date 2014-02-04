package testing;

import java.sql.SQLException;

import festival.ErrorLog;
import prices.PricesManager;
import tents.TentManager;
import accounts.Attendee;
import accounts.AttendeeManager;
import accounts.ChildManager;
import booking.Booking;
import booking.BookingManager;

public class Testcase {
	
	private static AttendeeManager amg = new AttendeeManager();
	private static BookingManager bmg = new BookingManager();
	private static ChildManager cmg = new ChildManager();
	private static PricesManager pmg = new PricesManager();
	private static TentManager tmg = new TentManager();
	
	private String description;
	private Booking test_booking;
	private Attendee[] list_attendees;
	
	private int totalExpected = 0;
	private boolean isFull = false;
	private boolean isEmpty = true;
	
	public Testcase(String description, Booking booking, Attendee[] attendees) {
		this.description = description;
		this.test_booking = booking;
		this.list_attendees = attendees;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getTotalExpected() {
		return this.totalExpected;
	}
	
	public boolean isFull() {
		return this.isFull;
	}
	
	public boolean isEmpty() {
		return this.isEmpty;
	}
	
	public Attendee[] getAttendees() {
		return this.list_attendees;
	}
	
	public int get_number_of_attendees(Booking booking) {
		
		return bmg.get_number_of_attendees(booking);
		
	}
	
	public int get_number_of_children(Booking booking) {
		
		return cmg.get_number_of_children(booking);
		
	}
	
	public int get_number_of_tents(Booking booking) {
		
		return tmg.get_number_of_tents(booking.getRef());
		
	}
	
	public Attendee add_attendee(Attendee att) {
		
		Attendee nAtt = new Attendee();
		nAtt = amg.create_attendee(att);
		
		return nAtt;
	}
	
	public void update_attendee(Attendee att) {
		
		amg.update_attendee(att);
		
	}
	
	public void update_child(Attendee att) {
		
		cmg.update_child(att);
	}
	
	public Booking add_booking(String attendee_ref) {
		
		Booking bok;
		bok = bmg.create_booking(attendee_ref, test_booking.getValid_Day());
		
		return bok;
	}
	
	/**
	 * Drop the tables before performing the testcase 
	 */
	public boolean drop_tables() {
		
		try {
			
			amg.drop_table();
			bmg.drop_table();
			cmg.drop_table();
			pmg.drop_table();
			tmg.drop_table();
			
			return true;
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not drop tables!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);	
			return false;
		}
	}
	
	/**
	 * Create the tables before performing the testcase
	 */
	public boolean create_tables() {
		
		try {
			
			amg.create_table();
			bmg.create_table();
			cmg.create_table();
			pmg.create_table();
			tmg.create_table();
			return true;
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not create tables!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return false;
		}
	}
	
}
