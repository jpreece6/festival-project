package menus;

import java.sql.SQLException;

import prices.Days;
import prices.PricesManager;
import tents.TentManager;
import accounts.Attendee;
import accounts.AttendeeManager;
import booking.Booking;
import booking.BookingManager;
import festival.ErrorLog;

public class StaffMenu extends Menu {
	
	private static AttendeeManager amg = new AttendeeManager();
	private static BookingManager bmg = new BookingManager();
	private static PricesManager pmg = new PricesManager();
	private static TentManager tmg = new TentManager();
	
	public static void display_menu() {

		do {
			
			System.out.println("\n-- Main Menu --");
			System.out.println("Create Attendee : " + StaffMenuOptions.CREATE_ATTENDEE.ordinal());
			System.out.println("Edit Attendee : " + StaffMenuOptions.EDIT_ATTENDEE.ordinal());
			System.out.println("Delete Attendee : " + StaffMenuOptions.DELETE_ATTENDEE.ordinal());
			System.out.println();
			System.out.println("Create Booking : " + StaffMenuOptions.CREATE_BOOKING.ordinal());
			System.out.println("Edit Booking : " + StaffMenuOptions.EDIT_BOOKING.ordinal());
			System.out.println("Delete Booking : " + StaffMenuOptions.DELETE_BOOKING.ordinal());
			System.out.println("Search : " + StaffMenuOptions.SEARCH.ordinal());
			System.out.println();
			System.out.println("Create Tables : " + StaffMenuOptions.CREATE_TABLES.ordinal());
			System.out.println("Drop Tables : " + StaffMenuOptions.DROP_TABLES.ordinal());
			System.out.println();
			System.out.println("Set Prices : " + StaffMenuOptions.SET_PRICES.ordinal());
			System.out.println("Get Prices : " + StaffMenuOptions.GET_PRICES.ordinal());
			
			System.out.println("Exit Menu : 999");
			
			choice = get_option();
			if (choice > 0 && choice <= 12) {
				
				if (choice == StaffMenuOptions.CREATE_ATTENDEE.ordinal()) {
					
					display_create_attendee();
					
					
				} else if (choice == StaffMenuOptions.EDIT_ATTENDEE.ordinal()) {
					
					display_edit_attendee();
					
				} else if (choice == StaffMenuOptions.DELETE_ATTENDEE.ordinal()) {
					
					display_delete_attendee();
					
				} else if (choice == StaffMenuOptions.CREATE_BOOKING.ordinal()) {
					
					display_create_booking();
					
				} else if (choice == StaffMenuOptions.EDIT_BOOKING.ordinal()) {
					
					display_edit_booking();
					
				} else if (choice == StaffMenuOptions.DELETE_BOOKING.ordinal()) {
					
					display_delete_booking();
					
				} else if (choice == StaffMenuOptions.SEARCH.ordinal()) {
					
					display_search_menu();
					
				} else if (choice == StaffMenuOptions.CREATE_TABLES.ordinal()) {
					
					display_create_tables();
					
				} else if (choice == StaffMenuOptions.DROP_TABLES.ordinal()) {
					
					display_drop_tables();
					
				} else if (choice == StaffMenuOptions.SET_PRICES.ordinal()) {
					
					display_set_prices();
					
				} else if (choice == StaffMenuOptions.GET_PRICES.ordinal()) {
					
					display_get_prices();
					
				}
				
			}
			
			
		} while (exit_menu == false);
		
	}
	
