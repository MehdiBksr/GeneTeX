package analysis.recognition.neuralnetwork;

import static org.junit.Assert.*;

import org.junit.Test;

import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;

/** Class for testing the primitive calculation for the neural network.
 * 
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT
 *
 */
public class TestPrimitive {

	@Test
	public void testFailBasicMethods() {
		Primitive p = new Primitive();
		testResetValues(p);
	}

	/** Test for primitive computation.
	 * 	The test fails if an unexpected exception occurs, if an expected exception
	 * doesn't, or if an operation doesn't return the expected result.
	 */
	@Test
	public void testFailComputePrimitives() {

		Primitive p = new Primitive();
		try {
			p.computePrimitives(null);
			fail("computePrimitives with null argument did not throw an " +
					"exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}

		try {
			SplittedSymbol img = new SplittedSymbol(null);
			p.computePrimitives(img);
			fail("computePrimitives with empty binarised image did not throw" +
					" an exception.");
		} catch (ComputePrimitivesException e) {
			//expected behaviour
		}

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
			int sizes[] = new int[3];
			sizes[0] = 23;
			sizes[1] = 32;
			sizes[2] = 43;

			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					int xSize = sizes[i], ySize = sizes[j]; 

					/*    all white      */
					String mess = "After computation on a white image of size " + xSize +
							"x" + ySize + ", ";
					p.computePrimitives(selfColouredImage(xSize,ySize,false));
					testNonPrimitive(p, mess);
					testStandardisation(p, mess, xSize, ySize);
					testExtremeOutline(p, mess, false);

					/*    all black      */
					mess = "After computation on a black image of size " + xSize +
							"x" + ySize + ", ";
					p.computePrimitives(selfColouredImage(xSize,ySize,true));
					testNonPrimitive(p, mess);
					testStandardisation(p, mess, xSize, ySize);
					testExtremeOutline(p, mess, true);

					p.resetValues();
					testResetValues(p);

					/*    black border   */
					mess = "After computation on a white image with a black border of size "
							+ xSize + "x" + ySize + ", ";
					p.computePrimitives(emptySquareImage(xSize,ySize,true));
					testStandardisation(p, mess, xSize, ySize);
					testNonPrimitive(p, mess);
					testExtremeOutline(p, mess, true);

					p.resetValues();
					testResetValues(p);

					/*    black line    */
					mess = "After computation on a black cross image of size "
							+ xSize + "x" + ySize + ", ";
					p.computePrimitives(LineImage(xSize,ySize,true));
					testStandardisation(p, mess, xSize, ySize);
					testNonPrimitive(p, mess);
					testLineOutline(p, mess, xSize, ySize);

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

		float val = p.getValue(size);
		if (val != 0){
			fail("call of getValue(i), with i>= number of primitives," +
					" returns value " + val + ", it should be 0.");
		}

		val = p.getValue(-10);
		if (val != 0){
			fail("call of getValue(i), with i<0, returns value " + val + "," +
					" it should be 0.");
		}

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
  
	private void testStandardisation(Primitive p, String mess, int xSize, int ySize){
		float val = p.getValue(0);
		float expectedValue = ((float)(Primitive.standardSize))/((float)(xSize));
		if (val != expectedValue){
			fail(mess + Primitive.nameOfPrimitive(0) +
					" returns value " + val + ", it should be " + expectedValue
					+ "1.");
		}

		val = p.getValue(1);
		expectedValue = ((float)(Primitive.standardSize))/((float)(ySize));
		if (val != expectedValue){
			fail(mess + Primitive.nameOfPrimitive(1) +
					" returns value " + val + ", it should be 1.");
		}
	}

	private void testExtremeOutline(Primitive p, String mess, boolean black){
		for (int i=2; i<p.size(); i++) {
			float val = p.getValue(i);
			float expectedValue = 0;
			if (!black){
				expectedValue = 1;
			}
			if (val != expectedValue){
				fail(mess + Primitive.nameOfPrimitive(i) + " has value " + val + ", it should" +
						" be " + expectedValue + ".");
			}
		}
	}

	private void testLineOutline(Primitive p, String mess, int xSize, int ySize){

		int initDown = Primitive.getDownOutlineIndex();
		int initUp = Primitive.getUpOutlineIndex();
		int initRight = Primitive.getRightOutlineIndex();
		int initLeft = Primitive.getLeftOutlineIndex();

		for (int i=0; i<Primitive.standardSize; i++) {
			float forwardExpectedValue = Primitive.standardSize;
			float backwardExpectedValue = -1;
			//forwardExpectedValue = min(firstNewPixel(oldY)) for oldY in firstOldPixel(i)..lastOldPixel(i)
			//backwardExpectedValue = max(lastNewPixel(oldY)) for oldY in firstOldPixel(i)..lastOldPixel(i)
			int firstOldPixelX = (xSize*i)/Primitive.standardSize;
			int lastOldPixelX = (int) Math.min(xSize,
					Math.ceil((xSize*(i+1)) / (double)Primitive.standardSize)) - 1;
			if(firstOldPixelX<ySize){
				for (int oldY = firstOldPixelX; oldY <= lastOldPixelX; oldY++){
					forwardExpectedValue = Math.min(forwardExpectedValue,
							(Primitive.standardSize * oldY)/ySize);
					backwardExpectedValue = Math.max(backwardExpectedValue,
							(int) Math.min(Primitive.standardSize,
									Math.ceil((Primitive.standardSize*(oldY+1))
											/ (double)ySize)) - 1);
				}
			}
			forwardExpectedValue =
					forwardExpectedValue/((float)Primitive.standardSize);
			backwardExpectedValue =
					(Primitive.standardSize - 1 - backwardExpectedValue) /
					((float)Primitive.standardSize);

			//downOutline
			float val = p.getValue(initDown + i);
			if (val != forwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initDown + i) + 
						" has value " + val + ", it should be " + 
						forwardExpectedValue + ".");
			}
			//upOutline
			val = p.getValue(initUp + i);
			if (val != backwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initUp + i) + 
						" has value " + val + ", it should be " + 
						backwardExpectedValue + ".");
			}



			forwardExpectedValue = Primitive.standardSize;
			backwardExpectedValue = -1;
			//forwardExpectedValue = min(firstNewPixel(oldY)) for oldY in firstOldPixel(i)..lastOldPixel(i)
			//backwardExpectedValue = max(lastNewPixel(oldY)) for oldY in firstOldPixel(i)..lastOldPixel(i)
			int firstOldPixelY = (ySize*i)/Primitive.standardSize;
			int lastOldPixelY = (int) Math.min(ySize,
					Math.ceil((ySize*(i+1)) / (double)Primitive.standardSize)) - 1;
			if(firstOldPixelY<xSize){
				for (int oldX = firstOldPixelY; oldX <= lastOldPixelY; oldX++){
					forwardExpectedValue = Math.min(forwardExpectedValue,
							(Primitive.standardSize * oldX)/xSize);
					backwardExpectedValue = Math.max(backwardExpectedValue,
							(int) Math.min(Primitive.standardSize,
									Math.ceil((Primitive.standardSize*(oldX+1)) / (double)xSize)) - 1);
				}
			}
			forwardExpectedValue =
					forwardExpectedValue/((float)Primitive.standardSize);
			backwardExpectedValue =
					(Primitive.standardSize - 1 - backwardExpectedValue) /
					((float)Primitive.standardSize);

			//rightOutline
			val = p.getValue(initRight + i);
			if (val != forwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initRight + i) +
						" has value " + val + ", it should be " +
						forwardExpectedValue + ".");
			}		
			//leftOutline
			val = p.getValue(initLeft + i);
			if (val != backwardExpectedValue){
				fail(mess + Primitive.nameOfPrimitive(initLeft + i) +
						" has value " + val + ", it should be " +
						backwardExpectedValue + ".");
			}		
		}
	}

	/* SplittedImage creation *************************************************/

	private SplittedSymbol selfColouredImage(int xSize, int ySize, boolean b){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int i=0; i<xSize;i++){
			for (int j=0; j<ySize;j++){
				pixels[i][j] = b;
			}
		}
		return new SplittedSymbol(pixels);
	}

	private SplittedSymbol emptySquareImage(int xSize, int ySize, boolean b){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int j=0; j<ySize;j++){
			pixels[0][j] = b;
			pixels[xSize-1][j] = b;
		}
		for (int i=1; i<xSize-1;i++){
			for (int j=1; j<ySize-1;j++){
				pixels[i][j] = !b;
			}
			pixels[i][0] = b;
			pixels[i][ySize-1] = b;
		}
		return new SplittedSymbol(pixels);
	}

	private SplittedSymbol LineImage(int xSize, int ySize, boolean b){
		boolean pixels[][] = new boolean[xSize][ySize];
		for (int i=0; i<xSize;i++){
			for (int j=0; j<ySize;j++){
				if (i==j){
					pixels[i][j] = b;
				} else {
				pixels[i][j] = !b;
				}
			}
		}
		return new SplittedSymbol(pixels);
	}


/*
 * 	private void printBinaryImage(boolean img[][]){
 *		for (int i=0; i<img.length; i++) {
 *			for (int j=0; j<img.length; j++) {
 *				if (img[i][j]) {
 *					System.out.print("b");
 *				} else {
 *					System.out.print("w");
 *				}
 *			}
 *			System.out.println();
 *		}
 *		System.out.println();
 *	}
**/
}
