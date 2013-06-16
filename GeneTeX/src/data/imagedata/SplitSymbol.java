package data.imagedata;

import data.Symbol;

/**
 * This class defines the symbol component of the structure containing the 
 * image chunks related to the splitting step.
 * An instance of <code>SplitSymbol</code> is contained in an instance of
 * <code>SplitLine</code>.
 * 
 * @see {@link Symbol}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class SplitSymbol extends Symbol {
	
    /* ************************************************************************
     *                               ATTRIBUTES                               * 
     ************************************************************************ */

	/** A two-dimensional array containing a binary version of this symbol's 
	 * image. */
	private boolean[][] binary;
	
	/** Position of the first pixel column of this image in the page. */
	private int firstPixelX = 0;
	/** Position of the first pixel row of this image in the page. */
	private int firstPixelY = 0;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	/**
	 * Creates a symbol, given an array of boolean values. 
	 * The array represents the image of the symbol. A boolean value is true if
	 * the pixel is colourful, false if it is in the background colour (white).
	 * 
	 * @param binaryImage The image as an array of boolean
	 */
	public SplitSymbol(boolean[][] binaryImage) {
		this.setBinary(binaryImage);
	}

	/**
	 * Creates a symbol, given an array of boolean values, the index of the 
	 * first pixel column of this symbol in the page, and the index of the first
	 * pixel row of this symbol in the page. 
	 * The array represents the image of the symbol. A boolean value is true if
	 * the pixel is colourful, false if it is in the background colour (white).
	 * 
	 * @param binaryImage The image as an array of boolean
	 * @param firstPixelX The index of the symbol's first pixel column in the page.
	 * @param firstPixelY The index of the symbol's first pixel row in the page. 
	 */
	public SplitSymbol(boolean[][] binaryImage, int x, int y) {
		this.binary 		= binaryImage;
		this.firstPixelX 	= x;
		this.firstPixelY 	= y;
	}
	
    /* ************************************************************************
     *                                ACCESSORS                               * 
     ************************************************************************ */
	
	/**
	 * Returns the image of the symbol as an array of boolean values.
	 * The array represents the image of the symbol. A boolean value is true if
	 * the pixel is colourful, false if it is in the background colour (white).
	 * 
	 * @return The image as an array of boolean
	 */
	public boolean[][] getBinary() {
		return this.binary;
	}

	/**
	 * Sets the binary array of this symbol.
	 * 
	 * @param binary The two-dimensional array.
	 */
	public void setBinary(boolean[][] binary) {
		this.binary = binary;
	}

	/**
	 * Gets the index of the first pixel column of this symbol in the page.
	 * 
	 * @return The first X index.
	 */
	public int getFirstPixelX() {
		return this.firstPixelX;
	}

	/**
	 * Sets the index of the first pixel column of this symbol in the page.
	 * 
	 * @param x The first X index.
	 */
	public void setFirstPixelX(int x) {
		this.firstPixelX = x;
	}

	/**
	 * Gets the index of the first pixel row of this symbol in the page.
	 * 
	 * @return The first Y index.
	 */
	public int getFirstPixelY() {
		return this.firstPixelY;
	}

	/**
	 * Sets the index of the first pixel row of this symbol in the page.
	 * 
	 * @param y The first Y index.
	 */
	public void setFirstPixelY(int y) {
		this.firstPixelY = y;
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/**
	 * Gets the index of the last pixel column of this symbol in the page.
	 * 
	 * @return The last X index.
	 */
	public int getLastPixelX() {
		return this.firstPixelX + this.binary.length - 1;
	}
	
	/**
	 * Gets the index of the last pixel row of this symbol in the page. 
	 * 
	 * @return The last Y index.
	 */
	public int getLastPixelY() {
		return this.firstPixelY + this.binary[0].length - 1;
	}
}
	
