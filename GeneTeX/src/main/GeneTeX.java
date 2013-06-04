package main;

import java.util.Vector;

import argument.ArgumentHandler;

/**
 * This is the main class of the program.
 * <p>
 * 
 * @return None.
 */
public class GeneTeX {
	public static void main(String[] args) {
		ArgumentHandler options = ArgumentHandler.getInstance();
		try {
			ArgumentHandler.initialise(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<String> source = options.getSourceFiles();
		String dest = options.getDestinationFile();
		if (options.getHelp()) {
			System.out.println(Help.getHelpString());
			return;
		}
		System.out.println("Source file : " + source.size());
		System.out.println("Destination file : " + dest);
		if (options.getVerbose()) {
			System.out.println("Verbose");
		}
	}
}
