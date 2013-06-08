package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;
import error.data.BadInstanceException;

public class BasicSplitter implements Splitter {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/* ************************************************************************
     *                                METHODS                                 * 
     ************************************************************************ */
	
	public SplittedPage primarySegmentation(PreprocessedImage preprocessedPage) {
		
		int y = 0;
		SplittedBlock b = new SplittedBlock();
		System.out.println("getNextLine begin");
		SplittedLine l 	= getNextLine(preprocessedPage.getPixels(), y);
		System.out.println("getNextLine end");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}
	
	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
	
	private SplittedLine getNextLine(boolean[][] page, int y) {

		int x = 0;
		SplittedSymbol s;
		SplittedLine l;
		
		boolean[][] line = new boolean[page.length][];
		
		// true starting position of the line
		int start_y = y;
		// true length of the line (from the starting position) 
		int length_y = 0;
		
		System.out.println("start_y before = " + start_y);

		// get the length and starting y position of the current line
		while (start_y < page[0].length && lineEmpty(page, start_y))
			start_y++;
		
		System.out.println("start_y after = " + start_y);

		
		
		// no new line in the page
		if (start_y == page[0].length) return null;
		
		while ((length_y + start_y < page[0].length) && !lineEmpty(page, length_y + start_y))
			length_y++;
		
		System.out.println("length_y = " + length_y);

		
		if (length_y == 0)
			// no new exploitable line, end of the page
			return null;
		
		l = new SplittedLine(start_y, start_y + length_y - 1);
		
		// generate a sub-array containing the current line only
		for (int i = 0; i < page.length; i++) {
			line[i] = new boolean[length_y];
			System.arraycopy(page[i], start_y, line[i], 0, length_y - 1);
		}
		
		System.out.println("end of sub-array loop");

		System.out.println("getNextSymbol start");
		s = getNextSymbol(line, x);
		System.out.println("getNextSymbol end");
		//while (s != null) {
			
			try {
				l.addSymbol(s);
			} catch (BadInstanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			x = s.getLastPixelX() + 2;
			System.out.println("getNextSymbol start, x = " + x);
			//s = getNextSymbol(line, x);
			System.out.println("getNextSymbol end, x = " + x);
		//}
		
		return l;
	}
	
	private SplittedSymbol getNextSymbol(boolean[][] line, int x) {
		
		int start_x = x;
		int length_x = 0;
		int firstPixelX;
		int firstPixelY;
		
		System.out.println("getNextSymbol - x = " + x);
		//look for the first non-empty column
		while (columnEmpty(line, start_x) && (x < line[0].length)) start_x++;
		
		System.out.println("getNextSymbol - start_x = " + start_x);
		
		//no column found, there is no more symbol in the line
		if (x == line[0].length) return null; 
		
		firstPixelX = start_x;
		firstPixelY = 0;
		
		//finding the end of the column
		while (!columnEmpty(line, start_x + length_x) && (start_x + length_x < line[0].length))
			length_x++;
		
		if (length_x == 0) return null;
		
		//copying the sub-array containing the symbol
		boolean[][] symbol = new boolean[length_x][line[0].length];
		for (int i = 0; i < symbol.length; i++) {
			System.arraycopy(line[start_x+i], 0, symbol[i], 0, line[0].length - 1);
		}
		
		SplittedSymbol s = new SplittedSymbol(symbol, firstPixelX, firstPixelY);
		return s;
	}

	private boolean lineEmpty(boolean[][] pixels, int y) {
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i][y]) 
				return false;
		}
		return true;
	}
	
	private boolean columnEmpty(boolean[][] pixels, int x) {
		for (int j = 0; j < pixels[x].length; j++) {
			if (pixels[x][j])
				return false;
		}
		return true;
	}

}


