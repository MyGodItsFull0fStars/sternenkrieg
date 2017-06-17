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
    public void setUp() {
        shipLogic_no_parameters = new ShipLogic();
        smallArray = new int[1];
        middleArray = new int[2];
        bigArray = new int[3];
        shipLogic_with_parameters = new ShipLogic(smallArray, middleArray, bigArray);
    }

    @Test
    public void checkInitialisation() {
        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray().length, shipLogic_with_parameters.getSmallShipArray().length);
        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray().length, shipLogic_with_parameters.getMiddleShipArray().length);
        Assert.assertEquals(shipLogic_no_parameters.getBigShipArray().length, shipLogic_with_parameters.getBigShipArray().length);
    }

    @Test
    public void checkGetterSetterMethods() {
        shipLogic_no_parameters.setSmallShipArray(shipLogic_with_parameters.getSmallShipArray());
        shipLogic_no_parameters.setMiddleShipArray(shipLogic_with_parameters.getMiddleShipArray());
        shipLogic_no_parameters.setBigShipArray(shipLogic_with_parameters.getBigShipArray());

        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray(), shipLogic_with_parameters.getSmallShipArray());
        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray(), shipLogic_with_parameters.getMiddleShipArray());
        Assert.assertEquals(shipLogic_no_parameters.getBigShipArray(), shipLogic_with_parameters.getBigShipArray());
    }

    @Test
    public void insertIntoSmallShip(){
        int[] array = {1};
        shipLogic_no_parameters.setSmallShipArray(array);
        shipLogic_with_parameters.setSmallShipArray(array);

        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray()[0], 1);
        Assert.assertEquals(shipLogic_with_parameters.getSmallShipArray()[0], 1);
    }

    @Test
    public void insertIntoMiddleShipWithCorrectSize(){
        int[] array = {1 , 2};
        shipLogic_no_parameters.setMiddleShipArray(array);
        shipLogic_with_parameters.setMiddleShipArray(array);

        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray(), array);
        Assert.assertEquals(shipLogic_with_parameters.getMiddleShipArray(), array);

        for (int i = 0; i < array.length; i++ ){
            Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray()[i], array[i]);
            Assert.assertEquals(shipLogic_with_parameters.getMiddleShipArray()[i], array[i]);
        }
    }

    @Test
    public void shipConstuctorThrowsException(){
        String exceptionString = "java.lang.IllegalArgumentException: IllegalArgumentException at ShipLogic constructor";
        try {
            shipLogic_with_parameters = new ShipLogic(null, middleArray, bigArray);
        } catch (IllegalArgumentException e){
            Assert.assertEquals(e.toString(), exceptionString);
        }

        try {
            shipLogic_with_parameters = new ShipLogic(smallArray, null, bigArray);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        try{
            shipLogic_with_parameters = new ShipLogic(smallArray, middleArray, null);
        }catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        try{
            shipLogic_with_parameters = new ShipLogic(null, null, null);
        } catch (IllegalArgumentException e){
            Assert.assertEquals(e.toString(), exceptionString);
        }
    }


}
