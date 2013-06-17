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
import java.util.EnumMap;
import java.util.Random;

import analysis.preprocess.BasicPreprocessor;
import analysis.preprocess.Preprocessor;
import analysis.recognition.neuralnetwork.NeuralNetwork;
import data.PreprocessedImage;
import data.contentdata.Token;
import data.imagedata.SplitLine;
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
 * @author Mehdi BOUKSARA, Théo MERLE, Marceau THALGOTT 
 *
 */
public class NeuralNetworkLearner {

	/* ************************************************************************
	 *                            MAIN FUNCTION                               * 
	 ************************************************************************ */
	
	/**
	 * The name of the file where the neural network is saved. If the file does
	 * not exist, the learner will have to create a new network and its file.
	 */
	private static String networkFileName = "learning.object";

	/**
	 * Launches the learning process. If no network was saved, it will create a
	 * new one. The network is saved after each training iteration, so that the
	 * program can be interrupted and still save a valid network.
	 * 
	 * @param args For the moment no options are managed.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 * @throws NeuralNetworkException
	 * @throws NeuronLayerException
	 */
	public static void main(String[] args) throws IOException,
	ClassNotFoundException, FileNotFoundException, ComputePrimitivesException,
	NeuronException, NeuralNetworkException, NeuronLayerException {
		(new NeuralNetworkLearner()).run();
	}

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */

	/** The neural network to train. */
	private NeuralNetwork network;
	
	/** 
	 * Contains the file descriptors for the learning data determined by the
	 *  cross-validation.
	 */
	private EnumMap<Token, File[]> learningData;
	
	/** 
	 * Contains the file descriptors for the validation data determined by the
	 *  cross-validation.
	 */
	private EnumMap<Token, File[]> validationData;
	
	/** Determine the scale of the parameter adaptation. */
	private float alpha;
	
	/** Used to count the number of success for a given token. */
	private int nbSuccess;
	
	/** Used to count the number of samples processed for a given token. */
	private int nbRecognitions;
	
	/** Used to count the total number of samples processed. */
	private int nbSamplesExamined;
	
	/** Used to measure the average success rate after a validation phase. */
	private int globalSuccessRate;
	
	/**
	 * Used to stock the success rate for each token after a validation phase.
	 */
	private EnumMap<Token, Float> recognitionValues;

	/**
	 * Used to measure the quadratic error during a validation or
	 *  a training phase. The sum is on all samples and all token.
	 */
	private float quadraticError;
	
	/**
	 * Used to measure the squared norm of the quadratic error's gradient,
	 * for all samples and tokens.
	 */
	private float squareGradientSum;
	
	/**
	 * Used to measure the squared norm of the quadratic error's gradient,
	 * for all samples of a given token.
	 */
	private float localQuadraticError;

	/** Indicates if the cross-validation determined validation data */
	private boolean hasValidationData;
	
	/** Indicates if the learning process was not able execute an iteration */
	private boolean learningBlocked;
	
	/* ************************************************************************
	 *                              CONSTRUCTORS                              * 
	 ************************************************************************ */

	/**
	 * Loads the saved neural network or create a new one if no network was
	 * saved. It then initialises all parameters and measure of the training
	 * process.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public NeuralNetworkLearner() throws ClassNotFoundException, IOException {

		this.loadNeuralNetwork();
		this.alpha = (float) 1.1;

		this.nbSamplesExamined = 0;
		this.squareGradientSum = 0;
		this.quadraticError = 95;
		this.nbSuccess = 0;
		this.nbRecognitions = 0;
		this.localQuadraticError = 0;
		this.globalSuccessRate = 0;
		
		this.hasValidationData = false;
		this.learningBlocked = false;
	}

	/**
	 * Execute the training process. It uses a cross-validation to determine
	 * validation and learning data. It then adapts the network to the learning
	 * data and check its efficiency on the validation data. 
	 * 
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 * @throws NeuralNetworkException
	 * @throws NeuronLayerException
	 * @throws IOException
	 */
	public void run() throws ComputePrimitivesException, NeuronException,
	NeuralNetworkException, NeuronLayerException, IOException {

		while (!this.learningBlocked && this.globalSuccessRate < 0.8){
			System.out.println("Improve the network with a new random " +
					"cross-validation.");
			// choose the validation and training data.
			this.createCrossValidationData();
			// Trains the network.
			this.trainNetwork();
			if (this.hasValidationData) {
				// Check the error on the validation data.
				System.out.println("lolilol");
				this.checkOnValidationData();
			}
		}
		System.out.println();
		System.out.println("Training of the neural network has ended.");
	}


