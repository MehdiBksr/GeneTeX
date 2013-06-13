package analysis;

import data.contentdata.StructuredPage;

/** The interface for the analysing module. Its role is to read the image
 *  input, to detect the lines and symbosl in it, and to put them in a structure
 *  for file generation.
 *  
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */
public interface Analyzer {
	
	/** The analysing method.
	 * 
	 * @param fileName The name of the image to be analysed.
	 * @return a StructuredPage containing all the elements for file generation.
	 * @throws Exception
	 */
	public StructuredPage analyse(String fileName) throws Exception;

}
