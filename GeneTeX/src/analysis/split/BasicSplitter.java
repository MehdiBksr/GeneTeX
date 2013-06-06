package analysis.split;

import java.util.Vector;

import data.PreprocessedImage;
import data.imagedata.*;
import error.split.StructuredInSplittedException;

public class BasicSplitter implements Splitter {
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public SplittedPage primarySegmentation(PreprocessedImage preprocessedPage) {
		SplittedPage page = new SplittedPage();
		Vector<SplittedBlock> blocks = splitInBlocks(preprocessedPage);
		// Our image has only one block here
		try {
			page.addBlock(blocks.get(0));
		} catch (StructuredInSplittedException e) {
			
			e.printStackTrace();
		}
		// We look for the lines in the page		
		
		
		return page;		
	}
	
	public Vector<SplittedBlock> splitInBlocks(PreprocessedImage preprocessedPage) {
		Vector<SplittedBlock> blocks = new Vector<SplittedBlock>();
		SplittedBlock onlyBlock = new SplittedBlock();
		blocks.add(onlyBlock);
		
		return blocks;
	}
	
	public Vector<SplittedLine> splitInLines(PreprocessedImage preprocessedBlock) {
		
		return null;
	}
	
	public Vector<SplittedSymbol> splitInSymbols(PreprocessedImage preprocessedLine) {
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
	
	private int lookForLine(PreprocessedImage image) {
		return 0;
		
	}
	
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
