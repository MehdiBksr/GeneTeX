package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Line;
import data.Symbol;
import error.data.BadInstanceException;

public class SplittedLine extends Line {
	
	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */
	
	private int firstPixelY;
	private int lastPixelY;
	
	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */
	
	private Vector<Symbol> symbols = new Vector<Symbol>();

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param l The symbol to be added
	 * @throws StructuredInSplittedException 
	 * @see data.Line#addSymbol(data.Symbol)
	 */
	@Override
	public void addSymbol(Symbol s) throws BadInstanceException {
		if (!(s instanceof SplittedSymbol)) 
			throw new BadInstanceException(SplittedSymbol.class, s.getClass());
		symbols.add(s);
	}
	
	/** 
	 * Returns an iterator over the symbols contained in the current line.
	 * 
	 * @return An iterator over the symbols contained in the current line
	 * @see data.Line#getIterator()
	 */
	@Override
	public Iterator<Symbol> getIterator() {
		return symbols.iterator();
	}
	
	/* ************************************************************************
	 *                             CONSTRUCTORS                               * 
	 ************************************************************************ */
	
	public SplittedLine() { 
		this.firstPixelY 	= 0;
		this.lastPixelY 	= 0;
	}
	
	public SplittedLine(int firstPixelLine, int lastPixelLine) {
		this.firstPixelY 	= firstPixelLine;
		this.lastPixelY 	= lastPixelLine;
	}
	
	/* ************************************************************************
	 *                               MUTATORS                                 * 
	 ************************************************************************ */

	public void setFirstPixelLine(int firstPixelLine) {
		this.firstPixelY = firstPixelLine;
	}
	
	public void setLastPixelLine(int lastPixelLine) {
		this.lastPixelY = lastPixelLine;
	}
	
	/* ************************************************************************
	 *                               ACCESSORS                                * 
	 ************************************************************************ */

	public int getLastPixelLine() {
		return lastPixelY;
	}
	
	public int getFirstPixelLine() {
		return firstPixelY;
	}

}
