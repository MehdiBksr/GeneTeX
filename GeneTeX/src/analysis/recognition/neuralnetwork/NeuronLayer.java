package analysis.recognition.neuralnetwork;

import java.util.Iterator;
import java.util.Vector;

import error.analysis.recognition.neuralnetwork.NeuronException;

/** This class represents a layer in the neural network. It is composed of a given number of neurons
 * and can calculate output values using the output values of the previous layer.
 * 
 * @author Théo Merle
 *
 */
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

	/**
	 * Return the value of the index-th neuron computed. If no element
	 * corresponds to index, it returns 0.
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public float getValue(int index) {
		return this.neurons.get(index).getValue();
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


	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
