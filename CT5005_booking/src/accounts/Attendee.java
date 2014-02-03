/**
 * @author Joshua Preece
 * @version 1.6
 * Object to store information about attendees
 */
package accounts;

public class Attendee extends Person {
	
	private String booking_ref;
	
	/**
	 * Create a new attendee object. This constructor is mainly used
	 * when we don't know all the attendees information straight away
	 */
	public Attendee() {}
	
	/**
	 * Create a new attendee object with parameters
	 * @param first_name String attendee's first name
	 * @param last_name String attendee's last name
	 * @param age int attendee's age
	 * @param email_address String attendee's email address
	 */
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
	
	/**
	 * Prints the attendee's details. Calls super as the inherited function
	 * contains most of the code we want to use
	 */
	public String toString() {
		super.toString();
		System.out.print("Booking Ref : " + getBooking());
		return "";
	}

}
