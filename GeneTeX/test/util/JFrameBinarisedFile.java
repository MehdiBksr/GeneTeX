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
	   bufferedImage = Utility.toBI(proc.preprocess(bufferedImage));

	   
	   JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
	    
	   JPanel jPanel = new JPanel();
	   jPanel.add(jLabel);
	   this.add(jPanel);
	}
	
	
}
