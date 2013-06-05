package error.filegenerator;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class BadFileNameException extends Exception {

	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public BadFileNameException(String fileName) {
		super(MessageFormat.format(GeneratorErrorMessages.BAD_FILE_NAME_MESSAGE,
				fileName));
	}

}
