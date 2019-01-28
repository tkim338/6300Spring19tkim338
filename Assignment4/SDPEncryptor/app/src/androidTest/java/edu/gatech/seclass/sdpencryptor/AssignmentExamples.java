package edu.gatech.seclass.sdpencryptor;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


//@RunWith(AndroidJUnit4.class)
public class AssignmentExamples {

    @Rule
    public ActivityTestRule<SDPEncryptorActivity> tActivityRule = new ActivityTestRule<>(
            SDPEncryptorActivity.class);


    @Test
    public void Screenshot1() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Up with the White and Gold!"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("25"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("To vhsg sgd Vghsd zmc Fnkc!")));
    }

    @Test
    public void Screenshot2() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("123AbcCat123"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("3"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("123123AbcCat")));
    }

    @Test
    public void ScreenshotErrors1() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("35505!"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("5"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.messageText)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("Alphabetic Message Required")));
    }

    @Test
    public void ScreenshotErrors2() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Another Error Test"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("50"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.shiftNumber)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("Must Be Between 0 And 25")));
    }

    @Test
    public void ScreenshotErrors3() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Error Tests!"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.shiftNumber)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotErrors4() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Error Tests!"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("0"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("0"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.rotateNumber)).check(ViewAssertions.matches(ViewMatchers.hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotLabel() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Cat"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("1"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("1"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("uDb")));
    }

    @Test
    public void ExtraTest1() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Lorem ipsum dolor sit amet"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("5"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("7"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("ny frjyQtwjr nuxzr itqtw x")));
    }

    @Test
    public void ExtraTest2() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Cat 123 Dog"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("0"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("50"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("23 DogCat 1")));
    }

    @Test
    public void ExtraTest3() {
        onView(ViewMatchers.withId(R.id.messageText)).perform(ViewActions.clearText(), ViewActions.typeText("Cat"));
        onView(ViewMatchers.withId(R.id.shiftNumber)).perform(ViewActions.clearText(), ViewActions.typeText("0"));
        onView(ViewMatchers.withId(R.id.rotateNumber)).perform(ViewActions.clearText(), ViewActions.typeText("6"));
        onView(ViewMatchers.withId(R.id.encryptButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.resultText)).check(ViewAssertions.matches(ViewMatchers.withText("Cat")));
    }

}