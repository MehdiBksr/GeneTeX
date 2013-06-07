package analysis.split;

import data.PreprocessedImage;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;

public class BasicSplitter implements Splitter {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/* ************************************************************************
     *                                METHODS                                 * 
     ************************************************************************ */
	
	public SplittedPage primarySegmentation(PreprocessedImage preprocessedPage) {
		return null;
	}
	
	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
	
	private SplittedLine getNextLine(PreprocessedImage preprocessedPage, int currentPixelLine) {
		return null;
	}
	
	private SplittedSymbol getNextSymbol(PreprocessedImage preprocessedLine, int currentPixelColumn) {
		return null;
	}

}


