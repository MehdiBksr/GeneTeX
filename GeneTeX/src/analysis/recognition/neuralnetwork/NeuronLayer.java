package analysis.recognition.neuralnetwork;

import java.util.Iterator;
import java.util.Vector;

import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

/** This class represents a layer in the neural network. It is composed of a given number of neurons
 * and can calculate output values using the output values of the previous layer.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 *
 */
@SuppressWarnings("serial")
public class NeuronLayer implements Layer {


	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	/**
	 * The neurons composing the layer
	 */
	private Vector<Neuron> neurons;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	/**
	 * Builds an empty layer. 
	 */
	public NeuronLayer(){
		this.neurons = new Vector<Neuron>();
	}
	
	/**
	 * Builds an layer containing the arguments given as argument.
	 * 
	 *  @param aLayer The neuron wanted in the built layer.
	 */
	public NeuronLayer(Vector<Neuron> aLayer) {
		this.neurons = aLayer;
	}

	/* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/* structure modifications ************************************************/

	/** Adds a neuron to the neuron layer.
	 * 
	 * @param n The neuron to be added.
	 */
	public void addNeuron(Neuron n){
		this.neurons.add(n);
	}

	
	/* neurons manipulations ************************************************/
	
	/**
	 * Return the size of the NeuronLayer
	 * 
	 * @return number of neurons contained in the layer
	 */
	public int size() {
		return this.neurons.size();
	}

	/**
	 * Returns the value of the index-th neuron in the layer. Good execution is
	 * not ensured if index is not between 0 and this.size()-1, included. 
	 */
	public float getValue(int index) {
		return this.neurons.get(index).getValue();
	}
	
	/**
	 * Return the index-th neuron. If no element
	 * corresponds to index, it throws an exception.
	 * 
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public Neuron getNeuron(int index) {
		return this.neurons.get(index);
	}

	/**
	 * Resets the values of all the neurons in the layer, putting them
	 * to 0.
	 */
	@Override
	public void resetValues() {
		Iterator<Neuron> it = this.neurons.iterator();
		while (it.hasNext()) {
			it.next().resetValue();
		}
	}

	/**
	 * Calculates the values for the neurons in this layer using the values 
	 * given by the previous layer.
	 * 
	 * @param l The previous layer in the neural network
	 * @throws NeuronException
	 */
	public void computeNeuralValues(Layer l) throws NeuronException{
		if (l == null){
			throw new NeuronException("In call to function" +
					" layer.computeValues(Layer), the parameter is null.");
		}
		Iterator<Neuron> it = this.neurons.iterator();
		while (it.hasNext()) {
			it.next().computeValue(l);
		}
	}

	/**
	 * Adapt the synaptic weights of all neurons in this layer depending
	 * on the values computed in the previous layer, the weighted deltas
	 * computed by the next layer and the adaptation rate. 
	 * 
	 * @param previousLayer           The previous layer in the neural network.
	 * @param nextLayerWeightedDeltas The weighted deltas from the next layer.
	 * @param alpha                   The adaptation rate.
	 * @throws NeuronException
	 * @throws NeuronLayerException
	 * @return The weighted deltas computed by this layer.
	 */
	public float[] adaptSynapticWeights(Layer previousLayer,
			float[] nextLayerWeightedDeltas, float alpha) 
					throws NeuronLayerException, NeuronException {
		// check if a previous layer is given to the methods. 
		if (previousLayer == null) {
			throw new NeuronLayerException("In call to" +
					" neuronLayer.adaptSynapticWeights(previousLayer," +
					" nextLayerWeightedDeltas, alpha), previousLayer is null.");
		}
		
		// check if the gradient's components coming from the next layer
		// correspond to this layer.
		if (this.size()!=nextLayerWeightedDeltas.length) {
			throw new NeuronLayerException("In call to" +
					" neuronLayer.adaptSynapticWeights(previousLayer," +
					" nextLayerWeightedDeltas, alpha), the current layer and" +
					" the weighted deltas comnig from the next layer do not" +
					" have the same size.");
		}
		
		// Initialised the gradient's components to return.
		float weightedDeltas[] = new float[previousLayer.size()+1];
		for (int i=0; i< weightedDeltas.length; i++){
			weightedDeltas[i] = 0;
		}
		
		// Adapts the neurons of the layer.
		Iterator<Neuron> it = this.neurons.iterator();
		int i = 0;
		while (it.hasNext()) {
			Neuron currentNeuron = it.next();
			float [] neuronWeightedDeltas = currentNeuron.adaptSynapticWeigths(
					previousLayer, nextLayerWeightedDeltas[i], alpha);
			// check the correctness of the gradient's components returned by
			// the neuron.
			if (neuronWeightedDeltas.length != weightedDeltas.length) {
				throw new NeuronLayerException("In call to" +
						" neuronLayer.adaptSynapticWeights(previousLayer," +
						" nextLayerWeightedDeltas, alpha), a neuron returned" +
						" the wrong number of weighted deltas.");
			}
			
			// Update the gradient's components for the previous layer.
			for (int j=0; j< weightedDeltas.length-1; j++){
				weightedDeltas[j] += neuronWeightedDeltas[j];
			}
			// Update the gradient's norm for the next layer
			weightedDeltas[weightedDeltas.length-1] +=
					neuronWeightedDeltas[weightedDeltas.length-1];
			i++;
		}

		return weightedDeltas;
	}

}
