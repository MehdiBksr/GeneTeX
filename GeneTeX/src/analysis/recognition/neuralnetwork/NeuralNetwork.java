package analysis.recognition.neuralnetwork;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import data.contentdata.StructuredSymbol;
import data.contentdata.Token;
import data.imagedata.SplittedBlock;
import data.imagedata.SplittedLine;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;
import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

/**
 * This class stores the neural network object. It allows to control the
 * structure of the network, recognise a token, or adapt itself given learning
 * data.
 * 
 * @author Mehdi BOUKSARA, Th�o MERLE, Marceau THALGOTT 
 */
@SuppressWarnings("serial")
public class NeuralNetwork implements Serializable {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
	/**
	 * Contains the characteristics extracted from a SplitSymbol.
	 */
	private Primitive primitives;
	
	/**
	 * Contains the hidden layers of neurons.
	 */
	private LinkedList<NeuronLayer> neuronLayers;
	
	/**
	 * Contains the output layer, consisting only of OutputNeuron elements.
	 */
	private NeuronLayer outputLayer;
	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	/**
	 * Builds a network with one hidden layer of s neurons and an output layer
	 * containing one OutputNeuron for each Token that can be recognised.
	 *  
	 * @param s Number of hidden neurons for the layer.
	 */
	public NeuralNetwork(int s) {
		this.primitives = new Primitive();
		this.neuronLayers = new LinkedList<NeuronLayer>();
		
		// building and filling of the hidden layer
		NeuronLayer l = new NeuronLayer();
		for (int i=0; i<s; i++) {
			l.addNeuron(new Neuron(this.primitives.size()));
		}
		this.neuronLayers.add(l);

		// building and filling of the output layer
		this.outputLayer = new NeuronLayer();
		for (Token t : Token.values()){
			this.outputLayer.addNeuron(new OutputNeuron(s, t));
		}
	}
	
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
	/**
	 * Uses the neural network to try to recognise a token.
	 *  
	 * @param symbol An object containing the characteristics returned by a
	 * 					splitting
	 * @return An object containing the token recognised and informations for
	 * 					the file generation
	 */
	public StructuredSymbol recognise(SplittedSymbol symbol)
			throws ComputePrimitivesException, NeuronException {

		// Extract primitives from the SplitSymbol
		this.primitives.computePrimitives(symbol);
		
		// Iterate on the different hidden layers
		Iterator<NeuronLayer> it = this.neuronLayers.iterator();
		Layer previousLayer = this.primitives;
		while (it.hasNext()) {
			NeuronLayer currentLayer = it.next();
			currentLayer.computeNeuralValues(previousLayer);
			previousLayer = currentLayer;
		}
		
		// Compute the output values
		this.outputLayer.computeNeuralValues(previousLayer);
		
		// Look for the maximal output value and returns the associated token.
		OutputNeuron chosenNeuron = (OutputNeuron) this.outputLayer.getNeuron(0);
		for (int i=1; i<this.outputLayer.size(); i++) {
			Neuron currentNeuron = this.outputLayer.getNeuron(i);
			if (currentNeuron.getValue() > chosenNeuron.getValue()) {
				chosenNeuron = (OutputNeuron) currentNeuron;
			}
		}
		return new StructuredSymbol(chosenNeuron.getToken());
	}
	
