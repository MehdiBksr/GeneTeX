package analysis.recognition.neuralnetwork;

import static org.junit.Assert.*;

import org.junit.Test;

import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;

/** Class for testing the primitive calculation for the neural network.
 * 
 * @author Mehdi BOUKSARA, Th�o MERLE, Marceau THALGOTT
 *
 */
public class TestPrimitive {

	/** Test for primitive computation.
	 * 	The test fails if an unexpected exception occurs, if an expected exception
	 *  doesn't, or if an operation doesn't return the expected result.
	 */
	@Test
	public void testFailComputePrimitives() {

		Primitive p = new Primitive();
		testResetValues(p); // testing if all values are 0.

		// check if a call to computePrimitives with a null argument throws an
		// exception
		try {
			p.computePrimitives(null);
			fail("computePrimitives with null argument did not throw an " +
					"exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}

		// check if a call to computePrimitives with a null image throws an
		// exception
		try {
			SplittedSymbol img = new SplittedSymbol(null);
			p.computePrimitives(img);
			fail("computePrimitives with empty binarised image did not throw" +
					" an exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}

		// check if a call to computePrimitives with a empty image throws an
		// exception
		try {
			boolean bin[][] = new boolean[0][0];
			SplittedSymbol img = new SplittedSymbol(bin);
			p.computePrimitives(img);
			fail("computePrimitives with empty binarised image did not throw" +
					" an exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}
		try {
			boolean bin[][] = new boolean[1][0];
			SplittedSymbol img = new SplittedSymbol(bin);
			p.computePrimitives(img);
			fail("computePrimitives with empty binarised image did not throw" +
					" an exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}

		try {
			// sizes to test all stretching limit cases for the standardisation.
			int sizes[] = new int[3];
			sizes[0] = 23;
			sizes[1] = 32;
			sizes[2] = 43;

			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					int xSize = sizes[i], ySize = sizes[j]; 

					// test an image with only white pixels
					//  _______
					// |       |
					// |       |
					// |       |
					// |       |
					//  -------
					String mess = "After computation on a white image of size "
							+ xSize + "x" + ySize + ", ";
					p.computePrimitives(selfColouredImage(xSize,ySize,false));
					// testing if the standardisation coefficients are correct
					testStandardisation(p, mess, xSize, ySize);
					// test the values returned for index values not
					// corresponding to a primitive.
					testNonPrimitive(p, mess);
					// testing if the outline is correctly computed.
					testExtremeOutline(p, mess, false);

					// test an image with only black pixels
					//  _______
					// |*******|
					// |*******|
					// |*******|
					// |*******|
					//  -------
					mess = "After computation on a black image of size " +
							xSize + "x" + ySize + ", ";
					p.computePrimitives(selfColouredImage(xSize,ySize,true));
					// testing if the standardisation coefficients are correct
					testStandardisation(p, mess, xSize, ySize);
					// test the values returned for index values not
					// corresponding to a primitive.
					testNonPrimitive(p, mess);
					// testing if the outline is correctly computed.
					testExtremeOutline(p, mess, true);

					// test an image with a black border containing only whites
					// pixels.
					//  _______
					// |*******|
					// |*     *|
					// |*     *|
					// |*******|
					//  -------
					mess = "After computation on a white image with a black border of size "
							+ xSize + "x" + ySize + ", ";
					p.computePrimitives(emptySquareImage(xSize,ySize));
					// testing if the standardisation coefficients are correct
					testStandardisation(p, mess, xSize, ySize);
					// test the values returned for index values not
					// corresponding to a primitive.
					testNonPrimitive(p, mess);
					// testing if the outline is correctly computed.
					testExtremeOutline(p, mess, true);

					// test an image with a black diagonal line of
					// pixels.
					//  _______
					// |*      |
					// | *     |
					// |  *    |
					// |   *   |
					//  -------
					mess = "After computation on a black cross image of size "
							+ xSize + "x" + ySize + ", ";
					p.computePrimitives(LineImage(xSize,ySize));
					// testing if the standardisation coefficients are correct
					testStandardisation(p, mess, xSize, ySize);
					// test the values returned for index values not
					// corresponding to a primitive.
					testNonPrimitive(p, mess);
					// testing if the outline is correctly computed.
					testLineOutline(p, mess, xSize, ySize);

					// testing if resetValues put all values to 0.
					p.resetValues();
					testResetValues(p);
				}
			}
		} catch (ComputePrimitivesException e) {
			fail("Call to computePrimitives ended with an unexpected exception.");
		}
	}


	/* ************************************************************************
	 *                          PRIVATE UTILITIES                             * 
	 ************************************************************************ */

	/* methods to test resetValues ********************************************/

	/** Tests the resetting method.
	 * Fails if the resetting didn't work correctly.
	 * @param p A Primitive.
	 */
	private void testResetValues(Primitive p) {
		int size = p.size();

		// Index is too high.
		float val = p.getValue(size);
		if (val != 0){
			fail("call of getValue(i), with i>= number of primitives," +
					" returns value " + val + ", it should be 0.");
		}
		
		// Index is negative.
		val = p.getValue(-10);
		if (val != 0){
			fail("call of getValue(i), with i<0, returns value " + val + "," +
					" it should be 0.");
		}

		// correct indexes
		for (int i=0; i<size; i++) {
			val = p.getValue(i);
			if (val != 0){
				fail(Primitive.nameOfPrimitive(i) + " has value " + val + ", it should" +
						" be 0.");
			}
		}
	}

	/* methods to test computePrimitives **************************************/

	/** Testing if the value returned by the method getValue() when called
	 * with invalid parameters is 0.
	 * Fails if an invalid call return a different value.
	 * @param p A Primitive.
	 * @param mess A message indicating the failing test if a test fails.
	 * @see 
	 */
	private void testNonPrimitive(Primitive p, String mess){
		float val = p.getValue(p.size());
		if (val != 0){
			fail(mess + "call of getValue(i)," +
					" with i >= number of primitives, returns value " + val +
					", it should be 0.");
		}

		val = p.getValue(-10);
		if (val != 0) {
			fail(mess + "call of getValue(i)," +
					" with i < 0, returns value " + val + ", it should be 0.");
		}
	}
  
	/** Testing if the value returned by the method getValue() when called
	 * with a parameter corresponding to a stretching coefficient is correct,
	 * given the image's size before the standardisation.
	 * @param p     A Primitive.
	 * @param mess  A message indicating the failing test if a test fails.
	 * @param xSize Horizontal length of the not stretched image
	 * @param ySize Vertical length of the not stretched image
	 * @see 
	 */
	private void testStandardisation(Primitive p, String mess,
			int xSize, int ySize){
		float val = p.getValue(0);
		float expectedValue = ((float)(Primitive.standardSize))/((float)(xSize));
		if (val != expectedValue){
			fail(mess + Primitive.nameOfPrimitive(0) +
					" returns value " + val + ", it should be " + expectedValue
					+ ".");
		}

		val = p.getValue(1);
		expectedValue = ((float)(Primitive.standardSize))/((float)(ySize));
		if (val != expectedValue){
			fail(mess + Primitive.nameOfPrimitive(1) +
					" returns value " + val + ", it should be " + expectedValue
					+ ".");
		}
	}

	/** Testing if the outline values computed are correct for an extreme
	 *  outline.
	 *  If black is true, the expected images are : 
	 *	_______        _______  
	 * |*******|      |*******|
	 * |*******|  or  |*     *|
	 * |*******|      |*     *|
	 * |*******|      |*******|
	 *  -------        -------
	 *  
	 *  If black is false, the expected image is : 
	 *	_______   
	 * |       |
	 * |       |
	 * |       |
	 * |       |
	 *  -------
	 *  
	 * @param p     A Primitive.
	 * @param mess  A message indicating the failing test if a test fails.
	 * @param black If true, then all expected values are 0, else all expected
	 *                   values are 1.
	 * @see 
	 */
	private void testExtremeOutline(Primitive p, String mess, boolean black){
		for (int i=2; i<p.size(); i++) {
			float val = p.getValue(i);
			float expectedValue = 0;
			if (!black){
				expectedValue = 1;
			}
			if (val != expectedValue){
				fail(mess + Primitive.nameOfPrimitive(i) + " has value " + val +
						", it should" + " be " + expectedValue + ".");
			}
		}
	}

	/** Testing if the outline values computed are correct for an diagonal line
	 *  of black pixels.
	 *  The expected image is : 
	 *	_______   
	 * |*      |
	 * | *     |
	 * |  *    |
	 * |   *   |
	 *  -------
	 *  
	 * @param p     A Primitive.
	 * @param mess  A message indicating the failing test if a test fails.
	 * @param xSize Horizontal length of the not stretched image
	 * @param ySize Vertical length of the not stretched image
	 * @see 
	 */
	private void testLineOutline(Primitive p, String mess, int xSize, int ySize){

		int initDown = Primitive.getDownOutlineIndex();
		int initUp = Primitive.getUpOutlineIndex();
		int initRight = Primitive.getRightOutlineIndex();
		int initLeft = Primitive.getLeftOutlineIndex();

		// for each column and line
		for (int i=0; i<Primitive.standardSize; i++) {
			// expected value for down outlines
			float forwardExpectedValue = Primitive.standardSize;
			// expected value for up outlines
			float backwardExpectedValue = -1;
			// computing the interval of columns in the image, before
			// standardisation, influencing the studied column.
			int firstOldPixelX = (xSize*i)/Primitive.standardSize;
			int lastOldPixelX = (int) Math.min(xSize,
					Math.ceil((xSize*(i+1)) / (double)Primitive.standardSize))
					- 1;
			if(firstOldPixelX<ySize){
				// if the old columns can contain a black pixel, update the
				// expected values in function of the influencing columns.
				for (int oldY = firstOldPixelX; oldY <= lastOldPixelX; oldY++){
					forwardExpectedValue = Math.min(forwardExpectedValue,
							(Primitive.standardSize * oldY)/ySize);
					backwardExpectedValue = Math.max(backwardExpectedValue,
							(int) Math.min(Primitive.standardSize,
									Math.ceil((Primitive.standardSize*(oldY+1))
											/ (double)ySize)) - 1);
				}
			}
			// standardising the expected values.
			forwardExpectedValue =
					forwardExpectedValue/((float)Primitive.standardSize);
			backwardExpectedValue =
					(Primitive.standardSize - 1 - backwardExpectedValue) /
					((float)Primitive.standardSize);

			// checking the down outline.
			float val = p.getValue(initDown + i);
			if (val != forwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initDown + i) + 
						" has value " + val + ", it should be " + 
						forwardExpectedValue + ".");
			}
			// checking the up outline
			val = p.getValue(initUp + i);
			if (val != backwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initUp + i) + 
						" has value " + val + ", it should be " + 
						backwardExpectedValue + ".");
			}



			// expected value for right outlines
			forwardExpectedValue = Primitive.standardSize;
			// expected value for left outlines
			backwardExpectedValue = -1;
			// computing the interval of lines in the image, before
			// standardisation, influencing the studied line.
			int firstOldPixelY = (ySize*i)/Primitive.standardSize;
			int lastOldPixelY = (int) Math.min(ySize,
					Math.ceil((ySize*(i+1)) / (double)Primitive.standardSize)) - 1;
			if(firstOldPixelY<xSize){
				// if the old lines can contain a black pixel, update the
				// expected values in function of the influencing columns.
				for (int oldX = firstOldPixelY; oldX <= lastOldPixelY; oldX++){
					forwardExpectedValue = Math.min(forwardExpectedValue,
							(Primitive.standardSize * oldX)/xSize);
					backwardExpectedValue = Math.max(backwardExpectedValue,
							(int) Math.min(Primitive.standardSize,
									Math.ceil((Primitive.standardSize*(oldX+1)) / (double)xSize)) - 1);
				}
			}
			// standardising the expected values.
			forwardExpectedValue =
					forwardExpectedValue/((float)Primitive.standardSize);
			backwardExpectedValue =
					(Primitive.standardSize - 1 - backwardExpectedValue) /
					((float)Primitive.standardSize);

			// checking the right outline.
			val = p.getValue(initRight + i);
			if (val != forwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initRight + i) +
						" has value " + val + ", it should be " +
						forwardExpectedValue + ".");
			}		
			// checking the left outline.
			val = p.getValue(initLeft + i);
			if (val != backwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initLeft + i) +
						" has value " + val + ", it should be " +
						backwardExpectedValue + ".");
			}		
		}
	}

	/* SplittedImage creation *************************************************/

	/**
	 * Creates a self-coloured image of dimension xSize x ySize containing only
	 * black pixels, if b is true, and only white pixels otherwise.
	 * 
	 *   true           false
	 *	_______        _______  
	 * |*******|      |       |
	 * |*******|  or  |       |
	 * |*******|      |       |
	 * |*******|      |       |
	 *  -------        -------
	 *  
	 * @param xSize Horizontal size.
	 * @param ySize Vertical size.
	 * @param b     Fills the image with black pixels if true (white otherwise).
	 * @return A self-coloured image.
	 */
	private SplittedSymbol selfColouredImage(int xSize, int ySize, boolean b){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int i=0; i<xSize;i++){
			for (int j=0; j<ySize;j++){
				pixels[i][j] = b;
			}
		}
		return new SplittedSymbol(pixels);
	}

	/**
	 * Creates a image of dimension xSize x ySize containing a black-bordered
	 * square filled with white of maximal size.
	 *	_______   
	 * |*******|
	 * |*     *|
	 * |*     *|
	 * |*******|
	 *  -------
	 *  
	 * @param xSize Horizontal size.
	 * @param ySize Vertical size.
	 * @return An image of dimension xSize x ySize containing a black-bordered
	 *               square filled with white of maximal size.
	 */
	private SplittedSymbol emptySquareImage(int xSize, int ySize){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int j=0; j<ySize;j++){
			pixels[0][j] = true;
			pixels[xSize-1][j] = true;
		}
		for (int i=1; i<xSize-1;i++){
			for (int j=1; j<ySize-1;j++){
				pixels[i][j] = false;
			}
			pixels[i][0] = true;
			pixels[i][ySize-1] = true;
		}
		return new SplittedSymbol(pixels);
	}

	/**
	 * Creates a image of dimension xSize x ySize containing a black-bordered
	 * square filled with white of maximal size.
	 *	_______   
	 * |*      |
	 * | *     |
	 * |  *    |
	 * |   *   |
	 *  -------
	 *  
	 * @param xSize Horizontal size.
	 * @param ySize Vertical size.
	 * @return An image of dimension xSize x ySize containing a black diagonal
	 *              line.
	 */
	private SplittedSymbol LineImage(int xSize, int ySize){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int i=0; i<xSize;i++){
			for (int j=0; j<ySize;j++){
				if (i==j){
					pixels[i][j] = true;
				} else {
				pixels[i][j] = false;
				}
			}
		}
		return new SplittedSymbol(pixels);
	}

}
