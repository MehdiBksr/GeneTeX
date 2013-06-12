package analysis.preprocess;

import javax.swing.SwingUtilities;

import util.JFrameBinarisedFile;

/** Test for the basic implementation of the preprocessor.
 * 
 * @author bouksarm, merlet, thalgotm
 *
 */
public class TestBasicPreprocessor {

	static JFrameBinarisedFile jFrameBin;

	public static void main(String[] args) {
	    testBinarisedFile();
    }
	
	/** Runs the test, which binarises a given image, converts it back to a
	 * BufferedImage and displays it.
	 */
	private static void testBinarisedFile() {
		SwingUtilities.invokeLater(runJFrameBinarisedFile);
	}
	 
	static Runnable runJFrameBinarisedFile = new Runnable() {
	    @Override
	    public void run() {
	        jFrameBin = new JFrameBinarisedFile("images\\abc.png");
	        jFrameBin.setVisible(true);
	    }
	};
	
}
