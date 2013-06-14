package analysis.recognition.neuralnetwork;

import data.imagedata.SplitSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;


/**
 * This class stores the results of each primitive calculated from a
 * PreprocessedImage.
 * Those values are the initial input values of the neural network.
 * 
 * @author Mehdi BOUKSARA, Th�o MERLE, Marceau THALGOTT 
 */
@SuppressWarnings("serial")
public class Primitive implements Layer {

	/* ************************************************************************
	 *                      STATIC METHODS AND ATTRIBUTES                     * 
	 ************************************************************************ */
	
	/**
	 * Size of the standardised image.
	 */
	public static final int standardSize = 32;

	
	
	/**
	 * @return The first index of the standardisation coefficients.
	 */
	public static int getStandardisationCoefficientIndex(){
		return 0;
	}

	/**
	 * @return The first index of the outline's values (up direction).
	 */
	public static int getUpOutlineIndex(){
		return 2;
	}

	/**
	 * @return first index of the outline's values (down direction).
	 */
	public static int getDownOutlineIndex(){
		return 2+Primitive.standardSize;
	}

	/**
	 * @return first index of the outline's values (right direction).
	 */
	public static int getRightOutlineIndex(){
		return 2+2*Primitive.standardSize;
	}

	/**
	 * @return first index of the outline's values (left direction).
	 */
	public static int getLeftOutlineIndex(){
		return 2+3*Primitive.standardSize;
	}

	public static int getRelativeCoordinatesIndex(){
		return 2+4*Primitive.standardSize;
	}
	
	
	/**
	 * @param index Index number of a primitive
	 * @return The name of the primitive ("not a primitive" if index is incorrect).
	 */
	public static String nameOfPrimitive(int index){
		if (index >= 0){
			switch (index) {
			case 0 : 
				return "standardisationCoefficientX";
			case 1 :
				return "standardisationCoefficientY";
			default :
				switch ((index-2) / Primitive.standardSize){
				case 0 : 
					return "upOutline[" + (index-2) % Primitive.standardSize
							+ "]";
				case 1 : 
					return "downOutline[" + (index-2) % Primitive.standardSize
							+ "]";
				case 2 : 
					return "rightOutline[" + (index-2) % Primitive.standardSize
							+ "]";
				case 3 : 
					return "leftOutline[" + (index-2) % Primitive.standardSize
							+ "]";
				default :
					switch (index - getRelativeCoordinatesIndex())	{
					case 0 :
						return "firstRelativeX";
					case 1 :
						return "firstRelativeY";
					case 2 :
						return "lastRelativeX";
					case 3 :
						return "lastRelativeY";
					}
				}
			}
		}
		return "not a primitive";
	}

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */

	/**
	 * The coefficients corresponding to the standardisation of the image
	 * format.
	 */
	private float standardisationCoefficientX, standardisationCoefficientY;


	/**
	 * The outlines of the image depending of the direction used to calculate
	 * them.
	 */
	private float upOutline[], downOutline[], rightOutline[], leftOutline[];


	private int lineWidth;
	
	private float firstRelativeX, firstRelativeY, lastRelativeX, lastRelativeY;

	/* ************************************************************************
	 *                              CONSTRUCTORS                              * 
	 ************************************************************************ */


	/**
	 * Creates a Primitive with all values to 0
	 */
	public Primitive() {
		super();
		this.standardisationCoefficientX = 0;
		this.standardisationCoefficientY = 0;
		this.upOutline = new float[standardSize];
		this.downOutline = new float[standardSize];
		this.rightOutline = new float[standardSize];
		this.leftOutline = new float[standardSize];
		for (int i = 0; i<standardSize; i++) {
			this.upOutline[i] = 0;
			this.downOutline[i] = 0;
			this.rightOutline[i] = 0;
			this.leftOutline[i] = 0;
		}
		this.lineWidth = 0;
		this.firstRelativeX = 0;
		this.firstRelativeY = 0;
		this.lastRelativeX = 0;
		this.lastRelativeY = 0;
	}

	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */

	/**
	 * Returns the number of primitives.
	 * @return number of primitives contained in the layer
	 */
	public int size() {
		return 2 + 4*standardSize + 4;
	}

