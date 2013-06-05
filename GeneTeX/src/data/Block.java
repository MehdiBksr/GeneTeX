package data;

import java.util.Iterator;

public abstract class Block {

	public abstract void addLine(Line l) throws Exception;

	public abstract Iterator<Line> getIterator();

}
