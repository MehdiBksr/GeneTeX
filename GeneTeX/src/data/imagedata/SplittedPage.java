package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Page;
import data.contentdata.StructuredBlock;
import error.split.StructuredInSplittedException;

public class SplittedPage extends Page {
	
    /* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
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
	public void addBlock(Block b) throws StructuredInSplittedException {
		if (!(b instanceof StructuredBlock)) throw new StructuredInSplittedException();
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
	
	public Block getBlock(int index) {
		return blocks.get(index);
	}	

}
