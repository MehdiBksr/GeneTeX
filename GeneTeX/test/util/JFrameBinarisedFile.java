package util;

import imageloader.ImageLoader;
import imageloader.TestImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.PreprocessedImage;

import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;


/** Class testing the binarisation of a given image.
 * 
 * @author Mehdi
 *
 */
@SuppressWarnings("serial")
public class JFrameBinarisedFile extends JFrame {
 
	public JFrameBinarisedFile(String fileName) {
	   this.setTitle("java-buddy.blogspot.com");
	   this.setSize(300, 200);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	   BufferedImage bufferedImage = null;
	   // loading the image
	   try {
	       bufferedImage = ImageLoader
	       		.load(fileName);
	   } catch (IOException ex) {
	       Logger.getLogger(TestImageLoader.class.getName()).log(
	       		Level.SEVERE, null, ex);
	   }
	   // binarising the BufferedImage
	   Preprocessor proc = new BasicPreprocessor();
	   // turning it back to BufferedImage
	   bufferedImage = toBI(proc.preprocess(bufferedImage));

	   
	   JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
	    
	   JPanel jPanel = new JPanel();
	   jPanel.add(jLabel);
	   this.add(jPanel);
	}
	
	
	/** Reverts the binarisation process by creating a BufferedImage in which
	 * each pixel represents a pixel of the binarised image : if a given pixel
	 * is coloured (true), the corresponding pixel in the BufferedImage will
	 * be set to black.
	 * 
	 * @param preprocessedImage The PreprocessedImage representing a binarised image.
	 * @return a BufferedImage representing the binarised image.
	 */
	private static BufferedImage toBI(PreprocessedImage preprocessedImage) {
		BufferedImage bi = new BufferedImage(preprocessedImage.getPixels().length, preprocessedImage.getPixels()[0].length, 
				BufferedImage.TYPE_INT_ARGB);
		
		// looking the value of each pixel in the binarised image
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
