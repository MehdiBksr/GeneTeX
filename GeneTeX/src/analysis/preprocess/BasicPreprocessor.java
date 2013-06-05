package analysis.preprocess;

import java.awt.image.BufferedImage;

public class BasicPreprocessor implements Preprocessor {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Threshold above which a pixel is considered as colourful (red). */
	private static final int RED_THRESHOLD = 128;
	
	/** Threshold above which a pixel is considered as colourful (green). */
	private static final int GREEN_THRESHOLD = 128;
	
	/** Threshold above which a pixel is considered as colourful (blue). */
	private static final int BLUE_THRESHOLD = 128;
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                          STATIC FUNCTIONS                              * 
     ************************************************************************ */
	
	public boolean[][] binarise(BufferedImage image) {
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
		
		return bin;
	}
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
}
