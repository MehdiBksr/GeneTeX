package argument;

import java.text.MessageFormat;
import java.util.Vector;

import error.argumenthandler.InvalidCommandLineException;
import error.argumenthandler.InvalidDestinationException;
import error.argumenthandler.UnknownArgumentException;

/**
 * Class in form of a singleton storing source and/or destination files as well 
 * as options specified in the command line.
 * 
 * @author Marceau Thalgott
 */
public class ArgumentHandler {
	
    /* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	/** Unique instance. */
	final private static ArgumentHandler instance = new ArgumentHandler();
	
	/** Name of the destination file. */
	private String 			destinationFile	= new String();
	/** Name of the source file(s). */
	private Vector<String> 	sourceFiles		= new Vector<String>();
	/** Option verbose. */
	private boolean 		verbose			= false;
	/** Help option. */
	private boolean 		help			= false;
	
	/** Whether the unique instance has already been initialised. */
	private boolean			initialised		= false;
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	/** Private constructor. */
	private ArgumentHandler() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/**
	 * Initialises the instance of ArgumentHandler.
	 * Once the instance has already been initialised, additional 
	 * initialisations will have no effect.
	 * 
	 * @param args List of the parameters of a command line.
	 * @throws Exception 
	 */
	public static void initialise(String[] args) throws Exception {
		// protection to avoid multiple initialisations
		if (instance.initialised) 
			return;
		instance.initialised = true;
		
		if (args == null || args.length == 0)
			throw new InvalidCommandLineException();
		
		/* Parameters */
		
		// argument currently handled
		String argument;
		int i = 0;
				
		while (i < args.length && (argument = args[i]).charAt(0) != '-') {
			/* add file names to source files list */
			instance.sourceFiles.add(argument);
			i++;
		}
		
		while (i < args.length) {
			argument = args[i];
			/* option handling */
			switch (argument) {
			case "-help":
			case "-h":
				instance.help = true;
				break;
			case "-verbose":
			case "-v":
				instance.verbose = true;
				break;
			case "-rename":
			case "-r":
				i++;
				if (i == args.length || (argument = args[i]).charAt(0) == '-')
					throw new InvalidDestinationException(argument);
				instance.destinationFile = argument;
				break;
			default:
				throw new UnknownArgumentException(argument);
			}
			i++;
		}
		
		if (instance.sourceFiles.size() != 0) { 
			// set the default destination file name if needed
			if (instance.destinationFile.isEmpty())
				instance.destinationFile = MessageFormat.format("{0}.tex", 
						instance.sourceFiles.elementAt(0));
		} else {
			if (!instance.help)
				// bad command line
				throw new InvalidCommandLineException();
		}
	}
	
	/**
	 * Returns the unique ArgumentHandler instance.
	 * 
	 * @return The instance of ArgumentHandler.
	 */
	public static ArgumentHandler getInstance() {
		return instance;
	}
	
	/**
	 * Resets the instance of ArgumentHandler so that it can be initialised
	 * again.
	 */
	public static void reset() {
		instance.sourceFiles 		= new Vector<String>();
		instance.destinationFile 	= new String();
		instance.help 				= false;
		instance.verbose 			= false;
		instance.initialised 		= false;
	}
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	
	/** Returns the name of the destination file *
	 * 
	 * @return the name of the destination file.
	 */
	public String getDestinationFile() {
		return this.destinationFile;
	}
	
	/** Returns the boolean saying if the verbose option is enabled *
	 * 
	 * @return the verbose boolean
	 */
	public boolean getVerbose() {
		return this.verbose;
	}
	
	/** Returns the boolean saying if the help option is enabled *
	 * 
	 * @return the help boolean
	 */
	public boolean getHelp() {
		return this.help;
	}
	
	/** Returns a vector containing the names of the source files, in the same order
	 * than the order they have been specified in the command line.
	 * 
	 * @return a vector of String containing the names of the source files
	 */
	public Vector<String> getSourceFiles() {
		return this.sourceFiles;
	}

}
