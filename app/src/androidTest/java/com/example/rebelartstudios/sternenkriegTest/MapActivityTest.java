package com.example.rebelartstudios.sternenkriegTest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rebelartstudios.sternenkrieg.Map;
import com.example.rebelartstudios.sternenkrieg.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by christianbauer on 30/05/2017.
 */


@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    @Rule public final ActivityTestRule<Map> mapActivityTestRule = new ActivityTestRule<Map>(Map.class);

    @Test
    public void testTurnButtonClickable(){

        for (int i = 0; i < 4; i++) {
            onView(withId(R.id.image_turn)).perform(click());
        }
    }

    public void testGridViewClickable(){
        onView(withId(R.id.grid_item_image)).perform(click());
    }
}
