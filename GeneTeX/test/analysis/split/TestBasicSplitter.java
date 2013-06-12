package analysis.split;

import javax.swing.SwingUtilities;

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


}
