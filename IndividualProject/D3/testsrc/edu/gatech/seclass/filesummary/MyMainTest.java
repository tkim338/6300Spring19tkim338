package edu.gatech.seclass.filesummary;
//import edu.gatech.seclass.filesummary.MainTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class MyMainTest {

	private ByteArrayOutputStream outStream;
	private ByteArrayOutputStream errStream;
	private PrintStream outOrig;
	private PrintStream errOrig;
	private Charset charset = StandardCharsets.UTF_8;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		outStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outStream);
		errStream = new ByteArrayOutputStream();
		PrintStream err = new PrintStream(errStream);
		outOrig = System.out;
		errOrig = System.err;
		System.setOut(out);
		System.setErr(err);
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(outOrig);
		System.setErr(errOrig);
	}

	/*
	 *  TEST UTILITIES
	 */

	// Create File Utility
	private File createTmpFile() throws Exception {
		File tmpfile = temporaryFolder.newFile();
		tmpfile.deleteOnExit();
		return tmpfile;
	}

	// Write File Utility
	private File createInputFile(String input) throws Exception {
		File file =  createTmpFile();

		OutputStreamWriter fileWriter =
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

		fileWriter.write(input);

		fileWriter.close();
		return file;
	}


	//Read File Utility
	private String getFileContent(String filename) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/*
	 * TEST FILE CONTENT
	 */
	private static final String FILE1 = "1 dog" + System.lineSeparator() + "2 cat";
	private static final String FILE2 = "Log: 123 abc\nError: 123 xyz\nError: 567 abc\nLog: 567 abc";
 
