package analysis.split;

import java.util.Vector;

import data.PreprocessedImage;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;
import error.data.BadInstanceException;

/** 
 * This basic implementation of a splitter divides the given image into lines
 * and then searches for the symbols inside of the line.
 * 
 * Line segmentation is done by using a method based on horizontal histograms.
 * 
 * Character segmentation is done by searching for empty columns of pixels in
 * a line, such columns separating characters. 
 * This separation might not be sufficient and a secondary segmentation is then 
 * performed, separating overlapping characters which are not connected, and, if 
 * needed, connected characters using a method based on vertical histograms. 
 * 
 * @see Splitter
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara 
 *
 */
public class BasicSplitter implements Splitter {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Number of pixels below which a pixel row is considered empty. */
	private final static int ROW_PIXELS_THRESHOLD = 5;
	/** Coefficient estimating the width of a character (width = height*coef). */
	private final static double HEIGHT_TO_WIDTH = 1.3;
	/** Coefficient determining the minimum width of a space (min_sp_width = width/coef). */
	private final static int SPACE_WIDTH_DIVIDER = 2;

    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	public SplittedPage split(PreprocessedImage preprocessedPage) {
		
		int y = 0;
		SplittedBlock b = new SplittedBlock();
		SplittedLine l 	= getNextLine(preprocessedPage.getPixels(), y);
		SplittedPage p 	= new SplittedPage();

		// as long as there are lines in the page
		while (l != null) {			
			// adds the line
			try {
				b.addLine(l);
			} catch (BadInstanceException e) {
				e.printStackTrace();
			}
			// start again on the first unchecked pixel line
			y = l.getLastPixelLine() + 2;
			l = getNextLine(preprocessedPage.getPixels(), y);
		}
		
		// adds the block
		try {
			p.addBlock(b);
		} catch (BadInstanceException e) {
			e.printStackTrace();
		}

		return p;
	}

	/* ************************************************************************
	 *                          PRIVATE FUNCTIONS                             * 
	 ************************************************************************ */
	
	/**
	 * Get the next line of the currently processed pages. 
	 *  
	 * @param page The page being currently split.
	 * @param y The index of the pixel row starting from which the next line is
	 * 			to be found.
	 * @return The line as a <code>SplitLine</code>.
	 */	
	private static SplittedLine getNextLine(boolean[][] page, int y) {

		// number of pixels in the current pixel row
		int nbOfPixels = 0;
		// horizontal histogram counting the number of pixels in relevant rows
		Vector<Integer> horizontalHistogram = new Vector<Integer>();
		// average width of a character in this row
		int avgWidth;
		// the line being split
		SplittedLine l;
		// current x position in the line
		int x = 0;
		// next symbol(s) discovered in the line
		Vector<SplittedSymbol> nextSymbols;
		// line's content
		boolean[][] line = new boolean[page.length][];
		// y starting position of the line in the page
		int start_y = y;
		//  height of the line (from the starting position) 
		int length_y = 0;

		/* LINE DELIMITATION */
		
		// get the starting y position of the current line
		while (start_y < page[0].length 
				&& pixelsInRow(page, start_y) <= ROW_PIXELS_THRESHOLD)
			start_y++;
		// end of the page: no new line in the page
		if (start_y >= page[0].length) return null;

		// computing the height of the line
		nbOfPixels = pixelsInRow(page, length_y + start_y);
		while ((length_y + start_y < page[0].length) 
				&& nbOfPixels > ROW_PIXELS_THRESHOLD) {
			length_y++;
			horizontalHistogram.add(nbOfPixels);
			nbOfPixels = pixelsInRow(page, length_y + start_y);
		}
		
		// no new exploitable line, end of the page
		if (length_y == 0) return null;

		/* SYMBOL PROCESSING */
		
		l = new SplittedLine(start_y, start_y + length_y - 1, page.length);
		avgWidth = computeAverageWidth(horizontalHistogram);
		
		// generate a sub-array containing the current line only
		for (int i = 0; i < page.length; i++) {
			line[i] = new boolean[length_y];
			System.arraycopy(page[i], start_y, line[i], 0, length_y - 1);
		}

		nextSymbols = getNextSymbol(line, x, avgWidth);
		// as long as there are undiscovered symbols
		while (nextSymbols != null) {
			for (SplittedSymbol s : nextSymbols) {
				// adds the symbols
				try {
					l.addSymbol(s);
				} catch (BadInstanceException e) {
					e.printStackTrace();
				}
				// removing blanks around the symbol
				s.setFirstPixelY(start_y);
				s.setBinary(removeMargins(s.getBinary()));
			}
			// resuming search on the first unknown column
			x = nextSymbols.lastElement().getLastPixelX() + 2;
			nextSymbols = getNextSymbol(line, x, avgWidth);
		}
		return l;
	}

