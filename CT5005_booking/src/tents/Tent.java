/**
 * @author Joshua Preece
 * @version 1.1
 * Tent object for storing information about tents
 */
package tents;

public class Tent {

	private String space_no;
	private String booking_ref;
	
	/**
	 * Set the space number for this tent
	 * @param space String space number. String used to better store in the database
	 */
	public void set_space_no(String space) {
		this.space_no = space;
	}
	
	/**
	 * Get the space number for this tent
	 * @return String space number
	 */
	public String get_space_no() {
		return this.space_no;
	}
	
	/**
	 * Set a booking (ref) to this tent
	 * @param booking_ref String
	 */
	public void set_booking_ref(String booking_ref) {
		this.booking_ref = booking_ref;
	}
	
	/**
	 * Get the booking (ref) of this tent
	 * @return String booking ref
	 */
	public String get_booking_ref() {
		return this.booking_ref;
	}
	
	public String toString() {
		System.out.println("\n-- Tent --");
		System.out.println("Space Number : " + get_space_no());
		System.out.println("Booking : " + get_booking_ref());
		System.out.println("--  END Tent --\n");
		return "";
	}
	
}
