package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class NeuronLayerException extends Exception {

	/* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public NeuronLayerException(String mess) {
		super(MessageFormat.format("{0}", mess));
	}

}
