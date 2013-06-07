package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

import error.analysis.recognition.neuralnetwork.ComputePrimitivesErrorMessages;

@SuppressWarnings("serial")
public class ComputePrimitivesException extends Exception {

    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public ComputePrimitivesException(String mess) {
		super(MessageFormat.format("{0}{1}",
				ComputePrimitivesErrorMessages.NULL_ARGUMENT_MESSAGE, mess));
	}
}
