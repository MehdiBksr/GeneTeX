package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Line;

import error.data.BadInstanceException;


public class StructuredBlock extends Block {
	
	private Vector<Line> lines = new Vector<Line>();

	/**
	 * Adds a line to the block.
	 * 
	 * @param l The line to be added
	 * @throws BadInstanceException 
	 * @see data.Block#addLine(data.Line)
	 */
	@Override
	public void addLine(Line l) throws BadInstanceException {
		if (!(l instanceof StructuredLine)) 
			throw new BadInstanceException(StructuredLine.class, l.getClass());
		lines.add(l);
	}
	
	/** 
	 * Returns an iterator over the lines contained in the current block.
	 * 
	 * @return An iterator over the lines contained in the current block
	 * @see data.Block#getIterator()
	 */
	@Override
	public Iterator<Line> getIterator() {
		return lines.iterator();
	}


}
