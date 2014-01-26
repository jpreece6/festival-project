package accounts;

public abstract class Person {

	private String ref;
	private String name;
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
	 * Get this Person's name
	 * @return String name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets this Person's name
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
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
		System.out.println("-- Person --");
		System.out.println("Name : " + getName());
		System.out.println("Age : " + getAge());
		System.out.println("Email : " + getEmailAddress());
		
		return "";
	}
	
}
