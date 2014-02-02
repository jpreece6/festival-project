/**
 * @author Joshua Preece
 * @version 0.4
 * @description Handles the staff menus
 */

package menus;

import java.sql.SQLException;

import prices.Price_Entry;
import prices.PricesManager;
import tents.TentManager;
import accounts.Attendee;
import accounts.AttendeeManager;
import accounts.ChildManager;
import booking.Booking;
import booking.BookingManager;
import festival.ErrorLog;

public class StaffMenu extends Menu {
	
	private static AttendeeManager amg = new AttendeeManager();
	private static BookingManager bmg = new BookingManager();
	private static PricesManager pmg = new PricesManager();
	private static TentManager tmg = new TentManager();
	private static ChildManager cmg = new ChildManager();
	
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
			System.out.println("Get Booking Cost : " + StaffMenuOptions.BOOKING_COST.ordinal());
			System.out.println("List Booking Attendees : " + StaffMenuOptions.LIST_BOOKING_ATTENDEES.ordinal());
			System.out.println();
			System.out.println("Search : " + StaffMenuOptions.SEARCH.ordinal());
			System.out.println();
			System.out.println("Create Tables : " + StaffMenuOptions.CREATE_TABLES.ordinal());
			System.out.println("Drop Tables : " + StaffMenuOptions.DROP_TABLES.ordinal());
			System.out.println();
			System.out.println("Set Prices : " + StaffMenuOptions.SET_PRICES.ordinal());
			System.out.println("Get Prices : " + StaffMenuOptions.GET_PRICES.ordinal());
			
			System.out.println("Exit Menu : 999");
			
			choice = get_option();
			if (choice > 0) {
				
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
					
				} else if (choice == StaffMenuOptions.BOOKING_COST.ordinal()) {
					
					display_booking_cost();
					
				} else if (choice == StaffMenuOptions.LIST_BOOKING_ATTENDEES.ordinal()) {
					
					display_list_attendees();
					
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
			
			input = get_input();
			if (input.isEmpty() == false) {
				bmg.delete_booking(input);
			}
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_create_booking() {
		
		do {
			
			System.out.println("\n-- Create Booking --");
			System.out.println("Attendee Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Exclude tents from the list as we don't want to store this in bookings
				String[] exclude = { "TENTS" };
				pmg.list_price_types(exclude);
				System.out.println("\nSelect Price_Entry : ");
				
				choice = get_option();
				if (choice >= 0 && choice <= 9) {
					
					bmg.create_booking(input, Price_Entry.values()[choice]);
					
				}
				
			}
			
			Menu.menu_end();
					
		} while (exit_menu == false);
		
		menu_reset();
		
	}
	
	private static void display_edit_booking() {
		
		final int EDIT_DAYS = 1;
		final int EDIT_ADD_TENT = 2;
		final int EDIT_REMOVE_TENT = 3;
		final int LIST_TENTS = 4;
		
		Booking bok = new Booking();
		
		do {
			
			System.out.println("\n-- Edit Booking --");
			System.out.println("Booking Ref :");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				bok = bmg.getBooking(input);
			
				System.out.println("Change Days : " + EDIT_DAYS);
				System.out.println("Add a tent : " + EDIT_ADD_TENT);
				System.out.println("Remove a tent : " + EDIT_REMOVE_TENT);
				System.out.println("List all tents : " + LIST_TENTS);
				
				choice = get_option();
				if (choice > 0) {
					
					switch (choice) {
					
					case EDIT_DAYS :
						
						pmg.list_price_types(null);
						System.out.println("Select new price entry : ");
						
						int entry = get_option();
						if (entry >= 0 && entry <= 9) {
							
							bok.setValid_Day(Price_Entry.values()[entry]);
							bmg.edit_booking(bok);
							
						}
						
						break;
					case EDIT_ADD_TENT :
						
						System.out.println("Adding Tent...");
						tmg.add_tent(bok.getRef());
						
						break;
					case EDIT_REMOVE_TENT:
						
						System.out.println("Removing Tent...");
						System.out.println("Space Number : ");
						
						input = get_input();
						if (input.isEmpty() == false) {
							
							tmg.remove_tent(input);
							
						}
						
						break;
					case LIST_TENTS :
						
						tmg.list_tents(input);
						
						break;
					
					}
					
					
				}
			}
			
			Menu.menu_end();
			
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
		
	}
	
	private static void display_booking_cost() {
		
		do {
			
			System.out.println("\n-- Booking Cost --");
			System.out.println("Booking Ref : ");
			
			input = get_input();
			if (input.isEmpty() == false) {
				
				System.out.println("Total : £" + bmg.get_total_cost(input));
				
				Menu.menu_end();
				
			}
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	private static void display_list_attendees() {
		
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
	
	private static void display_delete_attendee() {
		
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
	
	private static void display_create_attendee() {
		
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
	
	
	private static void display_search_menu() {
		
		do {
			
			System.out.println("\n-- Search --");
			System.out.println("Find Attendee : " + SearchOptions.FIND_ATTENDEE.ordinal());
			System.out.println("Find Booking : " + SearchOptions.FIND_BOOKING.ordinal());
			System.out.println("Find Child : " + SearchOptions.FIND_CHILD.ordinal());
			System.out.println("Find Tent : " + SearchOptions.FIND_TENT.ordinal());
			
			choice = get_option();
			if (choice >= 0) {
				
				System.out.println("Category e.g. (ref, first_name, last_name, age, email_address, booking) : ");
				
				String column = get_input();
				if (column.isEmpty() == false) {
				
					System.out.println("Search : ");
					
					String search = get_input();
					if (search.isEmpty() == false) {
						
					
						if (choice == SearchOptions.FIND_ATTENDEE.ordinal()) {
						
							amg.search_for_attendee(column, search);
							
						} else if (choice == SearchOptions.FIND_BOOKING.ordinal()) {
							
							bmg.search_for_booking(column, search);
							
						} else if (choice == SearchOptions.FIND_CHILD.ordinal()) {
							
							cmg.search_for_child(column, search);
							
						} else if (choice == SearchOptions.FIND_TENT.ordinal()) {
							
							// TODO
							
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
				cmg.create_table();
				
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
				cmg.drop_table();
				
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
			pmg.list_price_types(null);
			
			System.out.println("Enter Vaules (e.g. Monday Wednesday Sunday = 036) : ");
			
			// Get user input
			input = get_input();
			if (input.isEmpty() == false) {
				
				// Process each char (number) inputed by the user
				for (int i = 0; i < input.length(); i++) {
					
					try {
						
						// Parse the input to an integer
						int val = Character.getNumericValue(input.charAt(i));
						if (val >= 0 && val <= 9) {
							
							// Get user to input price
							System.out.println(Price_Entry.values()[val].toString() + " Price = ");
							String price = get_input();
							
							if (price.isEmpty() == false) {
															
								if (pmg.does_day_exist(Price_Entry.values()[val].toString()) == false) { 
									
									pmg.set_price(Price_Entry.values()[val], price);
									
								} else {
									
									pmg.update_price(Price_Entry.values()[val], price);
									
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
			
			pmg.print_stored_prices();
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
	
}
