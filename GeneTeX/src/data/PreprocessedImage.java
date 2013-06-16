package data;

/**
 * This class represents an image after it has been treated by the preprocessor.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class PreprocessedImage {
	
    /* ************************************************************************
     *                               ATTRIBUTES                               * 
     ************************************************************************ */
	
	/** The array representing the image. */
	private boolean[][] pixels;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	/**
	 * Creates a <code>PreprocessedImage</code>, given an array representing the 
	 * image in form of a two-dimensional boolean table. 
	 * When a value of this array is true, it represents a colourful pixel,
	 * when it is false, it represents a colourless pixel, a pixel at background
	 * colour.
	 *  
	 * @param pixels The array of pixels.
	 */
	public PreprocessedImage(boolean[][] pixels) {
		this.pixels = pixels;
	}
	
    /* ************************************************************************
     *                              GETTERS                                   * 
     ************************************************************************ */
	
	/**
	 * Gets the two-dimensional array of pixels representing the image.
	 * 
	 * @return The two-dimensional array of pixels representing the image.
	 */
	public boolean[][] getPixels() {
		return pixels;
	}
	
	public void setPixel(int x, int y, boolean value) {
		pixels[x][y] = value;
	}

}
