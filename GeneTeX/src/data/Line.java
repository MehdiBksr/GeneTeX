package data;

import java.util.Iterator;

import error.data.BadInstanceException;

/**
 * This class defines a line component.
 * An instance of <code>Line</code> is meant to be contained in an instance of
 * <code>Block</code> and may contain instances of <code>Symbol</code>.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public abstract class Line {

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param s The symbol to be added.
	 * @throws BadInstanceException Thrown if the given instance of the symbol is 
	 * 		not compatible with the instance of the line.
	 */
	public abstract void addSymbol(Symbol s) throws BadInstanceException;
	
	/** 
	 * Returns an iterator over the symbols contained in the current line.
	 * 
	 * @return An iterator over the symbols contained in the current line.
	 */
	public abstract Iterator<Symbol> getIterator();
}
