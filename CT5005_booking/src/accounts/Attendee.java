/**
 * @author Joshua Preece
 * @version 1.6
 * @description Object to store information about attendees
 */
package accounts;

public class Attendee extends Person {
	
	private String booking_ref;
	
	public Attendee() {}
	
	public Attendee(String first_name, String last_name, int age, String email_address) {
		setFirst_Name(first_name);
		setLast_Name(last_name);
		setAge(age);
		setEmailAddress(email_address);
	}
	
	/**
	 * Assign this attendee a booking
	 * @param booking String booking_ref
	 */
	public void setBooking(String booking) {
		this.booking_ref = booking;
	}
	
	/**
	 * Get the booking of this attendee
	 * @return String booking_ref
	 */
	public String getBooking() {
		return this.booking_ref;
	}
	
	public String toString() {
		// TODO
		super.toString();
		System.out.print("Booking Ref : " + getBooking());
		return "";
	}

}
