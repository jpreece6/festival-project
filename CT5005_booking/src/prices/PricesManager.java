package prices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;

public class PricesManager implements IDatabaseFunctions {
	
	private Price pri;
	
	public void set_price(Price_Entry day, String price) {
		
		try {
			pri = new Price(day, price);

			add_entry(pri);
			
		} catch (SQLException ex) {
			ErrorLog.printError("Set price failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	/**
	 * Updates the price stored in the database
	 * @param day Day to set price for
	 * @param price String price of the day
	 * @Pre-Condition Price must be > 0
	 */
	public void update_price(Price_Entry entry, String price) {
		
		try {
			
			if (DatabaseManager.does_entry_exist("prices", "type", entry.toString())) {
			
				pri = new Price(entry, price);
				update_entry(pri);
				
			} else {
				
				// Should'nt get here but just in case
				System.out.println("Entry does not yet exist. Please set an inital price.");
				
			}
			
		} catch (SQLException ex) {
			ErrorLog.printError("Update price failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public boolean does_day_exist(String entry) {
		try {
			
			return DatabaseManager.does_entry_exist("prices", "type", entry);
			
		} catch (SQLException ex) {
			ErrorLog.printError("Check day exists failed!\n" + ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
			return false;
		}
	}
	
	public void list_price_types() {
		
		for (int i = 0; i < Price_Entry.values().length; i++) {
			
			System.out.println(Price_Entry.values()[i].toString() + " : " + Price_Entry.values()[i].ordinal());
			
		}
		
	}
	
	@Override
	public boolean add_entry(Object data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.executeUpdate("INSERT INTO prices (type, price) "
				+ "VALUES('" + pri.getDay().toString() + "', '" + pri.getPrice() + "')");
		
		stat.close();

		return false;
	}

	@Override
	public void remove_entry(String ref) throws SQLException {

		Statement stat = DatabaseManager.getConnection().createStatement();
			
		stat.execute("DELETE FROM prices WHERE ref=" + ref);

		stat.close();
		
	}
	
	@Override
	public void update_entry(Object data) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		stat.executeUpdate("UPDATE prices SET type='" + pri.getDay().toString() + "', price='"
				+ pri.getPrice() + "' WHERE type=" + pri.getDay().toString());
		
		stat.close();
		
	}
	
	@Override
	public void create_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("prices")) {
			
			ErrorLog.printInfo("Table 'prices' already exists");
			
		} else {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("CREATE TABLE prices "
					+ "(type varchar(20), price varchar(10),"
					+ "PRIMARY KEY (type))");
			
			stat.close();
			
			System.out.println("CREATE prices DONE...");
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("prices")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE prices");
			
			stat.close();
			
			System.out.println("DROP prices DONE...");
			
		}
	
	}

	
	@Override
	public Object get_item(String type) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM prices WHERE type=" + type);
		
		if (result.next()) {
			
			pri.setDay(Price_Entry.valueOf(result.getString("type")));
			pri.setPrice(result.getString("price"));
		
			return pri;
			
		}
		
		return null;
	}
	
	public void print_stored_prices() throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * from prices");
		
		System.out.println("\n-- Current Prices --");
		
		while (result.next()) {
			
			System.out.println(result.getString("type") + " : £" + result.getString("price"));
			
		}
		
	}

}
