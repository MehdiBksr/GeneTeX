package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

/** 
 * This basic implementation of a preprocessor only transforms the input image
 * into a <code>PreprocessedImage</code>, i.e. a two-dimensional array of 
 * boolean values.
 * When such a value is true, the pixel is coloured, when it is false the pixel
 * is at background colour.
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
	
	/** 
	 * This method turns a BufferedImage, which contains an array of pixels (using their
	 * RGB values) into an array of booleans of the same dimensions. A pixel of the binarized image
	 * will be colored (true) if its equivalent in the BufferedImage satisfies the given conditions.
	 * 
	 *  @param image The image to be converted.
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
