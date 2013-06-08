package analysis.recognition.neuralnetwork;

import java.util.Random;

import error.analysis.recognition.neuralnetwork.NeuronException;

public class Neuron {
	
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
			this.synapticWeights[i] = Math.abs(randGen.nextFloat());
		}
	}

	public Neuron(float synapticWeights[]) throws NeuronException{
		this.value = 0;
		if (synapticWeights.length == 0){
			throw new NeuronException("In call to the constructor of Neuron(float[]), the");
		}
		this.synapticWeights = synapticWeights;
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	public void computeValue(Layer previousLayer){
		this.value = this.activationFunction(
				this.cartesianProduct(previousLayer));
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
		beta = Math.max(Float.MIN_VALUE, beta);
		return (float)(1.0/(1.0+Math.exp(-beta*x)));
	}
	
	private float cartesianProduct(Layer previousLayer){
		float res = 0;
		// TODO Auto-generated method stub
		return res;
	}
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

	public float getValue() {
		return value;
	}

}
