package analysis.preprocess;

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
import javax.swing.SwingUtilities;

public class TestBasicPreprocessor {

	static JFrameBinarisedFile jFrameBin;

	public static void main(String[] args) {
	    testBinarisedFile();
    }
	
	private static void testBinarisedFile() {
		SwingUtilities.invokeLater(runJFrameBinarisedFile);
	}
    
	@SuppressWarnings("serial")
	private static class JFrameBinarisedFile extends JFrame {
	 
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
		   bufferedImage = toBI(BasicPreprocessor.binarise(bufferedImage));
		   JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
		    
		   JPanel jPanel = new JPanel();
		   jPanel.add(jLabel);
		   this.add(jPanel);
		}
	}
	 
	static Runnable runJFrameBinarisedFile = new Runnable() {
	    @Override
	    public void run() {
	        jFrameBin = new JFrameBinarisedFile();
	        jFrameBin.setVisible(true);
	    }
	};
	
	private static BufferedImage toBI(boolean[][] bin) {
		BufferedImage bi = new BufferedImage(bin.length, bin[0].length, 
				BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < bin.length; i++) {
			for (int j = 0; j < bin[i].length; j++) {
				if (bin[i][j])
					bi.setRGB(i, j, 0xFF000000);
				else
					bi.setRGB(i, j, 0xFFFFFFFF);
			}
		}
		
		return bi;
	}
}
