/**
 * @author Joshua Preece
 * @version 0.2
 * Provides methods to print error messages and their severity
 */
package festival;

public abstract class ErrorLog {

	private static int num_errors = 0;
	private static int num_warnings = 0;
	
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
		
		num_errors++;
	}
	
	/**
	 * Print basic information. This can be used as a warning to the user.
	 * Such as when they have not entered in a correct value. Or a value that they have
	 * entered cannot be found in the database.
	 * @param msg String message to print
	 */
	public static void printInfo(String msg) {
		
		System.out.println("\n-- INFO --");
		System.out.println("Message : " + msg);
		System.out.println("-- END INFO --\n");
		
		num_warnings++;
	}
	
	/**
	 * Returns the number of errors/exceptions that the application
	 * has encountered. This method is used heavily in the test harness
	 * @return Integer number of errors thrown
	 */
	public static int get_number_of_errors() {
		return num_errors;
	}
	
	/**
	 * Gets the number of warnings shown to the user. Every ErrorLog.printInfo
	 * will increase the warning count.
	 * @return Integer number of warnings
	 */
	public static int get_number_of_warnings() {
		return num_warnings;
	}
	
}
