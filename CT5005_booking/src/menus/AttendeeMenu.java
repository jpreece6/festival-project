/**
 * @author Joshua Preece
 * @version 1.2
 * @description Menus used to manipulate attendees
 */
package menus;

import accounts.Attendee;
import festival.ErrorLog;


public class AttendeeMenu extends Menu {
	
	/**
	 * Displays the delete attendee menu
	 */
	public static void display_delete_attendee() {
		
		do {
			
			System.out.println("\n-- Delete Attendee -- ");
			System.out.println("Attendee Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				

				amg.remove_attendee(input);
				
				Menu.menu_end();
				
			} else {
				
				ErrorLog.printInfo("Please enter an attendee ref");
				
			}
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the menu to create a new attendee
	 */
	public static void display_create_attendee() {
		
		do {
			
			String first_name;
			String last_name;
			int age;
			String email;
			
			System.out.println("-- Create new attendee --\n");
			System.out.println("First Name : ");
			
			// Get the attendee's first name
			first_name = get_input();
			if (first_name.isEmpty() == false) {
				
				// Get the attendee's last name
				System.out.println("Last Name : ");
				last_name = get_input();
				if (last_name.isEmpty() == false) {
					
					// Get the attendee's age
					System.out.println("Age : ");
					age = Integer.parseInt(get_input());
					if (age > 0 && age < 100) {
						
						// Get the attendee's email address
						System.out.println("Email Address : ");
						email = get_input();
						if (email.isEmpty() == false && email.contains("@")) {
							
							amg.create_attendee(first_name, last_name, age, email);
							Menu.menu_end();
							
						}
						
					} else {
						
						ErrorLog.printInfo("Please enter an age above 0 and lower than 100");
						
					}
				} else {
					
					ErrorLog.printInfo("Please enter a last name");
					
				}
				
			} else {
				
				ErrorLog.printInfo("Please enter a first name");
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays the menu to edit attendees
	 */
	public static void display_edit_attendee() {
		
		final int EXIT_MENU = 0;
		final int EDIT_NAME = 1;
		final int EDIT_AGE = 2;
		final int EDIT_EMAIL = 3;
		final int EDIT_BOOKING = 4;
		
		Attendee att = new Attendee();
		
		String first_name;
		String last_name;
		
		do {
			
			System.out.println("\n-- Select Attendee --");
			System.out.println("Attendee Ref : ");
				
			String ref = get_input();
			// TODO assert
			//assert(ref.isEmpty()) : "Blah";
			if (ref.isEmpty() == false) {
				
				att = amg.get_attendee(ref);
			
				System.out.println("\n-- Edit Attendee --");
				System.out.println("Edit Name : " + EDIT_NAME);
				System.out.println("Edit Age : " + EDIT_AGE);
				System.out.println("Edit Email : " + EDIT_EMAIL);
				System.out.println("Edit Booking : " + EDIT_BOOKING);
				System.out.println("Exit Menu : " + Menu.EXIT_MENU);
				
				choice = get_option();
				if (choice > 0) {
					
					switch(choice) {
					
					case EDIT_NAME :
						
						// Get attendee's first name
						System.out.println("First Name : ");
						first_name = get_input();
						if (first_name.isEmpty() == false) {
							
							// Get attendee's last name
							System.out.println("Last Name : ");
							last_name = get_input();
							if (last_name.isEmpty() == false) {
								
								att.setFirst_Name(first_name);
								att.setLast_Name(last_name);
								
							}
							
						}
						
						break;
						
					case EDIT_AGE :
						
						// Get attendee's age
						System.out.println("Age : ");
						
						choice = get_option();
						if (choice > 0) {
							
							att.setAge(choice);
							
						}
						
						break;
						
					case EDIT_EMAIL :
						
						// Get attendee's email address
						System.out.println("Email : ");
						input = get_input();
						
						if (input.isEmpty() == false && input.contains("@")) {
							
							att.setEmailAddress(input);
							
						}
						
						break;
						
					case EDIT_BOOKING :
						
						// Get attendee's booking ref
						System.out.println("Booking Ref : ");
						input = get_input();
						
						if (input.isEmpty() == false) {
							
							if (bmg.does_booking_exist(input)) {
								
								att.setBooking(input);
								
							}
							
						}
						
						break;
					case EXIT_MENU :
						
						Menu.menu_end();
						break;
					}
				}
			
			} else {
				
				ErrorLog.printInfo("Please enter an attendee ref");
				
			}
			
			
		} while (exit_menu == false);
		
		// Apply changes
		amg.update_attendee(att.getRef());
		
		Menu.menu_reset();
		
	}
	
	/**
	 * Displays a list of attendees that are assigned to a booking ref
	 */
	public static void display_list_attendees() {
		
		do {
			
			System.out.println("\n-- Booking Attendees --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				bmg.print_all_attendees_attached(input);
				
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
			
		Menu.menu_reset();
		
	}
	
}
