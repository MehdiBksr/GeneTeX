package data;

import java.util.Iterator;

public abstract class Page {
	
	public abstract void addBlock(Block b) throws Exception;
	
	public abstract Iterator<Block> getIterator();
	
}
