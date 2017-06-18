package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.GameLogic.ShipLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by christianbauer on 17/06/2017.
 * <p>
 * ShipLogic UnitTest class
 */

public class ShipLogicTest {
    private ShipLogic shipLogic_no_parameters;
    private ShipLogic shipLogic_with_parameters;

    private int[] smallArray;
    private int[] middleArray;
    private int[] bigArray;

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
        shipLogic_no_parameters.setSmallShipArray(smallArray);
        shipLogic_no_parameters.setMiddleShipArray(middleArray);
        shipLogic_no_parameters.setBigShipArray(bigArray);

        int[] arraySmall = {5};
        int[] arrayMiddle = {5, 6};
        int[] arrayBig = {5, 6, 7};

        shipLogic_with_parameters.setSmallShipArray(arraySmall);
        shipLogic_with_parameters.setMiddleShipArray(arrayMiddle);
        shipLogic_with_parameters.setBigShipArray(arrayBig);

        Assert.assertEquals(smallArray, shipLogic_no_parameters.getSmallShipArray());
        Assert.assertEquals(middleArray, shipLogic_no_parameters.getMiddleShipArray());
        Assert.assertEquals(bigArray, shipLogic_no_parameters.getBigShipArray());

        Assert.assertEquals(arraySmall, shipLogic_with_parameters.getSmallShipArray());
        Assert.assertEquals(arrayMiddle, shipLogic_with_parameters.getMiddleShipArray());
        Assert.assertEquals(arrayBig, shipLogic_with_parameters.getBigShipArray());
    }

    @Test
    public void insertIntoSmallShipCorrectSize() {
        int[] array = {1};
        shipLogic_no_parameters.setSmallShipArray(array);
        shipLogic_with_parameters.setSmallShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray(), shipLogic_with_parameters.getSmallShipArray());
        Assert.assertEquals(shipLogic_no_parameters.getSmallShipArray()[0], 1);
        Assert.assertEquals(shipLogic_with_parameters.getSmallShipArray()[0], 1);
    }

    @Test
    public void insertIntoMiddleShipWithCorrectSize() {
        int[] array = {1, 2};
        shipLogic_no_parameters.setMiddleShipArray(array);
        shipLogic_with_parameters.setMiddleShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray(), shipLogic_with_parameters.getMiddleShipArray());
        Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray(), array);
        Assert.assertEquals(shipLogic_with_parameters.getMiddleShipArray(), array);

        // test for each position as well
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(shipLogic_no_parameters.getMiddleShipArray()[i], array[i]);
            Assert.assertEquals(shipLogic_with_parameters.getMiddleShipArray()[i], array[i]);
        }
    }

    @Test
    public void insertIntoBigShipWithCorrectSize() {
        int[] array = {1, 2, 3};
        shipLogic_with_parameters.setBigShipArray(array);
        shipLogic_no_parameters.setBigShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogic_no_parameters.getBigShipArray(), shipLogic_with_parameters.getBigShipArray());
        Assert.assertEquals(shipLogic_no_parameters.getBigShipArray(), array);
        Assert.assertEquals(shipLogic_with_parameters.getBigShipArray(), array);

        // test for each position as well
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(shipLogic_no_parameters.getBigShipArray()[i], array[i]);
            Assert.assertEquals(shipLogic_with_parameters.getBigShipArray()[i], array[i]);
        }
    }

    @Test
    public void shipLogicConstructorThrowsException() {
        String exceptionString = "java.lang.IllegalArgumentException: IllegalArgumentException at ShipLogic constructor";

        // input parameters all null
        try {
            shipLogic_with_parameters = new ShipLogic(null, null, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // first input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(null, middleArray, bigArray);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // second input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(smallArray, null, bigArray);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // third input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(smallArray, middleArray, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }
    }

    @Test
    public void checkGetterSetterSmallShipIsSetOnField() {

        // If setter never used, false is expected
        Assert.assertEquals(false, shipLogic_no_parameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isSmallShipIsSetOnField());

        // Basic setter test
        shipLogic_no_parameters.setSmallShipIsSetOnField(true);
        shipLogic_with_parameters.setSmallShipIsSetOnField(true);
        Assert.assertEquals(true, shipLogic_no_parameters.isSmallShipIsSetOnField());
        Assert.assertEquals(true, shipLogic_with_parameters.isSmallShipIsSetOnField());

        shipLogic_no_parameters.setSmallShipIsSetOnField(false);
        shipLogic_with_parameters.setSmallShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogic_no_parameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isSmallShipIsSetOnField());

    }

    @Test
    public void checkGetterSetterMiddleShipIsSetOnField() {

        // If setter never used, false is expected
        Assert.assertEquals(false, shipLogic_no_parameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isMiddleShipIsSetOnField());

        // Basic getter setter tests
        shipLogic_no_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(true);
        Assert.assertEquals(true, shipLogic_no_parameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(true, shipLogic_with_parameters.isMiddleShipIsSetOnField());

        shipLogic_no_parameters.setMiddleShipIsSetOnField(false);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogic_no_parameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isMiddleShipIsSetOnField());

    }

    @Test
    public void checkAllShipsSetOnFieldMethod() {
        // allShipsSetOnPlayerField returns true if all ships are set on field.
        // Expected to return false at start
        Assert.assertEquals(false, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogic_with_parameters.allShipsSetOnPlayerField());

        // small ship on field, still expected to return false
        shipLogic_no_parameters.setSmallShipIsSetOnField(true);
        shipLogic_with_parameters.setSmallShipIsSetOnField(true);

        Assert.assertEquals(false, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogic_with_parameters.allShipsSetOnPlayerField());

        // middle ship set as well, still expected to return false
        shipLogic_no_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(true);

        Assert.assertEquals(false, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogic_with_parameters.allShipsSetOnPlayerField());

        // big ship set as well, expected to return true
        shipLogic_no_parameters.setBigShipIsSetOnField(true);
        shipLogic_with_parameters.setBigShipIsSetOnField(true);

        Assert.assertEquals(true, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(true, shipLogic_with_parameters.allShipsSetOnPlayerField());

        // If one value will be set to false again, method should return false
        shipLogic_no_parameters.setMiddleShipIsSetOnField(false);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogic_with_parameters.allShipsSetOnPlayerField());

        // test as well with small ship
        shipLogic_no_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_no_parameters.setSmallShipIsSetOnField(false);
        shipLogic_with_parameters.setSmallShipIsSetOnField(false);

        Assert.assertEquals(false, shipLogic_no_parameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogic_with_parameters.allShipsSetOnPlayerField());
    }

    @Test
    public void checkShipsOnFieldInitializeMethod() {

        // set all onField booleans to true to check the method
        shipLogic_no_parameters.setSmallShipIsSetOnField(true);
        shipLogic_no_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_no_parameters.setBigShipIsSetOnField(true);

        shipLogic_with_parameters.setSmallShipIsSetOnField(true);
        shipLogic_with_parameters.setMiddleShipIsSetOnField(true);
        shipLogic_with_parameters.setBigShipIsSetOnField(true);

        // shipsOnFieldInitialize() should set all boolean values for isSetOnField to false
        shipLogic_no_parameters.shipsOnFieldInitialize();
        shipLogic_with_parameters.shipsOnFieldInitialize();

        Assert.assertEquals(false, shipLogic_no_parameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_no_parameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_no_parameters.isBigShipIsSetOnField());

        Assert.assertEquals(false, shipLogic_with_parameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogic_with_parameters.isBigShipIsSetOnField());

    }

}
