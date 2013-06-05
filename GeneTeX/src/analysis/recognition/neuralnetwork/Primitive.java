package analysis.recognition.neuralnetwork;

import data.imagedata.SplittedSymbol;


/**
 * This class stores the results of each primitive calculated from a imageData.
 * 
 * @author Marceau Thalgott 
 * @author Mehdi Bouksara
 * @author Théo Merle
 */
public class Primitive implements Layer {
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */

	private final int standardSize = 32;
	
	
	
	/**
	 * The coefficients corresponding to the standardisation of the image
	 * format 
	 */
	private float standardisationCoefficientX, standardisationCoefficientY;
	
	
	/**
	 * Those corresponds to the outline of the image depending of the direction
	 * used to calculate them.
	 */
	private float upOutline[], downOutline[], rightOutline[], leftOutline[];
    
	/* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
    
	
	/**
	 * create a Primitives with all values to 0
	 */
	public Primitive() {
		super();
		this.standardisationCoefficientX = 0;
		this.standardisationCoefficientY = 0;
		this.upOutline = new float[this.standardSize];
		this.downOutline = new float[this.standardSize];
		this.rightOutline = new float[this.standardSize];
		this.leftOutline = new float[this.standardSize];
		for (int i = 0; i<this.standardSize; i++) {
			this.upOutline[i] = 0;
			this.downOutline[i] = 0;
			this.rightOutline[i] = 0;
			this.leftOutline[i] = 0;
		}
	}

    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
    
	/**
	 * Return the number of primitives
	 * @return number of primitives contained in the layer
	 */
	public int size() {
		return 2 + 4*this.standardSize;
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
		switch (index) {
		case 0 : 
			return this.standardisationCoefficientX;
		case 1 :
			return this.standardisationCoefficientY;
		default :
			switch ((index-2) / 32){
			case 0 : 
				return this.upOutline[(index-2) % this.standardSize];
			case 1 : 
				return this.downOutline[(index-2) % this.standardSize];
			case 2 : 
				return this.rightOutline[(index-2) % this.standardSize];
			case 3 : 
				return this.leftOutline[(index-2) % this.standardSize];
			}
		}
		return 0;
	}

	@Override
	public void resetValues() {
		this.standardisationCoefficientX = 0;
		this.standardisationCoefficientY = 0;
		this.upOutline = new float[this.standardSize];
		this.downOutline = new float[this.standardSize];
		this.rightOutline = new float[this.standardSize];
		this.leftOutline = new float[this.standardSize];
		for (int i = 0; i<this.standardSize; i++) {
			this.upOutline[i] = 0;
			this.downOutline[i] = 0;
			this.rightOutline[i] = 0;
			this.leftOutline[i] = 0;
		}
	}

	
	public void computePrimitives(SplittedSymbol img){
		SplittedSymbol standardisedImage = this.standardisation(img);
		this.computeUpOutline(standardisedImage);
		this.computeDownOutline(standardisedImage);
		this.computeRightOutline(standardisedImage);
		this.computeLeftOutline(standardisedImage);
	}
	
	/* ************************************************************************
     *                          PRIVATE FUNCTIONS                             * 
     ************************************************************************ */

	public SplittedSymbol standardisation(SplittedSymbol img){
		// TODO Auto-generated method stub
		return null;
	}
    
	public void computeUpOutline(SplittedSymbol img){
		// TODO Auto-generated method stub
	}
	
	public void computeDownOutline(SplittedSymbol img){
		// TODO Auto-generated method stub
	}
	public void computeRightOutline(SplittedSymbol img){
		// TODO Auto-generated method stub
	}
	public void computeLeftOutline(SplittedSymbol img){
		// TODO Auto-generated method stub
	}

	/* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */


}
