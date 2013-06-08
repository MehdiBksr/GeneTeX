package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class NeuronException extends Exception {

	/* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public NeuronException(String mess) {
		super(MessageFormat.format("{0}", mess));
	}

}
