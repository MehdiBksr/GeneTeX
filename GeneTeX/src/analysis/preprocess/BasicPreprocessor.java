package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

/** 
 * This basic implementation of a preprocessor only transforms the input image
 * into a <code>PreprocessedImage</code>, i.e. a two-dimensional array of 
 * pixels represented as boolean values, such value being true when the pixel is 
 * coloured, and false when the pixel is at background colour.
 * 
 * @see PreprocessedImage, Preprocessor
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara 
 */
public class BasicPreprocessor implements Preprocessor {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	// To increase sensitivity, raise the threshold
	
	/** Threshold above which a pixel is considered as colourful (red). */
	private static final int RED_THRESHOLD = 128;
	
	/** Threshold above which a pixel is considered as colourful (green). */
	private static final int GREEN_THRESHOLD = 128;
	
	/** Threshold above which a pixel is considered as colourful (blue). */
	private static final int BLUE_THRESHOLD = 128;

    /* ************************************************************************
     *                              CONSTRUCTORS   						      *                                                         * 
     ************************************************************************ */
	
	public BasicPreprocessor() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public PreprocessedImage preprocess(BufferedImage image) {
		return this.binarise(image);
	}
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
	
	/** 
	 * Transforms the given image in form of a BufferedImage into a 
	 * PreprocessedImage containing a binarised version of the initial image.
	 * The binarised image is a two-dimensional array of pixels represented as 
	 * boolean values, such value being true when the pixel is coloured, and 
	 * false when the pixel is at background colour.
	 * 
	 *  @param image The image to be converted.
	 *  
	 *  @return	a structure containing the array of booleans representing the image.
	 */
	private PreprocessedImage binarise(BufferedImage image) {
		boolean[][] bin = new boolean[image.getWidth()][image.getHeight()];
		
		for (int i = 0; i < bin.length; i++) {
			for (int j = 0; j < bin[0].length; j++) {
				// get RGB components for this pixel
				int rgb = image.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >>  8) & 0xFF;
				int b = (rgb      ) & 0xFF;
				// whether or not this pixel is black (colorful)
				bin[i][j] =  (r < RED_THRESHOLD || g < GREEN_THRESHOLD 
						|| b < BLUE_THRESHOLD);
			}
		}
		
		return new PreprocessedImage(bin);
	}
	
}
