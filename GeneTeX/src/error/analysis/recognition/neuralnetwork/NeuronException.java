package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

public class NeuronException extends Exception {

	/* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public NeuronException(String mess) {
		super(MessageFormat.format("{0}", mess));
	}

}
