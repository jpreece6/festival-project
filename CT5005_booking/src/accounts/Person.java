package accounts;

public abstract class Person {

	private String ref;
	private String name;
	private int age;
	private String email_address;
	
	public String getRef() {
		return this.ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getEmailAddress() {
		return this.email_address;
	}
	
	public void setEmailAddress(String address) {
		this.email_address = address;
	}
	
	public String toString() {
		System.out.println("-- Person --");
		System.out.println("Name : " + getName());
		System.out.println("Age : " + getAge());
		System.out.println("Email : " + getEmailAddress());
		
		return "";
	}
	
}
