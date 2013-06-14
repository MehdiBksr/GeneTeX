package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Page;
import error.data.BadInstanceException;

/**
 * This class defines the page component of the structure containing the 
 * image chunks related to the splitting step.
 * An instance of <code>SplitPage</code> contains instances of 
 * <code>SplitBlock</code>.
 * 
 * @see {@link Page}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class SplitPage extends Page {
	
    /* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** The blocks contained in the page. */
	private Vector<Block> blocks = new Vector<Block>();

    /* ************************************************************************
     *                                METHODS                                 * 
     ************************************************************************ */
	
	/**
	 * Adds a block to the page.
	 * 
	 * @param b The block to be added
	 * @throws StructuredInSplittedException 
	 * @see data.Page#addBlock(data.Block)
	 */
	@Override
	public void addBlock(Block b) throws BadInstanceException {
		if (!(b instanceof SplitBlock))
			throw new BadInstanceException(SplitBlock.class, b.getClass());
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
	
	/* ************************************************************************
     *                               ACCESSORS                                * 
     ************************************************************************ */
	
	/**
	 * Gets the block contained in the page at the specified position.
	 * 
	 * @param index The position of the block to be returned.
	 * @return The block at the specified position.
	 */
	public Block getBlock(int index) {
		return blocks.get(index);
	}	

}
