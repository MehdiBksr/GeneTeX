package analysis.recognition.neuralnetwork;

import data.contentdata.Token;
import error.analysis.recognition.neuralnetwork.NeuronException;

@SuppressWarnings("serial")
public class OutputNeuron extends Neuron {

	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	private Token token;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */

	public OutputNeuron(int previousLayerSize, Token t) {
		super(previousLayerSize);
		this.token = t;
	}

	public OutputNeuron(float[] synapticWeights, Token t) throws NeuronException {
		super(synapticWeights);
		this.token = t;
	}


	/* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

	public Token getToken() {
		return token;
	}


}
