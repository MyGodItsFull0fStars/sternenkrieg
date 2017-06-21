package com.example.rebelartstudios.test;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rebelartstudios.sternenkrieg.Main;
import com.example.rebelartstudios.sternenkrieg.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 *
 * Main test class
 * Starting with testing the buttons, and later will add the test for
 * the Players name
 *
 * LargeTest notifies Android that this test will take longer than 2 seconds
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainTest {
    @Rule
    public final ActivityTestRule<Main> main  = new ActivityTestRule<>(Main.class);

    /**
     * Test to check if the button to start the game map activity is clickable
     */
    @Test
    public void testStartButton(){
        onView(withId(R.id.start)).perform(click());
    }


    /**
     * Test to check if the button to start options activity class is clickable
     */
    @Test
    public void testOptionsButton(){
        onView(withId(R.id.options)).perform(click());
    }


}
