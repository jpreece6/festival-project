package menus;

import java.util.Scanner;

import accounts.AccountsManager;

public abstract class Menu {

	protected static final int EXIT_MENU = 999;
	protected static final int BACK = 100;
	protected static int choice;
	protected static String input;
	protected static boolean exit_menu;
	protected static AccountsManager act;
	
	protected static void display_menu() {
		
		System.out.print("MAIN");
		
	}

	protected static void display_login() {
		
		act = new AccountsManager();
		
		do {
			
			System.out.print("Username : ");
			
			String username = get_input();
			if (input.trim().isEmpty() == false) {
				
				System.out.print("\nPassword : ");
				
				String password = get_input();
				if (input.trim().isEmpty() == false) {
					
					
					act.user_login(username, password);
					exit_menu = true;
					
					
				} else {
					
					System.out.println("Please enter a password");
					
				}
				
				
			} else {
				
				System.out.println("Please enter a username");
				
			}
			
			
			
		} while (exit_menu == false);
		
		exit_menu = false;
		
	}
	
	protected static void display_registration() {
		
		do {
			
			clear_screen();
			
			String name;
			int age;
			String address;
			
			
			System.out.println("Attendee Registration\n\n");
			System.out.print("Name : ");
			
			name = get_input();
			if (input.trim().isEmpty() == false) {
				
				try {
					age = Integer.parseInt(get_input());
				} catch (Exception ex) {
					System.out.println("Please enter a valid name");
				}
				
				
			} else {
				
				System.out.println("Please enter a name");
				
			}
			
		} while (exit_menu == false);
		
	}
	
	protected static int get_option() {
		
		try {
			
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
			
		} catch (Exception ex) {
			return 0;
		}
		
	}
	
	protected static String get_input() {
		
		try {
			
			Scanner scan = new Scanner(System.in);
			return scan.next();
			
		} catch (Exception ex) {
			return "";
		}
		
	}
	
	protected static void clear_screen() {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("");
		}
		
	}
	
	protected static void menu_end() {
		exit_menu = true;
	}
	
	protected static void menu_reset() {
		exit_menu = false;
	}
	
}
