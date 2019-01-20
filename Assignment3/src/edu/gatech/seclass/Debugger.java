package edu.gatech.seclass;

public class Debugger {
  public static void main(String[] args) {
    MyCustomString mcs = new MyCustomString();
    mcs.setString("");
    System.out.println(mcs.getString());
    mcs.convertDigitsToNamesInSubstring(1, 1);
    System.out.println("Test string *onenine* long");
    
    //mycustomstring.setString("Test string 19 long");
   // mycustomstring.convertDigitsToNamesInSubstring(0, 19);
    //assertEquals("Test string *onenine* long", mycustomstring.getString());

    
    
       /* - For n=2 and reverse=false, "hello 90, bye 2" would be converted to "hello 12, bye 4".
   * - For n=4 and reverse=false, "1234567890" would be converted to "5678901234".
   * - For n=2 and reverse=true, "hello 90, bye 2" would be converted to "hello 21, bye 4".
   * - For n=4 and reverse=true, "1234567890" would be converted to "4321098765". */
  }
}