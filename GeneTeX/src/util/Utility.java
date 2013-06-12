package util;

import argument.ArgumentHandler;

/** Utility class, regrouping functions which are potentially useful.
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */
public class Utility {
	
	/** Prints verbose messages when the verbose option is specified in the
	 * command line
	 * @param message The verbose message to be printed
	 * @param over Boolean indicating if the message is to be printed at the
	 *  end of a specific action.
	 */
	public static void printVerbose(String message, boolean over) {
		ArgumentHandler options = ArgumentHandler.getInstance();
		if (options.getVerbose() && over) System.out.println(message);
		else if (options.getVerbose()) System.out.print("[Verbose] " + message);
	}

}