	/* ************************************************************************
	 *      PRIVATE FUNCTIONS FOR SAVING AND LOADING THE NEURAL NETWORK       * 
	 ************************************************************************ */

	/**
	 * Loads the saved neural network from the file "networkFileName". If no
	 * save file is found, it will create a new network with random synaptic
	 * weights.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void loadNeuralNetwork() throws IOException,
	ClassNotFoundException {
		// trying to load the neural network
		try {
			FileInputStream getLearningData =
					new FileInputStream(networkFileName);
			ObjectInputStream objLearningData =
					new ObjectInputStream(getLearningData);
			network = (NeuralNetwork)objLearningData.readObject();
			objLearningData.close();
			getLearningData.close();
		} catch (FileNotFoundException e) {
			// if no save was found, create a new neural network
			System.out.println("File learning.object not found," +
					" creating new network");
			network = new NeuralNetwork(150);
		}
	}

	/**
	 * Saves the saved neural network to the file "networkFileName". If the
	 * file does not exist, it is created.
	 * 
	 * @throws IOException
	 */
	private void saveNeuralNetwork() throws IOException {
		FileOutputStream saveLearningData = null;
		ObjectOutputStream saver = null;
		try {
			saveLearningData = new FileOutputStream(networkFileName);
			saver = new ObjectOutputStream(saveLearningData);
		} catch (FileNotFoundException e) {
			System.out.println("File learning.object not found, creating it");
			File createFile = new File(networkFileName);
			createFile.createNewFile();
			System.out.println("File created");
			saveLearningData = new FileOutputStream(networkFileName);
			saver = new ObjectOutputStream(saveLearningData);
		} finally {
			saver.writeObject(this.network);
			saver.close();
			saveLearningData.close();
		}
	}


	/* ************************************************************************
	 *              PRIVATE FUNCTIONS FOR THE CROSS-VALIDATION                *
	 ************************************************************************ */

	/**
	 * For each token to be recognised, it loads all associated samples and then
	 * sorts them into 2 types
	 * <li>
	 * <ul> learning data
	 * <ul> validation data
	 * </li>
	 * It ensures that both types have the same number of samples, with possibly
	 * one more learning sample.
	 */
	private void createCrossValidationData() {
		Random randomGenerator = new Random();

		// initialise the 2-fold cross-validation
		this.learningData = new EnumMap<Token, File[]>(Token.class);
		this.validationData = new EnumMap<Token, File[]>(Token.class);
		for (Token T : Token.values()) {

			/* getting the name of the directory containing the samples
			 * for this token */
			File dirName = new File("learningdata/" + T.getSampleDirectory());
//			System.out.println("Directory name : " + dirName.getName());
			File[] samples = dirName.listFiles();
			if (samples == null) {
				System.out.println("Dossier inexistant pour " + T + ".");
				continue;
			}

			// determining the numbers of validation and learning data.
			int sampleIndex = -1;
			int learningSamplesSize = samples.length/2;
			if (samples.length - learningSamplesSize > learningSamplesSize) {
				learningSamplesSize = samples.length - learningSamplesSize;
			}
			int validationSamplesSize = samples.length - learningSamplesSize;
			this.hasValidationData = validationSamplesSize != 0;
			
//			System.out.println(learningSamplesSize + " learning data and " +
//					validationSamplesSize + " validation data.");
			// randomly choosing the type of each sample.
			File[] learningSamples = new File[learningSamplesSize];
			File[] validationSamples = new File[validationSamplesSize];
			while (learningSamplesSize + validationSamplesSize > 0){
				int r = randomGenerator.nextInt(
						learningSamplesSize + validationSamplesSize);
				if (r < learningSamplesSize) {
					sampleIndex++;
					learningSamplesSize--;
					learningSamples[learningSamplesSize] =
							samples[sampleIndex];
//					System.out.println("learning file : " +
//							learningSamples[learningSamplesSize]);
				} else {
					sampleIndex++;
					validationSamplesSize--;
					validationSamples[validationSamplesSize] =
							samples[sampleIndex];
				}
			}
			

			// adding the samples to the maps.
			this.learningData.put(T, learningSamples);
			this.validationData.put(T, validationSamples);
		}
	}

