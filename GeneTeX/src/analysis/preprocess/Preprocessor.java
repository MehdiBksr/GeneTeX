package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

/** 
 * The pre-processor class provides methods allowing the pre-processing of a
 * given image.
 * Pre-processing aims at simplifying the image such that it can be easily
 * processed afterwards (e.g. splitting and recognition), by modelling the image
 * from an array of pixels to something more simple (e.g. an array of boolean
 * values) and by removing noise and other sorts of parasites.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara 
 */
public interface Preprocessor {

	/**
	 * Pre-processes the given image and returns an object of type <code>
	 * PreprocessedImage</code> containing the altered image.
	 * 
	 * @param image The input image as a <code>BufferedImage</code>.
	 * 
	 * @return The pre-processed image.
	 */
	public PreprocessedImage preprocess(BufferedImage image);
	
}
