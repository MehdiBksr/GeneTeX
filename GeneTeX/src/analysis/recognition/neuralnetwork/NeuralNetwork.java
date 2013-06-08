package analysis.recognition.neuralnetwork;

import java.util.Vector;

import data.contentdata.StructuredSymbol;
import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;

public class NeuralNetwork {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	private Primitive primitives;
	
	private Vector<NeuronLayer> neuronLayers;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public NeuralNetwork() {
		this.primitives = new Primitive();
		this.neuronLayers = new Vector<NeuronLayer>();
		this.neuronLayers.add(new NeuronLayer());
		this.neuronLayers.add(new NeuronLayer());
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
	public void addNewNeuron(int layerIndex) throws NeuralNetworkException {
		if (layerIndex>=this.neuronLayers.size()){
			throw new NeuralNetworkException("In call to NeuralNetwork." +
					"addNewNeuron(layerIndex), layerIndex is out of bound");
		}
//		this.neuronLayers.get(layerIndex).addNeuron(new Neuron(130));
	}
	
	public StructuredSymbol recognise(SplittedSymbol symbol) {
		// TODO Auto-generated method stub
		return null;
	}
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
