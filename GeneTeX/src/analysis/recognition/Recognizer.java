package analysis.recognition;

import data.contentdata.StructuredPage;
import data.imagedata.SplittedPage;

/**
 * This interface is used to abstract and have access to the recognition
 * methods implemented.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 */
public interface Recognizer {
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/**
	 * Analyses the content of the data created by the splitter.
	 * 
	 * @return The data computed for the FileGenerator.
	 * @throws Exception 
	 */
	public StructuredPage recognise(SplittedPage page) throws Exception;
}
