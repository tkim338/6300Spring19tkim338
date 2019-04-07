package edu.gatech.seclass.filesummary;

import com.sun.deploy.util.ArrayUtil;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

/*
Empty Main class for Individual Project.
 */

	public static void main(String[] args) {
		// Empty Skeleton Method - Implement this.

		// show usage if no arguments are passed
		if (args.length == 0) usage();
		else {
			FileSum fs = new FileSum(args);
			fs.processFile();
			fs.printFile();
		}
	}

	private static void usage() {
		System.err.println("Usage: filesummary [-a [int]] [-r string | -k string] [-s string] <filename>");
	}

}

class FileSum {
	private ArrayList<String> argList;
	private ArrayList<String> outputFileList;
	private String filename;
	private File file;
	private boolean rkFlag;
	private String lineBreak;

	public void processFile() {
		// no arguments besides filename
		if (argList.size() == 0) processN();
		// apply each argument to entire list at a time
		else {
			int sIndex = argList.indexOf("-s");
			if (sIndex != -1) processS(sIndex);

			int aIndex = argList.indexOf("-a");
			if (aIndex != -1) processA(aIndex);

			int rIndex = argList.indexOf("-r");
			if (rIndex != -1) {
				processR(rIndex);
				rkFlag = true;
			}

			int kIndex = argList.indexOf("-k");
			if (kIndex != -1) {
				if (rkFlag) errorRK();
				else processK(kIndex);
			}

			int nIndex = argList.indexOf("-n");
			if (nIndex != -1) {
				processN(nIndex);
			}
		}
	}

	public FileSum(String[] args) {
		// initialize necessary lists
		argList = new ArrayList<>();
		outputFileList = new ArrayList<>();

		// add all args to argList
		for (String a : args) argList.add(a);

		// add input file to inputFileList
		filename = argList.get(argList.size() - 1);
		argList.remove(argList.size() - 1);
		file = new File(filename);
		try {
			Scanner fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				outputFileList.add(fileScanner.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rkFlag = false;
		identifyLineBreak();
	}

	private void identifyLineBreak() {
		try {
			String text = new String(Files.readAllBytes(Paths.get(filename)));
			if (text.contains("\r\n")) lineBreak = "\r\n";
			else if (text.contains("\r")) lineBreak = "\r";
			else lineBreak = "\n";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processN() {
		int count = 0;
		for (int i = 0; i < outputFileList.size(); i++) {
			String currentString = outputFileList.get(i);
			String[] splitString = currentString.split("\\s+");
			count += splitString.length;
		}
		System.out.println(count);
	}

	private void processS(int index) {
		if (argList.size() <= index+1) {
			errorParam("s");
			argList.remove(index);
		}
		else {
			String maxLine = "";
			String currentLine = "";
			String param = argList.get(index+1);
			for (int i = 0; i < outputFileList.size(); i++) {
				String currentString = outputFileList.get(i);
				for (int j = 0; j < currentString.length(); j++) {
					if (param.indexOf(currentString.charAt(j)) != -1) {
						if (j == 0 && currentLine.length() > 0) currentLine = currentLine + lineBreak;
						currentLine = currentLine + currentString.charAt(j);
					}
					else {
						if (currentLine.length() > maxLine.length()) maxLine = currentLine;
						currentLine = "";
					}
					if (currentLine.length() > maxLine.length()) maxLine = currentLine;
				}
			}
			System.out.println(maxLine);
			argList.remove(index);
			argList.remove(index);
		}
	}

	private void processA(int index) {
		boolean hasInt = false;
			int param;
			try {
				param = Integer.parseInt(argList.get(index + 1));
				hasInt = true;
			} catch (Exception e) {
				param = 0;
			}
			if (param < 0) errorPositiveA();
			else {
				Sorter comparator = new Sorter(param);
				Collections.sort(outputFileList, comparator);
				argList.remove(index);
				if (hasInt)	argList.remove(index);
			}
	}

	private void processR(int index) {
		if (argList.size() <= index+1) {
			errorParam("r");
			argList.remove(index);
		}
		else {
			String param = argList.get(index+1);
			int startIndex = 0;
			if (argList.size() > index + 2) {
				int intParam = stringToInt(argList.get(index+2));
				if (intParam > 0) startIndex = intParam;

			}
			for (int i = startIndex; i < outputFileList.size(); i++) {
				if (outputFileList.get(i).contains(param)) {
					outputFileList.remove(i);
					i--;
				}
			}
			argList.remove(index);
			argList.remove(index);
		}
	}

	private void processK(int index) {
		if (argList.size() <= index+1) {
			errorParam("k");
			argList.remove(index);
		}
		else {
			String param = argList.get(index+1);
			int startIndex = 0;
			if (argList.size() > index + 2) {
				int intParam = stringToInt(argList.get(index+2));
				if (intParam > 0) startIndex = intParam;
			}
			ArrayList<String> newOutputList = new ArrayList<>();
			for (int i = startIndex; i < outputFileList.size(); i++) {
				if (outputFileList.get(i).contains(param)) {
					newOutputList.add(outputFileList.get(i));
				}
			}
			outputFileList = newOutputList;
			argList.remove(index);
			argList.remove(index);
		}
	}

	private int stringToInt(String s) {
		// Method body sourced from: https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
		int output = -1;
		try {
			output = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
		}
		if (output < 0) errorRKInt();
		return output;
	}

	private void processN(int index) {
		String newLine = "";
		for (int i = 0; i < outputFileList.size(); i++) {
			newLine = outputFileList.get(i) + (i+1);
			outputFileList.set(i, newLine);
		}
		argList.remove(index);
	}

	public void printFile() {
		// write output file to string for file output
		String newFileString = "";
		if (outputFileList.size() > 0) newFileString = outputFileList.get(0);
		for (int i = 1; i < outputFileList.size(); i++) {
			String line = outputFileList.get(i);
			newFileString = newFileString + lineBreak + line;
		}
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(newFileString);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void errorPositiveA() {
		System.err.println("Error: -a must be positive.");
	}

	private void errorParam(String param) {
		System.err.println("Error: -" + param + " string parameter must be given.");
	}

	private void errorRK() {
		System.err.println("Error: -r and -k are mutually exclusive.");
	}

	private void errorRKInt() {
		System.err.println("Error: Integer parameter for -r and -k must be a positive integer.");
	}

	private void errorNonexsistent() {
		System.err.println("Error: file doesn't exist.");
	}
}

class Sorter implements Comparator<String> {
	int index;
	public Sorter(int a) {
		this.index = a;
	}
	@Override
	public int compare(String a, String b) {
		a = removeDigits(a);
		b = removeDigits(b);
		if (a.length() > index && b.length() > index) return a.substring(index).compareTo(b.substring(index));
		else return 0;
	}

	private String removeDigits(String s) {
		String newString = "";
		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetter(s.charAt(i))) newString = newString + s.charAt(i);
		}
		return newString;
	}
}