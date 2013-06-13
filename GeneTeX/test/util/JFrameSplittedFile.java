package util;

import imageloader.ImageLoader;
import imageloader.TestImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Block;
import data.Line;
import data.PreprocessedImage;
import data.Symbol;
import data.imagedata.SplittedPage;
import data.imagedata.SplittedSymbol;
import error.data.BadInstanceException;

import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.split.BasicSplitter;

@SuppressWarnings("serial")
public class JFrameSplittedFile extends JFrame {

	public JFrameSplittedFile(String fileName) throws BadInstanceException {
		this.setTitle("java-buddy.blogspot.com");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageLoader
					.load(fileName);
		} catch (IOException ex) {
			Logger.getLogger(TestImageLoader.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage bin = proc.preprocess(bufferedImage);
		BasicSplitter splitter = new BasicSplitter();
		SplittedPage page = splitter.split(bin);
		Iterator<Block> itBlock = page.getIterator();
		while (itBlock.hasNext()) {
			Block currentBlock = itBlock.next();
			Iterator<Line> itLine = currentBlock.getIterator();
			if (itLine.hasNext()) {
				Line currentLine = itLine.next();
				Iterator<Symbol> itSymbol = currentLine.getIterator();
				if (itSymbol.hasNext()) {
					SplittedSymbol symbol = (SplittedSymbol)itSymbol.next();
					symbol = (SplittedSymbol)itSymbol.next();
					symbol = (SplittedSymbol)itSymbol.next();
					symbol = (SplittedSymbol)itSymbol.next();
					symbol = (SplittedSymbol)itSymbol.next();
					PreprocessedImage symbolArray = new PreprocessedImage(symbol.getBinary());
					bufferedImage = toBI(symbolArray);
					JLabel jLabel = new JLabel(new ImageIcon(bufferedImage));

					JPanel jPanel = new JPanel();
					jPanel.add(jLabel);
					this.add(jPanel);
				} 
			}
		}
		




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