	/** 
	 * Adapts the synaptic weights of all neurons in this neural network
	 * depending on the token expected and the adaptation rate.
	 * 
	 * Precondition : Did not reset after a recognition.
	 * 
	 * @param expectedToken           The expected token.
	 * @param alpha                   The adaptation rate.
	 * @throws NeuronException
	 * @throws NeuralNetworkException 
	 * @throws NeuronLayerException
	 * @return The squared norm of quadratic error's gradient.
	 */
	public float adaptSynapticWeights (Token expectedToken, float alpha)
			throws NeuronException, NeuralNetworkException,
			NeuronLayerException {

		// initialisation of a reverse iterator on the hidden layers.
		ListIterator<NeuronLayer> it =
				this.neuronLayers.listIterator(this.neuronLayers.size());
		Layer previousLayer;
		if (it.hasPrevious()) {
			// There is at least one hidden value.
			previousLayer = it.previous();
		} else {
			// There is no hidden layer.
			previousLayer = this.primitives;
		}
		
		/* initialisation of gradient's norm and
		* components for the previous layer. */
		float gradientNorm = 0;
		float weightedDeltas[] =
				new float[previousLayer.size()];
		for (int i=0; i< weightedDeltas.length; i++){
			weightedDeltas[i] = 0;
		}
		
		// adaptation of the output layer.
		for(int outputNeuronIndex=0; outputNeuronIndex<this.outputLayer.size();
				outputNeuronIndex++) {
			// adapt the current output neuron and saves the corresponding
			//     gradient parts
			float neuronWeightedDeltas[] =
					((OutputNeuron)this.outputLayer.getNeuron(
							outputNeuronIndex)).
							adaptSynapticWeigths(previousLayer, expectedToken,
									alpha);
			// check that the components returned can be used to adapt the
			//     previous layer.
			if (weightedDeltas.length != neuronWeightedDeltas.length - 1) {
				throw new NeuralNetworkException("In call to" +
					" neuralnetwork.adaptSynapticWeights(expectedToken," +
					" alpha), an output neuron returned the wrong number of " +
					"weighted deltas.");
			}
			// update the weighted deltas for adaptation of the previous layer
			//     and the gradient norm of the quadratic error.
			gradientNorm += weightedDeltas[weightedDeltas.length-1];
			for (int i=0; i<weightedDeltas.length-1; i++) {
				weightedDeltas[i] += neuronWeightedDeltas[i];
				gradientNorm += neuronWeightedDeltas[i]*neuronWeightedDeltas[i];
			}
		}

		// adapt all hidden layers
		//     except the one having the primitives as input.
		while (it.hasPrevious()) {
			NeuronLayer currentlayer = (NeuronLayer) previousLayer;
			previousLayer = it.previous();
			weightedDeltas = currentlayer.adaptSynapticWeights(previousLayer,
					weightedDeltas, alpha);
		}
		
		// if there was hidden layers, adapt the one having the primitives as
		//     input.
		if (previousLayer instanceof NeuronLayer) {
			((NeuronLayer) previousLayer).adaptSynapticWeights(this.primitives,
					weightedDeltas, alpha);
		}
		
		return gradientNorm;
	}
	
	
	/**
	 * Computes the quadratic error after a recognition
	 * 
	 * @param t The token that was expected for the recognition.
	 * @return The quadratic error for this recognition.
	 */
	public float quadraticError(Token t) {
		float res = 0;
		for (int i=0; i<this.outputLayer.size(); i++) {
			float diff = this.outputLayer.getValue(i);
			if (((OutputNeuron)this.outputLayer.getNeuron(i)).getToken() == t) {
				diff -= 1;
			}
			res += (diff*diff)/2.0;
		}
		return res;
	}
	
	/**
	 * Prints the values computed after a recognition.
	 * 
	 * @param t The token that was expected for the recognition.
	 */
	public void printOutputValues(Token t) {
		System.out.print("Values for " + t + " : ");
		for (int i=0; i<this.outputLayer.size()-1; i++) {
			OutputNeuron output = (OutputNeuron) this.outputLayer.getNeuron(i);
			System.out.print(output.getToken() + " => " + output.getValue() + ", ");
		}		
		OutputNeuron output = (OutputNeuron) this.outputLayer.getNeuron(this.outputLayer.size()-1);
		System.out.println(output.getToken() + " => " + output.getValue() + ".");
	}
	
	/**
	 * Register data concerning the whole page.
	 * @param page A page that is split.
	 */
	public void registerPageData(SplittedPage page) {
	}

	/**
	 * Register data concerning a block.
	 * @param block A block of the split image
	 */
	public void registerBlockData(SplittedBlock block) {
	}

	/**
	 * Register data concerning a line.
	 * @param block A line of the split image
	 */
	public void registerLineData(SplittedLine line) {
		this.primitives.setLineWidth(line.getLineWidth());
	}

}
