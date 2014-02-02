/**
 * @author Joshua Preece
 * @version 1.2
 * @description Menus to create and drop tables on the remote database
 */
package menus;

import java.sql.SQLException;

import festival.ErrorLog;

public class TablesMenu extends Menu {
	
	/**
	 * Display the menu to create all tables
	 */
	public static void display_create_tables() {
		
		do {
			
			System.out.println("\nCreating Tables...");
			
			try {
			
				// Create all tables
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
	
	/**
	 * Display menu to drop all tables
	 */
	public static void display_drop_tables() {
		
		do {
			
			try {
				
				System.out.println("\nDropping Tables....");
				
				// Drop all tables
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
	
}
