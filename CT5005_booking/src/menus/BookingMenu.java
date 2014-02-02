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
						
						// update booking ref
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
		final int EDIT_ADD_TENT = 2;
		final int EDIT_REMOVE_TENT = 3;
		final int LIST_TENTS = 4;
		
		Booking bok = new Booking();
		
		do {
			
			System.out.println("\n-- Edit Booking --");
			System.out.println("Booking Ref :");
			
			// Get the booking ref
			input = get_input();
			if (input.isEmpty() == false) {
				
				bok = bmg.getBooking(input);
			
				System.out.println("Change Days : " + EDIT_DAYS);
				System.out.println("Add a tent : " + EDIT_ADD_TENT);
				System.out.println("Remove a tent : " + EDIT_REMOVE_TENT);
				System.out.println("List all tents : " + LIST_TENTS);
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
						
					// Add a tent to the booking
					case EDIT_ADD_TENT :
						
						System.out.println("Adding Tent...");
						tmg.add_tent(bok.getRef());
						
						break;
						
					// Remove a tent from the booking
					case EDIT_REMOVE_TENT:
						
						System.out.println("Removing Tent...");
						System.out.println("Space Number : ");
						
						input = get_input();
						if (input.isEmpty() == false) {
							
							tmg.remove_tent(input);
							
						}
						
						break;
						
					// List all tents assigned to this booking
					case LIST_TENTS :
						
						tmg.list_tents(input);
						
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
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	

}
