/**
 * @author Joshua Preece
 * @version 0.2
 * @description Handles the methods used to manipulate bookings
 */
package menus;

import festival.ErrorLog;
import prices.Price_Entry;
import booking.Booking;

public class BookingMenu extends Menu {
	
	/**
	 * Displays the menu to delete a booking from the database
	 */
	public static void display_delete_booking() {
		
		do {
			
			System.out.println("\n-- Delete Booking --");
			System.out.println("Booking Ref :  ");
			
			// Get the booking ref
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Remove all tents assigned to this booking
				tmg.remove_all_tents(input);
				bmg.delete_booking(input);
			
			} else {
				
				ErrorLog.printInfo("Please enter a booking ref");
				
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the menu to create a new booking
	 */
	public static void display_create_booking() {
		
		do {
			
			System.out.println("\n-- Create Booking --");
			System.out.println("Attendee Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Exclude tents from the list as we don't want to store this in bookings
				String[] exclude = { "TENTS" };
				pmg.list_price_types(exclude);
				System.out.println("\nSelect Price_Entry : ");
				
				choice = get_option();
				if (choice >= 0 && choice <= 9) {
					
					// If booking created successfully update booker with booking ref
					if (bmg.create_booking(input, Price_Entry.values()[choice])); {
						
						// TODO update booking ref
					}
					
				}
				
			}
			
			Menu.menu_end();
					
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	/**
	 * Displays the menu to edit a booking
	 */
	public static void display_edit_booking() {
		
		final int EDIT_DAYS = 1;
		
		Booking bok = new Booking();
		
		do {
			
			System.out.println("\n-- Edit Booking --");
			System.out.println("Booking Ref :");
			
			// Get the booking ref
			input = get_input();
			if (input.isEmpty() == false) {
				
				bok = bmg.getBooking(input);
				
				if (bok == null) {
					return;
				}
			
				System.out.println("Change Price Type : " + EDIT_DAYS);
				System.out.println("Exit Menu : " + Menu.EXIT_MENU);
				
				choice = get_option();
				if (choice >= 0) {
					
					switch (choice) {
					
					// Edit the booking's day
					case EDIT_DAYS :
						
						pmg.list_price_types(null);
						System.out.println("Select new price entry : ");
						
						int entry = get_option();
						if (entry >= 0 && entry <= 9) {
							
							bok.setValid_Day(Price_Entry.values()[entry]);
							bmg.edit_booking(bok);
							
						}
						
						break;
					
					// Exit this menu
					case Menu.EXIT_MENU :
						
						Menu.menu_end();
						break;
					}
				}
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Display the total booking cost
	 */
	public static void display_booking_cost() {
		
		do {
			
			System.out.println("\n-- Booking Cost --");
			System.out.println("Booking Ref : ");
			
			// Get the booking ref
			input = get_input();
			if (input.isEmpty() == false) {
				
				System.out.println("Total : £" + bmg.get_total_cost(input));
				
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter a booking ref");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	public static void display_booking_details() {
		
		do {
			
			System.out.println("\n-- Booking Details --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				Booking bok = bmg.getBooking(input);
				if (bok != null) {
					
					System.out.println("Ref : " + bok.getRef());
					System.out.println("Price Type : " + bok.getValid_Day());
					System.out.println("Booker : " + bok.getBooker());
					System.out.println("Number of attendees : " + Integer.toString(bmg.get_number_of_attendees(bok)));
					System.out.println("Number of tents : " + Integer.toString(tmg.get_number_of_tents(bok.getRef())));
					System.out.println("-- End Booking Details --\n");
					
					Menu.menu_end();
					
				} else {
					
					ErrorLog.printInfo("Could not retrieve booking. Please check ref");
					
				}
				
				
			} else {
				
				ErrorLog.printInfo("Please enter a booking ref");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	

}