/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/

	/*
	 * TEST FILE CONTENT
	 */
	private static final String TEST_FILE1 = "1 dog" + System.lineSeparator() + "2 cat";
	private static final String TEST_FILE2 = "Log: 123 abc\nError: 123 xyz\nError: 567 abc\nLog: 567 abc";

	// Purpose: tests case where argument "a" is negative
	// Frame #: 3
	// Failure Type: BUG: The code fails when a negative number is passed as the parameter for -a, maybe because it defaults to skipping at least the first character whenever a second parameter is included
	@Test
	public void filesummaryTest1() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "-1", inputFile.getPath()};
		Main.main(args);
		assertEquals("Error: -a must be positive.", errStream.toString().trim());
	}

	// Purpose: tests case where "-k" is given without a parameter
	// Frame #: 6
	// Failure Type: Test Failure
	@Test
	public void filesummaryTest2() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-k", inputFile.getPath()};
		Main.main(args);
		//assertEquals("Error: -k string parameter must be given.", errStream.toString().trim());
		assertEquals("Usage: filesummary [-a [int]] [-r string [int] | -k string [int]] [-s string] [-n] <filename>", errStream.toString().trim());
	}

	// Purpose: tests case where "-r" is given without a parameter
	// Frame #: 5
	// Failure Type: Test Failure
	@Test
	public void filesummaryTest3() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-r", inputFile.getPath()};
		Main.main(args);
		//assertEquals("Error: -r string parameter must be given.", errStream.toString().trim());
		assertEquals("Usage: filesummary [-a [int]] [-r string [int] | -k string [int]] [-s string] [-n] <filename>", errStream.toString().trim());
	}

	// Purpose: tests case where "-s" is given without a parameter
	// Frame #: 4
	// Failure Type: BUG: The code returns a string even when no integer parameter is given, possibly because it defaults to passing the last word in the input file.
	@Test
	public void filesummaryTest4() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-s", inputFile.getPath()};
		Main.main(args);
		assertEquals("Error: -s string parameter must be given.", errStream.toString().trim());
	}

	// Purpose: tests case where "-r" is used with the entire file as an argument
	// Frame #: 69
	@Test
	public void filesummaryTest5() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals(TEST_FILE1, output);
	}

	// Purpose: tests case where "-r" is given with an empty string argument
	// Frame #: 67
	// Failure Type: BUG: The code fails when an empty string is given as a parameter for -r, possibly because the code parses the input by continually trying to remove all instances of "", which never ends
	@Test
	public void filesummaryTest6() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("", output);
	}

	// Purpose: tests case where "-r" and "-s" are both used with the entire file as arguments
	// Frame #: 63
	@Test
	public void filesummaryTest7() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-r", TEST_FILE1, "-s", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", TEST_FILE1, output);
		assertEquals("Outputs differ!", TEST_FILE1, outStream.toString().trim());
	}

	// Purpose: tests case where "-s" is used with the entire file and "-r" is used with a string
	// Frame #: 62
	@Test
	public void filesummaryTest8() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-s", TEST_FILE1, "-r", "1", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat", output);
	}

	// Purpose: tests case where "-r" is used with the entire file and "-s" is empty
	// Frame #: 61
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest9() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-r", TEST_FILE1, "-s", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", TEST_FILE1, output);
		assertEquals("Outputs differ!", "", outStream.toString().trim());
	}

	// Purpose: tests case where "-a" is present and "-s" is used with the entire file as an argument
	// Frame #: 42
	@Test
	public void filesummaryTest10() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "-s", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat" + System.lineSeparator() + "1 dog", output);
	}

	// Purpose: tests case where "-a" is present and "-s" is used with an empty string as an argument
	// Frame #: 40
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest11() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "-s", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", "2 cat" + System.lineSeparator() + "1 dog", output);
		assertEquals("Outputs differ!", "", outStream.toString().trim());
	}

	// Purpose: tests case where "-a" is used with maxint as an argument and "-s" is used with the entire file as an argument
	// Frame #: 39
	// Failure Type: BUG: The code fails when integer is given as the parameter -a that is larger than the length of the input file
	@Test
	public void filesummaryTest12() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", TEST_FILE1, output);
		assertEquals("Outputs differ!", TEST_FILE1, outStream.toString().trim());
	}

	// Purpose: tests case where "-a" is used with 0 as an argument and "-s" is used with the entire file as an argument
	// Frame #: 36
	@Test
	public void filesummaryTest13() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat" + System.lineSeparator() + "1 dog", output);
	}

	// Purpose: tests case where "-a" is used with 0 as an argument and "-s" is used and empty string as an argument
	// Frame #: 34
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest14() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", "2 cat" + System.lineSeparator() + "1 dog", output);
		assertEquals("Outputs differ!", "", outStream.toString().trim());
	}

	// Purpose: tests case where "-a", "-s", and "-r" are used
	// Frame #: 11
	// Failure Type: Test Failure
	@Test
	public void filesummaryTest15() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "dog2cat", "-r", "d", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", "2 cat", output);
		//assertEquals("Outputs differ!", "dog\r\n2", outStream.toString().trim());
		assertEquals("Outputs differ!", "dog", outStream.toString().trim());
	}

	// Purpose: tests case where file doesn't exist
	// Frame #: 2
	// Failure Type: Corner Case
	@Test
	public void filesummaryTest16() throws Exception {
		String nonExistentFilePath = "non_existent_file";
		String args[] = {nonExistentFilePath};
		Main.main(args);
		String output = getFileContent(nonExistentFilePath);
		//assertEquals("Files differ!", "", output);
		assertEquals("Files differ!", null, output);
		//assertEquals("Outputs differ!", "0", outStream.toString().trim());
		assertEquals("Outputs differ!", "", outStream.toString().trim());
	}

	// Purpose: tests case where all params besides -k are used, and all are empty strings
	// Frame #: 7
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest17() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "", "-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("", output);
	}

	// Purpose: tests case where all params besides -k are used, and all are empty strings except for -r
	// Frame #: 8
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest18() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "", "-r", "1 dog", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat", output);
	}

	// Purpose: tests case where all params besides -k are used, and all are empty strings except -r equals file
	// Frame #: 9
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest19() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "", "-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat" + System.lineSeparator() + "1 dog", output);
	}

	// Purpose: tests case where all params besides -k are used, and all are empty strings except -s
	// Frame #: 10
	// Failure Type: BUG: The code fails when an empty string is given as a parameter for -r, possibly because the code parses the input by continually trying to remove all instances of "", which never ends
	@Test
	public void filesummaryTest20() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "dog", "-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Files differ!", "", output);
		assertEquals("Outputs differ!", "dog", outStream.toString().trim());
	}

	// Purpose: tests case where all params besides -k are used, and -s param is not empty and -r param equals file
	// Frame #: 12
	@Test
	public void filesummaryTest21() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", "dogcat", "-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat" + System.lineSeparator() + "1 dog", output);
		assertEquals("Outputs differ!", "dog", outStream.toString().trim());
	}

	// Purpose: tests case where all params besides -k are used, and -s param is equal to file and -r is empty
	// Frame #: 13
	// Failure Type: BUG: The code fails when an empty string is given as a parameter for -r, possibly because the code parses the input by continually trying to remove all instances of "", which never ends
	@Test
	public void filesummaryTest22() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", TEST_FILE1, "-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("", output);
	}

	// Purpose: tests case where all params besides -k are used, and -s param is equal to file and -r is not empty
	// Frame #: 14
	@Test
	public void filesummaryTest23() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", TEST_FILE1, "-r", "1", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat", output);
	}

	// Purpose: tests case where all params besides -k are used, and -s and -r params both equal file
	// Frame #: 15
	@Test
	public void filesummaryTest24() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", "0", "-s", TEST_FILE1, "-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat" + System.lineSeparator() + "1 dog", output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and -r and -s are empty
	// Frame #: 16
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest25() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "", "-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("", output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and -r is not empty and -s is empty
	// Frame #: 17
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest26() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "", "-r", "dog", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat", output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and -r is equal to file and -s is empty
	// Frame #: 18
	// Failure Type: BUG: The code fails when an empty string is given as the parameter for -s, possibly because it uses "\Q" and "\E" with an empty string in between, causing an unenclosed character error
	@Test
	public void filesummaryTest27() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "", "-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals(TEST_FILE1, output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and -r is empty and -s is not empty
	// Frame #: 19
	// Failure Type: BUG: The code fails when an empty string is given as a parameter for -r, possibly because the code parses the input by continually trying to remove all instances of "", which never ends
	@Test
	public void filesummaryTest28() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "dog", "-r", "", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("", output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and both -r and -s are not empty
	// Frame #: 20
	@Test
	public void filesummaryTest29() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "12", "-r", "dog", inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("2 cat", output);
	}

	// Purpose: tests case where all params besides -k are used, and -a param is maxint and -r is equal to file and -s is not empty
	// Frame #: 21
	// Failure Type: BUG: The code fails when integer is given as the parameter -a that is larger than the length of the input file
	@Test
	public void filesummaryTest30() throws Exception {
		File inputFile = createInputFile(TEST_FILE1);
		String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", "dogcat", "-r", TEST_FILE1, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals(TEST_FILE1, output);
	}

	// Purpose: To test functionality of -a alphabetizing by alphabetic characters only
	// Frame #: Extra example; Deliverable 2 test
	// Failure Type: BUG: The code fails when a negative number is passed as the parameter for -a, maybe because it defaults to skipping at least the first character whenever a second parameter is included
	@Test
	public void filesummaryTest31() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-a", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Error: 567 abc\nError: 123 xyz\nLog: 123 abc\nLog: 567 abc", output);
	}

	// Purpose: To test functionality of -r integer param
	// Frame #: Extra example; Deliverable 2 test
	// Failure Type: BUG: The code fails when a negative number is passed as the parameter for -a, maybe because it defaults to skipping at least the first character whenever a second parameter is included
	@Test
	public void filesummaryTest32() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-r", "Error", "2", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Log: 123 abc\nError: 123 xyz\nLog: 567 abc", output);
	}

	// Purpose: To test functionality of -k integer param
	// Frame #: Extra example; Deliverable 2 test
	// Failure Type: BUG: The code fails when a negative number is passed as the parameter for -a, maybe because it defaults to skipping at least the first character whenever a second parameter is included
	@Test
	public void filesummaryTest33() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-k", "Error", "2", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Error: 567 abc", output);
	}

	// Purpose: To test functionality of -k negative integer param error thrown
	// Frame #: Extra example; Deliverable 2 test
	// Failure Type: BUG: The code fails a negative number is passed as a parameter for -k, maybe because the code iterates through the file until the line number counter matches the input, which never happens
	@Test
	public void filesummaryTest34() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-k", "Error", "-2", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Error: Integer parameter for -r and -k must be a positive integer.", errStream.toString().trim());
		assertEquals("Error: 123 xyz\nError: 567 abc", output);
	}

	// Purpose: To test functionality of -n argument
	// Frame #: Extra example; Deliverable 2 test
	// Failure Type: BUG: The code fails when a negative number is passed as the parameter for -a, maybe because it defaults to skipping at least the first character whenever a second parameter is included
	@Test
	public void filesummaryTest35() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-n", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Log: 123 abc1\nError: 123 xyz2\nError: 567 abc3\nLog: 567 abc4", output);
	}
	
	// New Failure Type: BUG: The code fails when a new line character is given as the parameter for -r, since the code excludes new line characters when parsing the file.
	@Test
	public void filesummaryTest36() throws Exception {
		File inputFile = createInputFile(TEST_FILE2);
		String args[] = {"-r", "\n", TEST_FILE2, inputFile.getPath()};
		Main.main(args);
		String output = getFileContent(inputFile.getPath());
		assertEquals("Log: 123 abc1\nError: 123 xyz2\nError: 567 abc3\nLog: 567 abc4", output);
	}

}
