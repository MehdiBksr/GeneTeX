package imageloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** 
 * This class allows the user to open the input files and get a 
 * {@link BufferedImage} representing the image to be transcribed.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
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
