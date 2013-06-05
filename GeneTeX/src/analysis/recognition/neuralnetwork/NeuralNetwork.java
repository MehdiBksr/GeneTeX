package analysis.recognition.neuralnetwork;

import java.util.Vector;

import data.contentdata.StructuredSymbol;
import data.imagedata.SplittedSymbol;

public class NeuralNetwork {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	private Primitive primitives;
	
	private Vector<NeuronLayer> neuronLayers;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public NeuralNetwork(){
		this.primitives = new Primitive();
		this.neuronLayers = new Vector<NeuronLayer>();
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
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
