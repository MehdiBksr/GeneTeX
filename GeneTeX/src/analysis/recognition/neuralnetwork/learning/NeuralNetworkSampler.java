package analysis.recognition.neuralnetwork.learning;

import java.util.Vector;

import data.contentdata.Token;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	 */
	private void sampleByGivenOrder(Vector<Token> tokenOrder) {
		
	}
	
	/**
	 * Samples the image knowing the symbols it contains are in the same order
	 * than the tokens in the Token enumeration.
	 * This sampling method is more generic and should be used in priority.
	 */
	private void sampleByTokenOrder() {
		
	}

}
