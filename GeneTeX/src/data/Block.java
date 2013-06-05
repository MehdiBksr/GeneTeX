package data;

import java.util.Iterator;

public abstract class Block {

	public abstract void addLine(Line l);

	public abstract Iterator<Line> getIterator();

}
