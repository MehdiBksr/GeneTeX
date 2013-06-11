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
	
			NeuralNetwork network;
			
			try {
			FileInputStream getLearningData = new FileInputStream("learning.object");
			ObjectInputStream objLearningData = new ObjectInputStream(getLearningData);
			network = (NeuralNetwork)objLearningData.readObject();
			objLearningData.close();
			getLearningData.close();
			} catch (FileNotFoundException e) {
				System.out.println("File learning.object not found, creating new network");
				network = new NeuralNetwork(150);
			} 
	
			int nbSamplesExamined = 0;
			float squareGradientSum = 0;
			float quadraticError = 0;
			int nbSuccess = 0, nbRecognition = 0;
			float alpha = (float)1.1;
			while (squareGradientSum >= (1/1000000000)*nbSamplesExamined) {
//				System.out.println("DADA");
//				System.out.println("Norme du gradient au carr� = " + 
//			squareGradientSum + ", seuil = " + 0.000001*nbSamplesExamined);
				nbSamplesExamined = 0;
				squareGradientSum = 0;
				quadraticError = 0;
				nbSuccess = 0;
				nbRecognition = 0;
				for (Token T : Token.values()) {
					File dirName = new File("learningdata/" + T.getSampleDirectory());
//					System.out.println("Nom du dossier : " + dirName.getName());
					File[] samples = dirName.listFiles();
					if (samples == null) {
						System.out.println("Dossier inexistant pour " + T + ".");
						continue;
					}
					BufferedImage currentSample = null;
					for (int i = 0; i < samples.length; i++) {
						nbSamplesExamined++;
//						System.out.println("Nom du fichier : " + samples[i].getAbsolutePath());
						currentSample = ImageLoader.load(samples[i].getAbsolutePath());
						Preprocessor proc = new BasicPreprocessor();
						PreprocessedImage binarizedSample = proc.binarise(currentSample);
						SplittedSymbol currentSymbol = new SplittedSymbol(binarizedSample.getPixels(), 0, 0);
						Token returnedToken = network.recognise(currentSymbol).getToken();
//						network.printOutputValues(T);
						if (returnedToken == T) {
//							System.out.println(T + " is recognised.");
							nbSuccess++;
						} else {
							System.out.println(T + " is not recognised. " + returnedToken + " was returned instead.");
						}
						nbRecognition++;
						quadraticError += network.quadraticError(T);
						squareGradientSum +=network.adaptSynapticWeights(T, alpha);
					}
//					System.out.println("-------------------\n\n-------------------");
//					System.out.println("Nombre d'exemples analys�s = " + nbSamplesExamined);
					
				}
				System.out.println("Number of successes = " + nbSuccess +
						", number of recognition = " + nbRecognition);
				System.out.println("Total quadratic error = " + quadraticError +
						", average quadratic error per output neuron = " + quadraticError/(Token.values().length*Token.values().length));
				System.out.println("Total squared gradient = " + squareGradientSum);
	
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
	
	
	
	
	
	
	
