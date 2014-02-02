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
					
					// TODO
					
				} else if (choice == UserMenuOptions.BOOKING_OPTIONS.ordinal()) {
					
					//TODO
					
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
		
		do {
			
			System.out.println("\n-- Attendee Options --");
			System.out.println("");
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_booking_options() {
		
		do {
			
			System.out.println("\n-- Booking Options --");
			System.out.println("");
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
