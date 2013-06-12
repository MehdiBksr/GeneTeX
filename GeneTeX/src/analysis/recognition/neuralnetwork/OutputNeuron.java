package analysis.recognition.neuralnetwork;

import data.contentdata.Token;
import error.analysis.recognition.neuralnetwork.NeuronException;

/** This class represents an output neuron. It is used to recognise a given
 * token.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 *
 */
@SuppressWarnings("serial")
public class OutputNeuron extends Neuron {

	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
   
	/**
	 * The token, this neuron is supposed to recognise. In a perfect case, it
	 * should compute 1 for the recognition of this toke and 0 otherwise.
	 */
	private Token token;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */

	/**
	 * Builds an output neuron with previousLayerSize + 1 synaptic weights on its
	 * entries and the Token t.
	 * 
	 * @param previousLayerSize The number of neurons in the previous layer.
	 * @param t                 The token, this neuron should recognise.
	 */
	public OutputNeuron(int previousLayerSize, Token t) {
		super(previousLayerSize);
		this.token = t;
	}

	/**
	 * Builds a neuron with given synaptic weights.
	 * 
	 * @param previousLayerSize The number of neurons in the previous layer.
	 * @param t                 The token, this neuron should recognise.
	 */
	public OutputNeuron(float[] synapticWeights, Token t) throws NeuronException {
		super(synapticWeights);
		this.token = t;
	}


	/* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/** Adapt the synaptic weights of this neuron depending on the input values
	 *  from the previous layer, the weighted deltas from the next layer and
	 *  the adaptation rate.
	 * 
	 * @param previousLayer The previous layer in the neural network.
	 * @param expectedToken The weighted delta from the next Layer.
	 * @param alpha         The adaptation rate.
	 * @return The weighted deltas from this neuron.
	 */
	public float[] adaptSynapticWeigths(Layer previousLayer,
			Token expectedToken, float alpha)
					throws NeuronException{
		// check if previousLayer does not correspond to this neuron.
		if (this.synapticWeights.length != previousLayer.size() + 1) {
			throw new NeuronException("In call to " +
					"neuron.adaptSynapticWeigths, the size of the previous " +
					"layer is not the number of synaptics weights minus 1.");
		}
		

		// initialises the expected value.
		float expectedValue = 0;
		if (expectedToken == this.token){
			expectedValue = 1;
		}
		// delta is used to modify the weights. Careful, if this.value is near
		// 0 or 1, the modification is insignificant. 
		float delta = this.value*(1-this.value)*(this.value - expectedValue);

		// compute the weighted deltas, that will be used to adapt the previous
		// layer.
		float[] resultingWeightedDeltas = this.synapticWeights.clone();
		for (int i=0; i<this.synapticWeights.length-1; i++) {
			resultingWeightedDeltas[i] *= delta;
			this.synapticWeights[i] -= alpha*delta*previousLayer.getValue(i);
		}
		this.synapticWeights[this.synapticWeights.length-1] -= alpha*delta*-1;
		return resultingWeightedDeltas;
	}
	
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

	/**
	 * @return The token, this neuron should recognise.
	 */
	public Token getToken() {
		return token;
	}


}
