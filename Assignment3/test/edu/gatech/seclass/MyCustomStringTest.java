package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {
  
  private MyCustomStringInterface mycustomstring;
  
  @Before
  public void setUp() {
    mycustomstring = new MyCustomString();
  }
  
  @After
  public void tearDown() {
    mycustomstring = null;
  }
  
  //Test Purpose: This is the first instructor example test.
  @Test
  public void testCountNumbers1() {
    mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
    assertEquals(7, mycustomstring.countNumbers());
  }
  
  //Test Purpose: check that 0 is return for a string with no numbers
  @Test
  public void testCountNumbers2() {
    mycustomstring.setString("Test string here with no numbers.");
    assertEquals(0, mycustomstring.countNumbers());
  }
  
  //Test Purpose: check that the correct number is return when the string is one, multi-digit number
  @Test
  public void testCountNumbers3() {
    mycustomstring.setString("2345236234562346");
    assertEquals(1, mycustomstring.countNumbers());
  }
  
  //Test Purpose: check that numbers at the beginning of the string are counted
  @Test
  public void testCountNumbers4() {
    mycustomstring.setString("2341 Test string here with leading digits.");
    assertEquals(1, mycustomstring.countNumbers());
  }
  
  //Test Purpose: check that numbers at the end of the string are counted.
  @Test
  public void testCountNumbers5() {
    mycustomstring.setString("Test string here with trailing digits. 2345");
    assertEquals(1, mycustomstring.countNumbers());
  }
  
  //Test Purpose: check that an empty string returns a number count of 0
  @Test
  public void testCountNumbers6() {
    mycustomstring.setString("");
    assertEquals(0, mycustomstring.countNumbers());
  }
  
  //Test Purpose: This is the second instructor example test.
  @Test
  public void testAddDigits1() {
    mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
    assertEquals("5678!!! H7y, L7t'9 put 94me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, false));
  }
  
  //Test Purpose: This the third instructor example test.
  @Test
  public void testAddDigits2() {
    mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
    assertEquals("8765!!! H7y, L7t'9 put 49me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, true));
  }
  
  //Test Purpose: check to see if an n<=0 will throw an IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testAddDigits3() {
    mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
    mycustomstring.addDigits(-2, false);
  }
  
  //Test Purpose: check to see if an n>9 will throw an IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testAddDigits4() {
    mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
    mycustomstring.addDigits(45, false);
  }
  
  //Test Purpose: check to see that a NullPointerException is thrown with a null string argument
  @Test(expected = NullPointerException.class)
  public void testAddDigits5() {
    mycustomstring.setString(null);
    mycustomstring.addDigits(5, true);
  }
  
  //Test Purpose: check to see that a NullPointerException is thrown with a null string argument and an n>9
  @Test(expected = NullPointerException.class)
  public void testAddDigits6() {
    mycustomstring.setString(null);
    mycustomstring.addDigits(565, false);
  }
  
  //Test Purpose: check to see that a NullPointerException is thrown with a null string argument and an n<=0
  @Test(expected = NullPointerException.class)
  public void testAddDigits7() {
    mycustomstring.setString(null);
    mycustomstring.addDigits(-5, true);
  }
  
  //Test Purpose: check to see that a string that consists of only digits is returned properly with reverse==false
  @Test
  public void testAddDigits8() {
    mycustomstring.setString("2341");
    assertEquals("5876", mycustomstring.addDigits(4, true));
  }
  
  //Test Purpose: check to see that a string that consists of only digits is returned properly with reverse==true
  @Test
  public void testAddDigits9() {
    mycustomstring.setString("2341");
    assertEquals("5876", mycustomstring.addDigits(4, true));
  }
  
  //Test Purpose: check to see that an empty string is returned as-is
  @Test
  public void testAddDigits10() {
    mycustomstring.setString("");
    assertEquals("", mycustomstring.addDigits(4, true));
  }
  
  //Test Purpose: check to see that non-alphanumeric characters are unaffected
  @Test
  public void testAddDigits11() {
    mycustomstring.setString("asdf25df@3f$%");
    assertEquals("asdf96df@7f$%", mycustomstring.addDigits(4, true));
  }
  
  //Test Purpose: check to see that digit wrapping from 9 to 0 is working properly 
  @Test
  public void testAddDigits12() {
    mycustomstring.setString("1234567890");
    assertEquals("4567890123", mycustomstring.addDigits(3, false));
  }
  
  
  //Test Purpose: This is the fourth instructor example test.
  @Test
  public void testConvertDigitsToNamesInSubstring1() {
    mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
    mycustomstring.convertDigitsToNamesInSubstring(17, 23);
    assertEquals("I'd b3tt3r put s*zero*me d*onesix*1ts in this 5tr1n6, right?", mycustomstring.getString());
  }
  
  //Test Purpose: This is the fifth instructor example test, demonstrating a test for an exception.  Your exceptions should be tested in this format.
  @Test(expected = NullPointerException.class)
  public void testConvertDigitsToNamesInSubstring2() {
    mycustomstring.convertDigitsToNamesInSubstring(2, 10);
  }
  
  //Test Purpose: check to see that an IllegalArgumentException is thrown if startPosition < 1
  @Test(expected = IllegalArgumentException.class)
  public void testConvertDigitsToNamesInSubstring3() {
    mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
    mycustomstring.convertDigitsToNamesInSubstring(-4, 23);
  }
  
  //Test Purpose: check to see that an IllegalArgumentException is thrown if startPosition > endPosition
  @Test(expected = IllegalArgumentException.class)
  public void testConvertDigitsToNamesInSubstring4() {
    mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
    mycustomstring.convertDigitsToNamesInSubstring(7, 3);
  }
  
  //Test Purpose: check to see that an MyIndexOutOfBoundsException is thrown if endPosition > length of string
  @Test(expected = MyIndexOutOfBoundsException.class)
  public void testConvertDigitsToNamesInSubstring5() {
    mycustomstring.setString("Test string 19 long");
    mycustomstring.convertDigitsToNamesInSubstring(7, 21);
  }
  
  //Test Purpose: check that specifying a substring containing the whole string works
  @Test
  public void testConvertDigitsToNamesInSubstring6() {
    mycustomstring.setString("Test string 19 long");
    mycustomstring.convertDigitsToNamesInSubstring(1, 19);
    assertEquals("Test string *onenine* long", mycustomstring.getString());
  }
  
  //Test Purpose: check that a string with no digits is unaffected
  @Test
  public void testConvertDigitsToNamesInSubstring7() {
    mycustomstring.setString("Test string without numbers");
    mycustomstring.convertDigitsToNamesInSubstring(1, 19);
    assertEquals("Test string without numbers", mycustomstring.getString());
  }
  
  //Test Purpose: check that a string that is completely made up of digits works properly
  @Test
  public void testConvertDigitsToNamesInSubstring8() {
    mycustomstring.setString("2345623463");
    mycustomstring.convertDigitsToNamesInSubstring(4, 7);
    assertEquals("234*fivesixtwothree*463", mycustomstring.getString());
  }
  
  //Test Purpose: check that a substring specified that contains no digits is unaffected
  @Test
  public void testConvertDigitsToNamesInSubstring9() {
    mycustomstring.setString("123 test string here 23");
    mycustomstring.convertDigitsToNamesInSubstring(4, 7);
    assertEquals("123 test string here 23", mycustomstring.getString());
  }
  
  //Test Purpose: check that an empty string throws a MyIndexOutOfBoundsException
  @Test(expected = MyIndexOutOfBoundsException.class)
  public void testConvertDigitsToNamesInSubstring10() {
    mycustomstring.setString("");
    mycustomstring.convertDigitsToNamesInSubstring(1,1);
  }
  
}
