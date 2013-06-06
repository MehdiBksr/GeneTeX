package filegenerator;

import java.util.Vector;

import data.Page;
import data.contentdata.StructuredBlock;
import data.contentdata.StructuredLine;
import data.contentdata.StructuredPage;
import data.contentdata.StructuredSymbol;
import data.contentdata.Token;

public class TestLatexGenerator {

	public static void main(String[] args) throws Exception {
		Vector<Page> pages 	= new Vector<Page>();
		StructuredPage p 	= new StructuredPage();
		StructuredBlock b 	= new StructuredBlock();
		StructuredLine l 	= new StructuredLine();

		l.addSymbol(new StructuredSymbol(Token.ZERO, false));
		l.addSymbol(new StructuredSymbol(Token.ONE, false));
		l.addSymbol(new StructuredSymbol(Token.TWO, false));
		l.addSymbol(new StructuredSymbol(Token.THREE, false));
		l.addSymbol(new StructuredSymbol(Token.FOUR, false));
		l.addSymbol(new StructuredSymbol(Token.FIVE, false));
		l.addSymbol(new StructuredSymbol(Token.SIX, false));
		l.addSymbol(new StructuredSymbol(Token.SEVEN, false));
		l.addSymbol(new StructuredSymbol(Token.EIGHT, false));
		l.addSymbol(new StructuredSymbol(Token.NINE, false));
		l.addSymbol(new StructuredSymbol(Token.SPACE, false));
		l.addSymbol(new StructuredSymbol(Token.LOWER_CASE_PI, true));
		l.addSymbol(new StructuredSymbol(Token.SPACE, false));
		l.addSymbol(new StructuredSymbol(Token.LOWER_CASE_RHO, true));
		l.addSymbol(new StructuredSymbol(Token.SPACE, false));
		l.addSymbol(new StructuredSymbol(Token.LOWER_CASE_SIGMA, true));

		b.addLine(l);
		b.addLine(l);

		p.addBlock(b);
		p.addBlock(b);

		pages.add(p);
		pages.add(p);
		
		LatexGenerator.getInstance().generate(pages, "output.tex");
	}

}
