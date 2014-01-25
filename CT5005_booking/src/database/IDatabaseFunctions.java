package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabaseFunctions {
	
	public boolean add_entry(Object data) throws SQLException;
	public boolean remove_entry(String ref) throws SQLException;
	public boolean update_entry(Object data) throws SQLException;
	public Object get_item(String ref) throws SQLException;

	public boolean create_table() throws SQLException;
	public boolean drop_table() throws SQLException;
	public int count_items() throws SQLException;
	public void print_results(ResultSet results) throws SQLException;
	public void search_database(String column, String data) throws SQLException;
	
	
}
