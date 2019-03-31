package edu.gatech.seclass.filesummary;
import edu.gatech.seclass.filesummary.MainTest;

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
 @Test
 public void filesummaryTest1() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "-1", inputFile.getPath()};
  Main.main(args);
  assertEquals("Error: a must be positive.", errStream.toString().trim());
 }

 // Purpose: tests case where "-k" is given without a parameter
 // Frame #: 6
 @Test
 public void filesummaryTest2() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-k", inputFile.getPath()};
  Main.main(args);
  assertEquals("Error: k string parameter must be given.", errStream.toString().trim());
 }

 // Purpose: tests case where "-r" is given without a parameter
 // Frame #: 5
 @Test
 public void filesummaryTest3() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-r", inputFile.getPath()};
  Main.main(args);
  assertEquals("Error: r string parameter must be given.", errStream.toString().trim());
 }

 // Purpose: tests case where "-s" is given without a parameter
 // Frame #: 4
 @Test
 public void filesummaryTest4() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-s", inputFile.getPath()};
  Main.main(args);
  assertEquals("Error: s string parameter must be given.", errStream.toString().trim());
 }

 // Purpose: tests case where "-r" is used with the entire file as an argument
 // Frame #: 69
 @Test
 public void filesummaryTest5() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-r", TEST_FILE1, inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
 }

 // Purpose: tests case where "-r" is given with an empty string argument
 // Frame #: 67
 @Test
 public void filesummaryTest6() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-r", "", inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals(TEST_FILE1, output);
 }

 // Purpose: tests case where "-r" and "-s" are both used with the entire file as arguments
 // Frame #: 63
 @Test
 public void filesummaryTest7() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-r", TEST_FILE1, "-s", TEST_FILE1, inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
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
 @Test
 public void filesummaryTest9() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-r", TEST_FILE1, "-s", "", inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
 }

 // Purpose: tests case where "-a" is present and "-s" is used with the entire file as an argument
 // Frame #: 42
 @Test
 public void filesummaryTest10() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "-s", TEST_FILE1, inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals(TEST_FILE1, output);
 }

 // Purpose: tests case where "-a" is present and "-s" is used with an empty string as an argument
 // Frame #: 40
 @Test
 public void filesummaryTest11() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "-s", "", inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
 }

 // Purpose: tests case where "-a" is used with maxint as an argument and "-s" is used with the entire file as an argument
 // Frame #: 39
 @Test
 public void filesummaryTest12() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", Integer.toString(Integer.MAX_VALUE), "-s", TEST_FILE1, inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
 }

 // Purpose: tests case where "-a" is used with 0 as an argument and "-s" is used with the entire file as an argument
 // Frame #: 36
 @Test
 public void filesummaryTest13() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "0", "-s", TEST_FILE1, inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals(TEST_FILE1, output);
 }

 // Purpose: tests case where "-a" is used with 0 as an argument and "-s" is used and empty string as an argument
 // Frame #: 34
 @Test
 public void filesummaryTest14() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "0", "-s", "", inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("", output);
 }

 // Purpose: tests case where "-a", "-s", and "-r" are used
 // Frame #: 11
 @Test
 public void filesummaryTest15() throws Exception {
  File inputFile = createInputFile(TEST_FILE1);
  String args[] = {"-a", "0", "-s", "dog2cat", "-r", "d", inputFile.getPath()};
  Main.main(args);
  String output = getFileContent(inputFile.getPath());
  assertEquals("2 cat\nog", output);
 }

}
