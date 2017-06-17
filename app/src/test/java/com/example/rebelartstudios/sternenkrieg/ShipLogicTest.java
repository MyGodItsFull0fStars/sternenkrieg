package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.GameLogic.ShipLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by christianbauer on 17/06/2017.
 */

public class ShipLogicTest {
    ShipLogic shipLogic_no_parameters;
    ShipLogic shipLogic_with_parameters;

    int[] smallArray;
    int[] middleArray;
    int[] bigArray;

    @Before
    public void setUp(){
        shipLogic_no_parameters = new ShipLogic();
        smallArray = new int[1];
        middleArray = new int[2];
        bigArray = new int[3];
        shipLogic_with_parameters = new ShipLogic(smallArray, middleArray, bigArray);
    }

    @Test
    public void checkInitialisation(){
        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray().length, shipLogic_with_parameters.getSmallShipArray().length);
        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray().length, shipLogic_with_parameters.getMiddleShipArray().length);
        Assert.assertEquals(shipLogic_no_parameters.getBigShipArray().length, shipLogic_with_parameters.getBigShipArray().length);
    }
}
