/**
 * 
 */
package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

/**
 * @author merlet
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
