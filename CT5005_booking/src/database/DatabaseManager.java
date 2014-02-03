/**
 * @author Joshua Preece
 * @version 1.0
 * This class allows the program to connect to a remote database and defines methods 
 * which aid getting information about the database and its tables.
 */

package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

import festival.ErrorLog;

public class DatabaseManager {

	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;
	
	/**
	 * Creates a new DatabaseManager object
	 * @param dataProperties String path to properties file which contains the database credentials
	 */
	public DatabaseManager(String dataProperties) {
		
		try {
			
			System.out.println("Loading Database Settings...");
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(dataProperties);
			prop.load(in);
			
			String driver = prop.getProperty("jdbc.driver");
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			
			// Check if the username for the database is empty if so throw an exception
			if (username.isEmpty()) {
				throw new Exception("Database username empty!");
			}
			
			password = prop.getProperty("jdbc.password");
			
			// Check if the password for the database is empty if so throw an exception
			if (password.isEmpty()) {
				throw new Exception("Database password empty!");
			}
			
			// Check if the driver for the database is empty if so throw an exception
			if (driver.isEmpty()) {
				throw new Exception("Database driver not found!");
			} else {
				Class.forName(driver);
			}
			
		} catch (Exception ex) {
			ErrorLog.printError("Could not load database credentials!\n" + ex.getMessage(), ErrorLog.SEVERITY_CRITICAL);
		}
	}
	
	/**
	 * Create a new connection to the database
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		
		// Connect to the database
		conn = DriverManager.getConnection(url, username, password);
		
	}
	
	/**
	 * Returns the connection object
	 * @return Connection object
	 */
	public static Connection getConnection() {
		return conn;
	}
	
	/**
	 * Prints the results of a ResultSet returned by a search function
	 * @param title String title of the results
	 * @param result ResultSet containing the data retrieved from the database
	 * @throws SQLException
	 */
	public static void print_results(String title, ResultSet result) throws SQLException {
		
		ResultSetMetaData meta = result.getMetaData();
		
		System.out.println("\n-- " + title + " --");
		final String FORMAT = "%-20s";
		
		// Print the column names
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			
			System.out.format(FORMAT, meta.getColumnName(i));
		
		}
		
		// Print the returned results
		while (result.next()) {
			
			System.out.println();
			
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.format(FORMAT, result.getString(i));
			}
			
		}
		
		System.out.println();
		
	}
	
	/**
	 * Counts the number of entries in a table
	 * @param table String name of the table to count
	 * @return Integer count
	 * @throws SQLException
	 */
	public static int count_items(String table) throws  SQLException {
		
		Statement stat = getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM " + table);
		
		if (rs.next()) {
			
			return rs.getInt(1);
			
		}
		
		return 0;
		
	}
	
	/**
	 * Counts a the number of specific items in the database
	 * @param table String table to count in
	 * @param column String column to search in
	 * @param data String item to specifically search for and count
	 * @return Returns the count/number of results returned
	 * @throws SQLException
	 */
	public static int count_specific_items(String table, String column, String data) throws SQLException {
		
		ResultSet rs = search_database(table, column, data);
		
		int count = 0;
		while (rs.next()) {
			
			count++;
			
		}
		
		return count;
		
	}
	
	/**
	 * Check to see if an entry exists in the database
	 * @param table String name of the table to search in
	 * @param column String name of the column to search
	 * @param entry String entry/item to search for
	 * @return Boolean true if item exists, false if it does not
	 * @throws SQLException
	 */
	public static boolean does_entry_exist(String table, String column, String entry) throws SQLException {
		
		Statement stat = getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + entry + "'");
		
		return rs.next();
		
	}
	
	/**
	 * Checks to see if a table exists in the database
	 * @param table String table name to check for
	 * @return Boolean result of the check. Returns true if the table exists and false if not
	 * @throws SQLException
	 */
	public static boolean does_table_exist(String table) throws SQLException {
		
		DatabaseMetaData meta = getConnection().getMetaData();
		
		ResultSet rs = meta.getTables(null, null, table.toUpperCase(), null);
		
		return rs.next();
		
	}
	
	/**
	 * Searches for entries in the database which match the passed parameters
	 * @param table String table to search in
	 * @param column String column to search in
	 * @param data String item to search for
	 * @return Returns a ResultSet of the matched items
	 * @throws SQLException
	 */
	public static ResultSet search_database(String table, String column, String data) throws SQLException {
		
		Statement stat = getConnection().createStatement();
		
		ResultSet rs = stat.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + data + "'");
		
		//stat.close();
		return rs;
		
	}
	
	/**
	 * Generates a random id to be used in the database
	 * @return String random string
	 */
	public static String generate_random_id() {
		
		final int LENGTH = 5;
		final String CONTENT = "0123456789";
		Random rnd = new Random();
		
		// Build the new random ref
		StringBuilder sb = new StringBuilder(LENGTH);
		for (int i = 0; i < LENGTH; i++) {
			
			sb.append(CONTENT.charAt(rnd.nextInt(CONTENT.length())));
			
		}
		
		return sb.toString();
		
	}
	
}
