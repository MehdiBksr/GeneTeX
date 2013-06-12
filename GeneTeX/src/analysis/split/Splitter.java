package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedPage;

/** Interface representing the splitting module. Its role is to find the limits of
 * each text block in the page, each line in the page, and of each symbol in the line.
 * 
 * @author Mehdi Bouksara
 *
 */
public interface Splitter {
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/**
	 * Splits the given page into an ordered set of symbols contained in a 
	 * <code>SplitPage</code>.
	 * 
	 * @param preprocessedPage The previously pre-processed image.
	 * 
	 * @return The set of characters contained in the page, as a <code>SplitPage
	 * </code>.
	 */
	public SplittedPage split(PreprocessedImage preprocessedPage);
    
}
