package data;

import java.util.Iterator;

import error.data.BadInstanceException;

/**
 * This class defines a page component.
 * An instance of <code>Page</code> may contain instances of <code>Block</code>.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public abstract class Page {

	/**
	 * Adds a block to the page.
	 * 
	 * @param b The block to be added.
	 * @throws BadInstanceException Thrown if the given instance of the block is 
	 * 		not compatible with the instance of the page.
	 */
	public abstract void addBlock(Block b) throws BadInstanceException;
	
	/** 
	 * Returns an iterator over the blocks contained in the current page.
	 * 
	 * @return An iterator over the blocks contained in the current page.
	 */
	public abstract Iterator<Block> getIterator();
	
}
