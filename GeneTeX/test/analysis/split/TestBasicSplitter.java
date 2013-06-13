package analysis.split;

import static org.junit.Assert.*;

import javax.swing.SwingUtilities;

import org.junit.Test;

import util.JFrameSplittedFile;
import error.data.BadInstanceException;

public class TestBasicSplitter {
		
		static JFrameSplittedFile jFrameSrc;

		public static void main(String[] args) {
		    testSourceFile();
	    }
		
		private static void testSourceFile() {
			SwingUtilities.invokeLater(runJFrameSourceFile);
		}
		
		static Runnable runJFrameSourceFile = new Runnable() {
		    @Override
		    public void run() {
		        try {
					jFrameSrc = new JFrameSplittedFile("images\\testGeneTeXexact.png");
					jFrameSrc.setVisible(true);
				} catch (BadInstanceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		    }
		};

		@Test
		public void testRemoveMargin() {
			boolean[][] example;
			boolean[][] result;
			
			///////////////////////////////////////////////////////////////////
			//  _  ////////////////////////////////////////////////////////////
			// |*| ////////////////////////////////////////////////////////////
			//  -  ////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			example = new boolean [1][1];
			example[0][0] = true;
			result = BasicSplitter.removeMargins(example);
			if (!identicalImage(example,result)) {
				// print the images
				System.out.println("Example image :");
				printBooleanArray(example);
				System.out.println("Resulting image : ");
				printBooleanArray(result);
				fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
						"with only one black pixel in the center, did not " +
						"return an 1x1 black image.");
			}
			
			///////////////////////////////////////////////////////////////////
			//  __        __        __        __  /////////////////////////////
			// | *| then |**| then |* | then |**| /////////////////////////////
			// |**|      | *|      |**|      |* | /////////////////////////////
			//  --        --        --        --  /////////////////////////////
			///////////////////////////////////////////////////////////////////
			for (int bi=0; bi<2; bi++) {
				for (int bj=0; bj<2; bj++) {
					example = new boolean [2][2];
					for (int i=0; i<2; i++) {
						for (int j=0; j<2; j++) {
							example[i][j] = !(i==bi && j==bj);
						}
					}
					result = BasicSplitter.removeMargins(example);
					if (!identicalImage(example,result)) {
						// print the images
						System.out.println("Example image :");
						printBooleanArray(example);
						System.out.println("Resulting image : ");
						printBooleanArray(result);
						fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
								"with only one black pixel in the center, did not " +
								"return an 1x1 black image.");
					}
				}
			}

			///////////////////////////////////////////////////////////////////
			//  __        __  /////////////////////////////////////////////////
			// |* | then | *| /////////////////////////////////////////////////
			// | *|      |* | /////////////////////////////////////////////////
			//  --        --  /////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			for (int b=0; b<2; b++) {
					example = new boolean [2][2];
					for (int i=0; i<2; i++) {
						for (int j=0; j<2; j++) {
							if (b==0) {
								example[i][j] = i==j;
							} else {
								example[i][j] = i!=j;
							}
						}
					}
					result = BasicSplitter.removeMargins(example);
					if (!identicalImage(example,result)) {
						// print the image
						System.out.println("Example image :");
						printBooleanArray(example);
						System.out.println("Resulting image : ");
						printBooleanArray(result);
						fail("Call to BasicSplitter.removeMargins on an 2x2 image, " +
								"with one black diagonal in the center, did not " +
								"the same image.");
					}
			}

			///////////////////////////////////////////////////////////////////
			//  __        __  /////////////////////////////////////////////////
			// |**| then |  | /////////////////////////////////////////////////
			// |  |      |**| /////////////////////////////////////////////////
			//  --        --  /////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			for (int bj=0; bj<2; bj++) {
					example = new boolean [2][2];
					for (int i=0; i<2; i++) {
						for (int j=0; j<2; j++) {
							example[i][j] = j==bj;
						}
					}
					result = BasicSplitter.removeMargins(example);
					if (result.length!=2 || result[0].length!=1 || !result[0][0] || !result[1][0]) {
						// print the image
						System.out.println("Example image :");
						printBooleanArray(example);
						System.out.println("Resulting image : ");
						printBooleanArray(result);
						fail("Call to BasicSplitter.removeMargins on an 2x2 image, " +
								"with only one horizontal black line in the center, did not " +
								"return an 2x1 black image.");
					}
			}

			///////////////////////////////////////////////////////////////////
			//  __  ///////////////////////////////////////////////////////////
			// |  | ///////////////////////////////////////////////////////////
			// |**| ///////////////////////////////////////////////////////////
			// |  | ///////////////////////////////////////////////////////////
			//  --  ///////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			example = new boolean [2][3];
			for (int i=0; i<2; i++) {
				for (int j=0; j<3; j++) {
					example[i][j] = j==1;
				}
			}
			result = BasicSplitter.removeMargins(example);
			if (result.length!=2 || result[0].length!=1 || !result[0][0] || !result[1][0]) {
				// print the images
				System.out.println("Example image :");
				printBooleanArray(example);
				System.out.println("Resulting image : ");
				printBooleanArray(result);
				fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
						"with only one black pixel in the center, did not " +
						"return an 1x1 black image.");
			}
		}

		private boolean identicalImage(boolean[][] img1, boolean[][] img2){
			boolean res = img1.length==img2.length;
			if (res && img1.length>0) {
				for (int i=0; res && i<img1.length; i++) {
					res = img1[i].length==img2[i].length;
					if (res){
						for (int j=0; res && j<img1[0].	length; j++) {
							res = img1[i][j]==img2[i][j];
						}
					}
				}
			}
			return res;
		}
		
		private void printBooleanArray(boolean[][] bin) {
			if (bin.length!=0) {
				for (int j=0; j<bin[0].length; j++) {
					for (int i=0; i<bin.length; i++) {
						if (bin[i][j]) {
							System.out.print("b");
						} else {
							System.out.print("w");
						}
					}
					System.out.println();
				}
			}
		}
}
