package edu.gatech.seclass;

public class Debugger {
  public static void main(String[] args) {
    MyCustomString mcs = new MyCustomString();
    mcs.setString("hello 90, bye 2");
    System.out.println(mcs.getString());
    System.out.println(mcs.countNumbers());
    System.out.println(mcs.addDigits(4,false));
    System.out.println(mcs.addDigits(4,true));
    mcs.convertDigitsToNamesInSubstring(0,14);
    System.out.println(mcs.getString());
    
    
       /* - For n=2 and reverse=false, "hello 90, bye 2" would be converted to "hello 12, bye 4".
   * - For n=4 and reverse=false, "1234567890" would be converted to "5678901234".
   * - For n=2 and reverse=true, "hello 90, bye 2" would be converted to "hello 21, bye 4".
   * - For n=4 and reverse=true, "1234567890" would be converted to "4321098765". */
  }
}