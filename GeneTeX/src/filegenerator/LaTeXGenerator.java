package filegenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import data.Block;
import data.Page;
import data.Symbol;
import data.Line;
import data.contentdata.StructuredSymbol;
import error.data.BadInstanceException;
import error.filegenerator.BadFileNameException;

public class LaTeXGenerator implements FileGenerator {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Unique instance. */
	private static final LaTeXGenerator instance = new LaTeXGenerator();
	/** BufferedWriter allowing to write in the output tex file */
	private BufferedWriter out;
	
	private static final String LB = System.lineSeparator();
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	private LaTeXGenerator() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public void generate(Page p, String destinationFileName) 
			throws BadFileNameException, IOException, BadInstanceException {
		
		Vector<Page> v = new Vector<Page>();
		v.add(p);
		generate(v, destinationFileName);
	}
	
	public void generate(Collection<Page> pages, String destinationFileName) 
			throws BadFileNameException, IOException, BadInstanceException {
		
		openOutputFile(destinationFileName);
		writeHeader();
		writePages(pages);
		writeFooter();
		
		out.close();
	}
    
    /* ************************************************************************
     *                          STATIC FUNCTIONS                              * 
     ************************************************************************ */
    
	public static LaTeXGenerator getInstance() {
		return instance;
	}
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
	
	private void openOutputFile(String destinationFileName) 
			throws BadFileNameException {
		
		File f = new File(destinationFileName);
		
		if (f.exists()) {
			throw new BadFileNameException(destinationFileName);
		}
		
		try {
			out = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			throw new BadFileNameException(destinationFileName);
		}
	}
	
	private void writeHeader() throws IOException {
		out.write(LB + "\\begin{document}");
	}
	
	private void writeFooter() throws IOException {
		out.write(LB + "\\end{document}");
	}
	
	private void writePages(Collection<Page> c) throws IOException, BadInstanceException {
		Iterator<Page> pages = c.iterator();
		while (pages.hasNext()) {
			Page p = pages.next();
			writeBlocks(p);
			out.write(LB + "\newpage");
		}
	}
	
	private void writeBlocks(Page p) throws IOException, BadInstanceException {
		Iterator<Block> blocks = p.getIterator();
		
		while (blocks.hasNext()) {
			Block b = blocks.next();
			out.write(LB + "\\section{");
			writeLines(b);
			out.write(LB + "}");
		}
	}
	
	private void writeLines(Block b) throws IOException, BadInstanceException {
		Iterator<Line> lines = b.getIterator();
		
		while (lines.hasNext()) {
			Line l = lines.next();
			out.write(LB);
			writeSymbols(l);
		}
	}
	
	private void writeSymbols(Line l) throws IOException, BadInstanceException {
		Iterator<Symbol> symbols = l.getIterator();
		
		while (symbols.hasNext()) {
			if (!(symbols.next() instanceof StructuredSymbol)) 
				throw new BadInstanceException(symbols.next().getClass(), 
						StructuredSymbol.class);
			StructuredSymbol s = (StructuredSymbol) symbols.next();
			out.write(s.getToken().toLatex());
		}
	}
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
