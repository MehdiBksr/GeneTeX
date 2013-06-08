package data.imagedata;

import data.Symbol;

public class SplittedSymbol extends Symbol {
	
    /* ************************************************************************
     *                               ATTRIBUTES                               * 
     ************************************************************************ */

	private boolean[][] binary;
	
	private int firstPixelX;
	private int firstPixelY;
	
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
	public SplittedSymbol(boolean[][] binaryImage) {
		this.setBinary(binaryImage);
	}
	
	public SplittedSymbol(boolean[][] binaryImage, int x, int y) {
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
	 * the pixel is colorful, false if it is in the background color (white).
	 * 
	 * @return The image as an array of boolean
	 */
	public boolean[][] getBinary() {
		return binary;
	}

	public void setBinary(boolean[][] binary) {
		this.binary = binary;
	}

	public int getFirstPixelX() {
		return firstPixelX;
	}

	public void setFirstPixelX(int firstPixelX) {
		this.firstPixelX = firstPixelX;
	}

	public int getLastPixelX() {
		return firstPixelX + binary.length - 1;
	}
}
	
