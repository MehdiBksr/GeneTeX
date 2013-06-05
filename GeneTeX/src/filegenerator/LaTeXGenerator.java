package filegenerator;

import java.util.Collection;

import data.Page;
import error.filegenerator.BadFileNameException;

public class LaTeXGenerator implements FileGenerator {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Unique instance. */
	private static final LaTeXGenerator instance = new LaTeXGenerator();
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	private LaTeXGenerator() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	@SuppressWarnings("unused")
	public void generate(Collection<Page> c, String destinationFileName) 
			throws BadFileNameException {
		
		if (false) {
			throw new BadFileNameException(destinationFileName);
		}
	}
    
    /* ************************************************************************
     *                          STATIC FUNCTIONS                              * 
     ************************************************************************ */
    
	public static LaTeXGenerator getInstance() {
		return instance;
	}
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
