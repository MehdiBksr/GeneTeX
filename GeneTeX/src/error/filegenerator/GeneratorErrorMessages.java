package error.filegenerator;

import java.text.MessageFormat;

public final class GeneratorErrorMessages {

	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	private static final String LB = System.getProperty("line.separator");
	
	public static final String BAD_FILE_NAME_MESSAGE = 
			MessageFormat.format("{0}{1}{2}", "Bad file name: {0}", LB, 
					"The file may already exist, or the specified name may " +
					"not be compatible with your operating system.");
}
