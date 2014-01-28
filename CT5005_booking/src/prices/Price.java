package prices;

public class Price {

	private String price;
	private Days day;
	
	/**
	 * Create a new Price object
	 * @param day
	 * @param price
	 */
	public Price(Days day, String price) {
		this.day = day;
		this.price = price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	public void setDay(Days day) {
		this.day = day;
	}
	
	public Days getDay() {
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
