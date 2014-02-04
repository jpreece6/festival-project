/**
 * @author Joshua Preece
 * @version 1.2
 * Repeatable testing framework to test the festival application
 */
package testing;

import java.sql.SQLException;

import prices.Price_Entry;
import accounts.Attendee;
import booking.Booking;
import database.DatabaseManager;
import festival.ErrorLog;

public class Testharness {

	private static final String COL1 = "%-30s";
	private static final String COL2 = "%-30s";
	private static final String COL3 = "%-30s";
	private static final String COL4 = "%-10s";
	private static final String lineFormat = COL1 + COL2 + COL3 + COL4;
	
	static Booking booking = new Booking(Price_Entry.MONDAY);
	
	static Attendee[] simple_list = {
		
		new Attendee("josh", "preece", 19, "email@a.com"),
		new Attendee("havvanur", "ozkan", 18, "email@b.com")
		
	};
	
	static Attendee[] full_list = {
		
		new Attendee("havvanur", "ozkan", 18, "email@b.com"),
		new Attendee("ada", "test", 22, "email@c.com"),
		new Attendee("limestone", "linden", 40, "email@d.com"),
		new Attendee("snow", "little", 16, "email@e.com"),
		// TODO bug check if booking exists before assign
		new Attendee("ada", "test", 1, "email@c.com"),
		new Attendee("limestone", "linden", 11, "email@d.com"),
		
	};
	
	static Attendee[] overflow = {
		
		new Attendee("josh", "preece", 19, "email@a.com"),
		new Attendee("havvanur", "ozkan", 18, "email@b.com"),
		new Attendee("ada", "test", 22, "email@c.com"),
		new Attendee("limestone", "linden", 40, "email@d.com"),
		new Attendee("snow", "little", 16, "email@e.com"),
		
		new Attendee("ada", "test", 1, "email@c.com"),
		new Attendee("limestone", "linden", 2, "email@d.com"),
		new Attendee("snow", "little", 12, "email@e.com")
		
	};
	
	
	static Testcase[] sample = {
		
		new Testcase("Add a small number of attendees to a booking", booking, simple_list, 1),
		new Testcase("Add the max number of attendees to a booking", booking, full_list, 2),
		new Testcase("Add more than the maximum number of attendees", booking, overflow, 3)
		
	};
	
	public static void main(String[] args) {
		
		System.out.println("Test harness / repeatable testing framework\n\n");
		
		DatabaseManager db = new DatabaseManager("bin/database/database.properties");
		
		try {
			
			DatabaseManager.createConnection();
			
		} catch (SQLException ex) {
			ErrorLog.printError("Could not connect to the database!\n" + ex.getMessage(), ErrorLog.SEVERITY_CRITICAL);
		}
		
		System.out.println(">");
		
		Testcase tc;
		int number_of_testcases = 0;
	
		number_of_testcases = sample.length;
		for (int test_number = 0; test_number < number_of_testcases; test_number++) {
			
			tc = sample[test_number];
			
			System.out.println("-- Testcase " + test_number + " - " + tc.getDescription() + " --\n");
			
			// Drop the tables for a new testcase/testing environment
			System.out.println("-- Drop Tables --");
			if (tc.drop_tables()) {
				
				System.out.println("\n-- Create Tables --");
				// Create new tables
				if (tc.create_tables()) {
					
					System.out.println("\n-- Run Test Case --");
					// add first attendee
					Attendee[] list_attendee = tc.getAttendees();
					Attendee bookerAttendee = list_attendee[0];
					Attendee returnedAtt = new Attendee();
					
					// Create a new attendee to set as the booker
					returnedAtt = tc.add_attendee(bookerAttendee);
				
					// Create a new booking
					//Booking returnedBooking = new Booking();
					booking = tc.add_booking(returnedAtt.getRef());
					
					returnedAtt.setBooking(booking.getRef());
					tc.update_attendee(returnedAtt);
					
					
					
					// Process and add all attendees
					for (int i = 1; i < list_attendee.length; i++) {
						
						// Set all the attendees bookings to the new booking
						list_attendee[i].setBooking(booking.getRef());

						// Add each attendee to the database
						Attendee ar = tc.add_attendee(list_attendee[i]);
						ar.setBooking(booking.getRef());
						
						if (list_attendee[i].getAge() > 12) {
						
							tc.update_attendee(ar);
							
						} else {
							
							tc.update_child(ar);
							
						}
						
					}
					
					// Add tents
					for (int i = 0; i < tc.getNumber_of_tents(); i++) {
						
						tc.add_tent(booking);
						
					}
					
				
					System.out.printf(lineFormat, "Test Number", "No. Attendees", "No. Children", "No. Tents");
					System.out.println();
					System.out.printf(lineFormat, test_number, tc.get_number_of_attendees(booking),
							tc.get_number_of_children(booking), tc.get_number_of_tents(booking));
					
					
				} else {
					
					// Stop the testcase as the tables cannot be created
					System.out.println("\n!-- STOP Testcase unrecoverable error. Cannot create tables !--\n");
					break;
				}
				
			} else {
				
				// Stop the testcase as the tables cannot be dropped
				System.out.println("\n!-- STOP Testcase unrecoverable error. Cannot drop tables !--\n");
				break;
				
			}
			
			System.out.println("\n\n-- Testcase " + test_number + " - END --\n");	
			
		}
		
		print_errors();
		print_warnings();

	}
	
	/**
	 * Print the number of errors found while testing
	 */
	private static void print_errors() {
		
		if (ErrorLog.get_number_of_errors() == 0) {
			
			System.out.println("\n-- No errors found --");
			
		} else {
			
			System.out.println("\n-- A number of errors were found = " + ErrorLog.get_number_of_errors() + " --");
			
		}
		
	}
	
	/**
	 * Print the number of warnings found while testing
	 */
	private static void print_warnings() {
		
		if (ErrorLog.get_number_of_warnings() > 0) {
			
			System.out.println("\n-- A number of warnings were generated = " + ErrorLog.get_number_of_warnings() + " --");
			
		}
		
	}

}
