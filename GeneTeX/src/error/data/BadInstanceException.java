package error.data;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class BadInstanceException extends Exception {

	public BadInstanceException(Class<?> expected, Class<?> actual) {
		super(MessageFormat.format(BadInstanceErrorMessages.BAD_INSTANCE_MESSAGE,
				expected.toString(), actual.toString()));
	}
	
}
