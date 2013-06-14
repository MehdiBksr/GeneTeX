package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplitPage;

/** 
 * The splitter class finds the characters and symbols of an image of type 
 * <code>PreprocessedImage</code> and puts them in a <code>SplitPage</code>
 * instance.
 * 
 *	@author Mehdi BOUKSARA, Marceau THALGOTT, Theo MERLE
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
	 * @param preprocessedPage The previously preprocessed image.
	 * @param handlingSplaces The boolean indicating if the splitting must 
	 * 		  handle the space character.
	 * 
	 * @return The set of characters contained in the page, as a <code>SplitPage
	 * </code>.
	 */
	public SplitPage split(PreprocessedImage preprocessedPage, boolean handlingSpaces);
    
}
