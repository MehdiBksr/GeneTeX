package error.argumenthandler;


@SuppressWarnings("serial")
public class InvalidCommandLineException extends Exception {
	
    /* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public InvalidCommandLineException() {
		super(CommandLineErrorMessages.INVALID_COMMAND_LINE_MESSAGE);
	}

}
