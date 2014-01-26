package menus;

import java.sql.SQLException;

import booking.BookingManager;
import accounts.Attendee;
import accounts.AttendeeManager;

public class StaffMenu extends Menu {
	
	private static AttendeeManager amg = new AttendeeManager();
	private static BookingManager bmg = new BookingManager();
	
	private static Attendee att;
	
	public static void display_menu() {

		do {
			
			System.out.println("\n-- Main Menu --");
			System.out.println("Create Attendee : " + StaffMenuOptions.CREATE_ATTENDEE.ordinal());
			System.out.println("Edit Attendee : " + StaffMenuOptions.EDIT_ATTENDEE.ordinal());
			System.out.println("Delete Attendee : " + StaffMenuOptions.DELETE_ATTENDEE.ordinal());
			System.out.println();
			System.out.println("Create Booking : " + StaffMenuOptions.CREATE_BOOKING.ordinal());
			System.out.println("Delete Booking : " + StaffMenuOptions.DELETE_BOOKING.ordinal());
			System.out.println("Search : " + StaffMenuOptions.SEARCH.ordinal());
			System.out.println();
			System.out.println("Create Table : " + StaffMenuOptions.CREATE_TABLES.ordinal());
			System.out.println("Drop Table : " + StaffMenuOptions.DROP_TABLES.ordinal());
			
			System.out.println("Exit Menu : 999");
			
			choice = get_option();
			if (choice > 0 && choice <= 11) {
				
				if (choice == StaffMenuOptions.CREATE_ATTENDEE.ordinal()) {
					
					display_create_attendee();
					
					
				} else if (choice == StaffMenuOptions.EDIT_ATTENDEE.ordinal()) {
					
					display_edit_attendee();
					
				} else if (choice == StaffMenuOptions.DELETE_ATTENDEE.ordinal()) {
					
					display_delete_attendee();
					
				} else if (choice == StaffMenuOptions.CREATE_BOOKING.ordinal()) {
					
					display_create_booking();
					
				} else if (choice == StaffMenuOptions.DELETE_BOOKING.ordinal()) {
					
					display_delete_booking();
					
				} else if (choice == StaffMenuOptions.SEARCH.ordinal()) {
					
					display_search_menu();
					
				} else if (choice == StaffMenuOptions.CREATE_TABLES.ordinal()) {
					
					display_create_tables();
					
				} else if (choice == StaffMenuOptions.DROP_TABLES.ordinal()) {
					
					display_drop_tables();
					
				}
				
			}
			
			
		} while (exit_menu == false);
		
	}
	
	private static void display_delete_booking() {
		
		do {
			
			System.out.println("-- Delete Booking --");
			System.out.println("Booking Ref :  ");
			
			try {
				
				BookingManager bok = new BookingManager ();
				
				input = get_input();
				if (input.isEmpty() == false) {
					bok.remove_entry(input);
				}
				
			} catch (SQLException ex) {
				System.out.println("-! Could not delete this booking at this time !- ");
			}
			
			menu_end();
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_create_booking() {
		
		do {
			
			System.out.println("-- Create Booking --");
			System.out.println("Attendee Ref : ");
			
					
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_delete_attendee() {
		
		do {
			
			System.out.println("\n-- Delete Attendee -- ");
			System.out.println("Attendee Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				try {
					
					//TEMP
					AttendeeManager amg = new AttendeeManager();
					amg.remove_entry(input);
					
				} catch (SQLException e) {
					System.out.println("-! Could not remove attendee at this time !-");
				}
				
				menu_end();
				
			}
			
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_create_attendee() {
		
		do {
			
			String name;
			int age;
			String email;
			
			System.out.println("-- Create new attendee --\n");
			System.out.println("Name : ");
			
			name = get_input();
			if (name.isEmpty() == false) {
				
				System.out.println("Age : ");
				
				age = Integer.parseInt(get_input());
				if (age > 0 && age < 100) {
					
					System.out.println("Email Address : ");
					
					email = get_input();
					if (email.isEmpty() == false && email.contains("@")) {
						
						AttendeeManager amg = new AttendeeManager();
						
						// TEMP
						/*try {
							//amg.drop_table();
							amg.create_table();
						} catch (SQLException e) {
							e.printStackTrace();
						}*/
						
						amg.create_attendee(name, age, email);
						menu_end();
						
					}
					
				}
				
			}
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_edit_attendee() {
		
		final int EDIT_NAME = 1;
		final int EDIT_AGE = 2;
		final int EDIT_EMAIL = 3;
		final int EDIT_BOOKING = 4;
		
		do {
			
			try {
				
				System.out.println("\n-- Select Attendee --");
				System.out.println("Ref : ");
					
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
				
				if (choice == EDIT_NAME) {
					
					System.out.println("Name : ");
					input = get_input();
					
					if (input.isEmpty() == false) {
						
						att.setName(input);
						
					}
					
				} else if (choice == EDIT_AGE) {
					
					System.out.println("Age : ");
					
					choice = get_option();
					if (choice > 0) {
						
						att.setAge(choice);
						
					}
					
				} else if (choice == EDIT_EMAIL) {
					
					System.out.println("Email : ");
					input = get_input();
					
					if (input.isEmpty() == false && input.contains("@")) {
						
						att.setEmailAddress(input);
						
					}
					
				} else if (choice == EDIT_BOOKING) {
					
					System.out.println("Booking Ref : ");
					input = get_input();
					
					if (input.isEmpty() == false) {
						
						// SET BOOKING 
						// PRE CHECK BOOKING EXISTS
						
					}
					
				}
				
				Menu.menu_end();
				
			}
			
			
		} while (exit_menu == false);
		
		try {
			amg.update_entry(att);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Menu.menu_reset();
		
	}
	
	
	private static void display_search_menu() {
		
		do {
			
			System.out.println("-- Search --");
			System.out.println("Find Attendee : " + StaffMenuOptions.FIND_ATTENDEE.ordinal());
			System.out.println("Find Booking : " + StaffMenuOptions.FIND_BOOKING.ordinal());
			
			choice = get_option();
			if (choice > 0) {
				
				try {
					
					System.out.println("Category e.g. (ref, name) : ");
					
					String column = get_input();
					if (column.isEmpty() == false) {
					
						System.out.println("Search : ");
						
						String search = get_input();
						if (search.isEmpty() == false) {
							
						
							if (choice == StaffMenuOptions.FIND_ATTENDEE.ordinal()) {
							
								amg.search_database(column, search);
								
							} else if (choice == StaffMenuOptions.FIND_BOOKING.ordinal()) {
								
								bmg.search_database(column, search);
								
							}
						}
					}
					
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
				
			}
			
			menu_end();
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_create_tables() {
		
		do {
			
			System.out.println("\nCreating Tables...");
			
			try {
				
				amg.create_table();
				bmg.create_table();
				
			} catch (SQLException e) {
				System.out.println("-- Error --");
				System.out.println(e.getMessage());
				System.out.println("----");
			}
			
			Menu.menu_end();
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
	private static void display_drop_tables() {
		
		do {
			
			try {
				
				System.out.println("\nDropping Tables....");
				
				amg.drop_table();
				bmg.drop_table();
				
			} catch (SQLException e) {
				System.out.println("-- Error --");
				System.out.println(e.getMessage());
				System.out.println("----");
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
}
