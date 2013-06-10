package analysis.recognition.neuralnetwork;

import java.util.Iterator;
import java.util.Vector;

import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

/** This class represents a layer in the neural network. It is composed of a given number of neurons
 * and can calculate output values using the output values of the previous layer.
 * 
 * @author Th�o Merle
 *
 */
@SuppressWarnings("serial")
public class NeuronLayer implements Layer {


	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	/** The neurons composing the layer */
	private Vector<Neuron> neurons;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public NeuronLayer(){
		this.neurons = new Vector<Neuron>();
	}
	
	public NeuronLayer(Vector<Neuron> neurons) {
		super();
		this.neurons = neurons;
	}

	/* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */

	/* structure modifications ************************************************/

/** Adds a neuron to the neuron layer
 * 
 * @param n the neuron to be added
 */
	public void addNeuron(Neuron n){
		this.neurons.add(n);
	}

	
	/* neurons manipulations ************************************************/
	
	/**
	 * Return the size of the NeuronLayer
	 * @return number of neurons contained in the layer
	 */
	public int size() {
		return this.neurons.size();
	}

	
	public float getValue(int index) {
		return this.neurons.get(index).getValue();
	}
	
	/**
	 * Return the index-th neuron. If no element
	 * corresponds to index, it throws an exception.
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public Neuron getNeuron(int index) {
		return this.neurons.get(index);
	}

	/** Resets the values of all the neurons in the layer, putting them
	 * to 0.
	 */
	@Override
	public void resetValues() {
		Iterator<Neuron> it = this.neurons.iterator();
		while (it.hasNext()) {
			it.next().resetValue();
		}
	}

	/** Calculates the values for the neurons in this layer using the values 
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

	/** Adapt the synaptic weights of all neurons in this layer depending
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
		if (previousLayer == null) {
			throw new NeuronLayerException("In call to" +
					" neuronLayer.adaptSynapticWeights(previousLayer," +
					" nextLayerWeightedDeltas, alpha), previousLayer is null.");
		}
		if (this.size()!=nextLayerWeightedDeltas.length) {
			throw new NeuronLayerException("In call to" +
					" neuronLayer.adaptSynapticWeights(previousLayer," +
					" nextLayerWeightedDeltas, alpha), the current layer and" +
					" the weighted deltas comnig from the next layer do not" +
					" have the same size.");
		}
		
		float weightedDeltas[] = new float[previousLayer.size()];
		for (int i=0; i< weightedDeltas.length; i++){
			weightedDeltas[i] = 0;
		}
		
		Iterator<Neuron> it = this.neurons.iterator();
		int i = 0;
		while (it.hasNext()) {
			Neuron currentNeuron = it.next();
			float [] neuronWeightedDeltas = currentNeuron.adaptSynapticWeigths(
					previousLayer, nextLayerWeightedDeltas[i], alpha);
			if (neuronWeightedDeltas.length != weightedDeltas.length) {
				throw new NeuronLayerException("In call to" +
						" neuronLayer.adaptSynapticWeights(previousLayer," +
						" nextLayerWeightedDeltas, alpha), a neuron returned" +
						" the wrong number of weighted deltas.");
			}
			for (int j=0; j< weightedDeltas.length; j++){
				weightedDeltas[j] += neuronWeightedDeltas[j];
			}
			i++;
		}

		return weightedDeltas;
	}

	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
