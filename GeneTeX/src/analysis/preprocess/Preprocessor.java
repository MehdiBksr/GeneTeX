package analysis.preprocess;

import java.awt.image.BufferedImage;

import data.PreprocessedImage;

public interface Preprocessor {

	public PreprocessedImage preprocess(BufferedImage image);
	
}
