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
import data.imagedata.SplitPage;

/** A basic implementation of the analyser.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 * @see analysis.Analyzer
 *
 */
public class BasicAnalyzer implements Analyzer {
	
	/** 
	 * Analyses an image file and builds the StructuredPage containing all its
	 * text information.
	 * It is advised to use absolute paths to access the file.
	 * 
	 * @param fileName The path of the file.
	 * @return The StructuredPage containing the text information of the image.
	 * 
	 */
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
		SplitPage page = splitter.split(binaryImage, true);
		Utility.printVerbose(" done", true);
		
		Utility.printVerbose("Recognizing... ", false);
		Recognizer recognizer = new NeuralNetworkRecognizer();
		Vector<Page> recognizedPages = new Vector<Page>();
		recognizedPages.add(recognizer.recognise(page));
		Utility.printVerbose(" done", true);
		
		return (StructuredPage)recognizedPages.get(0);
		
		
	}

}
