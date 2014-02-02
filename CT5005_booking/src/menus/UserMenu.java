/**
 * @author Joshua Preece
 * @vesion 1.1
 * @description Menus for the attendee's to register and create bookings
 */
package menus;

import festival.ErrorLog;
import menuOptions.UserMenuOptions;

public class UserMenu extends Menu {

	public static void display_user_menu() {
		
		do {
			
			System.out.println("\n-- Attendee Menu --");
			System.out.println("Register as an attendee : " + UserMenuOptions.REGISTER_ATTENDEE.ordinal());
			System.out.println("Create a booking : " + UserMenuOptions.CREATE_BOOKING.ordinal());
			System.out.println();
			System.out.println("Attendee Options : " + UserMenuOptions.ATTENDEE_OPTIONS.ordinal());
			System.out.println("Boooking Options : " + UserMenuOptions.BOOKING_OPTIONS.ordinal());
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
			choice = get_option();
			if (choice >= 0) {
				
				if (choice == UserMenuOptions.REGISTER_ATTENDEE.ordinal()) {
					
					// TODO
					
				} else if (choice == UserMenuOptions.CREATE_BOOKING.ordinal()) {
					
					// TODO
					
				} else if (choice == UserMenuOptions.ATTENDEE_OPTIONS.ordinal()) {
					
					display_attendee_options();
					
				} else if (choice == UserMenuOptions.BOOKING_OPTIONS.ordinal()) {
					
					display_booking_options();
					
				} else if (choice == Menu.EXIT_MENU) {
					
					Menu.menu_end();
					
				} else {
					
					ErrorLog.printInfo("Please enter a valid option");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Please enter a valid option");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_attendee_options() {
		
		final int PRINT_DETAILS = 1;
		final int EDIT_ATTENDEE = 2;
		final int REMOVE_ATTENDEE = 3;
		
		do {
			
			System.out.println("\n-- Attendee Options --");
			System.out.println("Print Attendee Details : " + PRINT_DETAILS);
			System.out.println("Edit Attendee : " + EDIT_ATTENDEE);
			System.out.println("Remove Attendee : " + REMOVE_ATTENDEE);
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
			choice = get_option();
			if (choice >= 0) {
				
				switch (choice) {
				
				case PRINT_DETAILS :
					
					
					
					break;
				case EDIT_ATTENDEE :
					
					
					
					break;
				case REMOVE_ATTENDEE :
					
					
					
					break;
				case Menu.EXIT_MENU :
					
					Menu.menu_end();
					break;
				}
				
			} else {
				
				ErrorLog.printInfo("Please enter a valid option");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_booking_options() {
		
		final int PRINT_DETAILS = 1;
		final int EDIT_BOOKING = 2;
		final int REMOVE_BOOKING = 3;
		final int GET_BOOKING_COST = 4;
		final int LIST_BOOKING_ATTENDEES = 5;
		final int LIST_BOOKING_TENTS = 6;
		
		do {
			
			System.out.println("\n-- Booking Options --");
			System.out.println("Print Booking Details : " + PRINT_DETAILS);
			System.out.println("Edit Booking : " + EDIT_BOOKING);
			System.out.println("Remove Booking : " + REMOVE_BOOKING);
			System.out.println("Get Booking Cost : " + GET_BOOKING_COST);
			System.out.println("List Booking Attendees : " + LIST_BOOKING_ATTENDEES);
			System.out.println("List Booking Tents : " + LIST_BOOKING_TENTS);
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
			choice = get_option();
			if (choice >= 0) {
				
				switch (choice) {
				
				case PRINT_DETAILS :
					
					
					
					break;
				case EDIT_BOOKING :
					
					
					
					break;
				case REMOVE_BOOKING :
					
					
					
					break;
				case GET_BOOKING_COST :
					
					
					
					break;
				case LIST_BOOKING_ATTENDEES :
					
					
					
					break;
				case LIST_BOOKING_TENTS :
					
					
					
					break;
				case Menu.EXIT_MENU :
					
					Menu.menu_end();
					break;
				}
				
			} else {
				
				ErrorLog.printInfo("Please enter a valid option");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_child_options() {
		
		do {
			
			System.out.println("\n-- Child Options --");
			System.out.println("Print Child Details : " );
			System.out.println("Edit Child : " );
			System.out.println("Remove Chlid : ");
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
