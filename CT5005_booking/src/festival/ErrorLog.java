/**
 * @author Joshua Preece
 * @version 0.2
 * @description Provides methods to print error messages and their severity
 */
package festival;

public abstract class ErrorLog {

	/**
	 * Critical error application cannot continue
	 */
	public static final String SEVERITY_CRITICAL = "Critical";
	
	/**
	 * High error functionality affected may need to restart application
	 */
	public static final String SEVERITY_HIGH = "High";
	
	/**
	 * Medium error some functionality affected application can still run correctly
	 */
	public static final String SEVERITY_MEDIUM = "Medium";
	
	/**
	 * Low error little functionality affected
	 */
	public static final String SEVERITY_LOW = "Low";
	
	/**
	 * Prints an error message to the screen
	 * @param msg Error message to be displayed
	 * @param severity Severity of the error, CRITICAL, HIGH, MEDIUM, LOW
	 */
	public static void printError(String msg, String severity) {
		
		System.out.println("\n-- ERROR --");
		System.out.println("Message : " + msg);
		System.out.println("Severity : " + severity);
		System.out.println("-- END ERROR --\n");
		
	}
	
	public static void printInfo(String msg) {
		
		System.out.println("\n-- INFO --");
		System.out.println("Message : " + msg);
		System.out.println("-- END INFO --\n");
	}
	
}
