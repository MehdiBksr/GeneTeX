package analysis.split;

import java.util.Vector;

import data.imagedata.*;
import error.data.BadInstanceException;

public class BasicSplitter implements Splitter {
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public SplittedPage primarySegmentation(boolean[][] preprocessedPage) throws BadInstanceException {
		SplittedPage page = new SplittedPage();
		Vector<SplittedBlock> blocks = splitInBlocks(preprocessedPage);
		// Our image has only one block here
		page.addBlock(blocks.get(0));
		// We look for the lines in the page		
		int lineIndex;
		for (lineIndex = 0; lineIndex < preprocessedPage[0].length; lineIndex++) {
			if (countPixels(preprocessedPage[0]) != 0) {
				
			}
		}
		
		return page;		
	}
	
	public Vector<SplittedBlock> splitInBlocks(boolean[][] preprocessedPage) {
		return null;
	}
	
	public Vector<SplittedLine> splitInLines(boolean[][] preprocessedBlock) {
		return null;
	}
	
	public Vector<SplittedSymbol> splitInSymbols(boolean[][] preprocessedLine) {
		return null;
	}
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
	private int countPixels(boolean[] pixelLine) {
		int pixelCount = 0;
		for (int i = 0; i < pixelLine.length; i++) {
			if (pixelLine[i]) pixelCount++;
		}
		return pixelCount;
	}
	
	private int lookForLine(boolean[][] preprocessedPage) {
		return 0;
		
	}
	
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