	/**
	 * Returns the value of the index-th primitive computed. If no primitive
	 * corresponds to index, it returns 0.
	 * <ul>
	 * <li> index = 0 corresponds to standardisationCoefficientX.
	 * <li> index = 1 corresponds to standardisationCoefficientY.
	 * <li> index = 2..33 corresponds to upOutline.
	 * <li> index = 34..65 corresponds to downOutline.
	 * <li> index = 66..97 corresponds to rightOutline.
	 * <li> index = 98..129 corresponds to leftOutline.
	 * <li> index = 130..133 corresponds to the relatives coordinates.
	 * </ul>
	 *
	 * @param index an integer corresponding to a primitive.
	 * @return      value of the selected primitive (0 by default).
	 */
	public float getValue(int index) {
		if (index >=0){
			switch (index) {
			case 0 : 
				return this.standardisationCoefficientX;
			case 1 :
				return this.standardisationCoefficientY;
			default :
				switch ((index-2) / Primitive.standardSize){
				case 0 : 
					return this.upOutline[(index-2) % standardSize];
				case 1 : 
					return this.downOutline[(index-2) % standardSize];
				case 2 : 
					return this.rightOutline[(index-2) % standardSize];
				case 3 : 
					return this.leftOutline[(index-2) % standardSize];
				default :
					switch (index - getRelativeCoordinatesIndex())	{
					case 0 :
						return this.firstRelativeX;
					case 1 :
						return this.firstRelativeY;
					case 2 :
						return this.lastRelativeX;
					case 3 :
						return this.lastRelativeY;
					}
				}
			}
		}
		return 0;
	}


	/**
	 * Reset all primitives' values to 0.
	 */
	public void resetValues() {
		this.standardisationCoefficientX = 0;
		this.standardisationCoefficientY = 0;
		this.upOutline = new float[standardSize];
		this.downOutline = new float[standardSize];
		this.rightOutline = new float[standardSize];
		this.leftOutline = new float[standardSize];
		for (int i = 0; i<standardSize; i++) {
			this.upOutline[i] = 0;
			this.downOutline[i] = 0;
			this.rightOutline[i] = 0;
			this.leftOutline[i] = 0;
		}
		this.lineWidth = 0;
		this.firstRelativeX = 0;
		this.firstRelativeY = 0;
		this.lastRelativeX = 0;
		this.lastRelativeY = 0;
	}


	/**
	 * Compute the values resulting from an image.
	 * 
	 * @param img The symbol to analyse.
	 * @throws ComputePrimitivesException
	 */
	public void computePrimitives(SplitSymbol img)
			throws ComputePrimitivesException {
		// check if the argument is null.
		if (img == null){
			throw new ComputePrimitivesException("" +
					"the SplittedImage is a null reference.");
		}
		
		// check if the binary image is null or empty.
		boolean emptyBinary = (img.getBinary() == null) ||
				(img.getBinary().length == 0);
		if (!emptyBinary){
			int height = img.getBinary()[0].length;
			if (height>0) {
				for (int i=1; i<img.getBinary().length; i++){
					if (img.getBinary()[i].length != height){
						throw 
						new ComputePrimitivesException(
								"The binarised image is not a rectangle in" +
								" call to computePrimitives.");
					}
				}
			} else {
				emptyBinary = true;
			}
		}
		if (emptyBinary){
			throw new ComputePrimitivesException("the binarised image is" +
					" empty");
		}

		
		// standardises the image.
		SplitSymbol standardisedImage = this.standardisation(img);
		
		// compute the relative coordinates.
		this.computeRelativeCoordinates(img);

		// compute the outline.
		this.computeUpOutline(standardisedImage);
		this.computeDownOutline(standardisedImage);
		this.computeRightOutline(standardisedImage);
		this.computeLeftOutline(standardisedImage);
	}
	
	/**
	 * Set the line width.
	 * @param lineWidth The line width.
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;		
	}


	/* ************************************************************************
	 *                          PRIVATE FUNCTIONS                             * 
	 ************************************************************************ */

