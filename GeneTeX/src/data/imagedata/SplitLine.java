package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Line;
import data.Symbol;
import error.data.BadInstanceException;

/**
 * This class defines the line component of the structure containing the 
 * image chunks related to the splitting step.
 * An instance of <code>SplitLine</code> is contained in an instance of
 * <code>SplitBlock</code> and contains instances of 
 * <code>SplitSymbol</code>.
 * 
 * @see {@link Line}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class SplitLine extends Line {
	
	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */
	
	/** Index of the first pixel row of this line in the page. */
	private int firstPixelY;
	/** Index of the last pixel row of this line in the page. */
	private int lastPixelY;
	/** Size of  the line. */
	private int lineWidth;

	/** The symbols contained in the line. */
	private Vector<Symbol> symbols = new Vector<Symbol>();
	
	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param l The symbol to be added
	 * @throws StructuredInSplittedException 
	 * @see data.Line#addSymbol(data.Symbol)
	 */
	@Override
	public void addSymbol(Symbol s) throws BadInstanceException {
		if (!(s instanceof SplitSymbol)) 
			throw new BadInstanceException(SplitSymbol.class, s.getClass());
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
	
	/**
	 * Creates a line.
	 */
	public SplitLine() { 
		this.firstPixelY 	= 0;
		this.lastPixelY 	= 0;
		this.lineWidth		= 0;
	}
	
	/**
	 * Creates a line, given the index of the first pixel row of this line in 
	 * the page and the index of the last pixel row of this line in the page.
	 * 
	 * @param firstPixelLine Index of the first pixel row.
	 * @param lastPixelLine Index of the last pixel row.
	 */
	public SplitLine(int firstPixelLine, int lastPixelLine, int lineWidth) {
		this.firstPixelY 	= firstPixelLine;
		this.lastPixelY 	= lastPixelLine;
		this.lineWidth		= lineWidth;
	}

	/* ************************************************************************
	 *                               ACCESSORS                                * 
	 ************************************************************************ */

	/**
	 * Get the index of the first pixel row of this line in the page.
	 * 
	 * @return The index of the first pixel row.
	 */
	public int getLastPixelLine() {
		return lastPixelY;
	}
	
	/**
	 * Set the index of the first pixel row of this line in the page.
	 * 
	 * @param y The index of the first pixel row.
	 */
	public void setFirstPixelRow(int y) {
		this.firstPixelY = y;
	}
	
	/**
	 * Get the index of the last pixel row of this line in the page.
	 * 
	 * @return The index of the last pixel row.
	 */
	public int getFirstPixelLine() {
		return firstPixelY;
	}
	
	/**
	 * Set the index of the last pixel row of this line in the page.
	 * 
	 * @param y THe index of the last pixel row.
	 */
	public void setLastPixelRow(int y) {
		this.lastPixelY = y;
	}

	/**
	 * Get the size of the line.
	 * @return return the width of the line.
	 */
	public int getLineWidth() {
		return lineWidth;
	}

}
