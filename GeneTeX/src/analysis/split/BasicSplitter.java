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
		
		// get the length and starting y position of the current line
		while (lineEmpty(page, start_y))
			start_y++;
		
		while (!lineEmpty(page, length_y))
			length_y++;
		
		if (length_y == 0)
			// no new exploitable line, end of the page
			return null;
		
		l = new SplittedLine(start_y, start_y + length_y - 1);
		
		// generate a sub-array containing the current line only
		for (int i = 0; i < page.length; i++)
			System.arraycopy(page[i], start_y, line[i], 0, i + length_y);
		
		s = getNextSymbol(line, x);
		while (s != null) {
			
			try {
				l.addSymbol(s);
			} catch (BadInstanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			x = s.getLastPixelX() + 2;
			s = getNextSymbol(line, x);
		}
		
		return l;
	}
	
	private SplittedSymbol getNextSymbol(boolean[][] line, int x) {
		SplittedSymbol s;
		return null;
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


