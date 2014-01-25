package menus;


public class AttendeeMenu extends Menu {
	
	protected static void display_menu() {

		do {
			
			clear_screen();
			
			System.out.println("Get booking : " + AttendeeMenuOptions.GET_BOOKING.ordinal());
			System.out.println("Create booking : " + AttendeeMenuOptions.CREATE_BOOKING.ordinal());
			System.out.println("Delete booking : " + AttendeeMenuOptions.DELETE_BOOKING.ordinal());
			System.out.println("Add member to group : " + AttendeeMenuOptions.ADD_GROUP_MEMBER.ordinal());
			System.out.println("Exit : 0");
			
			choice = get_option();
			if (choice > 0 && choice <= 4) {
				
				if (choice == AttendeeMenuOptions.GET_BOOKING.ordinal()) {
					
					
					
				} else if (choice == AttendeeMenuOptions.CREATE_BOOKING.ordinal()) {
					
					// Create a new booking
					
				} else if (choice == AttendeeMenuOptions.DELETE_BOOKING.ordinal()) {
					
					// Delete a booking
					
				} else if (choice == AttendeeMenuOptions.ADD_GROUP_MEMBER.ordinal()) {
					
					// Add a member to the 
					
				}
				
			}
			
			
			
		} while (exit_menu == false);
		
	}
	
	
	
	
}
