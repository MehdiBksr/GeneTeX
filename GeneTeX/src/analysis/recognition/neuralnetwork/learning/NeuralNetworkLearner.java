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
import data.imagedata.SplitSymbol;
import error.analysis.recognition.neuralnetwork.ComputePrimitivesException;
import error.analysis.recognition.neuralnetwork.NeuralNetworkException;
import error.analysis.recognition.neuralnetwork.NeuronException;
import error.analysis.recognition.neuralnetwork.NeuronLayerException;

	/** 
	 * Class responsible for neural network learning. This class is not
	 * directly linked to the neural network, but the neural network
	 * needs the learning module to work correctly.
	 * This module has a main method, which allows to train the neural network
	 * and to save its object representation in a file.
	 * 
	 * @author Mehdi
	 *
	 */
	public class NeuralNetworkLearner {
	
		public static void main(String[] args) throws IOException, 
		ClassNotFoundException, FileNotFoundException, ComputePrimitivesException, NeuronException, NeuralNetworkException, NeuronLayerException {
	
			NeuralNetwork network;
			
			// trying to load the neural network
			try {
//			FileInputStream getLearningData = new FileInputStream("91charOutOf95.save");
			FileInputStream getLearningData = new FileInputStream("learning.object");
			ObjectInputStream objLearningData = new ObjectInputStream(getLearningData);
			network = (NeuralNetwork)objLearningData.readObject();
			objLearningData.close();
			getLearningData.close();
			} catch (FileNotFoundException e) {
				// if no save was found, create a new neural network
				System.out.println("File learning.object not found, creating new network");
				network = new NeuralNetwork(150);
			} 
	
			int nbSamplesExamined = 0;
			float squareGradientSum = 0;
			float quadraticError = 0;
			int nbSuccess = 0, nbRecognitions = 0;
			float alpha = (float)1.1;
			// as long as the quadratic error is above a given value
			while (quadraticError >= (1/1000000000)*nbSamplesExamined) {
				
				nbSamplesExamined = 0;
				squareGradientSum = 0;
				quadraticError = 0;
				nbSuccess = 0;
				nbRecognitions = 0;
				
				for (Token T : Token.values()) {
					/* getting the name of the directory containing the samples
					 * for this token */
					File dirName = new File("learningdata/" + T.getSampleDirectory());
//					System.out.println("Nom du dossier : " + dirName.getName());
					File[] samples = dirName.listFiles();
					if (samples == null) {
						System.out.println("Dossier inexistant pour " + T + ".");
						continue;
					}
					
					// learning for the samples in the directory
					BufferedImage currentSample = null;
					for (int i = 0; i < samples.length; i++) {
						nbSamplesExamined++;
						currentSample = ImageLoader.load(samples[i].getAbsolutePath());
						Preprocessor proc = new BasicPreprocessor();
						PreprocessedImage binarizedSample = proc.preprocess(currentSample);
						System.out.println(binarizedSample.getPixels().length);
						SplitSymbol currentSymbol = new SplitSymbol(binarizedSample.getPixels(), 0, 0);
						Token returnedToken = network.recognise(currentSymbol).getToken();
						if (returnedToken == T) {
							nbSuccess++;
						} else {
							// System.out.println(T + " is not recognised. " + returnedToken + " was returned instead.");
						}
						nbRecognitions++;
						// calculating total squared gradient
						quadraticError += network.quadraticError(T);
						// learning according to the results 
						squareGradientSum +=network.adaptSynapticWeights(T, alpha);
					}	
				}
				System.out.println("Number of successes = " + nbSuccess +
						", number of recognition = " + nbRecognitions);
				System.out.println("Total quadratic error = " + quadraticError +
						", average quadratic error per output neuron = " + quadraticError/(Token.values().length*Token.values().length));
				System.out.println("Total squared gradient = " + squareGradientSum);
	
				// saving the new network data
				FileOutputStream saveLearningData = null;
				ObjectOutputStream saver = null;
				try {
					saveLearningData = new FileOutputStream("learning.object");
					saver = new ObjectOutputStream(saveLearningData);
				} catch (FileNotFoundException e) {
					System.out.println("File learning.object not found, creating it");
					File createFile = new File("learning.object");
					createFile.createNewFile();
					System.out.println("File created");
					saveLearningData = new FileOutputStream("learning.object");
					saver = new ObjectOutputStream(saveLearningData);
				} finally {
					saver.writeObject(network);
					saver.close();
					saveLearningData.close();
				}
			}
		}
	}
	
	
	
	
	
	
	
