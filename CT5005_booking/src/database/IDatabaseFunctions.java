/**
 * @author Joshua Preece
 * @version 0.9
 * @description Interface defines the methods needed for classes to communicate with the remote database
 */
package database;

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
	 * @Pre-Conditions Entry must exist in the database before attempting to remove.
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
	 * @Pre-Condition ensure item exists in the database before attempting to retrieve the item
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
