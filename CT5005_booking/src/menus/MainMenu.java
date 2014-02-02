package menus;

import menuOptions.MainMenuOptions;

public class MainMenu extends Menu {
	
	public static void display_menu() {
		
		do {
			
			System.out.println("\n-- Main Menu --");
			System.out.println("Attendee Menu : " + MainMenuOptions.ATTENDEE_LOGIN.ordinal());
			System.out.println("Staff Menu : " + MainMenuOptions.STAFF_LOGIN.ordinal());
			System.out.println("Exit : " + MainMenuOptions.EXIT_MENU.ordinal());
		
			choice = get_option();
			if (choice >= 0) {
			
				if (choice == MainMenuOptions.ATTENDEE_LOGIN.ordinal()) {
					
					// Opens the user menu for attendees
					UserMenu.display_user_menu();
					
				} else if (choice == MainMenuOptions.STAFF_LOGIN.ordinal()) {
					
					// Opens the staff menu for staff
					StaffMenu.display_menu();
					
				} else if (choice == MainMenuOptions.EXIT_MENU.ordinal()) {
					
					// Returns to main menu
					Menu.menu_end();
					
				}
			
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
}
