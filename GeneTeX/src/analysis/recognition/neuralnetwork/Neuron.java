package analysis.recognition.neuralnetwork;

import java.io.Serializable;
import java.util.Random;

import error.analysis.recognition.neuralnetwork.NeuronException;

@SuppressWarnings("serial")
public class Neuron implements Serializable {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	private float value;
	
	private float synapticWeights[];
	
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public Neuron(int previousLayerSize){
		this.value = 0;
		this.synapticWeights = new float[previousLayerSize + 1];
		Random randGen = new Random();
		for (int i=0; i<=previousLayerSize; i++) {
			this.synapticWeights[i] = randGen.nextFloat();
		}
	}

	public Neuron(float synapticWeights[]) throws NeuronException{
		this.value = 0;
		if (synapticWeights==null || synapticWeights.length==0){
			throw new NeuronException("In call to the constructor of " +
					"Neuron(float[] synapticsWeights), the parameter is null" +
					" or of size 0.");
		}
		this.synapticWeights = synapticWeights;
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	public void computeValue(Layer previousLayer) throws NeuronException{
		if (previousLayer == null) {
			throw new NeuronException("In call to function" +
					" neuron.computeValue(Layer), the parameter is null.");
		}
		if (previousLayer.size() + 1 != this.synapticWeights.length){
			throw new NeuronException("In call to function" +
					" neuron.computeValue(Layer), the parameter is not " +
					"synapticWeights.length - 1.");
		}
		this.value = this.activationFunction(
				this.scalarProduct(previousLayer));
	}

	public void resetValue(){
		this.value = 0;
	}
	
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

	private float activationFunction(float x){
		// sigmoidal function of parameter beta > 0
		double beta = 1.0;
		return (float)(1.0/(1.0+Math.exp(-beta*x)));
	}
	
	private float scalarProduct(Layer previousLayer){
		int size = previousLayer.size();
		float res = -this.synapticWeights[size];
		for (int i = 0; i < size; i++) {
			res += this.synapticWeights[i]*previousLayer.getValue(i);
		}
		return res;
	}
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

	public float getValue() {
		return value;
	}

}
