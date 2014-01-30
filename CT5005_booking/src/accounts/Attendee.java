package accounts;

public class Attendee extends Person {
	
	private String booking_ref;
	
	public Attendee() {}
	
	public Attendee(String name, int age, String email_address) {
		setName(name);
		setAge(age);
		setEmailAddress(email_address);
	}
	
	public void setBooking(String booking) {
		this.booking_ref = booking;
	}
	
	public String getBooking() {
		return this.booking_ref;
	}
	
	public String toString() {
		super.toString();
		System.out.print("Booking Ref : " + getBooking());
		return "";
	}

}
