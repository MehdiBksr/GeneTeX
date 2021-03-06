package analysis.recognition.neuralnetwork;

import java.io.Serializable;
import java.util.Random;

import error.analysis.recognition.neuralnetwork.NeuronException;

/** This class represents the neuron, it is the smallest unit in a neural network.
 * It is composed of a value and of an array of synaptic weights used for
 * computation of its value.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 *
 */
@SuppressWarnings("serial")
public class Neuron implements Serializable {

	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	/**
	 * The value computed by the neuron. When nothing has been computed yet, its
	 * value is 0.
	 */
	protected float value;
	
	/** 
	 * The synaptic weights of the neuron.
	 */
	protected float synapticWeights[];
	
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	/**
	 * Builds a neuron with previousLayerSize + 1 synaptic weights on its
	 * entries.
	 * 
	 * @param previousLayerSize The number of neurons in the previous layer.
	 */
	public Neuron(int previousLayerSize){

		this.value = 0;

		// Gives the synaptic weight a random value.
		this.synapticWeights = new float[previousLayerSize + 1];
		Random randGen = new Random();
		float n = 10*previousLayerSize;
		for (int i=0; i<=previousLayerSize; i++) {
			this.synapticWeights[i] = ((float)randGen.nextInt((int)n))/n;
		}
		
		/* Standardize the weights, so that they are between -5 and 5, to avoid
		 * extreme starting values which can cause the learning to fail */
		float max = 0;
		float min = 0;
		for (int i=0; i<=previousLayerSize; i++) {
			if (this.synapticWeights[i]>max) {
				max = this.synapticWeights[i];
			} else if (this.synapticWeights[i]<min) {
				min -= this.synapticWeights[i];
			}
		}
		for (int i=0; i<=previousLayerSize; i++) {
			this.synapticWeights[i] =
					(10*(this.synapticWeights[i]-min)/(max-min) - 5);
		}		

		// Standardize the sum, so that sum(abs(synaptic weigth)) <= 2
		for (int i=0; i<=previousLayerSize; i++) {
			this.synapticWeights[i] *= 2.0/(5.0 * (float)(this.synapticWeights.length));	
		}
	}

	/**
	 * Builds a neuron with given synaptic weights.
	 * 
	 * @param synapticWeights The wanted synaptic weights.
	 * @throws NeuronException
	 */
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

	/** Computes the value of the neuron using the values of the layer before the
	 * one containing this neuron.
	 * 
	 * @param previousLayer The previous layer in the neural network.
	 * @throws NeuronException
	 */
	public void computeValue(Layer previousLayer) throws NeuronException{
		// check if the argument is correct.
		if (previousLayer == null) {
			throw new NeuronException("In call to function" +
					" neuron.computeValue(Layer), the parameter is null.");
		}
		if (previousLayer.size() + 1 != this.synapticWeights.length){
			throw new NeuronException("In call to function" +
					" neuron.computeValue(Layer), the parameter is not " +
					"synapticWeights.length - 1.");
		}
		
		// compute the value of the neuron
		this.value = this.activationFunction(
				this.scalarProduct(previousLayer));
	}

	/** Resets the value of this neuron by putting it back to 0. */
	public void resetValue(){
		this.value = 0;
	}
	
	/** Adapts the synaptic weights of this neuron depending on the input values
	 *  from the previous layer, the weighted deltas from the next layer's
	 *  gradient and the adaptation rate.
	 * 
	 * @param previousLayer          The previous layer in the neural network.
	 * @param nextLayerWeightedDelta The weighted delta from the next Layer.
	 * @param alpha                  The adaptation rate.
	 * @throws NeuronException
	 * @return The weighted deltas from this neuron.
	 */
	public float[] adaptSynapticWeigths(Layer previousLayer,
			float nextLayerWeightedDelta, float alpha)
					throws NeuronException{
		
		// check if the previous layer is corresponding to the current neuron.
		if (this.synapticWeights.length != previousLayer.size() + 1) {
			throw new NeuronException("In call to " +
					"neuron.adaptSynapticWeigths, the size of the previous " +
					"layer is not the number of synaptics weights minus 1.");
		}
		
		// delta is used to modify the weights. Careful, if this.value is near
		// 0 or 1, the modification is insignificant. 
		float delta = this.value*(1-this.value)*nextLayerWeightedDelta;
		// compute the weighted deltas, that will be used to adapt the previous
		// layer. The last case of this array contains the contribution to the
		// computation of the gradient's norm.
		float[] resultingWeightedDeltas =
				new float[this.synapticWeights.length];
		for (int i=0; i<resultingWeightedDeltas.length-1; i++) {
			resultingWeightedDeltas[i] = this.synapticWeights[i]*delta;
			resultingWeightedDeltas[resultingWeightedDeltas.length-1] =
					resultingWeightedDeltas[i]*resultingWeightedDeltas[i];
			this.synapticWeights[i] -= alpha*delta*previousLayer.getValue(i);
		}
		resultingWeightedDeltas[resultingWeightedDeltas.length-1] =
				this.synapticWeights[this.synapticWeights.length-1] * 
				this.synapticWeights[this.synapticWeights.length-1];
		this.synapticWeights[this.synapticWeights.length-1] -= alpha*delta*-1;
		return resultingWeightedDeltas;
	}
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

	/** The activation function, allowing to get a non-linear measure on the input
	 * image.
	 * 
	 * @param x The input value of the neuron
	 * @return the value of the activation function for x
	 */
	private float activationFunction(float x){
		// sigmoidal function of parameter beta > 0
		// For beta = 1 and x near 5, the result is nearly 1.
		// For beta = 1 and x near -5, the result is nearly 0.
		double beta = 1.0;
		return (float)(1.0/(1.0+Math.exp(-beta*x)));
	}
	
	/** Calculates the linear combination of all the output values of
	 * the previous layer.
	 * @param previousLayer The previous layer in the neural network. 
	 * @return The scalar product of those values.
	 */
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
	/**	Returns the current value of this neuron.
	 * 
	 * @return the value of the neuron. */
	public float getValue() {
		return value;
	}

}
