package main;

/** A simple class for storing different help messages. 
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */
public class Help {
	
	/** The help message, displayed when specified in the command line, or in 
	 * case of bad usage.
	 */
	private static final String helpString = "Aide de GeneTeX !";

	/** Returns the help message. 
	 * 
	 * @return A String containing the help message.
	 */
	public static String getHelpString() {
		return helpString;
	}

}
