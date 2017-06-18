package com.example.rebelartstudios.sternenkrieg;


import com.example.rebelartstudios.sternenkrieg.DiceClass;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by christianbauer on 24/05/2017.
 */

public class DiceTest {
    DiceClass diceClass = new DiceClass();


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
        assertTrue(one);
        assertTrue(two);
        assertTrue(three);
        assertTrue(four);
        assertTrue(five);
        assertTrue(six);
        assertTrue(other);
    }


    @Test
    public void whoisStarting(){
        assertEquals(0,diceClass.whoIsStarting(3,2));
        assertEquals(1,diceClass.whoIsStarting(2,3));
        assertEquals(2,diceClass.whoIsStarting(3,3));
    }
}
