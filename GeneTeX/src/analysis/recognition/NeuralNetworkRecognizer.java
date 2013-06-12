package analysis.recognition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import analysis.recognition.neuralnetwork.NeuralNetwork;
import data.Block;
import data.Line;
import data.Symbol;
import data.contentdata.StructuredBlock;
import data.contentdata.StructuredLine;
import data.contentdata.StructuredPage;
import data.contentdata.StructuredSymbol;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;

/**
 * This class loads a trained neural network and analyse the split data to
 * recognise the content of the document
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 */
public class NeuralNetworkRecognizer implements Recognizer {

	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */

	/**
	 * Create the content data corresponding to the split data given.
	 * The current version recognize the Token contained 
	 * 
	 * @param page Informations coming from the splitting.
	 * @return The content data obtained after analysing the split data
	 * @throws Exception
	 */
	public StructuredPage recognise(SplittedPage page)
			throws Exception {
		NeuralNetwork network;

		// load the trained neural network.
		try {
			FileInputStream getLearningData =
					new FileInputStream("learning.object");
			ObjectInputStream objLearningData =
					new ObjectInputStream(getLearningData);
			network = (NeuralNetwork)objLearningData.readObject();
			objLearningData.close();
			getLearningData.close();
		} catch (FileNotFoundException e) {
			throw
			new Exception("Can't find a configured neural network, aborting");
		}

		// create the content data corresponding to the split data.
		StructuredPage structuredPage = new StructuredPage();
		Iterator<Block> itBlock = page.getIterator();
		while (itBlock.hasNext()) {
			SplittedBlock block = (SplittedBlock)itBlock.next();
			StructuredBlock structuredBlock = new StructuredBlock();
			structuredPage.addBlock(structuredBlock);
			Iterator<Line> itLine = block.getIterator();
			while (itLine.hasNext()) {
				SplittedLine line = (SplittedLine)itLine.next();
				StructuredLine structuredLine = new StructuredLine();
				structuredBlock.addLine(structuredLine);
				Iterator<Symbol> itSymbol = line.getIterator();
				while (itSymbol.hasNext()) {
					SplittedSymbol symbol = (SplittedSymbol)itSymbol.next();
					StructuredSymbol structuredSymbol = network.recognise(symbol);
					structuredLine.addSymbol(structuredSymbol);
				}
			}
		}
		return structuredPage; 
	}

}
