package error.data;

import java.text.MessageFormat;

public final class BadInstanceErrorMessages {

	private static final String LB = System.lineSeparator();
	public static final String BAD_INSTANCE_MESSAGE = MessageFormat.format(
			"{0}{1}{2}", "Bad instance of an abstract class given as parameter.", 
			LB, "Expected instance type {0}, found {1}.");

}
