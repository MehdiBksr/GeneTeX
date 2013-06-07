package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

/** The preprocessor's role is to prepare the input image to be analyzed by the recognition system.
 * In this basic version, it simply translates the image into a binary version : a pixel is colored (true) or
 * empty (false). The conversion uses a threshold system, if the RGB values of a given pixel are under
 * a given value, it will be converted to a colored pixel in the binarized version.
 * @author Marceau Thalgott
 *
 */
public class BasicPreprocessor implements Preprocessor {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	// To increase sensitivity, raise the threshold
	
	/** Threshold above which a pixel is considered as colourful (red). */
	private static final int RED_THRESHOLD = 255;
	
	/** Threshold above which a pixel is considered as colourful (green). */
	private static final int GREEN_THRESHOLD = 255;
	
	/** Threshold above which a pixel is considered as colourful (blue). */
	private static final int BLUE_THRESHOLD = 255;

    /* ************************************************************************
     *                              CONSTRUCTORS   
     *                                                         * 
     ************************************************************************ */
	
	public BasicPreprocessor() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/** This method turns a BufferedImage, which contains an array of pixels (using their
	 * RGB values) into an array of booleans of the same dimensions. A pixel of the binarized image
	 * will be colored (true) if its equivalent in the BufferedImage satisfies the given conditions.
	 * 
	 *  @param image The image to be converted.
	 *  @return	a structure containing the array of booleans representing the image.
	 */
	public PreprocessedImage binarise(BufferedImage image) {
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
