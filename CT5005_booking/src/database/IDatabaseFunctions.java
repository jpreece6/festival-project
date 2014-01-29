package database;

import java.sql.SQLException;

public interface IDatabaseFunctions {
	
	/**
	 * Adds a new entry to the database
	 * @param data Object to be added to the database
	 * @returns Returns true if item was added to the database or false if it failed
	 * @throws SQLException
	 */
	public boolean add_entry(Object data) throws SQLException;
	
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
	
}
