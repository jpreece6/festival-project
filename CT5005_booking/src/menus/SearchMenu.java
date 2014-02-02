package menus;

import menuOptions.SearchOptions;

public class SearchMenu extends Menu {
	
	public static void display_search_menu() {
		
		do {
			
			System.out.println("\n-- Search --");
			System.out.println("Find Attendee : " + SearchOptions.FIND_ATTENDEE.ordinal());
			System.out.println("Find Booking : " + SearchOptions.FIND_BOOKING.ordinal());
			System.out.println("Find Child : " + SearchOptions.FIND_CHILD.ordinal());
			System.out.println("Find Tent : " + SearchOptions.FIND_TENT.ordinal());
			
			choice = get_option();
			if (choice >= 0) {
				
				System.out.println("Category e.g. (ref, first_name, last_name, age, email_address, booking) : ");
				
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
			
			menu_end();
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
}
