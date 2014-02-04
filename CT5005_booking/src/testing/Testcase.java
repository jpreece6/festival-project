/**
 * @author Joshua Preece
 * @version 1.3
 * Test case object to aid in testing the festival application 
 */
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
	private int num_of_tents = 0;
	
	/**
	 * Create a bew test case object
	 * @param description String description of the purpose of the test case
	 * @param booking Booking booking to test the attendees against
	 * @param attendees Array of Attendees to test with
	 */
	public Testcase(String description, Booking booking, Attendee[] attendees, int number_of_tents) {
		this.description = description;
		this.test_booking = booking;
		this.list_attendees = attendees;
		this.num_of_tents = number_of_tents;
	}
	
	/**
	 * Get the test case description
	 * @return String description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Get the number of tents for this test case
	 * @return Integer number of tents
	 */
	public int getNumber_of_tents() {
		return num_of_tents;
	}
	
	/**
	 * Gets the array of attendees for testing
	 * @return Array of attendees
	 */
	public Attendee[] getAttendees() {
		return this.list_attendees;
	}
	
	/**
	 * Gets the number of attndees attached to the booking
	 * @param booking Booking booking to check
	 * @return Integer count of attendees
	 */
	public int get_number_of_attendees(Booking booking) {
		
		return bmg.get_number_of_attendees(booking);
		
	}
	
	/**
	 * Gets the number of children assigned to this booking
	 * @param booking Booking to check
	 * @return Integer number of children
	 */
	public int get_number_of_children(Booking booking) {
		
		return cmg.get_number_of_children(booking);
		
	}
	
	// TODO
	public int get_number_of_tents(Booking booking) {
		
		return tmg.get_number_of_tents(booking.getRef());
		
	}
	
	/**
	 * Add an attendee to the booking
	 * @param att Attendee to add
	 * @return Attendee to process
	 */
	public Attendee add_attendee(Attendee att) {
		
		Attendee nAtt = new Attendee();
		nAtt = amg.create_attendee(att);
		
		return nAtt;
	}
	
	/**
	 * Update an attendee
	 * @param att Attendee to update
	 */
	public void update_attendee(Attendee att) {
		
		amg.update_attendee(att);
		
	}
	
	/**
	 * Update a child
	 * @param att Attendee child to update
	 */
	public void update_child(Attendee att) {
		
		cmg.update_child(att);
	}
	
	/**
	 * Add a booking to the database
	 * @param attendee_ref String ref of the attendee making the booking
	 * @return 
	 */
	public Booking add_booking(String attendee_ref) {
		
		Booking bok;
		bok = bmg.create_booking(attendee_ref, test_booking.getValid_Day());
		
		return bok;
	}
	
	public void add_tent(Booking book) {
		
		tmg.add_tent(book.getRef());
		System.out.println("Tent added successfully...");
		
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
