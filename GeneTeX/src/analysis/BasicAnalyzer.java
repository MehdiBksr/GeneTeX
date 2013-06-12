package analysis;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.util.Vector;

import util.Utility;
import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.recognition.NeuralNetworkRecognizer;
import analysis.recognition.Recognizer;
import analysis.split.BasicSplitter;
import analysis.split.Splitter;
import data.Page;
import data.PreprocessedImage;
import data.contentdata.StructuredPage;
import data.imagedata.SplittedPage;

public class BasicAnalyzer implements Analyzer {
	
	public StructuredPage analyse(String fileName) throws Exception {
		
		Utility.printVerbose("Loading image...", false);
		BufferedImage image = ImageLoader.load(fileName);
		Utility.printVerbose(" done", true);
		
		Utility.printVerbose("Preprocessing image...", false);
		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage binaryImage = proc.preprocess(image);
		Utility.printVerbose(" done", true);
		
		Utility.printVerbose("Segmenting preprocessed image...", false);
		Splitter splitter = new BasicSplitter();
		SplittedPage page = splitter.split(binaryImage);
		Utility.printVerbose(" done", true);
		
		Utility.printVerbose("Recognizing... ", false);
		Recognizer recognizer = new NeuralNetworkRecognizer();
		Vector<Page> recognizedPages = new Vector<Page>();
		recognizedPages.add(recognizer.recognise(page));
		Utility.printVerbose(" done", true);
		
		return (StructuredPage)recognizedPages.get(0);
		
		
	}

}
