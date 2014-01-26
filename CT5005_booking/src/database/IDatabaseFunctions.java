package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabaseFunctions {
	
	/**
	 * Adds a new entry to the database
	 * @param data Object to be added to the database
	 * @throws SQLException
	 */
	public void add_entry(Object data) throws SQLException;
	
	/**
	 * Remove an item from the database
	 * @param ref String to identify the object to be removed
	 * @throws SQLException
	 */
	public void remove_entry(String ref) throws SQLException;
	
	/**
	 * Updates an item in the database
	 * @param data Object to be updated
	 * @throws SQLException
	 */
	public void update_entry(Object data) throws SQLException;
	
	/**
	 * Returns an Object from the database
	 * @param ref String to identify the object to be returned
	 * @return Object
	 * @throws SQLException
	 */
	public Object get_item(String ref) throws SQLException;

	/**
	 * Creates a new database table
	 * @throws SQLException
	 */
	public void create_table() throws SQLException;
	
	/**
	 * Drops/Deletes a table from the database
	 * @throws SQLException
	 */
	public void drop_table() throws SQLException;
	
	/**
	 * Counts the number of items/rows in a table
	 * @return int number of rows
	 * @throws SQLException
	 */
	public int count_items() throws SQLException;
	
	/**
	 * Prints each column of a result set
	 * @param results ResultSet to retrieve data
	 * @throws SQLException
	 */
	public void print_results(ResultSet results) throws SQLException;
	
	/**
	 * Search the database for rows containing matching the query
	 * @param column String column to compare data
	 * @param data String data to compare
	 * @throws SQLException
	 */
	public void search_database(String column, String data) throws SQLException;
	
	
}