	private static void display_delete_booking() {
		
		do {
			
			System.out.println("\n-- Delete Booking --");
			System.out.println("Booking Ref :  ");
			
			try {
				
				BookingManager bok = new BookingManager ();
				
				input = get_input();
				if (input.isEmpty() == false) {
					bok.remove_entry(input);
				}
				
			} catch (SQLException ex) {
				ErrorLog.printError("Could not delete booking at this time.", ErrorLog.SEVERITY_MEDIUM);
			}
			
			menu_end();
			
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_create_booking() {
		
		do {
			
			System.out.println("\n-- Create Booking --");
			
			
			
					
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_edit_booking() {
		
		final int EDIT_DAYS = 1;
		final int EDIT_ADD_TENT = 2;
		final int EDIT_REMOVE_TENT = 3;
		
		Booking bok = new Booking();
		
		do {
			
			System.out.println("\n-- Edit Booking --");
			System.out.println("Ref :");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				bok.setRef(input);
			
				System.out.println("Change Days : " + EDIT_DAYS);
				System.out.println("Add a tent : " + EDIT_ADD_TENT);
				System.out.println("Remove a tent : " + EDIT_REMOVE_TENT);
				
				choice = get_option();
				if (choice > 0 && choice <= 3) {
					
					switch (choice) {
					
					case EDIT_DAYS :
						
						System.out.println("");
						
						break;
					case EDIT_ADD_TENT :
						
						System.out.println("Adding Tent...");
						tmg.add_tent(bok.getRef());
						
						break;
					case EDIT_REMOVE_TENT:
						
						System.out.println("Removing Tent...");
						//tmg.remove_tent(space_no);
						
						break;
					
					}
					
					
				}
			}
			
			Menu.menu_end();
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
		
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
					ErrorLog.printError("Could not remove attendee at this time.", ErrorLog.SEVERITY_MEDIUM);
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
		
		Attendee att = new Attendee();
		
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
				
				switch(choice) {
				
				case EDIT_NAME :
					
					System.out.println("Name : ");
					input = get_input();
					
					if (input.isEmpty() == false) {
						
						att.setName(input);
						
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
	
	
	private static void display_search_menu() {
		
		do {
			
			System.out.println("\n-- Search --");
			System.out.println("Find Attendee : " + StaffMenuOptions.FIND_ATTENDEE.ordinal());
			System.out.println("Find Booking : " + StaffMenuOptions.FIND_BOOKING.ordinal());
			
			choice = get_option();
			if (choice > 0) {
				
				System.out.println("Category e.g. (ref, name) : ");
				
				String column = get_input();
				if (column.isEmpty() == false) {
				
					System.out.println("Search : ");
					
					String search = get_input();
					if (search.isEmpty() == false) {
						
					
						if (choice == StaffMenuOptions.FIND_ATTENDEE.ordinal()) {
						
							amg.search_for_attendee(column, search);
							
						} else if (choice == StaffMenuOptions.FIND_BOOKING.ordinal()) {
							
							bmg.search_for_booking(column, search);
							
						}
					}
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
				pmg.create_table();
				tmg.create_table();
				
			} catch (SQLException e) {
				ErrorLog.printError("Error creating tables!\n" + e.getMessage(), ErrorLog.SEVERITY_HIGH);
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
				pmg.drop_table();
				tmg.drop_table();
				
			} catch (SQLException e) {
				ErrorLog.printError("Error dropping tables\n" +  e.getMessage(), ErrorLog.SEVERITY_HIGH);
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_set_prices() {
		
		do {
			
			// List days from Days enum
			System.out.println("\n-- Set Prices --");
			for (int i = 0; i < Days.values().length; i++) {
				
				System.out.println(Days.values()[i].toString() + " : " + Days.values()[i].ordinal());
				
			}
			
			System.out.println("Enter Vaules (e.g. Monday Wednesday Sunday = 036) : ");
			
			// Get user input
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Process each char (number) inputed by the user
				for (int i = 0; i < input.length(); i++) {
					
					try {
						
						// Parse the input to an integer
						int val = Character.getNumericValue(input.charAt(i));
						if (val >= 0 && val <= 6) {
							
							// Get user to input price
							System.out.println(Days.values()[val].toString() + " Price = ");
							String price = get_input();
							
							if (price.isEmpty() == false) {
															
								if (pmg.does_day_exist(Days.values()[val].toString()) == false) { 
									
									pmg.set_price(Days.values()[val], price);
									
								} else {
									
									pmg.update_price(Days.values()[val], price);
									
								}
								
							}
							
						}
						
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorLog.printError("", ErrorLog.SEVERITY_LOW);
						//break;
					}
					
				}
				
				Menu.menu_end();
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_get_prices() {
		
		do {
		
			try {
				
				pmg.print_stored_prices();
				
			} catch (SQLException e) {
				ErrorLog.printError(e.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
}
