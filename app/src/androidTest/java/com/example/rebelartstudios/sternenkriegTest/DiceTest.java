package com.example.rebelartstudios.sternenkriegTest;

import com.example.rebelartstudios.sternenkrieg.Dice;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by christianbauer on 24/05/2017.
 */

public class DiceTest {
    Dice wuerfel;


    @Before
    public void setUp(){
     wuerfel = new Dice();
    }


    @Test
    public void basicTest(){
       // wuerfel.changeDiceImage(1);
    }
}
