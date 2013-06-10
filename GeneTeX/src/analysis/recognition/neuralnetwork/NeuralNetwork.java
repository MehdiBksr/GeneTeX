package analysis.recognition.neuralnetwork;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import data.contentdata.StructuredSymbol;
import data.contentdata.Token;
import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;
import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

@SuppressWarnings("serial")
public class NeuralNetwork implements Serializable {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	private Primitive primitives;
	
	private LinkedList<NeuronLayer> neuronLayers;
	
	private NeuronLayer outputLayer;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	public NeuralNetwork(int s) {
		this.primitives = new Primitive();
		this.neuronLayers = new LinkedList<NeuronLayer>();
		
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
	
	public StructuredSymbol recognise(SplittedSymbol symbol)
			throws ComputePrimitivesException, NeuronException {
		this.primitives.computePrimitives(symbol);
		Iterator<NeuronLayer> it = this.neuronLayers.iterator();
		Layer previousLayer = this.primitives;
		while (it.hasNext()) {
			NeuronLayer currentLayer = it.next();
			currentLayer.computeNeuralValues(previousLayer);
			previousLayer = currentLayer;
		}
		this.outputLayer.computeNeuralValues(previousLayer);
		OutputNeuron chosenNeuron = (OutputNeuron) this.outputLayer.getNeuron(0);
		for (int i=1; i<this.outputLayer.size(); i++) {
			Neuron currentNeuron = this.outputLayer.getNeuron(i);
			if (currentNeuron.getValue() > chosenNeuron.getValue()) {
				chosenNeuron = (OutputNeuron) currentNeuron;
			}
		}
		return new StructuredSymbol(chosenNeuron.getToken());
	}
	
	/** Adapt the synaptic weights of all neurons in this neural network
	 *  depending on the token expected and the adaptation rate.
	 * 
	 * @param expectedToken           The expected token.
	 * @param alpha                   The adaptation rate.
	 * @throws NeuronException
	 * @throws NeuralNetworkException 
	 * @throws NeuronLayerException
	 * @return The squared norm of quadratic error's gradient.
	 */
	public float adaptSynapticWeights (Token expectedToken, float alpha)
			throws NeuronException, NeuralNetworkException, NeuronLayerException {

		// initialisation of gradientNorm, previousLayer and weightedDeltas
		float gradientNorm = 0;
		ListIterator<NeuronLayer> it = this.neuronLayers.listIterator(this.neuronLayers.size());
		Layer previousLayer;
		if (it.hasPrevious()) {
			previousLayer = it.previous();
		} else {
			previousLayer = this.primitives;
		}
		float weightedDeltas[] =
				new float[previousLayer.size()];
		for (int i=0; i< weightedDeltas.length; i++){
			weightedDeltas[i] = 0;
		}
		
		// adaptation of the output layer.
		for(int outputNeuronIndex=0; outputNeuronIndex<this.outputLayer.size();
				outputNeuronIndex++) {
			//adapt the current output neuron and saves the corresponding
			// gradient parts
			float neuronWeightedDeltas[] =
					((OutputNeuron)this.outputLayer.getNeuron(
							outputNeuronIndex)).
							adaptSynapticWeigths(previousLayer, expectedToken,
									alpha);
			if (weightedDeltas.length != neuronWeightedDeltas.length - 1) {
				throw new NeuralNetworkException("In call to" +
					" neuralnetwork.adaptSynapticWeights(expectedToken," +
					" alpha), an output neuron returned the wrong number of " +
					"weighted deltas.");
			}
			// update the weighted deltas for adaptation of hidden layer and
			// the gradient norm of the quadratic error.
			gradientNorm += weightedDeltas[weightedDeltas.length-1];
			for (int i=0; i<weightedDeltas.length-1; i++) {
				weightedDeltas[i] += neuronWeightedDeltas[i];
				gradientNorm += neuronWeightedDeltas[i]*neuronWeightedDeltas[i];
			}
		}

		//adapt all hidden layer except the one having the primitives as input.
		while (it.hasPrevious()) {
			NeuronLayer currentlayer = (NeuronLayer) previousLayer;
			previousLayer = it.previous();
			weightedDeltas = currentlayer.adaptSynapticWeights(previousLayer,
					weightedDeltas, alpha);
		}
		
		//if there was hidden layer, adapt the one having the primitives as
		// input.
		if (previousLayer instanceof NeuronLayer) {
			((NeuronLayer) previousLayer).adaptSynapticWeights(this.primitives,
					weightedDeltas, alpha);
		}
		
		return gradientNorm;
	}
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */

}
