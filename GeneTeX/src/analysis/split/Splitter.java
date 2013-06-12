package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedPage;

/** 
 * The splitter class finds the characters and symbols of an image of type 
 * <code>PreprocessedImage</code> and puts them in a <code>SplitPage</code>
 * instance.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
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
