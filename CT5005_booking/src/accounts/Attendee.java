package accounts;

import booking.Booking;


public class Attendee extends Person {
	
	private Booking booking;
	
	public Attendee() {}
	
	public Attendee(String name, int age, String email_address) {
		setName(name);
		setAge(age);
		setEmailAddress(email_address);
	}
	
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public Booking getBooking() {
		return this.booking;
	}
	
	public String toString() {
		super.toString();
		System.out.print("Booking : " + getBooking().getRef());
		return "";
	}

}
