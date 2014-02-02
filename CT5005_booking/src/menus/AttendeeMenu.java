package menus;

import java.sql.SQLException;

import accounts.Attendee;
import festival.ErrorLog;


public class AttendeeMenu extends Menu {
	
	public static void display_delete_attendee() {
		
		do {
			
			System.out.println("\n-- Delete Attendee -- ");
			System.out.println("Attendee Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				

				amg.remove_attendee(input);
				
				menu_end();
				
			}
			
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	public static void display_create_attendee() {
		
		do {
			
			String first_name;
			String last_name;
			int age;
			String email;
			
			System.out.println("-- Create new attendee --\n");
			System.out.println("First Name : ");
			
			first_name = get_input();
			if (first_name.isEmpty() == false) {
				
				System.out.println("Last Name : ");
				last_name = get_input();
				if (last_name.isEmpty() == false) {
					
					System.out.println("Age : ");
					
					age = Integer.parseInt(get_input());
					if (age > 0 && age < 100) {
						
						System.out.println("Email Address : ");
						
						email = get_input();
						if (email.isEmpty() == false && email.contains("@")) {
							
							amg.create_attendee(first_name, last_name, age, email);
							menu_end();
							
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
		
		menu_reset();
		
	}
	
	public static void display_edit_attendee() {
		
		final int EDIT_NAME = 1;
		final int EDIT_AGE = 2;
		final int EDIT_EMAIL = 3;
		final int EDIT_BOOKING = 4;
		
		Attendee att = new Attendee();
		
		String first_name;
		String last_name;
		
		do {
			
			try {
				
				System.out.println("\n-- Select Attendee --");
				System.out.println("Attendee Ref : ");
					
				String ref = get_input();
				//assert(ref.isEmpty()) : "Blah";
				if (ref.isEmpty() == false) {
					
					att = (Attendee)amg.get_item(ref);
						
				}
				
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
			
			System.out.println("\n-- Edit Attendee --");
			System.out.println("Edit Name : " + EDIT_NAME);
			System.out.println("Edit Age : " + EDIT_AGE);
			System.out.println("Edit Email : " + EDIT_EMAIL);
			System.out.println("Edit Booking : " + EDIT_BOOKING);
			
			choice = get_option();
			if (choice > 0 && choice <= 4) {
				
				switch(choice) {
				
				case EDIT_NAME :
					
					System.out.println("First Name : ");
					first_name = get_input();
					
					if (first_name.isEmpty() == false) {
						
						System.out.println("Last Name : ");
						last_name = get_input();
						
						if (last_name.isEmpty() == false) {
							
							att.setFirst_Name(first_name);
							att.setLast_Name(last_name);
							
						}
						
					}
					
					break;
					
				case EDIT_AGE :
					
					System.out.println("Age : ");
					
					choice = get_option();
					if (choice > 0) {
						
						att.setAge(choice);
						
					}
					
					break;
					
				case EDIT_EMAIL :
					
					System.out.println("Email : ");
					input = get_input();
					
					if (input.isEmpty() == false && input.contains("@")) {
						
						att.setEmailAddress(input);
						
					}
					
					break;
					
				case EDIT_BOOKING :
					
					System.out.println("Booking Ref : ");
					input = get_input();
					
					if (input.isEmpty() == false) {
						
						if (bmg.does_booking_exist(input)) {
							
							att.setBooking(input);
							
						}
						
					}
					
					break;
				}
				
				Menu.menu_end();
				
			}
			
			
		} while (exit_menu == false);
		
		try {
			
			amg.update_entry(att);
			
		} catch (SQLException e) {
			ErrorLog.printError("Could not update attendee at this time.", ErrorLog.SEVERITY_MEDIUM);
		}
		
		Menu.menu_reset();
		
	}
	
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
