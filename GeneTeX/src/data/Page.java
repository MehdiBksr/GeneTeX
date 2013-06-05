package data;

import java.util.Iterator;

public abstract class Page {
	
	public abstract void addBlock(Block b);
	
	public abstract Iterator<Block> getIterator();
	
}
