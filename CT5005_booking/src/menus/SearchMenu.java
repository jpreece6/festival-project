/**
 * @author Joshua Preece
 * @version 0.4
 * @desription Menus to search for attendees and bookings
 */
package menus;

import menuOptions.SearchOptions;

public class SearchMenu extends Menu {
	
	/**
	 * Displays the search menu
	 */
	public static void display_search_menu() {
		
		do {
			
			System.out.println("\n-- Search --");
			System.out.println("Find Attendee : " + SearchOptions.FIND_ATTENDEE.ordinal());
			System.out.println("Find Booking : " + SearchOptions.FIND_BOOKING.ordinal());
			System.out.println("Find Child : " + SearchOptions.FIND_CHILD.ordinal());
			System.out.println("Find Tent : " + SearchOptions.FIND_TENT.ordinal());
			System.out.println("Exit Menu : " + Menu.EXIT_MENU);
			
			choice = get_option();
			if (choice >= 0) {
				
				if (choice == Menu.EXIT_MENU) {
					return;
				}
				
				System.out.println("Category e.g. (ref, first_name, last_name, age, email_address, booking) : ");
				// Get the user to select a category
				String column = get_input();
				if (column.isEmpty() == false) {
				
					System.out.println("Search : ");
					
					String search = get_input();
					if (search.isEmpty() == false) {
						
					
						if (choice == SearchOptions.FIND_ATTENDEE.ordinal()) {
						
							amg.search_for_attendee(column, search);
							
						} else if (choice == SearchOptions.FIND_BOOKING.ordinal()) {
							
							bmg.search_for_booking(column, search);
							
						} else if (choice == SearchOptions.FIND_CHILD.ordinal()) {
							
							cmg.search_for_child(column, search);
							
						} else if (choice == SearchOptions.FIND_TENT.ordinal()) {
							
							// TODO
							
						}
					}
				}
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