	/**
	 * Get the next symbol of the currently processed line. 
	 *  
	 * @param page The page being currently split.
	 * @param y The index of the pixel row starting from which the next line is
	 * 			to be found.
	 * @param width The estimated average width of a character.
	 * @return The line as a <code>SplitLine</code>.
	 */
	private static Vector<SplittedSymbol> getNextSymbol(boolean[][] line, int x, int width) {

		int start_x = x;
		int length_x = 0;
		Vector<SplittedSymbol> res = new Vector<SplittedSymbol>();

		// look for the first non-empty column
		while ((start_x < line.length) && columnEmpty(line, start_x))
			start_x++;
		// end of the line: no new symbol in the line
		if (start_x >= line.length) return null; 
		
		// handling the space character
		if (start_x - x > width/SPACE_WIDTH_DIVIDER) {
			boolean[][] pixels = new boolean[width/SPACE_WIDTH_DIVIDER][line[0].length];
			for (int i = 0; i < pixels.length; i++)
				for (int j = 0; j < pixels[0].length; j++)
					pixels[i][j] = false;
			res.add(new SplittedSymbol(pixels, start_x - width/SPACE_WIDTH_DIVIDER - 2, 0));
			return res;
		}

		// finding the end of the column
		while ((start_x + length_x < line.length) 
				&& !columnEmpty(line, start_x + length_x))
			length_x++;

		if (length_x == 0) {
			return null;
		}
		
//		// the chunk of the line is too wide to be a single symbol
//		if (length_x > width) 
//			return secondarySegmentation(line, start_x, length_x, width);

		// copying the sub-array containing the symbol
		boolean[][] symbol = new boolean[length_x][line[0].length];
		for (int i = 0; i < symbol.length; i++)
			System.arraycopy(line[start_x+i], 0, symbol[i], 0, symbol[0].length);

		res.add(new SplittedSymbol(symbol, start_x, 0));
		return res;
	}

	/**
	 * Secondary segmentation function.
	 * This function will attempt to extract single symbols that are not 
	 * separated by one or more blank columns, either being overlapping or
	 * connected to each other.
	 *  
	 * @param line The line in which symbols are to be extracted.
	 * @param x X index at which the indistinct set of symbols starts.
	 * @param length_x Length of the indistinct set of symbols.
	 * @param width Estimated average width of a single symbol.
	 * @return A vector containing all the unit symbols after extraction.
	 */
	private static Vector<SplittedSymbol> secondarySegmentation(boolean[][] line, int x, int length_x, int width) {
		// the symbols to be returned
		Vector<SplittedSymbol> symbols = new Vector<SplittedSymbol>();
		// object containing the line portion
		boolean[][] multipleSymbol = new boolean[length_x][line[x].length];
		// the symbols being extracted
		SplittedSymbol connectedSymbol, singleSymbol;
		// the current x value in the line
		int current_x = x;

		for (int i = 0; i < multipleSymbol.length; i++)
			System.arraycopy(line[x+i], 0, multipleSymbol[i], 0, multipleSymbol[0].length);

		connectedSymbol = extractFirstOverlappingSymbol(multipleSymbol);
		// extract all the overlapping but not connected symbols
		while (connectedSymbol != null) {
			current_x += connectedSymbol.getFirstPixelX();
			connectedSymbol.setFirstPixelX(current_x);
			if (connectedSymbol.getBinary().length > width) {
				// extracted symbol still too wide, separates it
				singleSymbol = extractFirstConnectedSymbol(connectedSymbol.getBinary());
				while (singleSymbol != null) {
					current_x += singleSymbol.getFirstPixelX();
					singleSymbol.setFirstPixelX(current_x);
					symbols.add(singleSymbol);
					singleSymbol = extractFirstConnectedSymbol(connectedSymbol.getBinary());
				}
			} else {
				symbols.add(connectedSymbol);
			}
			connectedSymbol = extractFirstOverlappingSymbol(multipleSymbol);
		}
		mergeComposite(symbols);
		return symbols;
	}
	
