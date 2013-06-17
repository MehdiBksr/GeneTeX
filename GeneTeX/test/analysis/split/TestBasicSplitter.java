package analysis.split;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;


import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import data.Block;
import data.Line;
import data.PreprocessedImage;
import data.Symbol;
import data.imagedata.SplitPage;
import data.imagedata.SplitSymbol;

import util.Displayer;

public class TestBasicSplitter {
		
		static Displayer displayer;

		public static void main(String[] args) throws InterruptedException {
			String[] filename = {
					"images\\testGeneTeXexact.png", 
					"images\\gltroll.png",
					"images\\test_merge.png",
					"images\\complex_example.jpg",
					"images\\alphabet.jpg",
					"images\\portez.jpg",
					"images\\portez2.jpg"};
//			displayFile(filename[5]);
//			displaySymbol(filename[5], 1, 2);
//			displaySymbol(filename[1], 1, 5);
			
			for (int i = 1; i <= 30; i++) {
				displaySymbol(filename[5], 1, i);
				Thread.sleep(500);
			}
			
			for (int i = 1; i <= 14; i++) {
				displaySymbol(filename[5], 2, i);
				Thread.sleep(500);
			}
	    }
		
		/**
		 * Displays a split symbol of the specified text image.
		 * If the x or y index is before or after the first or last symbol of 
		 * line, the first or last symbol or line is chosen (e.g. the index 0
		 * works as if the selected index was 1). 
		 * @param file The source file name.
		 * @param y The line containing the symbol (first line at 1)
		 * @param x The symbol's number in the line (first symbol at 1)
		 */
		private static void displaySymbol(String file, int y, int x) {
			BufferedImage bufferedImage = null;
			Preprocessor proc = new BasicPreprocessor();
			BasicSplitter splitter = new BasicSplitter();
			int currentLineNumber = 1;
			int currentSymbolNumber = 1;
			
			try {
				bufferedImage = ImageLoader.load(file);
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
			
			PreprocessedImage bin = proc.preprocess(bufferedImage);
			SplitPage page = splitter.split(bin, true);
			Iterator<Block> itBlock = page.getIterator();
			while (itBlock.hasNext()) {
				Iterator<Line> itLine = itBlock.next().getIterator();
				Line currentLine = itLine.next();
				while (itLine.hasNext() && currentLineNumber < y) {
					currentLine = itLine.next();
					currentLineNumber++;
				}
				Iterator<Symbol> itSym = currentLine.getIterator();
				Symbol s = itSym.next();
				while (itSym.hasNext() && currentSymbolNumber < x) {
					 s = itSym.next();
					 currentSymbolNumber++;
				}

				displayer = new Displayer(toBI(new PreprocessedImage(
						((SplitSymbol) s).getBinary())));
			}
		}
		
		/**
		 * Displays the given file.
		 * @param file
		 */
		private static void displayFile(String file) {
			BufferedImage bufferedImage = null;
			try {
				bufferedImage = ImageLoader.load(file);
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
			
			displayer = new Displayer(bufferedImage);
		}
		
//		@Test
//		public void testRemoveMargin() {
//			boolean[][] example;
//			boolean[][] result;
//			
//			///////////////////////////////////////////////////////////////////
//			//  _  ////////////////////////////////////////////////////////////
//			// |*| ////////////////////////////////////////////////////////////
//			//  -  ////////////////////////////////////////////////////////////
//			///////////////////////////////////////////////////////////////////
//			example = new boolean [1][1];
//			example[0][0] = true;
//			result = BasicSplitter.removeMargins(example);
//			if (!identicalImage(example,result)) {
//				// print the images
//				System.out.println("Example image :");
//				printBooleanArray(example);
//				System.out.println("Resulting image : ");
//				printBooleanArray(result);
//				fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
//						"with only one black pixel in the center, did not " +
//						"return an 1x1 black image.");
//			}
//			
//			///////////////////////////////////////////////////////////////////
//			//  __        __        __        __  /////////////////////////////
//			// | *| then |**| then |* | then |**| /////////////////////////////
//			// |**|      | *|      |**|      |* | /////////////////////////////
//			//  --        --        --        --  /////////////////////////////
//			///////////////////////////////////////////////////////////////////
//			for (int bi=0; bi<2; bi++) {
//				for (int bj=0; bj<2; bj++) {
//					example = new boolean [2][2];
//					for (int i=0; i<2; i++) {
//						for (int j=0; j<2; j++) {
//							example[i][j] = !(i==bi && j==bj);
//						}
//					}
//					result = BasicSplitter.removeMargins(example);
//					if (!identicalImage(example,result)) {
//						// print the images
//						System.out.println("Example image :");
//						printBooleanArray(example);
//						System.out.println("Resulting image : ");
//						printBooleanArray(result);
//						fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
//								"with only one black pixel in the center, did not " +
//								"return an 1x1 black image.");
//					}
//				}
//			}
//
//			///////////////////////////////////////////////////////////////////
//			//  __        __  /////////////////////////////////////////////////
//			// |* | then | *| /////////////////////////////////////////////////
//			// | *|      |* | /////////////////////////////////////////////////
//			//  --        --  /////////////////////////////////////////////////
//			///////////////////////////////////////////////////////////////////
//			for (int b=0; b<2; b++) {
//					example = new boolean [2][2];
//					for (int i=0; i<2; i++) {
//						for (int j=0; j<2; j++) {
//							if (b==0) {
//								example[i][j] = i==j;
//							} else {
//								example[i][j] = i!=j;
//							}
//						}
//					}
//					result = BasicSplitter.removeMargins(example);
//					if (!identicalImage(example,result)) {
//						// print the image
//						System.out.println("Example image :");
//						printBooleanArray(example);
//						System.out.println("Resulting image : ");
//						printBooleanArray(result);
//						fail("Call to BasicSplitter.removeMargins on an 2x2 image, " +
//								"with one black diagonal in the center, did not " +
//								"the same image.");
//					}
//			}
//
//			///////////////////////////////////////////////////////////////////
//			//  __        __  /////////////////////////////////////////////////
//			// |**| then |  | /////////////////////////////////////////////////
//			// |  |      |**| /////////////////////////////////////////////////
//			//  --        --  /////////////////////////////////////////////////
//			///////////////////////////////////////////////////////////////////
//			for (int bj=0; bj<2; bj++) {
//					example = new boolean [2][2];
//					for (int i=0; i<2; i++) {
//						for (int j=0; j<2; j++) {
//							example[i][j] = j==bj;
//						}
//					}
//					result = BasicSplitter.removeMargins(example);
//					if (result.length!=2 || result[0].length!=1 || !result[0][0] || !result[1][0]) {
//						// print the image
//						System.out.println("Example image :");
//						printBooleanArray(example);
//						System.out.println("Resulting image : ");
//						printBooleanArray(result);
//						fail("Call to BasicSplitter.removeMargins on an 2x2 image, " +
//								"with only one horizontal black line in the center, did not " +
//								"return an 2x1 black image.");
//					}
//			}
//
//			///////////////////////////////////////////////////////////////////
//			//  __  ///////////////////////////////////////////////////////////
//			// |  | ///////////////////////////////////////////////////////////
//			// |**| ///////////////////////////////////////////////////////////
//			// |  | ///////////////////////////////////////////////////////////
//			//  --  ///////////////////////////////////////////////////////////
//			///////////////////////////////////////////////////////////////////
//			example = new boolean [2][3];
//			for (int i=0; i<2; i++) {
//				for (int j=0; j<3; j++) {
//					example[i][j] = j==1;
//				}
//			}
//			result = BasicSplitter.removeMargins(example);
//			if (result.length!=2 || result[0].length!=1 || !result[0][0] || !result[1][0]) {
//				// print the images
//				System.out.println("Example image :");
//				printBooleanArray(example);
//				System.out.println("Resulting image : ");
//				printBooleanArray(result);
//				fail("Call to BasicSplitter.removeMargins on an 3x3 image, " +
//						"with only one black pixel in the center, did not " +
//						"return an 1x1 black image.");
//			}
//		}

//		private boolean identicalImage(boolean[][] img1, boolean[][] img2){
//			boolean res = img1.length==img2.length;
//			if (res && img1.length>0) {
//				for (int i=0; res && i<img1.length; i++) {
//					res = img1[i].length==img2[i].length;
//					if (res){
//						for (int j=0; res && j<img1[0].	length; j++) {
//							res = img1[i][j]==img2[i][j];
//						}
//					}
//				}
//			}
//			return res;
//		}
		
//		private void printBooleanArray(boolean[][] bin) {
//			if (bin.length!=0) {
//				for (int j=0; j<bin[0].length; j++) {
//					for (int i=0; i<bin.length; i++) {
//						if (bin[i][j]) {
//							System.out.print("o");
//						} else {
//							System.out.print(".");
//						}
//					}
//					System.out.println();
//				}
//			}
//		}
		
		/**
		 * Transforms a pre-processed image into a buffered image that can be 
		 * displayed. 
		 * 
		 * @param preprocessedImage The image as a PreprocessedImage.
		 * @return The image as a BufferedImage.
		 */
		private static BufferedImage toBI(PreprocessedImage preprocessedImage) {
			BufferedImage bi = 
					new BufferedImage(preprocessedImage.getPixels().length, 
							preprocessedImage.getPixels()[0].length, 
							BufferedImage.TYPE_INT_ARGB);

			for (int i = 0; i < preprocessedImage.getPixels().length; i++) {
				for (int j = 0; j < preprocessedImage.getPixels()[i].length; j++) {
					if (preprocessedImage.getPixels()[i][j])
						bi.setRGB(i, j, 0xFF000000);
					else
						bi.setRGB(i, j, 0xFFFFFFFF);
				}
			}
			return bi;
		}
}
