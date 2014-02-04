/**
 * @author Joshua Preece
 * @version 0.3
 * Price object used to store price information
 */
package prices;

public class Price {

	private String price;
	private Price_Entry day;
	
	/**
	 * Create a new Price object
	 * @param day Price_Entry day/entry to be set
	 * @param price String price of entry
	 */
	public Price(Price_Entry day, String price) {
		this.day = day;
		this.price = price;
	}
	
	/**
	 * Sets the price
	 * @param price String
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	/**
	 * Gets the price
	 * @return String price
	 */
	public String getPrice() {
		return this.price;
	}
	
	/**
	 * Sets the day / entry
	 * @param day Day
	 */
	public void setDay(Price_Entry day) {
		this.day = day;
	}
	
	/**
	 * Gets the day / entry
	 * @return Day
	 */
	public Price_Entry getDay() {
		return day;
	}
	
	/**
	 * Overrides default toString to print the contents
	 * of this Price object
	 */
	public String toString() {
		System.out.println("\n-- Price --");
		final String FORMAT = "%-20s";
		System.out.format(FORMAT, "Day", "Price");
		System.out.format(FORMAT, getDay().toString(), getPrice());
		System.out.println("-- END Price --\n");
		return "";
	}
	
}
