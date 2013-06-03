package error;

import java.text.MessageFormat;

public class CommandLineError {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	private static final String lb = System.getProperty("line.separator");
	
	public static final String UNKNOWN_ARGUMENT = 
			MessageFormat.format("{0}{1}{2}", "Unknown argument.", lb,
					"For a list of available arguments, type genetex -h"); 
	
}
