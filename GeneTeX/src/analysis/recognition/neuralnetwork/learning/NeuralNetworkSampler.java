package analysis.recognition.neuralnetwork.learning;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

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
import data.imagedata.SplitBlock;
import data.imagedata.SplitLine;
import data.imagedata.SplitPage;
import data.imagedata.SplitSymbol;

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
		// TODO this main is there for testing purposes and shouldn't remain
		Vector<Token> tokenOrder = setStupidTokenOrder();
		sampleByGivenOrder(tokenOrder, "demo/portez.jpg");



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
		
		//Displayer hhh = new Displayer(Utility.toBI(binaryImage));
				
		Splitter splitter = new BasicSplitter();
		SplitPage splittedImage = splitter.split(binaryImage, false);
		

		Iterator<Token> itToken = tokenOrder.iterator();

		Iterator<Block> itBlock = splittedImage.getIterator();
		while (itBlock.hasNext()) {
			SplitBlock splittedBlock = (SplitBlock)itBlock.next();
			Iterator<Line> itLine = splittedBlock.getIterator();
			while (itLine.hasNext()) {
				SplitLine splittedLine = (SplitLine)itLine.next();
				Iterator<Symbol> itSymbol = splittedLine.getIterator();
				while (itSymbol.hasNext()) {
					SplitSymbol splittedSymbol = (SplitSymbol)itSymbol.next();
					BufferedImage bufferedSymbol = Utility.toBI(new PreprocessedImage(splittedSymbol.getBinary()));
					if (bufferedSymbol.getWidth() < 10 && bufferedSymbol.getHeight() < 10) continue;
					
					//Displayer displayer = new Displayer(bufferedSymbol);
					String directoryName = itToken.next().getSampleDirectory();

					Random random = new Random();
					Integer randomInt = random.nextInt(2000000000);
					
					File sampleDirectory = new File("learningdata/" + directoryName);
					if (!sampleDirectory.exists()) {
						System.out.println("Directory " + sampleDirectory.getAbsolutePath() + " doesn't exist, creating it");						
						sampleDirectory.mkdirs();
					}
					
					File saveFile = new File("learningdata/" + directoryName + "/" + directoryName + randomInt.toString() + ".png");

					System.out.println("Writing " + saveFile.getAbsolutePath());

					ImageIO.write(bufferedSymbol, "png", saveFile);

				}
			}

		}

	}
	
	private static Vector<Token> setStupidTokenOrder() {
		Vector<Token> tokenOrder = new Vector<Token>();
		//for (Token T : Token.values()) {
		//	tokenOrder.add(T);
		//}
		tokenOrder.add(Token.UPPER_CASE_P);
		tokenOrder.add(Token.LOWER_CASE_O);
		tokenOrder.add(Token.LOWER_CASE_R);
		tokenOrder.add(Token.LOWER_CASE_T);
		tokenOrder.add(Token.LOWER_CASE_E);
		tokenOrder.add(Token.LOWER_CASE_Z);
		tokenOrder.add(Token.LOWER_CASE_C);
		tokenOrder.add(Token.LOWER_CASE_E);
		tokenOrder.add(Token.LOWER_CASE_V);
		tokenOrder.add(Token.LOWER_CASE_I);
		tokenOrder.add(Token.LOWER_CASE_E);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_X);
		tokenOrder.add(Token.LOWER_CASE_W);
		tokenOrder.add(Token.LOWER_CASE_H);
		tokenOrder.add(Token.LOWER_CASE_I);
		tokenOrder.add(Token.LOWER_CASE_S);
		tokenOrder.add(Token.LOWER_CASE_K);
		tokenOrder.add(Token.LOWER_CASE_Y);
		tokenOrder.add(Token.LOWER_CASE_A);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_J);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_G);
		tokenOrder.add(Token.LOWER_CASE_E);
		tokenOrder.add(Token.LOWER_CASE_B);
		tokenOrder.add(Token.LOWER_CASE_L);
		tokenOrder.add(Token.LOWER_CASE_O);
		tokenOrder.add(Token.LOWER_CASE_N);
		tokenOrder.add(Token.LOWER_CASE_D);
		tokenOrder.add(Token.LOWER_CASE_Q);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_I);
		tokenOrder.add(Token.LOWER_CASE_F);
		tokenOrder.add(Token.LOWER_CASE_U);
		tokenOrder.add(Token.LOWER_CASE_M);
		tokenOrder.add(Token.LOWER_CASE_E);



		
	return tokenOrder;	
	}
	
}
