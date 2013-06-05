package data.imagedata;

import data.Symbol;

public class SplittedSymbol extends Symbol {

	private boolean[][] binary;
	
	/**
	 * Creates a symbol, given an array of boolean values. 
	 * The array represents the image of the symbol. A boolean value is true if
	 * the pixel is colourful, false if it is in the background colour (white).
	 * 
	 * @param binaryImage The image as an array of boolean
	 */
	public SplittedSymbol(boolean[][] binaryImage) {
		this.binary = binaryImage;
	}
	
	/**
	 * Returns the image of the symbol as an array of boolean values.
	 * The array represents the image of the symbol. A boolean value is true if
	 * the pixel is colourful, false if it is in the background colour (white).
	 * 
	 * @return The image as an array of boolean
	 */
	public boolean[][] getBinaryImage() {
		return this.binary;
	}
}
