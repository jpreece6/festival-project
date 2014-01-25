package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseManager {

	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;
	
	public DatabaseManager(String dataProperties) {
		
		try {
			
			System.out.println("Loading Database Settings...");
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(dataProperties);
			prop.load(in);
			
			String driver = prop.getProperty("jdbc.driver");
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			
			if (username.isEmpty()) {
				throw new Exception("Database username empty!");
			}
			
			password = prop.getProperty("jdbc.password");
			
			if (password.isEmpty()) {
				throw new Exception("Database password empty!");
			}
			
			if (driver.isEmpty()) {
				throw new Exception("Database driver not found!");
			} else {
				Class.forName(driver);
			}
			
		} catch (Exception ex) {
			
		}
	}
	
	public static void createConnection() throws SQLException {
		
		conn = DriverManager.getConnection(url, username, password);
		
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
}
