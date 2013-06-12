package filegenerator;

import java.io.IOException;
import java.util.Collection;
import data.Page;
import error.data.BadInstanceException;
import error.filegenerator.BadFileNameException;

/**
 * This interface defines a file generator.
 * A file generator proposes functions to allow the user to create a file given
 * a set of instances of {@link Page} as an input, and transcribe the content of
 * the pages in the created document.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public interface FileGenerator {
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/**
	 * Creates the output document and transcribes the given set of pages into 
	 * it.
	 * 
	 * @param c A collection of pages to be transcribed. 
	 * @param destinationFileName The name of the destination file.
	 * @throws BadFileNameException Raised when the given name is a bad name.
	 * @throws IOException Raised when the destination file could not be created.
	 * @throws BadInstanceException Raised when the given {@link Page} 
	 * 		instances are not of type {@link StructuredPage}, or the components
	 * 		are not of the valid type.
	 */
	public abstract void generate(Collection<Page> c, String destinationFileName)
			throws BadFileNameException, IOException, BadInstanceException;
    
}
