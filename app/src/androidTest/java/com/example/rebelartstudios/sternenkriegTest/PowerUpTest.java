package com.example.rebelartstudios.sternenkriegTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rebelartstudios.sternenkrieg.PowerUp;
import com.example.rebelartstudios.sternenkrieg.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by christianbauer on 30/05/2017.
 *
 * Testing Class for the PowerUp activity class.
 */


@RunWith(AndroidJUnit4.class)
public class PowerUpTest {
    @Rule public final ActivityTestRule<PowerUp> powerUpActivityTestRule = new ActivityTestRule<PowerUp>(PowerUp.class);


    /**
     * Following tests check if the buttons are clickable
     */

    @Test
    public void testFirstPowerUpClickable(){
        onView(withId(R.id.buttonPU1)).perform(click());
    }

    @Test
    public void testSecondPowerUpClickable(){
        onView(withId(R.id.buttonPU2)).perform(click());
    }

    @Test
    public void testThirdPowerUpClickable(){
        onView(withId(R.id.buttonPU3)).perform(click());
    }

    @Test
    public void testForthPowerUpClickable(){
        onView(withId(R.id.buttonPU4)).perform(click());
    }

    @Test
    public void testCheatClickable(){
        onView(withId(R.id.buttonPUcheat)).perform(click());
    }

}
