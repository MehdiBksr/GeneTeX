package analysis.recognition.neuralnetwork;

public class Primitive implements Layer {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
	/**
	 * Return the number of primitives
	 * @return number of primitives contained in the layer
	 */
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return the value of the index-th primitive computed. If no primitive
	 * corresponds to index, it returns 0.
	 * <ul>
	 * <li> index = 0 corresponds to standardisationCoefficientX
	 * <li> index = 1 corresponds to standardisationCoefficientY
	 * <li> index = 2..33 corresponds to upOutline
	 * <li> index = 34..65 corresponds to downOutline
	 * <li> index = 66..97 corresponds to rightOutline
	 * <li> index = 98..129 corresponds to leftOutline
	 * </ul>
	 *
	 * @param index an integer corresponding to a primitive.
	 * @return      value of the selected primitive (0 by default).
	 */
	public float getValue(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */


}
