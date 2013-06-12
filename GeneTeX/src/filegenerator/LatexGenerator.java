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

/** 
 * A file generator for Latex-format output files.
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class LatexGenerator implements FileGenerator {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	/** A buffer storing the text to be written in the output file. */
	private StringBuilder output = new StringBuilder();
	/** Line break (new line) constant. */
	private static final String LB = System.lineSeparator();
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	public LatexGenerator() {}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/**
	 * Creates the output document and transcribes the given page into it.
	 * 
	 * @param p The page to be transcribed. 
	 * @param destinationFileName The name of the destination file.
	 * @throws BadFileNameException Raised when the given name is a bad name.
	 * @throws IOException Raised when the destination file could not be created.
	 * @throws BadInstanceException Raised when the given {@link Page} 
	 * 		instances are not of type {@link StructuredPage}, or the components
	 * 		are not of the valid type.
	 */
	public void generate(Page p, String destinationFileName) 
			throws BadFileNameException, IOException, BadInstanceException {
		
		Vector<Page> v = new Vector<Page>();
		v.add(p);
		generate(v, destinationFileName);
	}
	
	public void generate(Collection<Page> pages, String destinationFileName) 
			throws BadFileNameException, IOException, BadInstanceException {
		
		
		//TODO : check if file already exists
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
	
	/**
	 * Tries to create and open a file with the given name.
	 * 
	 * @param destinationFileName The name of the file.
	 * 
	 * @return A bufferedWriter allowing file writing.
	 * @throws BadFileNameException Thrown when the specified file name is invalid.
	 */
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
	
	/**
	 * Writes the Latex header.
	 * 
	 * @throws IOException Thrown when an IO error occurred.
	 */
	private void writeHeader() throws IOException {
		output.append("\\documentclass[11pt, a4paper]{report}" + LB + LB);
		output.append("\\usepackage[french]{babel}" + LB);
		output.append("\\usepackage[utf8]{inputenc}" + LB + LB);
		output.append("\\begin{document}" + LB + LB);
	}
	
	/**
	 * Writes the Latex footer.
	 * 
	 * @throws IOException Thrown when an IO error occurred.
	 */
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
	
	/**
	 * Writes the blocks of the given page.
	 * 
	 * @param p The page
	 * @throws IOException Thrown when an IO error occurred.
	 * @throws BadInstanceException Thrown when the blocks are not of a valid type.
	 */
	private void writeBlocks(Page p) throws IOException, BadInstanceException {
		Iterator<Block> blocks = p.getIterator();
		
		while (blocks.hasNext()) {
			Block b = blocks.next();
			output.append("\\paragraph*{}" + LB + LB);
			writeLines(b);
			output.append(LB);
		}
	}
	
	/**
	 * Writes the lines of the given block.
	 * @param b The given block.
	 * @throws IOException Thrown when an IO error occurred.
	 * @throws BadInstanceException Thrown when the lines are not of a valid type.
	 */
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
	
	/**
	 * Writes the symbol of the given line.
	 * 
	 * @param l The given line.
	 * @throws IOException Thrown when an IO error occurred.
	 * @throws BadInstanceException Thrown when the symbols are not of a valid type.
	 */
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
			if (current.getToken().isMath())
				// mathematical expression: should be put between dollars
				output.append("$" + current.getToken().toLatex() + "$");
			else
				// should not be put between dollars
				output.append(current.getToken().toLatex());
		}
	}
	
	/**
	 * Replaces the given string with the given new string in the buffer 
	 * containing  the file to be written.
	 * 
	 * @param src The string to be replaced.
	 * @param replacement The string replacement.
	 */
	private void replaceOccurrences(String src, String replacement) {
		int i = output.indexOf(src);

		while (i != -1) {
			output.replace(i, i + src.length(), replacement);
			i = output.indexOf(src, i);
		}
	}
    
}