	/**
	 * Creates a standardised imaged of size Primitive.standardSize by
	 * stretching or compressing the binarised image given as argument.
	 * 
	 * @param img Symbol to standardise.
	 * @return The standardised image.
	 */
	private SplitSymbol standardisation(SplitSymbol img){
		boolean oldImg[][] = img.getBinary();
		this.standardisationCoefficientX = 
				((float)(Primitive.standardSize))/((float)(oldImg.length));
		this.standardisationCoefficientY = 
				((float)(Primitive.standardSize))/((float)(oldImg[0].length));
		boolean standardisedImg[][] = 
				new boolean[Primitive.standardSize][Primitive.standardSize];

		for (int newPixelX=0; newPixelX<Primitive.standardSize; newPixelX++) {
			for (int newPixelY=0; newPixelY<Primitive.standardSize;
					newPixelY++) {
				standardisedImg[newPixelX][newPixelY] =
						newPixel(oldImg, newPixelX, newPixelY);
			}
		}
		return new SplitSymbol(standardisedImg);
	}

	/**
	 * Computes the value of the new standardised pixel, given the old image 
	 * @param oldImg    Image to standardise.
	 * @param newPixelX First coordinate of a standardised pixel.
	 * @param newPixelY Second coordinate of a standardised pixel.
	 * @return The value of the new pixel.
	 */
	private boolean newPixel(boolean oldImg[][], int newPixelX, int newPixelY){
		boolean res = false;
		int firstOldPixelX = (oldImg.length * newPixelX)/Primitive.standardSize;
		int lastOldPixelX = (int) Math.min(oldImg.length,
				Math.ceil((oldImg.length*(newPixelX+1)) / (double)Primitive.standardSize)) - 1;
		for (int oldPixelX = firstOldPixelX; oldPixelX <= lastOldPixelX;
				oldPixelX++) {

			int firstOldPixelY = (oldImg[oldPixelX].length * newPixelY)/Primitive.standardSize;
			int lastOldPixelY = (int) Math.min(oldImg[oldPixelX].length,
					Math.ceil((oldImg[oldPixelX].length*(newPixelY+1)) /
							(double)Primitive.standardSize)) - 1;
			for (int oldPixelY = firstOldPixelY; oldPixelY <= lastOldPixelY;
					oldPixelY++) {
				res |= oldImg[oldPixelX][oldPixelY];
			}
		}
		return res;
	}

	/**
	 * Computes the outline by going upward.
	 * 
	 * @param img The image to analyse.
	 */
	private void computeUpOutline(SplitSymbol img){
		boolean binImg[][] = img.getBinary();
		
		for (int col=0; col<Primitive.standardSize; col++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[col][Primitive.standardSize-1-result]){
				result++;
			}
			this.upOutline[col] = result/((float)Primitive.standardSize);
		}
	}

	/**
	 * Computes the outline by going downward.
	 * 
	 * @param img The image to analyse.
	 */
	private void computeDownOutline(SplitSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int col=0; col<Primitive.standardSize; col++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[col][result]){
				result++;
			}
			this.downOutline[col] = result/((float)Primitive.standardSize);
		}
	}

	/**
	 * Computes the outline by going from left to right.
	 * 
	 * @param img The image to analyse.
	 */
	private void computeRightOutline(SplitSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int line=0; line<Primitive.standardSize; line++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[result][line]){
				result++;
			}
			this.rightOutline[line] = result/((float)Primitive.standardSize);
		}
	}

	/**
	 * Computes the outline by going from right to left.
	 * 
	 * @param img The image to analyse.
	 */
	private void computeLeftOutline(SplitSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int line=0; line<Primitive.standardSize; line++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[Primitive.standardSize-1-result][line]){
				result++;
			}
			this.leftOutline[line] = result/((float)Primitive.standardSize);
		}
	}

	/**
	 * Compute the relatives coordinates of the image containing the character.
	 * 
	 * Precondition : this.linewidth must be different from 0;
	 * 
	 * @param img A symbol containing the data extracted from the split.
	 * @throws ComputePrimitivesException 
	 */
	private void computeRelativeCoordinates(SplitSymbol img)
			throws ComputePrimitivesException {
		if (this.lineWidth == 0){
			throw new ComputePrimitivesException("In call to" +
					" Primitive.computePrimitive, the width of the line is 0.");
		}
		this.firstRelativeX =
				((float)img.getFirstPixelX()) / ((float)this.lineWidth);
		this.firstRelativeY =
				((float)img.getFirstPixelY()) / ((float)this.lineWidth);
		this.lastRelativeX =
				((float)img.getLastPixelX()) / ((float)this.lineWidth);
		this.lastRelativeY =
				((float)img.getLastPixelY()) / ((float)this.lineWidth);
	}
}
