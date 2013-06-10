package analysis.recognition.neuralnetwork.learning;

import imageloader.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.recognition.neuralnetwork.NeuralNetwork;
import data.PreprocessedImage;
import data.contentdata.Token;
import data.imagedata.SplittedSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;
import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

public class NeuralNetworkLearner {

	public static void main(String[] args) throws IOException, 
	ClassNotFoundException, FileNotFoundException, ComputePrimitivesException, NeuronException, NeuralNetworkException, NeuronLayerException {

		FileInputStream getLearningData = new FileInputStream("learning.object");
		ObjectInputStream objLearningData = new ObjectInputStream(getLearningData);
		NeuralNetwork network = (NeuralNetwork)objLearningData.readObject();
		objLearningData.close();
		getLearningData.close();

		int nbSamplesExamined = 0;
		float squareGradientSum = 0;
		float alpha = (float)1.1;
		while (squareGradientSum > 0.000001*nbSamplesExamined) {
			nbSamplesExamined = 0;
			squareGradientSum = 0;
			for (Token T : Token.values()) {
				File dirName = new File("learningdata\\" + T.getSampleDirectory());
				File[] samples = dirName.listFiles();
				BufferedImage currentSample = null;
				for (int i = 0; i < samples.length; i++) {
					nbSamplesExamined++;
					currentSample = ImageLoader.load(samples[i].getName());
					Preprocessor proc = new BasicPreprocessor();
					PreprocessedImage binarizedSample = proc.binarise(currentSample);
					SplittedSymbol currentSymbol = new SplittedSymbol(binarizedSample.getPixels(), 0, 0);
					network.recognise(currentSymbol);
					squareGradientSum +=network.adaptSynapticWeights(T, alpha);
				}
			}

			try {
				FileOutputStream saveLearningData = new FileOutputStream("learning.object");
				ObjectOutputStream saver = new ObjectOutputStream(saveLearningData);
				saver.writeObject(network);
				saver.close();
				saveLearningData.close();
			} catch (FileNotFoundException e) {
				System.out.println("AAARGH !");
			}
		}
	}
}







