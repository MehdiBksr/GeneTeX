package error.analysis.recognition.neuralnetwork;

import java.text.MessageFormat;

import error.analysis.recognition.neuralnetwork.ComputePrimitivesErrorMessages;

/** Exception raised in case of an error during primitive computation.
 * 
 * @author Théo Merle
 *
 */
@SuppressWarnings("serial")
public class ComputePrimitivesException extends Exception {

    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	
	public ComputePrimitivesException(String mess) {
		super(MessageFormat.format("{0}{1}",
				ComputePrimitivesErrorMessages.COMPUTE_PRIMITIVES_MESSAGE, mess));
	}
}
