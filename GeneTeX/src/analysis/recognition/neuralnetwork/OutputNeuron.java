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
		if (this.synapticWeights.length != previousLayer.size() + 1) {
			throw new NeuronException("In call to " +
					"neuron.adaptSynapticWeigths, the size of the previous " +
					"layer is not the number of synaptics weights minus 1.");
		}
		float expectedValue = 0;
		if (expectedToken == this.token){
			expectedValue = 1;
		}
		float delta = this.value*(1-this.value)*(this.value - expectedValue);
		float[] resultingWeightedDeltas = this.synapticWeights.clone();
		for (int i=0; i<this.synapticWeights.length-1; i++) {
			this.synapticWeights[i] -= alpha*delta*previousLayer.getValue(i);
			resultingWeightedDeltas[i] *= delta;
		}
		this.synapticWeights[this.synapticWeights.length] -= alpha*delta*-1;
		return resultingWeightedDeltas;
	}
	
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
