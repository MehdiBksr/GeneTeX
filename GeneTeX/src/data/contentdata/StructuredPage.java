package data.contentdata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Page;

public class StructuredPage extends Page {
	
	private Vector<Block> blocks = new Vector<Block>();

	/**
	 * Adds a block to the page.
	 * 
	 * @param b The block to be added
	 * @see data.Page#addBlock(data.Block)
	 */
	@Override
	public void addBlock(Block b) {
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
