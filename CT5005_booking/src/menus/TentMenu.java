/**
 * @author Joshua Preece
 * @version 0.1
 * Menus for tents
 */
package menus;

import festival.ErrorLog;

public class TentMenu extends Menu {

	/**
	 * Display the interface for tents
	 */
	public static void display_add_tent() {
		
		do {
			
			System.out.println("\n-- Add Tent --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				if (tmg.add_tent(input)) {
					
					System.out.println("-- Tent Added --\n");
					
				} else {
					
					System.out.println("Unable to add tent!");
					
				}
				
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter a valid option");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Display the interface to remove tents
	 */
	public static void display_remove_tent() {
		
		do {
			
			System.out.println("\n-- Remove Tent --");
			System.out.println("Space Number : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				tmg.remove_tent(input);
				System.out.println("-- Tent Removed --\n");
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter a valid option");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the interface to list all tents for a specific booking
	 */
	public static void display_list_tents() {
		
		do {
			
			System.out.println("\n-- Booking Tents --");
			System.out.println("Booking Ref :");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				tmg.list_tents(input);
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter a booking ref");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
	}
	
}
