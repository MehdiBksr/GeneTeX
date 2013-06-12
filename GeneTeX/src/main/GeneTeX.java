package main;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.util.Vector;

import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.recognition.NeuralNetworkRecognizer;
import analysis.split.BasicSplitter;
import argument.ArgumentHandler;
import data.Page;
import data.PreprocessedImage;
import data.imagedata.SplittedPage;
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

		printVerbose("Loading image...", false);
		BufferedImage image = ImageLoader.load(source.get(0));
		printVerbose(" done", true);
		
		printVerbose("Preprocessing image...", false);
		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage binaryImage = proc.preprocess(image);
		printVerbose(" done", true);
		
		printVerbose("Segmenting preprocessed image...", false);
		SplittedPage page = BasicSplitter.split(binaryImage);
		printVerbose(" done", true);

		printVerbose("Recognizing... ", false);
		Vector<Page> recognizedPages = new Vector<Page>();
		recognizedPages.add(NeuralNetworkRecognizer.readPage(page));
		printVerbose(" done", true);

		printVerbose("Generating LaTeX... ", false);
		FileGenerator latexGenerator = new LatexGenerator();
		latexGenerator.generate(recognizedPages, dest);
		printVerbose(" done", true);
	}

	private static void printVerbose(String message, boolean over) {
		ArgumentHandler options = ArgumentHandler.getInstance();
		if (options.getVerbose() && over) System.out.println(message);
		else if (options.getVerbose()) System.out.print(message);
	}
}
