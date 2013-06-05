package data;

import java.util.Iterator;

public abstract class Line {

	public abstract void addSymbol(Symbol s);
	
	public abstract Iterator<Symbol> getIterator();
}
