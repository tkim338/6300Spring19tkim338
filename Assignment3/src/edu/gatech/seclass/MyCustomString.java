package edu.gatech.seclass;
import java.util.*;

public class MyCustomString implements MyCustomStringInterface {
  
  String myString;
  
  public String getString() {
    return myString;
  }
  
  public void setString(String newString) {
    myString = newString;
  }
  
  public int countNumbers() {
    
    int numberCount = 0;
    boolean previousCharIsNum = false;
    char[] myStringCharArray = myString.toCharArray();
    int stringLength = myStringCharArray.length;
    char currentChar;
    
    for (int i = 0; i < stringLength; i++) {
      currentChar = myStringCharArray[i];
      if (Character.isDigit(currentChar)) {
        if (!previousCharIsNum) {
          numberCount++;
        }
        previousCharIsNum = true;
      }
      else {
        previousCharIsNum = false;
      }
    }
    
    return numberCount;
  }
  
  public String addDigits(int n, boolean reverse) {
    
    if (myString == null) {
      throw new NullPointerException();
    }
    
    if (myString != null && (n>9 || n<=0)) {
      throw new IllegalArgumentException();
    }
    
    boolean previousCharIsNum = false;
    char[] myStringCharArray = myString.toCharArray();
    int stringLength = myStringCharArray.length;
    ArrayList<Integer> newNumberArray = new ArrayList<Integer>();
    String newString = "";
    char currentChar;
    
    for (int i = 0; i < stringLength; i++) {
      currentChar = myStringCharArray[i];
      if (Character.isDigit(currentChar)) {
        currentChar = myStringCharArray[i];
        if (!previousCharIsNum) {
          newNumberArray = new ArrayList<Integer>();
          newNumberArray.add(Character.getNumericValue(currentChar));
          previousCharIsNum = true;
        }
        else {
          newNumberArray.add(Character.getNumericValue(currentChar));
        }
      }
      else {
        // process number here
        for (int j = 0; j < newNumberArray.size(); j++) {
          newNumberArray.set(j, (newNumberArray.get(j) + n) % 10);
        }
        if (!reverse) {
          for (int r = 0; r < newNumberArray.size(); r++) {
            newString = newString + String.valueOf(newNumberArray.get(r));
          }
        }
        else {
          for (int r = newNumberArray.size()-1; r > -1; r--) {
            newString = newString + String.valueOf(newNumberArray.get(r));
          }
        }
        newString = newString + Character.toString(currentChar);
        newNumberArray = new ArrayList<Integer>();
        previousCharIsNum = false;
      }
    }
    
    // process number here
    if (newNumberArray.size()>0) {
      for (int j = 0; j < newNumberArray.size(); j++) {
        newNumberArray.set(j, (newNumberArray.get(j) + n) % 10);
      }
      if (!reverse) {
        for (int r = 0; r < newNumberArray.size(); r++) {
          newString = newString + String.valueOf(newNumberArray.get(r));
        }
      }
      else {
        for (int r = newNumberArray.size()-1; r > -1; r--) {
          newString = newString + String.valueOf(newNumberArray.get(r));
        }
      }
    }
    
    return newString;
  }
  
  public void convertDigitsToNamesInSubstring(int startPosition, int endPosition) {
    
    if (myString == null) {
      throw new NullPointerException();
    }
    else if (startPosition < 1 || startPosition > endPosition) {
      throw new IllegalArgumentException();
    }
    else if (endPosition > myString.length()) {
      throw new MyIndexOutOfBoundsException();
    }
    
    boolean previousCharIsNum = false, endSeq = false;
    char[] myStringCharArray = myString.toCharArray();
    int stringLength = myStringCharArray.length;
   // ArrayList<Integer> newNumberArray = new ArrayList<Integer>();
    char currentChar;
    
    ArrayList<String> key = new ArrayList<String>();
    key.add("zero");
    key.add("one");
    key.add("two");
    key.add("three");
    key.add("four");
    key.add("five");
    key.add("six");
    key.add("seven");
    key.add("eight");
    key.add("nine");
    
    String beginString = myString.substring(0,startPosition-1);
    String endString = myString.substring(endPosition,myString.length());
    
    ArrayList<String> newStringList = new ArrayList<String>();
    String newString = "";
    
    for (int i = startPosition-1; i < endPosition; i++) {
      currentChar = myStringCharArray[i];
      if (Character.isDigit(currentChar)) {
        currentChar = myStringCharArray[i];
        if (!previousCharIsNum) {
          newStringList.add("*" + key.get(Character.getNumericValue(currentChar)));
          previousCharIsNum = true;
        }
        else {
          newStringList.add(key.get(Character.getNumericValue(currentChar)));
        }
        
        endSeq = i==endPosition-1 || (i<stringLength-1 && !Character.isDigit(myStringCharArray[i+1]));
        if (endSeq) {
          newStringList.add("*");
        }
      }
      else {
        newStringList.add(Character.toString(currentChar));
        previousCharIsNum = false;
      }
    }
    
    for (int t = 0; t<newStringList.size(); t++) {
      newString = newString + newStringList.get(t);
    }
    myString = beginString + newString + endString;
  }
}