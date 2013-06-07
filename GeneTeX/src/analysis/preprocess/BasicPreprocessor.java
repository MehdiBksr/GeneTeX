package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

public class BasicPreprocessor implements Preprocessor {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Threshold below which a pixel is considered as colourful (red).
	 * Increase the threshold to increase sensibility. */
	private static final int RED_THRESHOLD = 128;
	
	/** Threshold below which a pixel is considered as colourful (green).
	 * Increase the threshold to increase sensibility. */
	private static final int GREEN_THRESHOLD = 128;
	
	/** Threshold below which a pixel is considered as colourful (blue).
	 * Increase the threshold to increase sensibility. */
	private static final int BLUE_THRESHOLD = 128;
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public BasicPreprocessor() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public PreprocessedImage binarise(BufferedImage image) {
		boolean[][] bin = new boolean[image.getWidth()][image.getHeight()];
		
		for (int i = 0; i < bin.length; i++) {
			for (int j = 0; j < bin[0].length; j++) {
				// get RGB components for this pixel
				int rgb = image.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >>  8) & 0xFF;
				int b = (rgb      ) & 0xFF;
				// whether or not this pixel is black (colourful)
				bin[i][j] =  (r < RED_THRESHOLD || g < GREEN_THRESHOLD 
						|| b < BLUE_THRESHOLD);
			}
		}
		
		return new PreprocessedImage(bin);
	}
}
