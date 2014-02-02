package menus;

import prices.Price_Entry;
import booking.Booking;

public class BookingMenu extends Menu {
	
	public static void display_delete_booking() {
		
		do {
			
			System.out.println("\n-- Delete Booking --");
			System.out.println("Booking Ref :  ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				bmg.delete_booking(input);
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
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
	
	public static void display_edit_booking() {
		
		final int EDIT_DAYS = 1;
		final int EDIT_ADD_TENT = 2;
		final int EDIT_REMOVE_TENT = 3;
		final int LIST_TENTS = 4;
		
		Booking bok = new Booking();
		
		do {
			
			System.out.println("\n-- Edit Booking --");
			System.out.println("Booking Ref :");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				bok = bmg.getBooking(input);
			
				System.out.println("Change Days : " + EDIT_DAYS);
				System.out.println("Add a tent : " + EDIT_ADD_TENT);
				System.out.println("Remove a tent : " + EDIT_REMOVE_TENT);
				System.out.println("List all tents : " + LIST_TENTS);
				
				choice = get_option();
				if (choice > 0) {
					
					switch (choice) {
					
					case EDIT_DAYS :
						
						pmg.list_price_types(null);
						System.out.println("Select new price entry : ");
						
						int entry = get_option();
						if (entry >= 0 && entry <= 9) {
							
							bok.setValid_Day(Price_Entry.values()[entry]);
							bmg.edit_booking(bok);
							
						}
						
						break;
					case EDIT_ADD_TENT :
						
						System.out.println("Adding Tent...");
						tmg.add_tent(bok.getRef());
						
						break;
					case EDIT_REMOVE_TENT:
						
						System.out.println("Removing Tent...");
						System.out.println("Space Number : ");
						
						input = get_input();
						if (input.isEmpty() == false) {
							
							tmg.remove_tent(input);
							
						}
						
						break;
					case LIST_TENTS :
						
						tmg.list_tents(input);
						
						break;
					
					}
					
					
				}
			}
			
			Menu.menu_end();
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
		
	}
	
	public static void display_booking_cost() {
		
		do {
			
			System.out.println("\n-- Booking Cost --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				System.out.println("Total : £" + bmg.get_total_cost(input));
				
				Menu.menu_end();
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
}
