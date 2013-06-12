package data;

import java.util.Iterator;

import error.data.BadInstanceException;

/**
 * This class defines a block component.
 * An instance of <code>Block</code> is meant to be contained in an instance of
 * <code>Page</code> and may contain instances of <code>Line</code>.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public abstract class Block {

	/**
	 * Adds a line to the block.
	 * 
	 * @param l The line to be added.
	 * @throws BadInstanceException Thrown if the given instance of the line is 
	 * 		not compatible with the instance of the block.
	 */
	public abstract void addLine(Line l) throws BadInstanceException;
	
	/** 
	 * Returns an iterator over the lines contained in the current block.
	 * 
	 * @return An iterator over the lines contained in the current block.
	 */
	public abstract Iterator<Line> getIterator();

}
