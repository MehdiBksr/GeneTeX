package argument;

import java.text.MessageFormat;
import java.util.Vector;

import error.CommandLineError;

/**
 * Class in form of a singleton storing source and/or destination files as well 
 * as options specified in the command line.
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
		if (instance.initialised) return;
		instance.initialised = true;
		
		/* Parameters */
		// argument currently handled
		String argument;
		
		for (int i = 0; i < args.length; i++) {
			argument = args[i];
			
			if (argument.charAt(0) == '-') {
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
					instance.destinationFile = args[i]; 
				default:
					/** TODO: add proper exception */
					throw new Exception(CommandLineError.UNKNOWN_ARGUMENT);
				}
			} else {
				/* file name handling */
				instance.sourceFiles.add(argument);
				/** TODO: handle file name */
			}
		}
		
		// set the default destination file name if needed
		if (instance.destinationFile.isEmpty()) {
			instance.destinationFile = MessageFormat.format("{0}.tex", 
					instance.sourceFiles.elementAt(0));
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
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	
	public String getDestinationFile() {
		return this.destinationFile;
	}
	
	public boolean getVerbose() {
		return this.verbose;
	}
	
	public boolean getHelp() {
		return this.help;
	}
	
	public Vector<String> getSourceFiles() {
		return this.sourceFiles;
	}

}
