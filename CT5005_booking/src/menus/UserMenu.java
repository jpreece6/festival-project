/**
 * @author Joshua Preece
 * @vesion 1.1
 * @description Menus for the attendee's to register and create bookings
 */
package menus;

public class UserMenu extends Menu {

	public static void display_user_menu() {
		
		do {
			
			System.out.println("\n-- Attendee Menu --");
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
