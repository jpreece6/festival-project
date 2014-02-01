package accounts;

public abstract class Person {

	private String ref;
	private String first_name;
	private String last_name;
	private int age;
	private String email_address;
	
	/**
	 * Returns this Person's ref
	 * @return String ref
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Sets this Person's ref
	 * @param ref String
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	/**
	 * Get this Person's first name
	 * @return String first name
	 */
	public String getFirst_Name() {
		return this.first_name;
	}
	
	/**
	 * Sets this Person's first name
	 * @param name String
	 */
	public void setFirst_Name(String name) {
		this.first_name = name;
	}
	
	/**
	 * Gets this Person's last name
	 * @param name String
	 * @return String last name
	 */
	public String getLast_Name() {
		return this.last_name;
	}
	
	/**
	 * Sets this Person's last name
	 * @param name String
	 */
	public void setLast_Name(String name) {
		this.last_name = name;
	}
	
	/**
	 * Get this Person's age
	 * @return int age
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * Sets this Person's age
	 * @param age int
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Gets this Person's email address
	 * @return String address
	 */
	public String getEmailAddress() {
		return this.email_address;
	}
	
	/**
	 * Sets this Person's email address
	 * @param address String
	 */
	public void setEmailAddress(String address) {
		this.email_address = address;
	}
	
	/**
	 * Prints this Person's details to the screen
	 */
	public String toString() {
		System.out.println("\n-- Person --");
		System.out.println("Name : " + getFirst_Name() + " " + getLast_Name());
		System.out.println("Age : " + getAge());
		System.out.println("Email : " + getEmailAddress());
		System.out.println("-- END Person --\n");
		
		return "";
	}
	
}
