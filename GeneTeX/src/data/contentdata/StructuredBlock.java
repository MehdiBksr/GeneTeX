package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Line;

import error.split.SplittedInStructuredException;


public class StructuredBlock extends Block {
	
	private Vector<Line> lines = new Vector<Line>();

	/**
	 * Adds a line to the block.
	 * 
	 * @param l The line to be added
	 * @throws SplittedInStructuredException 
	 * @see data.Block#addLine(data.Line)
	 */
	@Override
	public void addLine(Line l) throws SplittedInStructuredException {
		if (!(l instanceof StructuredLine)) throw new SplittedInStructuredException();
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
