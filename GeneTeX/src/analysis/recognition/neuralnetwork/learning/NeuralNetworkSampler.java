package analysis.recognition.neuralnetwork.learning;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import util.Displayer;
import util.Utility;
import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.split.BasicSplitter;
import analysis.split.Splitter;
import data.Block;
import data.Line;
import data.PreprocessedImage;
import data.Symbol;
import data.contentdata.Token;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;

/**
 * This class allows to get token samples for learning from scanned images
 * following a given pattern.
 * A sample is an image containing a sequence of symbols written in a given
 * order. Knowing this order allows to split the symbols and save the resulting
 * image in the corresponding directory in the learning data.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */

/* ************************************************************************
 *                              CONSTRUCTORS                              * 
 ************************************************************************ */

/* ************************************************************************
 *                                 METHODS                                * 
 ************************************************************************ */

public class NeuralNetworkSampler {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Vector<Token> tokenOrder = new Vector<Token>();
		tokenOrder.add(Token.UPPER_CASE_J);
		tokenOrder.add(Token.LOWER_CASE_E);
		tokenOrder.add(Token.LOWER_CASE_S);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_I);
		tokenOrder.add(Token.LOWER_CASE_S);
		tokenOrder.add(Token.LOWER_CASE_L);
		tokenOrder.add(Token.QUOTE);
		tokenOrder.add(Token.LOWER_CASE_ALPHA);
		sampleByGivenOrder(tokenOrder, "images/testGeneTeXexact.png");

		

	}
	
	
/* ************************************************************************
 *                            PRIVATE FUNCTIONS                           * 
 ************************************************************************ */
	
	/** 
	 * Samples the image knowing the symbols it contains are sorted in
	 *  a given order.
	 *  This sampling method should only be used if the sampled image does
	 *  not respect the order of apparition of the tokens in the Token
	 *  enumeration.
	 *  
	 * @param tokenOrder a Vector of Token giving the order of apparition
	 * of the symbols in the sampled image.
	 * @param fileName the name of the image file to be sampled.
	 * @throws IOException 
	 */
	private static void sampleByGivenOrder(Vector<Token> tokenOrder, String fileName) throws IOException {
		BufferedImage image = ImageLoader.load(fileName);
		
		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage binaryImage = proc.preprocess(image);
		
		Splitter splitter = new BasicSplitter();
		SplittedPage splittedImage = splitter.split(binaryImage);
		
		Iterator<Token> itToken = tokenOrder.iterator();
		
		Iterator<Block> itBlock = splittedImage.getIterator();
		while (itBlock.hasNext()) {
			SplittedBlock splittedBlock = (SplittedBlock)itBlock.next();
			Iterator<Line> itLine = splittedBlock.getIterator();
			while (itLine.hasNext()) {
				SplittedLine splittedLine = (SplittedLine)itLine.next();
				Iterator<Symbol> itSymbol = splittedLine.getIterator();
				while (itSymbol.hasNext()) {
					SplittedSymbol splittedSymbol = (SplittedSymbol)itSymbol.next();
					BufferedImage bufferedSymbol = Utility.toBI(new PreprocessedImage(splittedSymbol.getBinary()));
					@SuppressWarnings("unused")
					Displayer displayer = new Displayer(bufferedSymbol);
					String directoryName = itToken.next().getSampleDirectory();
					
					Random random = new Random();
					Integer randomInt = random.nextInt(2000000000);
					File saveFile = new File("learningdata/" + directoryName + randomInt.toString() + ".png");
					
					System.out.println(saveFile.getAbsolutePath());
					
					//ImageIO.write(bufferedSymbol, "png", saveFile);
					
				}
			}
			
		}
		
	}
	
	/**
	 * Samples the image knowing the symbols it contains are in the same order
	 * than the tokens in the Token enumeration.
	 * This sampling method is more generic and should be used in priority.
	 * 
	 * @param fileName the name of the image file to be sampled.
	 */
	private void sampleByTokenOrder(String fileName) {
		
	}

}
