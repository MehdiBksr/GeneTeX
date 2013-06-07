package imageloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Simple class encapsulating the loading image methods given by Java.
 * 
 * @author Marceau Thalgott
 *  */
public class ImageLoader {
    
    /* ************************************************************************
     *                          STATIC FUNCTIONS                              * 
     ************************************************************************ */
	
	/**
	 * Creates and returns a BufferedImage corresponding to the image related to
	 * the given file name. 
	 * 
	 * @param fileName The image file name
	 * @return The image as a BufferedImage
	 * 
	 * @throws IOException If the given string is invalid
	 */
	public static BufferedImage load(String fileName) throws IOException {
		return ImageIO.read(new File(fileName));
	}
	
}
