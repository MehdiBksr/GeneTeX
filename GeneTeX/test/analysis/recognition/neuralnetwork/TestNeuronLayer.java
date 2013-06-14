package analysis.recognition.neuralnetwork;


import java.util.Vector;

import org.junit.Test;

import data.imagedata.SplitSymbol;

import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;
import error.analysis.recognition.neuralnetwork.NeuronException;

import junit.framework.TestCase;

/**
 * Test the public methods of NeuronLayer.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 */
public class TestNeuronLayer extends TestCase {

	/**
	 * Test if size() returns the number of neurons contained
	 * in the layer.
	 */
	@Test
	public void testSize() {
		NeuronLayer neuronLayer = new NeuronLayer();
		assertEquals(0, neuronLayer.size());
		try {
			neuronLayer.addNeuron(new Neuron(new float[3]));
		} catch (NeuronException e) {
			fail("Unexpected NeuronException");
		}
		assertEquals(1, neuronLayer.size());
		Vector<Neuron> aLayer = new Vector<Neuron>();
		for (int i = 0; i < 10; i++) {
			try {
				aLayer.add(new Neuron(new float[5]));
			} catch (NeuronException e) {
				fail("Unexpected NeuronException");
			}
		}
		NeuronLayer anotherNeuronLayer = new NeuronLayer(aLayer);
		assertEquals(10, anotherNeuronLayer.size());
	}
	
	/**
	 * Test if the methods computeNeuralValues and reset are correct
	 */
	public void testComputeAndReset() {
		// creating a primitive vector for a simple binarized image
		Primitive primitive = new Primitive();
		boolean[][] binary = new boolean[2][2];
		binary[0][0] = true;
		binary[0][1] = false;
		binary[1][0] = false;
		binary[1][1] = true;
		try {
			primitive.computePrimitives(new SplitSymbol(binary, 0, 0));
		} catch (ComputePrimitivesException e) {
			fail("Unexpected ComputePrimitivesException");
		}

		// creating a simple neuron layer
		Vector<Neuron> neurons = new Vector<Neuron>();
		for (int i = 0; i < 5; i++) {
			neurons.add(new Neuron(130));
		}
		
		// computing neuron values
		NeuronLayer neuronLayer = new NeuronLayer(neurons);
		try {
			neuronLayer.computeNeuralValues(primitive);
		} catch (NeuronException e) {
			fail("Unexpected NeuronException");
		}
		
		// checking computation
		boolean computation = false;
		for (int i = 0; i < neuronLayer.size(); i++) {
			if (neuronLayer.getValue(i) != 0)  {
				computation = true;
				return;
			}
		}
		assertTrue("Computation failed", computation);
		
		neuronLayer.resetValues();
		
		// checking reset
		boolean reset = true;
		for (int i = 0; i < neuronLayer.size(); i++) {
			if (neuronLayer.getValue(i) != 0) {
				reset = false;
				return;
			}
		}
		assertTrue("Reset failed", reset);
	}

}
