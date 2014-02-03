/**
 * @author Joshua Preece
 * @version 0.4
 * Handles the staff menus
 */

package menus;

import menuOptions.StaffMenuOptions;
import festival.ErrorLog;

public class StaffMenu extends Menu {
	
	/**
	 * Displays the main staff menu interface
	 */
	public static void display_menu() {

		do {
			
			System.out.println("\n-- Staff Menu --");
			System.out.println("Create Attendee : " + StaffMenuOptions.CREATE_ATTENDEE.ordinal());
			System.out.println("Edit Attendee : " + StaffMenuOptions.EDIT_ATTENDEE.ordinal());
			System.out.println("Delete Attendee : " + StaffMenuOptions.DELETE_ATTENDEE.ordinal());
			System.out.println();
			System.out.println("Create Booking : " + StaffMenuOptions.CREATE_BOOKING.ordinal());
			System.out.println("Edit Booking : " + StaffMenuOptions.EDIT_BOOKING.ordinal());
			System.out.println("Delete Booking : " + StaffMenuOptions.DELETE_BOOKING.ordinal());
			System.out.println("Get Booking Cost : " + StaffMenuOptions.BOOKING_COST.ordinal());
			System.out.println("List Booking Attendees : " + StaffMenuOptions.LIST_BOOKING_ATTENDEES.ordinal());
			System.out.println();
			System.out.println("Search : " + StaffMenuOptions.SEARCH.ordinal());
			System.out.println();
			System.out.println("Create Tables : " + StaffMenuOptions.CREATE_TABLES.ordinal());
			System.out.println("Drop Tables : " + StaffMenuOptions.DROP_TABLES.ordinal());
			System.out.println();
			System.out.println("Set Prices : " + StaffMenuOptions.SET_PRICES.ordinal());
			System.out.println("Get Prices : " + StaffMenuOptions.GET_PRICES.ordinal());
			System.out.println();
			
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
			choice = get_option();
			if (choice >= 0) {
				
				if (choice == StaffMenuOptions.CREATE_ATTENDEE.ordinal()) {
					
					AttendeeMenu.display_create_attendee();
					
					
				} else if (choice == StaffMenuOptions.EDIT_ATTENDEE.ordinal()) {
					
					AttendeeMenu.display_edit_attendee();
					
				} else if (choice == StaffMenuOptions.DELETE_ATTENDEE.ordinal()) {
					
					AttendeeMenu.display_delete_attendee();
					
				} else if (choice == StaffMenuOptions.CREATE_BOOKING.ordinal()) {
					
					BookingMenu.display_create_booking();
					
				} else if (choice == StaffMenuOptions.EDIT_BOOKING.ordinal()) {
					
					BookingMenu.display_edit_booking();
					
				} else if (choice == StaffMenuOptions.DELETE_BOOKING.ordinal()) {
					
					BookingMenu.display_delete_booking();
					
				} else if (choice == StaffMenuOptions.BOOKING_COST.ordinal()) {
					
					BookingMenu.display_booking_cost();
					
				} else if (choice == StaffMenuOptions.LIST_BOOKING_ATTENDEES.ordinal()) {
					
					AttendeeMenu.display_list_attendees();
					
				} else if (choice == StaffMenuOptions.SEARCH.ordinal()) {
					
					SearchMenu.display_search_menu();
					
				} else if (choice == StaffMenuOptions.CREATE_TABLES.ordinal()) {
					
					TablesMenu.display_create_tables();
					
				} else if (choice == StaffMenuOptions.DROP_TABLES.ordinal()) {
					
					TablesMenu.display_drop_tables();
					
				} else if (choice == StaffMenuOptions.SET_PRICES.ordinal()) {
					
					PriceMenu.display_set_prices();
					
				} else if (choice == StaffMenuOptions.GET_PRICES.ordinal()) {
					
					PriceMenu.display_get_prices();
					
				} else if (choice == Menu.EXIT_MENU) {
					
					Menu.menu_end();
					
				} else {
					
					ErrorLog.printInfo("Please enter a valid option");
					
				}
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
