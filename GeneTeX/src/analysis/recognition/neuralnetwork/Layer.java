package analysis.recognition.neuralnetwork;

import java.io.Serializable;

/**
 * The neural network is a structure composed of layers. Each layer contains
 * some elements of the same nature.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 */
public interface Layer extends Serializable {
	    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/**
	 * Return the size of the layer
	 * @return number of elements contained in the layer
	 */
	public int size();

	/**
	 * Return the value of the element of index index. If no element
	 * corresponds to index, it returns 0.
	 * @param index an integer corresponding to an element.
	 * @return      value of the selected element (0 by default).
	 */
	public float getValue(int index);
	
	/**
	 * Resets the values of the elements without changing the rest.
	 */
	public void resetValues();

}