	/**
	 * This function aims at extracting the first symbol present in a set of
	 * overlapping symbols.
	 * The set is given in form of a two-dimensional boolean array. The symbol
	 * is removed from the array as it is extracted.
	 * @param symbols The set of overlapping symbols.
	 * @return The extracted symbol.
	 */
	private static SplittedSymbol extractFirstOverlappingSymbol(boolean[][] symbols) {
		// TODO take naturally overlapping symbols into account (e.g. '=', ';' etc.)
		return null;
	}
	
	/**
	 * This function aims at extracting the first symbol present in a set of
	 * connected symbols.
	 * The set is given in form of a two-dimensional boolean array. The symbol
	 * is removed from the array as it is extracted.
	 * @param symbols The set of connected symbols.
	 * @return The extracted symbol.
	 */
	private static SplittedSymbol extractFirstConnectedSymbol(boolean[][] symbols) {
		return null;
	}
	
	/**
	 * Merge composite unconnected symbols that might have been extracted 
	 * separately because of the overlapping into the proper one, e.g. '=', ';',
	 * etc. 
	 * The given set of symbols is updated.
	 * @param symbols The set of separated symbols which is to be updated.
	 */
	private static void mergeComposite(Vector<SplittedSymbol> symbols) {
	}

	/**
	 * Returns the number of colourful pixels in the row specified by the given 
	 * index y in the given array pixels.
	 * 
	 * @param pixels The two-dimensional array of pixels.
	 * @param y The index of the row..
	 * @return The number of coloured pixels in this row.
	 */
	private static int pixelsInRow(boolean[][] pixels, int y) {
		int number = 0;
		for (int i = 0; i < pixels.length; i++)
			if (pixels[i][y])
				 number++;
		return number;
	}

	/**
	 * Returns whether the pixel row specified by the given index y in the given
	 * array pixels is empty or not.
	 * 
	 * @param pixels The two-dimensional array of pixels.
	 * @param y The index of the row to be checked.
	 * @return Whether the row is empty.
	 */
	private static boolean rowEmpty(boolean[][] pixels, int y) {
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i][y]) 
				return false;
		}
		return true;
	}


	/**
	 * Returns whether the pixel column specified by the given index x in the 
	 * given array pixels is empty or not.
	 * 
	 * @param pixels The two-dimensional array of pixels.
	 * @param x The index of the column to be checked.
	 * @return Whether the column is empty.
	 */
	private static boolean columnEmpty(boolean[][] pixels, int x) {
		for (int j = 0; j < pixels[x].length; j++) {
			if (pixels[x][j])
				return false;
		}
		return true;
	}

	/**
	 * Removes blank lines or columns at top, bottom, left and right sides of 
	 * the binary table representing the current symbol.
	 * 
	 * @param pixels The initial two-dimensional array of pixels.
	 * 
	 * @return A trimmed two-dimensional array of pixels.
	 */
	protected static boolean[][] removeMargins(boolean[][] pixels) {
		int start_y = 0, end_y = pixels[0].length - 1;
		boolean[][] symbol;

		// looking for first non-empty row
		while (start_y < pixels[0].length && rowEmpty(pixels, start_y))
			start_y++;
		
		// found a space character, return
		if (start_y >= pixels[0].length)
			return pixels;
		
		// looking for the last non-empty row
		while (rowEmpty(pixels, end_y))
			end_y--;

		// copying the sub-array containing the symbol
		int length_y = end_y - start_y + 1;
		symbol = new boolean[pixels.length][length_y];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(pixels[i], start_y, symbol[i], 0, length_y);
		}
		return symbol;
	}
	
	/**
	 * Calculates the average width of a character in the line corresponding to
	 * the given horizontal histogram.
	 * @param hHistogram The horizontal histogram.
	 * @return The average width of a character in this line.
	 */
	private static int computeAverageWidth(Vector<Integer> hHistogram) {
		int avg = 0;
		int height = 0;
		
		// total number of pixels
		for (int i : hHistogram)
			avg += i;
		// average number of pixels per row
		avg /= hHistogram.size();
		// number of rows having a number of pixels superior to average
		// i.e. 'average' estimated height of the line 
		for (int i : hHistogram)
			if (i > avg)
				height++;
		// returns the estimated width of a character in the line
		return (int) (height * HEIGHT_TO_WIDTH);
	}
}


