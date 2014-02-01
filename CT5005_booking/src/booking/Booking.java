package booking;

import prices.Price_Entry;

public class Booking {

	private String ref = "";
	private String booker_ref;
	private Price_Entry valid_day;
	private Group group;
	
	public Booking() {
		
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getBooker() {
		return this.booker_ref;
	}
	
	public void setBooker(String booker) {
		this.booker_ref = booker;
	}
	
	public void setValid_Day(Price_Entry entry) {
		this.valid_day = entry;
	}
	
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
