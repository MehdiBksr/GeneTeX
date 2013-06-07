package analysis.preprocess;

import javax.swing.SwingUtilities;

import util.JFrameBinarisedFile;

public class TestBasicPreprocessor {

	static JFrameBinarisedFile jFrameBin;

	public static void main(String[] args) {
	    testBinarisedFile();
    }
	
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
