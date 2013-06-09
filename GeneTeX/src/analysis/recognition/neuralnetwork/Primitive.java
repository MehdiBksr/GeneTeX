package analysis.recognition.neuralnetwork;

import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;


/**
 * This class stores the results of each primitive calculated from a imageData. Those values
 * are the initial input values of the neural network.
 * 
 * @author Théo Merle
 */

@SuppressWarnings("serial")
public class Primitive implements Layer {

	/* ************************************************************************
	 *                      STATIC METHODS AND ATTRIBUTES                     * 
	 ************************************************************************ */
	public static final int standardSize = 32;

	public static int getStandardisationCoefficientIndex(){
		return 0;
	}
	public static int getUpOutlineIndex(){
		return 2;
	}
	public static int getDownOutlineIndex(){
		return 2+Primitive.standardSize;
	}
	public static int getRightOutlineIndex(){
		return 2+2*Primitive.standardSize;
	}
	public static int getLeftOutlineIndex(){
		return 2+3*Primitive.standardSize;
	}

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
	}

	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */

	/**
	 * Return the number of primitives
	 * @return number of primitives contained in the layer
	 */
	public int size() {
		return 2 + 4*standardSize;
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
				}
			}
		}
		return 0;
	}


	@Override
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
	}


	public void computePrimitives(SplittedSymbol img)
			throws ComputePrimitivesException {
		if (img == null){
			throw new ComputePrimitivesException("" +
					"the SplittedImage is a null reference.");
		}
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

		SplittedSymbol standardisedImage = this.standardisation(img);
		this.computeUpOutline(standardisedImage);
		this.computeDownOutline(standardisedImage);
		this.computeRightOutline(standardisedImage);
		this.computeLeftOutline(standardisedImage);
	}
	
	/* ************************************************************************
	 *                          PRIVATE FUNCTIONS                             * 
	 ************************************************************************ */

	private SplittedSymbol standardisation(SplittedSymbol img){
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
		return new SplittedSymbol(standardisedImg);
	}

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

	private void computeUpOutline(SplittedSymbol img){
		boolean binImg[][] = img.getBinary();
		
		for (int col=0; col<Primitive.standardSize; col++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[col][Primitive.standardSize-1-result]){
				//System.out.println("computeUpOutline : col = " + col + ", result = "
				//		+ result + ", pixel = " + 
				//		binImg[col][Primitive.standardSize-1-result]);
				result++;
			}
			this.upOutline[col] = result;
		}
	}

	private void computeDownOutline(SplittedSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int col=0; col<Primitive.standardSize; col++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[col][result]){
				result++;
			}
			this.downOutline[col] = result;
		}
	}

	private void computeRightOutline(SplittedSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int line=0; line<Primitive.standardSize; line++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[result][line]){
				result++;
			}
			this.rightOutline[line] = result;
		}
	}

	private void computeLeftOutline(SplittedSymbol img){
		boolean binImg[][] = img.getBinary();
		for (int line=0; line<Primitive.standardSize; line++) {
			int result = 0;
			while (result<Primitive.standardSize &&
					!binImg[Primitive.standardSize-1-result][line]){
				result++;
			}
			this.leftOutline[line] = result;
		}
	}

//	private void printBinaryImage(boolean img[][]){
//	for (int i=0; i<img.length; i++) {
//		for (int j=0; j<img[i].length; j++) {
//			if (img[i][j]) {
//				System.out.print("b");
//			} else {
//				System.out.print("w");
//			}
//		}
//		System.out.println();
//	}
//	System.out.println();
//}

	
	/* ************************************************************************
	 *                              ACCESSORS                                 * 
	 ************************************************************************ */


}
