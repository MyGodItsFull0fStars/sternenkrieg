package com.example.rebelartstudios.sternenkrieg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by christianbauer on 17/05/2017.
 */

public class MapTest {

    Map map;
    String[] array;


    @Before
    public void setup(){
        map = new Map();
        array = new String[64];
    }


    @Test
    public void basicTest(){
        Assert.assertEquals(array, map.getPlayerField());
        Assert.assertEquals(array, map.playerField);

    }
}
