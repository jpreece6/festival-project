package prices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseManager;
import database.IDatabaseFunctions;
import festival.ErrorLog;

public class PricesManager implements IDatabaseFunctions {
	
	private Price pri;
	
	public void set_price(Days day, String price) {
		
		try {
			pri = new Price(day, price);

			add_entry(pri);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public void update_price(Days day, String price) {
		
		try {
			
			pri = new Price(day, price);
			
			update_entry(pri);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_MEDIUM);
		}
		
	}
	
	public boolean does_day_exist(String day) {
		try {
			
			return DatabaseManager.does_entry_exist("prices", "type", day);
			
		} catch (SQLException ex) {
			ErrorLog.printError(ex.getMessage(), ErrorLog.SEVERITY_LOW);
			return false;
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
			
		stat.execute("DELETE FROM attendees WHERE ref=" + ref);

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
			
		}
	}
	
	@Override
	public void drop_table() throws SQLException {
		
		if (DatabaseManager.does_table_exist("prices")) {
			
			Statement stat = DatabaseManager.getConnection().createStatement();
			
			stat.execute("DROP TABLE prices");
			
			stat.close();
			
		}
	
	}

	
	@Override
	public Object get_item(String type) throws SQLException {
		
		Statement stat = DatabaseManager.getConnection().createStatement();
		
		ResultSet result = stat.executeQuery("SELECT * FROM prices WHERE type=" + type);
		
		if (result.next()) {
			
			pri.setDay(Days.valueOf(result.getString("type")));
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
