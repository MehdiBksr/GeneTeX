package analysis.recognition.neuralnetwork;

import java.util.Vector;

import data.contentdata.StructuredSymbol;
import data.contentdata.Token;
import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;

public class NeuralNetwork {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	private Primitive primitives;
	
	private Vector<NeuronLayer> neuronLayers;
	
	private NeuronLayer outputLayer;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public NeuralNetwork(int s) {
		this.primitives = new Primitive();
		this.neuronLayers = new Vector<NeuronLayer>();
		
		// hidden layer
		NeuronLayer l = new NeuronLayer();
		for (int i=0; i<s; i++) {
			l.addNeuron(new Neuron(this.primitives.size()));
		}
		this.neuronLayers.add(l);

		//filling of the output layer
		this.outputLayer = new NeuronLayer();
		for (Token t : Token.values()){
			this.outputLayer.addNeuron(new OutputNeuron(s, t));
		}
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
	public void addNewNeuron(int layerIndex) throws NeuralNetworkException {
		if (layerIndex>=this.neuronLayers.size()){
			throw new NeuralNetworkException("In call to NeuralNetwork." +
					"addNewNeuron(layerIndex), layerIndex is out of bound");
		}
		this.neuronLayers.get(layerIndex).addNeuron(
				new Neuron(this.primitives.size()));
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
