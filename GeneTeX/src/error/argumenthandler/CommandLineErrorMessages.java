package error.argumenthandler;

import java.text.MessageFormat;

public final class CommandLineErrorMessages {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	private static final String LB = System.getProperty("line.separator");
	
	public static final String UNKNOWN_ARGUMENT_MESSAGE = 
			MessageFormat.format("{0}{1}{2}", "Unknown argument: {0}.", LB,
					"For a list of possible arguments, type 'genetex -h'.");
	
	public static final String INVALID_DESTINATION_MESSAGE = 
			"Bad destination file name: {0}";
	
	public static final String INVALID_COMMAND_LINE_MESSAGE =
			MessageFormat.format("{0}{1}{2}", "Invalid command line.", LB,
					"For more information, type 'genetex -h'.");
	
}
