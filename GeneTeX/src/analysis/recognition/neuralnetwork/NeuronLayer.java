package analysis.recognition.neuralnetwork;

import java.util.Vector;

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
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return the value of the index-th neuron computed. If no element
	 * corresponds to index, it returns 0.
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public float getValue(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void resetValues() {
		// TODO Auto-generated method stub
	}


	public void computeNeuralValues(Layer l){
		// TODO Auto-generated method stub
	}


	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
