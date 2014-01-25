package booking;

import accounts.Attendee;

public class Booking {

	private String ref = "";
	private Attendee booker;
	private Group group;
	
	public Booking (Attendee attendee) {
		
		this.booker = attendee;
		this.group = new Group();
		
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public Attendee getBooker() {
		return this.booker;
	}
	
	public void setBooker(Attendee booker) {
		this.booker = booker;
	}
	
	
}
