package imageloader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TestImageLoader {
	
	static JFrameSourceFile jFrameSrc;

	public static void main(String[] args) {
	    testSourceFile();
    }
	
	private static void testSourceFile() {
		SwingUtilities.invokeLater(runJFrameSourceFile);
	}
	
	@SuppressWarnings("serial")
	private static class JFrameSourceFile extends JFrame {
	     
	   public JFrameSourceFile() {
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
	        
	       JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));
	        
	       JPanel jPanel = new JPanel();
	       jPanel.add(jLabel);
	       this.add(jPanel);
	   }
	}

	static Runnable runJFrameSourceFile = new Runnable() {
	    @Override
	    public void run() {
	        jFrameSrc = new JFrameSourceFile();
	        jFrameSrc.setVisible(true);
	    }
	};

}
