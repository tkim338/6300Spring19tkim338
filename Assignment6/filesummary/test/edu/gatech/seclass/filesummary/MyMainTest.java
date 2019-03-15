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
 
/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/

 /*
 * TEST FILE CONTENT
 */
 private static final String TEST_FILE1 = "1 dog" + System.lineSeparator() + "2 cat";
 private static final String TEST_FILE2 = "Log: 123 abc\nError: 123 xyz\nError: 567 abc\nLog: 567 abc";

 // Purpose: tests case where "a" is negative
 // Frame #: 3
/* @Test
 public void filesummaryTest1() throws Exception {
  File inputFile1 = createInputFile(TEST_FILE1);
  String args[] = {"-a", "-1", inputFile1.getPath()};
  Main.main(args);
  //assertEquals
 }*/

}
