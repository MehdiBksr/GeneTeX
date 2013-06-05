package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Line;

public class SplittedBlock extends Block {
	
    /* ************************************************************************
     *                                ATTRIBUTES                              * 
     ************************************************************************ */
	
	private Vector<Line> lines = new Vector<Line>();

	
    /* ************************************************************************
     *                                  METHODS                               * 
     ************************************************************************ */
	
	/**
	 * Adds a line to the block.
	 * 
	 * @param l The line to be added
	 * @see data.Block#addLine(data.Line)
	 */
	@Override
	public void addLine(Line l) {
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
