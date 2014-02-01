package prices;

public class Price {

	private String price;
	private Price_Entry day;
	
	/**
	 * Create a new Price object
	 * @param day
	 * @param price
	 */
	public Price(Price_Entry day, String price) {
		this.day = day;
		this.price = price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	public void setDay(Price_Entry day) {
		this.day = day;
	}
	
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
		return "";
	}
	
}
