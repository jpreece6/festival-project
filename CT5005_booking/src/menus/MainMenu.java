package menus;

import menuOptions.MainMenuOptions;

public class MainMenu extends Menu {
	
	public static void display_menu() {
		
		do {
			
			System.out.println("Create Booking : " + MainMenuOptions.REGISTER.ordinal());
			System.out.println("Attendee Login : " + MainMenuOptions.ATTENDEE_LOGIN.ordinal());
			System.out.println("Staff Login : " + MainMenuOptions.STAFF_LOGIN.ordinal());
			System.out.println("Exit : 4");
		
			choice = get_option();
			if (choice > 0 && choice <= 4) {
			
				if (choice == MainMenuOptions.REGISTER.ordinal()) {
					
					// Register a new attendee
					
				} else if (choice == MainMenuOptions.ATTENDEE_LOGIN.ordinal()) {
					
					// Display attendee login
					//Menu.display_login();
					
				} else if (choice == MainMenuOptions.STAFF_LOGIN.ordinal()) {
					
					// Display staff login
					
				} else {
					
					exit_menu = true;
					
				}
			
			}
			
		} while (exit_menu == false);
		
	}
	
	
}
