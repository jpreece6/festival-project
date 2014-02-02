/**
 * @author Joshua Preece
 * @version 0.3
 * @description Menus for setting and getting the festivals booking prices
 */
package menus;

import prices.Price_Entry;
import festival.ErrorLog;

public class PriceMenu extends Menu {

	/**
	 * Display the menu to set the prices for the festival
	 */
	public static void display_set_prices() {
		
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
	
	/**
	 * Display the current prices
	 */
	public static void display_get_prices() {
		
		do {
			
			pmg.print_stored_prices();
			
			Menu.menu_end();
			
		} while (exit_menu == false);
		
		Menu.menu_reset();
		
	}
	
}
