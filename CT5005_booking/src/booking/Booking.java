package booking;

import prices.Price_Entry;

public class Booking {

	private String ref = "";
	private String booker_ref;
	private Price_Entry valid_day;
	
	/**
	 * Gets the ref for this booking
	 * @return String ref
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Sets the ref for this booking
	 * @param ref Strign new ref
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	/**
	 * Gets the booker (attendee ref) of this booking
	 * @return String booking ref
	 */
	public String getBooker() {
		return this.booker_ref;
	}
	
	/**
	 * Sets the booker (ref of the attendee making the booking) of this booking
	 * @param booker
	 */
	public void setBooker(String booker) {
		this.booker_ref = booker;
	}
	
	/**
	 * Sets the valid day of this booking
	 * @param entry String new price entry
	 */
	public void setValid_Day(Price_Entry entry) {
		this.valid_day = entry;
	}
	
	/**
	 * Gets the valid day of this booking
	 * @return Price_Entry valid day
	 */
	public Price_Entry getValid_Day() {
		return this.valid_day;
	}
	
	public String toString() {
		
		System.out.println("\n-- BOOKING DETAILS --");
		System.out.println("Booking Ref : " + getRef());
		System.out.println("Booker : " + getBooker());
		System.out.println("-- END BOOKER --\n");
		return "";
	}
	
	
}
