package argument;

import static org.junit.Assert.*;

import org.junit.Test;

import error.argumenthandler.InvalidCommandLineException;
import error.argumenthandler.InvalidDestinationException;

public class TestArgumentHandler {

	@Test
	public void testFailNoArgument() {
		String[] args = {};
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidCommandLineException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
	}
	
	@Test
	public void testFailNoSourceOrHelp() {
		String[] args = {"-v"};
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidCommandLineException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
	}

	@Test
	public void testSourceFile() {
		String[] args = {"source1.lol"};
		try {
			ArgumentHandler.initialise(args);
			assertEquals(ArgumentHandler.getInstance().getDestinationFile(),
					args[0] + ".tex");
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}

	@Test
	public void testOptionDestinationFileWithValidName() {
		String[] args = {"source.lol", "-r", "fichier.lol"};
		try {
			ArgumentHandler.initialise(args);
			assertEquals(ArgumentHandler.getInstance().getDestinationFile(),
					args[2]);
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}
	
	@Test
	public void testOptionDestinationFileWithInvalidName() {
		String[] args = {"source.lol", "-r", "-fichier.lol"};
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidDestinationException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
		
		args[1] = "-rename";
		
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidDestinationException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
	}
	
	@Test
	public void testOptionDestinationFileWithoutName() {
		String[] args = {"source.lol", "-r", "-fichier.lol"};
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidDestinationException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
		
		args[1] = "-rename";
		try {
			ArgumentHandler.initialise(args);
		} catch (InvalidDestinationException e) {
			// expected
			return;
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		fail("No exception thrown.");
	}
	
	@Test
	public void testOptionHelp() {
		String[] args = {"-h"};
		try {
			ArgumentHandler.initialise(args);
			assertTrue(ArgumentHandler.getInstance().getHelp());
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		
		args = new String[2];
		args[0] = "source.lol";
		args[1] = "-help";
		
		try {
			ArgumentHandler.initialise(args);
			assertTrue(ArgumentHandler.getInstance().getHelp());
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}
	
	@Test
	public void testOptionVerbose() {
		String[] args = {"source.lol", "-v"};
		try {
			ArgumentHandler.initialise(args);
			assertTrue(ArgumentHandler.getInstance().getVerbose());
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
		
		args[1] = "-verbose";
		
		try {
			ArgumentHandler.initialise(args);
			assertTrue(ArgumentHandler.getInstance().getVerbose());
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}

	@Test
	public void testOptionMultipleSourceFiles() {
		String[] args = {"source1.lol", "source2.lol", "source3.lol", "-v"};
		try {
			ArgumentHandler.initialise(args);
			assertTrue(ArgumentHandler.getInstance().getVerbose());
			assertEquals(ArgumentHandler.getInstance().getDestinationFile(),
					args[0] + ".tex");
			assertEquals(ArgumentHandler.getInstance().getSourceFiles().size(), 
					args.length - 1);
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}
	
	@Test
	public void testMultipleInitialisation() {
		String source1 = "source1.lol";
		String source2 = "source2.lol";
		String[] init1 = {source1, "-v"};
		String[] init2 = {source2, "-h"};
		try {
			ArgumentHandler.initialise(init1);
			ArgumentHandler.initialise(init2);
			assertTrue(ArgumentHandler.getInstance().getVerbose());
			assertFalse(ArgumentHandler.getInstance().getHelp());
			assertEquals(source1, ArgumentHandler.getInstance()
					.getSourceFiles().elementAt(0));
			assertEquals(ArgumentHandler.getInstance().getSourceFiles().size(),
					1);
		} catch (Exception e) {
			fail("Unexpected exception: " + e.toString());
		} finally {
			ArgumentHandler.reset();
		}
	}

}
