package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedPage;

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
