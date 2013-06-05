package analysis.preprocess;

import java.awt.image.BufferedImage;

public interface Preprocessor {

	public boolean[][] binarise(BufferedImage image);
	
}
