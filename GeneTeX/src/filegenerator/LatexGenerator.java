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
	
	/** BufferedWriter allowing to write in the output tex file */
	private StringBuilder output = new StringBuilder();
	/** Line break (new line) constant */
	private static final String LB = System.lineSeparator();
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public LatexGenerator() {}
    
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
		
		BufferedWriter out = openFile(destinationFileName);
		writeHeader();
		writePages(pages);
		writeFooter();
		// post-process the text to be written
		replaceOccurrences("$$", "");
		// put the content of the StringBuilder attribute into the dest. file
		out.write(output.toString());
		// flush and close
		out.close();
	}
    
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
	
	private BufferedWriter openFile(String destinationFileName) 
			throws BadFileNameException {
		
		File f = new File(destinationFileName);
		
		if (f.exists()) {
			throw new BadFileNameException(destinationFileName);
		}
		
		try {
			return new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			throw new BadFileNameException(destinationFileName);
		}
	}
	
	private void writeHeader() throws IOException {
		output.append("\\documentclass[11pt, a4paper]{report}" + LB + LB);
		output.append("\\usepackage[french]{babel}" + LB);
		output.append("\\usepackage[utf8]{inputenc}" + LB + LB);
		output.append("\\begin{document}" + LB + LB);
	}
	
	private void writeFooter() throws IOException {
		output.append("\\end{document}");
	}
	
	private void writePages(Collection<Page> c) 
			throws IOException, BadInstanceException {
		
		Iterator<Page> pages = c.iterator();
		while (pages.hasNext()) {
			Page p = pages.next();
			writeBlocks(p);
			if (pages.hasNext())
				output.append("\\newpage" + LB + LB);
		}
	}
	
	private void writeBlocks(Page p) throws IOException, BadInstanceException {
		Iterator<Block> blocks = p.getIterator();
		
		while (blocks.hasNext()) {
			Block b = blocks.next();
			output.append("\\paragraph*{}" + LB + LB);
			writeLines(b);
			output.append(LB);
		}
	}
	
	private void writeLines(Block b) throws IOException, BadInstanceException {
		Iterator<Line> lines = b.getIterator();
		
		while (lines.hasNext()) {
			Line l = lines.next();
			writeSymbols(l);
			if (lines.hasNext())
				output.append("\\\\");
			output.append(LB);
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
				output.append("$" + current.getToken().toLatex() + "$");
			else
				// should not be put between dollars
				output.append(current.getToken().toLatex());
		}
	}
	
	private void replaceOccurrences(String src, String replacement) {
		int i = output.indexOf(src);

		while (i != -1) {
			output.replace(i, i + src.length(), replacement);
			i = output.indexOf(src, i);
		}
	}
    
}
