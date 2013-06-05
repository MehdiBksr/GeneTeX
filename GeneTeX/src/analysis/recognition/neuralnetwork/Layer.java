package analysis.recognition.neuralnetwork;

/**
 * The neural network is composed of layer. Each layer contains some elements.
 * 
 * @author Marceau Thalgott 
 * @author Mehdi Bouksara
 * @author Théo Merle
 */
public interface Layer {
	    
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
	
    /* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	

}
