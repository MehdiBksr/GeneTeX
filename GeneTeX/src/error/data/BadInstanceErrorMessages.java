package error.data;

import java.text.MessageFormat;

/** Exception raised when a wrong class is instantiated in a structure 
 * (for example, a SplittedBlock is added to a StructuredPage).
 * 
 * @author Mehdi Bouksara
 *
 */
public final class BadInstanceErrorMessages {

	private static final String LB = System.lineSeparator();
	public static final String BAD_INSTANCE_MESSAGE = MessageFormat.format(
			"{0}{1}{2}", "Bad instance of an abstract class given as parameter.", 
			LB, "Expected instance type {0}, found {1}.");

}
