package filegenerator;

import java.io.IOException;
import java.util.Collection;
import data.Page;
import error.data.BadInstanceException;
import error.filegenerator.BadFileNameException;

public interface FileGenerator {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public abstract void generate(Collection<Page> c, String destinationFileName)
			throws BadFileNameException, IOException, BadInstanceException;
    
    /* ************************************************************************
     *                          STATIC FUNCTIONS                              * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
