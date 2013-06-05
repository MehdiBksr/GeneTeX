package error.filegenerator;

import java.text.MessageFormat;

public final class GeneratorErrorMessages {

	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	private static final String LB = System.getProperty("line.separator");
	
	public static final String BAD_FILE_NAME_MESSAGE = 
			MessageFormat.format("{0}{1}{2}{3}", "Bad file name: {0}", LB, 
					"The file has not been created. Ensure the specified file ",
					"name is compatible with your operating system.");
}
