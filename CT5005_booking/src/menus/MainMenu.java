/**
 * @author Joshua Preece
 * @version 0.3
 * Creates the main menu interface for users to use
 */
package menus;

import menuOptions.MainMenuOptions;

public class MainMenu extends Menu {
	
	/**
	 * Displays the main menu for users to navigate the app
	 */
	public static void display_menu() {
		
		do {
			
			System.out.println("\n-- Main Menu --");
			System.out.println("Attendee Menu : " + MainMenuOptions.ATTENDEE_LOGIN.ordinal());
			System.out.println("Staff Menu : " + MainMenuOptions.STAFF_LOGIN.ordinal());
			System.out.println("Search Menu : " + MainMenuOptions.SEARCH_OPTION.ordinal());
			System.out.println("Exit : " + Menu.EXIT_MENU);
		
			choice = get_option();
			if (choice >= 0) {
			
				if (choice == MainMenuOptions.ATTENDEE_LOGIN.ordinal()) {
					
					// Opens the user menu for attendees
					UserMenu.display_user_menu();
					
				} else if (choice == MainMenuOptions.STAFF_LOGIN.ordinal()) {
					
					// Opens the staff menu for staff
					StaffMenu.display_menu();
					
				} else if (choice == MainMenuOptions.SEARCH_OPTION.ordinal()) {
					
					// Opens the search interface
					SearchMenu.display_search_menu();
					
				} else if (choice == Menu.EXIT_MENU) {
					
					// Returns to main menu
					Menu.menu_end();
					
				}
			
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
}
