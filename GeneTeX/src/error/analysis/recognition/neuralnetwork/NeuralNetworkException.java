/**
 * 
 */
package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

/** Exception raised when an error occurs with the neural network.
 * 
 * @author Théo Merle
 *
 */
@SuppressWarnings("serial")
public class NeuralNetworkException extends Exception {

	/* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public NeuralNetworkException(String mess) {
		super(MessageFormat.format("{0}", mess));
	}

}
