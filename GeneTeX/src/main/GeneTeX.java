package main;

import java.util.Vector;

import util.Utility;

import analysis.Analyzer;
import analysis.BasicAnalyzer;
import argument.ArgumentHandler;
import data.Page;
import error.argumenthandler.InvalidCommandLineException;
import filegenerator.FileGenerator;
import filegenerator.LatexGenerator;

public class GeneTeX {
	public static void main(String[] args) throws Exception {

		ArgumentHandler options = ArgumentHandler.getInstance();
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidCommandLineException e) {
			System.out.println(Help.getHelpString());
			return;
		}
		Vector<String> source = options.getSourceFiles();
		String dest = options.getDestinationFile();
		if (options.getHelp()) {
			System.out.println(Help.getHelpString());
			return;
		}
		
		Analyzer analyser = new BasicAnalyzer();
		Vector<Page> recognisedPages = new Vector<Page>();
		recognisedPages.add(analyser.analyse(source.get(0)));

		Utility.printVerbose("Generating LaTeX... ", false);
		FileGenerator latexGenerator = new LatexGenerator();
		latexGenerator.generate(recognisedPages, dest);
		Utility.printVerbose(" done", true);
	}
}
