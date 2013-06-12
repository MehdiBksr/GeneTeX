package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Page;
import error.data.BadInstanceException;

/**
 * This class defines the page component of the structure containing the tokens 
 * related to the image recognition step.
 * An instance of <code>StructuredPage</code> contains instances of 
 * <code>StructuredBlock</code>.
 * 
 * @see {@link Token}, {@link Page}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class StructuredPage extends Page {
	
	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */
	
	/** The blocks contained in the page. */
	private Vector<Block> blocks = new Vector<Block>();
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/**
	 * Adds a block to the page.
	 * 
	 * @param b The block to be added
	 * @throws BadInstanceException 
	 * @see data.Page#addBlock(data.Block)
	 */
	@Override
	public void addBlock(Block b) throws BadInstanceException {
		if (!(b instanceof StructuredBlock)) 
			throw new BadInstanceException(StructuredBlock.class, b.getClass());
		blocks.add(b);
	}
	
	/** 
	 * Returns an iterator over the blocks contained in the current page.
	 * 
	 * @return An iterator over the blocks contained in the current page
	 * @see data.Page#getIterator()
	 */
	@Override
	public Iterator<Block> getIterator() {
		return blocks.iterator();
	}


}
