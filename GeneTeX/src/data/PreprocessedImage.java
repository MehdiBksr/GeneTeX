package data;

/**
 * This class represents an image after it has been treated by the preprocessor.
 * 
 * @author Marceau Thalgott
 *
 */
public class PreprocessedImage {
	
    /* ************************************************************************
     *                               ATTRIBUTES                               * 
     ************************************************************************ */
	/** The array representing the image */
	private boolean[][] pixels;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public PreprocessedImage(boolean[][] pixels) {
		this.pixels = pixels;
	}
	
    /* ************************************************************************
     *                              GETTERS                                   * 
     ************************************************************************ */
	
	public boolean[][] getPixels() {
		return pixels;
	}

}
