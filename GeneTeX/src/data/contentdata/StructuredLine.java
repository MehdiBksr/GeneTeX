package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Line;
import data.Symbol;
import error.split.SplittedInStructuredException;

public class StructuredLine extends Line {
	
	private Vector<Symbol> symbols = new Vector<Symbol>();

	/**
	 * Adds a symbol to the line.
	 * 
	 * @param l The symbol to be added
	 * @throws SplittedInStructuredException 
	 * @see data.Line#addSymbol(data.Symbol)
	 */
	@Override
	public void addSymbol(Symbol s) throws SplittedInStructuredException {
		if (!(s instanceof StructuredSymbol)) throw new SplittedInStructuredException();
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
