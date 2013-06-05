package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Line;
import data.Symbol;

public class SplittedLine extends Line {
	
	private Vector<Symbol> symbols = new Vector<Symbol>();

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param l The symbol to be added
	 * @see data.Line#addSymbol(data.Symbol)
	 */
	@Override
	public void addSymbol(Symbol s) {
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