	/**
	 * Check the neural network's results on the validation data determined by
	 * the cross-validation. It measures the average quadratic error for a
	 * sample, the success rate for each token and the average success rate for
	 * a token. 
	 * 
	 * @throws IOException
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 */
	private void checkOnValidationData() throws IOException,
	ComputePrimitivesException, NeuronException {
		//Check the error on the validation data
		this.nbSamplesExamined = 0;
		this.squareGradientSum = 0;
		this.quadraticError = 0;
		this.nbSuccess = 0;
		this.nbRecognitions = 0;
		this.globalSuccessRate = 0;
		this.recognitionValues = new EnumMap<Token, Float>(Token.class);
		for (Token T : Token.values()) {
			if (validationData.get(T).length>0) {
				// checking for the samples in the directory
				this.nbSuccess = 0;
				this.nbRecognitions = 0;
				this.localQuadraticError = 0;
				for (int i = 0; i < validationData.get(T).length; i++) {
					this.processValidationSample(ImageLoader.load(
							validationData.get(T)[i].getAbsolutePath()), T);
				}
				// rate of correctly recognised samples for T.
				float successRate =
						((float)this.nbSuccess) / ((float)this.nbRecognitions);
				this.recognitionValues.put(T, successRate);
				this.globalSuccessRate += successRate;
				System.out.println("Percent of recognised validation data for "
						+ T + " : " + (100*successRate) + "%");
				// updating the quadratic error.
				System.out.println("Average quadratic error on token " + T +
						" : " +	this.localQuadraticError/this.nbRecognitions +
						", on validation data.");
				quadraticError += this.localQuadraticError/this.nbRecognitions;
			} else {
				this.recognitionValues.put(T, (float) 0);
				quadraticError += Token.values().length/2.0;
				System.out.println("No validation data available for the " +
						"token " + T + ".");
			}
		}
		this.globalSuccessRate /= Token.values().length;
		System.out.println("Global success rate on validation data : " +
				this.globalSuccessRate);
		this.quadraticError /= Token.values().length * this.nbSamplesExamined;
		System.out.println("Average quadratic error on all tokens : " +
				quadraticError + ", on validation data.");

	}

	/**
	 * Check if the neural network correctly recognise the token T,
	 * when analysing the sample currentSample.
	 * 
	 * It updates the number of correct and attempted recognitions, as well as
	 * the quadratic error for the validation data, for the token T. 
	 * 
	 * @param currentSample The validation sample to be tested.
	 * @param T             The expected result of the recognition.
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 */
	private void processValidationSample(BufferedImage currentSample, Token T)
			throws ComputePrimitivesException, NeuronException {

		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage binarizedSample = proc.preprocess(currentSample);
		SplitLine aLine =
				new SplitLine(0, 50, binarizedSample.getPixels().length);
		this.network.registerLineData(aLine);
		SplitSymbol currentSymbol =
				new SplitSymbol(binarizedSample.getPixels(), 0, 0);
		Token returnedToken = network.recognise(currentSymbol).getToken();
		if (returnedToken == T) {
			nbSuccess++;
		} else {
			System.out.println(T + " is not recognised. " + returnedToken +
					" was returned instead.");
		}
		nbRecognitions++;
		// updating the local quadratic error.
		localQuadraticError += network.quadraticError(T);

	}


