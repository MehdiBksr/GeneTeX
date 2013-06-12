package analysis.split;

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
	
	/* ************************************************************************
     *                                METHODS                                 * 
     ************************************************************************ */
	
	public SplittedPage split(PreprocessedImage preprocessedPage) {
		
		int y = 0;
		SplittedBlock b = new SplittedBlock();
		SplittedLine l 	= getNextLine(preprocessedPage.getPixels(), y);
		SplittedPage p 	= new SplittedPage();
		
		while (l != null) {			
			try {
				b.addLine(l);
			} catch (BadInstanceException e) {
				e.printStackTrace();
			}
			
			y = l.getLastPixelLine() + 2;
			l = getNextLine(preprocessedPage.getPixels(), y);
		}
		
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

		int x = 0;
		SplittedSymbol s;
		SplittedLine l;
		
		boolean[][] line = new boolean[page.length][];
		
		// true starting position of the line
		int start_y = y;
		// true length of the line (from the starting position) 
		int length_y = 0;
		
		// get the length and starting y position of the current line
		while (start_y < page[0].length && rowEmpty(page, start_y))
			start_y++;
		// end of the page: no new line in the page
		if (start_y >= page[0].length) return null;
		
		while ((length_y + start_y < page[0].length) && !rowEmpty(page, length_y + start_y))
			length_y++;
		
		if (length_y == 0)
			// no new exploitable line, end of the page
			return null;
		
		l = new SplittedLine(start_y, start_y + length_y - 1);
		
		// generate a sub-array containing the current line only
		for (int i = 0; i < page.length; i++) {
			line[i] = new boolean[length_y];
			System.arraycopy(page[i], start_y, line[i], 0, length_y - 1);
		}
		
		s = getNextSymbol(line, x);
		if (s != null) {
			s.setFirstPixelY(start_y);
			boolean[][] removedMargins = removeMargins(s.getBinary());
			s.setBinary(removedMargins);
		}
		
		while (s != null) {
			
			try {
				l.addSymbol(s);
			} catch (BadInstanceException e) {
				e.printStackTrace();
			}
			
			x = s.getLastPixelX() + 2;
			s = getNextSymbol(line, x);
			if (s != null) {
				s.setFirstPixelY(start_y);
				boolean[][] removedMargins = removeMargins(s.getBinary());
				s.setBinary(removedMargins);
			}
		}
		
		return l;
	}
	
	/**
	 * Get the next symbol of the currently processed line. 
	 *  
	 * @param page The page being currently split.
	 * @param y The index of the pixel row starting from which the next line is
	 * 			to be found.
	 * @return The line as a <code>SplitLine</code>.
	 */
	private static SplittedSymbol getNextSymbol(boolean[][] line, int x) {
		
		int start_x = x;
		int length_x = 0;
		
		//look for the first non-empty column
		while ((start_x < line.length) && columnEmpty(line, start_x))
			start_x++;
		// end of the line: no new symbol in the line
		if (start_x >= line.length) return null; 
		
		//finding the end of the column
		while ((start_x + length_x < line.length) && !columnEmpty(line, start_x + length_x))
			length_x++;
		
		if (length_x == 0) {
			return null;
		}
		
		//copying the sub-array containing the symbol
		boolean[][] symbol = new boolean[length_x][line[0].length];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(line[start_x+i], 0, symbol[i], 0, line[0].length);
		}
		
		SplittedSymbol s = new SplittedSymbol(symbol, start_x, 0);
		return s;
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
	private static boolean[][] removeMargins(boolean[][] pixels) {
		int start_x = 0, length_x = 0;
		int start_y = 0, length_y = 0;
		boolean[][] symbol;
		
		while (columnEmpty(pixels, start_x))
			start_x++;
		
		while (start_x + length_x < pixels.length && 
				!columnEmpty(pixels, start_x + length_x))
			length_x++;
		
		while (rowEmpty(pixels, start_y))
			start_y++;
		
		while (start_y + length_y < pixels[0].length &&
				!rowEmpty(pixels, start_y + length_y))
			length_y++;
		
		//copying the sub-array containing the symbol
		symbol = new boolean[length_x][length_y];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(pixels[i], start_y, symbol[i], 0, length_y);
		}
		
		return symbol;
	}

}


