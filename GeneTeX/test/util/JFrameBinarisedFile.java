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

@SuppressWarnings("serial")
public class JFrameBinarisedFile extends JFrame {
 
	public JFrameBinarisedFile() {
	   this.setTitle("java-buddy.blogspot.com");
	   this.setSize(300, 200);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	   BufferedImage bufferedImage = null;
	   try {
	       bufferedImage = ImageLoader
	       		.load("images\\ClassesDiagram.png");
	   } catch (IOException ex) {
	       Logger.getLogger(TestImageLoader.class.getName()).log(
	       		Level.SEVERE, null, ex);
	   }
	   Preprocessor proc = BasicPreprocessor.getInstance();
	   bufferedImage = toBI(proc.binarise(bufferedImage));
	   JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
	    
	   JPanel jPanel = new JPanel();
	   jPanel.add(jLabel);
	   this.add(jPanel);
	}
	
	private static BufferedImage toBI(PreprocessedImage preprocessedImage) {
		BufferedImage bi = new BufferedImage(preprocessedImage.getPixels().length, preprocessedImage.getPixels()[0].length, 
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
