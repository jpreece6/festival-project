package menus;

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
	
}
