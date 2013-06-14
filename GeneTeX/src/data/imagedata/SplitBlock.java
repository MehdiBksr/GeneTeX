package data.imagedata;

import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Line;
import error.data.BadInstanceException;

/**
 * This class defines the block component of the structure containing the 
 * image chunks related to the splitting step.
 * An instance of <code>SplitBlock</code> is contained in an instance of
 * <code>SplitPage</code> and contains instances of 
 * <code>SplitLine</code>.
 * 
 * @see {@link Block}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class SplitBlock extends Block {
	
    /* ************************************************************************
     *                                ATTRIBUTES                              * 
     ************************************************************************ */
	
	/** The lines contained in the block. */
	private Vector<Line> lines = new Vector<Line>();
	
    /* ************************************************************************
     *                                  METHODS                               * 
     ************************************************************************ */
	
	@Override
	public void addLine(Line l) throws BadInstanceException {
		if (!(l instanceof SplitLine)) 
			throw new BadInstanceException(SplitLine.class, l.getClass());
		lines.add(l);
	}
	
	@Override
	public Iterator<Line> getIterator() {
		return lines.iterator();
	}


}
