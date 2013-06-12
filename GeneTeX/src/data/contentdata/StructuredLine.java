package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Line;
import data.Symbol;
import error.data.BadInstanceException;

/**
 * This class defines the line component of the structure containing the tokens 
 * related to the image recognition step.
 * An instance of <code>StructuredLine</code> is contained in an instance of 
 * <code>StructuredBlock</code> and contains instances of 
 * <code>StructuredSymbol</code>.
 * 
 * @see {@link Token}, {@link Line}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class StructuredLine extends Line {

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */
	
	/** The symbols contained in the line. */
	private Vector<Symbol> symbols = new Vector<Symbol>();
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param l The symbol to be added
	 * @throws BadInstanceException 
	 * @see data.Line#addSymbol(data.Symbol)
	 */
	@Override
	public void addSymbol(Symbol s) throws BadInstanceException {
		if (!(s instanceof StructuredSymbol)) 
			throw new BadInstanceException(StructuredSymbol.class, s.getClass());
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

}
