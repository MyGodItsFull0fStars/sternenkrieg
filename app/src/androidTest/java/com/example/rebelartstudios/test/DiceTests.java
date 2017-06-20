package com.example.rebelartstudios.test;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.example.rebelartstudios.sternenkrieg.DiceClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Android tests for the dice class
 */

public class DiceTests {
    private DiceClass diceClass;
    private Context instrumentationCtx;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Before
    public void before() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        diceClass= new DiceClass(instrumentationCtx);
        pref = instrumentationCtx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    @Test
    public void roll() {
        boolean one = false;
        boolean two = false;
        boolean three = false;
        boolean four = false;
        boolean five = false;
        boolean six = false;
        boolean other = true;

        for (int i = 0; i <= 1000; i++) {
            int value= diceClass.roll();
            if(value==1)
                one=true;
            else if(value==2)
                two=true;
            else if(value==3)
                three=true;
            else if (value==4)
                four=true;
            else if (value==5)
                five=true;
            else if (value==6)
                six=true;
            else
                other=false;
        }
        Assert.assertTrue(one);
        Assert.assertTrue(two);
        Assert.assertTrue(three);
        Assert.assertTrue(four);
        Assert.assertTrue(five);
        Assert.assertTrue(six);
        Assert.assertTrue(other);
    }


    @Test
    public void whoisStarting(){
        Assert.assertEquals(0,diceClass.whoIsStarting(3,2));
        Assert.assertEquals(1,diceClass.whoIsStarting(2,3));
        Assert.assertEquals(2,diceClass.whoIsStarting(3,3));
    }
}
