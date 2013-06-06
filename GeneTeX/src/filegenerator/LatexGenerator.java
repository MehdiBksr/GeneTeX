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

public class LatexGenerator implements FileGenerator {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
	
	/** Unique instance. */
	private static final LatexGenerator instance = new LatexGenerator();
	/** BufferedWriter allowing to write in the output tex file */
	private BufferedWriter out;
	
	private static final String LB = System.lineSeparator();
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	private LatexGenerator() {}
    
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
    
	public static LatexGenerator getInstance() {
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
		out.write("\\documentclass[11pt, a4paper, oneside]{report}" + LB + LB);
		out.write("\\usepackage[french]{babel}" + LB);
		out.write("\\usepackage[utf8]{inputenc}" + LB + LB);
		out.write("\\begin{document}" + LB + LB);
	}
	
	private void writeFooter() throws IOException {
		out.write("\\end{document}");
	}
	
	private void writePages(Collection<Page> c) 
			throws IOException, BadInstanceException {
		
		Iterator<Page> pages = c.iterator();
		while (pages.hasNext()) {
			Page p = pages.next();
			writeBlocks(p);
			if (pages.hasNext())
				out.write("\\newpage" + LB + LB);
		}
	}
	
	private void writeBlocks(Page p) throws IOException, BadInstanceException {
		Iterator<Block> blocks = p.getIterator();
		
		while (blocks.hasNext()) {
			Block b = blocks.next();
			out.write("\\paragraph*{}" + LB + LB);
			writeLines(b);
			out.write(LB);
		}
	}
	
	private void writeLines(Block b) throws IOException, BadInstanceException {
		Iterator<Line> lines = b.getIterator();
		
		while (lines.hasNext()) {
			Line l = lines.next();
			writeSymbols(l);
			if (lines.hasNext())
				out.write("\\\\");
			out.write(LB);
		}
	}
	
	private void writeSymbols(Line l) throws IOException, BadInstanceException {
		Iterator<Symbol> symbols = l.getIterator();
		Symbol s;
		
		while (symbols.hasNext()) {
			s = symbols.next();
			
			if (!(s instanceof StructuredSymbol))
				// bad input collection
				throw new BadInstanceException(symbols.next().getClass(), 
						StructuredSymbol.class);
			
			StructuredSymbol current = (StructuredSymbol) s;
			if (current.getMath())
				// mathematical expression: should be put between dollars
				out.write("$" + current.getToken().toLatex() + "$");
			else
				// should not be put between dollars
				out.write(current.getToken().toLatex());
		}
	}
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
