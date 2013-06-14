package util;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;
import argument.ArgumentHandler;

/** Utility class, regrouping functions which are potentially useful.
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */
public class Utility {
	
	/** Prints verbose messages when the verbose option is specified in the
	 * command line
	 * @param message The verbose message to be printed
	 * @param over Boolean indicating if the message is to be printed at the
	 *  end of a specific action.
	 */
	public static void printVerbose(String message, boolean over) {
		ArgumentHandler options = ArgumentHandler.getInstance();
		if (options.getVerbose() && over) System.out.println(message);
		else if (options.getVerbose()) System.out.print("[Verbose] " + message);
	}
	
	/** Reverts the binarisation process by creating a BufferedImage in which
	 * each pixel represents a pixel of the binarised image : if a given pixel
	 * is coloured (true), the corresponding pixel in the BufferedImage will
	 * be set to black.
	 * 
	 * @param preprocessedImage The PreprocessedImage representing a binarised image.
	 * @return a BufferedImage representing the binarised image.
	 */
	public static BufferedImage toBI(PreprocessedImage preprocessedImage) {
		BufferedImage bi = new BufferedImage(preprocessedImage.getPixels().length, preprocessedImage.getPixels()[0].length, 
				BufferedImage.TYPE_INT_ARGB);
		
		// looking the value of each pixel in the binarised image
		for (int i = 0; i < preprocessedImage.getPixels().length; i++) {
			for (int j = 0; j < preprocessedImage.getPixels()[i].length; j++) {
				if (preprocessedImage.getPixels()[i][j])
					bi.setRGB(i, j, 0xFF000000);
				else
					bi.setRGB(i, j, 0xFFFFFFFF);
			}
		}
		
		return bi;
	}

}