	/* ************************************************************************
	 *                  PRIVATE FUNCTIONS FOR THE LEARNING                    *
	 ************************************************************************ */

	/**
	 * Trains the neural network on the learning data determined by the
	 * cross-validation. For all token, for all sample, it adapts the
	 * synaptic weights depending on the local quadratic error's gradient.
	 * 
	 * It measures the success rate on all learning samples, the quadratic
	 * error and its gradient's norm.
	 * 
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 * @throws NeuralNetworkException
	 * @throws NeuronLayerException
	 * @throws IOException
	 */
	private void trainNetwork() throws ComputePrimitivesException,
	NeuronException, NeuralNetworkException, NeuronLayerException, IOException {
		this.learningBlocked = true;
		// as long as the quadratic error is above a given value
		while ((Math.sqrt(this.quadraticError)/this.nbSamplesExamined) >
		(1.0/(10.0*Token.values().length))) {
			this.learningBlocked = false;
			
			this.nbSamplesExamined = 0;
			this.squareGradientSum = 0;
			this.quadraticError = 0;
			this.nbSuccess = 0;
			this.nbRecognitions = 0;

			for (Token T : Token.values()) {
				// learning for the samples in the directory
				if (learningData.get(T) != null){
					for (int i = 0; i < learningData.get(T).length; i++) {
//						System.out.println(learningData.get(T)[i]);
						this.processLearningSample(ImageLoader.load(
								learningData.get(T)[i].getAbsolutePath()), T);
					}
				}
			}
			System.out.println("Number of successes = " + nbSuccess +
					", number of recognition = " + nbRecognitions);
			System.out.println("Total quadratic error = " + quadraticError);
			System.out.println("Average quadratic error per output neuron" +
					" and per sample = " + (quadraticError / 
							(Token.values().length*this.nbSamplesExamined)));
			System.out.println("Total squared gradient = " + squareGradientSum);

			// saving the new network data
			this.saveNeuralNetwork();
		}

	}

	/**
	 * Adapts the synaptic weights depending on the quadratic error's gradient
	 * for this sample.
	 * 
	 * It updates the number of successes and recognitions, the quadratic error
	 * and its gradient's norm.
	 * 
	 * @param currentSample The learning sample for the network's adaptation.
	 * @param T             The token this sample corresponds to.
	 * @throws ComputePrimitivesException
	 * @throws NeuronException
	 * @throws NeuralNetworkException
	 * @throws NeuronLayerException
	 */
	private void processLearningSample(BufferedImage currentSample, Token T)
			throws ComputePrimitivesException, NeuronException,
			NeuralNetworkException, NeuronLayerException {
		nbSamplesExamined++;
		Preprocessor proc = new BasicPreprocessor();
		PreprocessedImage binarizedSample = proc.preprocess(currentSample);
		SplitLine aLine =
				new SplitLine(0, 50, binarizedSample.getPixels().length);
		this.network.registerLineData(aLine);
		SplitSymbol currentSymbol =
				new SplitSymbol(binarizedSample.getPixels(), 0, 0);
		Token returnedToken = network.recognise(currentSymbol).getToken();
		if (returnedToken == T) {
			nbSuccess++;
		} else {
			System.out.println(T + " is not recognised. " + returnedToken +
					" was returned instead.");
		}
		nbRecognitions++;
		// calculating total squared gradient
		quadraticError += network.quadraticError(T);
		// learning according to the results 
		squareGradientSum +=network.adaptSynapticWeights(T, alpha);
	}
}