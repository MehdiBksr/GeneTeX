package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;
import error.data.BadInstanceException;

/** Basic version of the splitting module. It doesn't detect text blocks,
 * assuming the page is composed of only one paragraph.
 * This version is only able to detect lines and characters 
 * written in script style, which can be easily separated using projection 
 * histograms (it means that characters and lines do not overlap). *
 */
public class BasicSplitter implements Splitter {

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */

	/* ************************************************************************
	 *                                METHODS                                 * 
	 ************************************************************************ */

	/** The primary segmentation method. It detects lines in the page 
	 * one after another, and tries to detect symbols in the line immediately.
	 * When a line (resp. symbol) is detected, the returned structure is
	 * updated.
	 * 
	 * @param page The PreprocessedImage containing the text to be split
	 * @return The SplittedPage containing the structure of the text and the
	 * symbols that have been recognised.
	 */
	public static SplittedPage primarySegmentation(PreprocessedImage page) {

		// looking for first line
		int y = 0;
		SplittedBlock b = new SplittedBlock();
		SplittedLine l 	= getNextLine(page.getPixels(), y);
		SplittedPage p 	= new SplittedPage();

		// as long as there are lines in the page
		while (l != null) {			
			// adding the line
			try {
				b.addLine(l);
			} catch (BadInstanceException e) {
				e.printStackTrace();
			}
			// start again on the first unchecked pixel line
			// (l.getLastPixelLine() + 1 is empty)
			y = l.getLastPixelLine() + 2;
			l = getNextLine(page.getPixels(), y);
		}
		
		// adding the block
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

	/** Gets the next undiscovered line, and splitting the symbols contained
	 * in it.
	 * @param page The PreprocessedImage containing the text to be split
	 * @param y The first undiscovered pixel line in the page
	 * @return A SplittedLine containing every information about the symbols
	 * contained in the line, or null if all the lines in the page have already
	 * been discovered.
	 */
	private static SplittedLine getNextLine(boolean[][] page, int y) {

		int x = 0;
		SplittedSymbol s;
		SplittedLine l;

		boolean[][] line = new boolean[page.length][];

		// true starting position of the line
		int start_y = y;
		//  true length of the line (from the starting position) 
		int length_y = 0;

		// get the length and starting y position of the current line
		while (start_y < page[0].length && lineEmpty(page, start_y))
			start_y++;
		// end of the page: no new line in the page
		if (start_y >= page[0].length) return null;

		// computing the length of the line
		while ((length_y + start_y < page[0].length) 
				&& !lineEmpty(page, length_y + start_y))
			length_y++;
		
		// no new exploitable line, end of the page
		if (length_y == 0) return null;

		l = new SplittedLine(start_y, start_y + length_y - 1);
		// generate a sub-array containing the current line only
		for (int i = 0; i < page.length; i++) {
			line[i] = new boolean[length_y];
			System.arraycopy(page[i], start_y, line[i], 0, length_y - 1);
		}

		// looking for first symbol in the line
		s = getNextSymbol(line, x);
		if (s != null) {
			// removing blanks around the symbol
			s.setFirstPixelY(start_y);
			boolean[][] removedMargins = removeMargins(s.getBinary());
			s.setBinary(removedMargins);
		}
		
		// as long as there are undiscovered symbols
		while (s != null) {

			// adding the symbol
			try {
				l.addSymbol(s);
			} catch (BadInstanceException e) {
				e.printStackTrace();
			}

			// resuming search on the first unknown column
			// (s.getLastPixelX() + 1 is empty)
			x = s.getLastPixelX() + 2;
			s = getNextSymbol(line, x);
			if (s != null) {
				// removing blanks around the symbol
				s.setFirstPixelY(start_y);
				boolean[][] removedMargins = removeMargins(s.getBinary());
				s.setBinary(removedMargins);
			}
		}
		return l;
	}

	/** Gets the next undiscovered symbol in the line, and information
	 * about it.
	 * 
	 * @param line The binary representation of the line
	 * @param x The first undiscovered pixel column in the line
	 * @return A SplittedSymbol representing the next symbol in the line,
	 * or null if there are no undiscovered symbols.
	 */
	private static SplittedSymbol getNextSymbol(boolean[][] line, int x) {

		int start_x = x;
		int length_x = 0;

		// look for the first non-empty column
		while ((start_x < line.length) && columnEmpty(line, start_x))
			start_x++;
		// end of the line: no new symbol in the line
		if (start_x >= line.length) return null; 

		// finding the end of the column
		while ((start_x + length_x < line.length) 
				&& !columnEmpty(line, start_x + length_x))
			length_x++;

		if (length_x == 0) {
			return null;
		}

		// copying the sub-array containing the symbol
		boolean[][] symbol = new boolean[length_x][line[0].length];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(line[start_x+i], 0, symbol[i], 0, line[0].length);
		}

		SplittedSymbol s = new SplittedSymbol(symbol, start_x, 0);
		return s;
	}

	private static boolean lineEmpty(boolean[][] pixels, int y) {
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i][y]) 
				return false;
		}
		return true;
	}

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
	 */
	private static boolean[][] removeMargins(boolean[][] pixels) {
		int start_x = 0, length_x = 0;
		int start_y = 0, length_y = 0;
		boolean[][] symbol;

		while (columnEmpty(pixels, start_x))
			start_x++;

		while (start_x + length_x < pixels.length && 
				!columnEmpty(pixels, start_x + length_x))
			length_x++;

		while (lineEmpty(pixels, start_y))
			start_y++;

		while (start_y + length_y < pixels[0].length &&
				!lineEmpty(pixels, start_y + length_y))
			length_y++;

		// copying the sub-array containing the symbol
		symbol = new boolean[length_x][length_y];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(pixels[i], start_y, symbol[i], 0, length_y);
		}

		return symbol;
	}

}


