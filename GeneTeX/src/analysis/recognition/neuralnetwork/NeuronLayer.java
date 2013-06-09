package analysis.recognition.neuralnetwork;

import java.util.Iterator;
import java.util.Vector;

import error.analysis.recognition.neuralnetwork.NeuronException;

public class NeuronLayer implements Layer {


	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
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
	
	/**
	 * Return the index-th neuron. If no element
	 * corresponds to index, it throws an exception.
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public Neuron getNeuron(int index) {
		return this.neurons.get(index);
	}

	@Override
	public void resetValues() {
		Iterator<Neuron> it = this.neurons.iterator();
		while (it.hasNext()) {
			it.next().resetValue();
		}
	}

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
