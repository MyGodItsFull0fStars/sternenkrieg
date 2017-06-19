package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldValues;
import com.example.rebelartstudios.sternenkrieg.gamelogic.ShipLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * ShipLogic UnitTest class
 */

public class ShipLogicTests {
    private ShipLogic shipLogic_no_parameters;
    private ShipLogic shipLogic_with_parameters;
    private PlayerFieldValues fieldValues;

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
        fieldValues = new PlayerFieldValues();
    }

    @Test
    public void checkInitialisation() {
        try {
            Assert.assertEquals(smallArray.length, shipLogic_no_parameters.getSmallShipArray().length, shipLogic_with_parameters.getSmallShipArray().length);
            Assert.assertEquals(middleArray.length, shipLogic_no_parameters.getMiddleShipArray().length, shipLogic_with_parameters.getMiddleShipArray().length);
            Assert.assertEquals(bigArray.length, shipLogic_no_parameters.getBigShipArray().length, shipLogic_with_parameters.getBigShipArray().length);
        } catch (Exception e) {
            fail("Exception should not be reached");
        }
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


    /******************************************************** Boundary Tests Start********************************************************************/

    @Test
    public void checkSetSmallShipPositionInBoundariesThrowsNoException() {
        try {
            for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
                shipLogic_no_parameters.setSmallShipPosition(position);
                shipLogic_with_parameters.setSmallShipPosition(position);
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached" + e.toString());
        }
    }

    @Test
    public void checkSetSmallShipPositionOutOfBoundariesThrowsException() {
        try {
            shipLogic_no_parameters.setSmallShipPosition(-1);
            fail("Should not be reached");
            shipLogic_with_parameters.setSmallShipPosition(-1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Parameter position in setSmallShipPosition out of range");
        }

        try {
            shipLogic_no_parameters.setSmallShipPosition(64);
            fail("Should not be reached, IllegalArgumentException expected");

        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Parameter position in setSmallShipPosition out of range");
        }
    }

    @Test
    public void checkMiddleShipPositionInBoundariesThrowsNoException() {
        try {
            for (int position = 1; position < fieldValues.FIELDSIZE; position++) {
                shipLogic_no_parameters.setMiddleShipPosition(position, fieldValues.HORIZONTAL);
                shipLogic_with_parameters.setMiddleShipPosition(position, fieldValues.HORIZONTAL);
            }

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached: " + e.toString());
        }

        try {
            for (int position = 8; position < fieldValues.FIELDSIZE; position++) {
                shipLogic_no_parameters.setMiddleShipPosition(position, fieldValues.VERTICAL);
                shipLogic_with_parameters.setMiddleShipPosition(position, fieldValues.VERTICAL);
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached: " + e.toString());
        }
    }

    @Test
    public void checkMiddleShipPositionOutOfBoundariesThrowsException() {

        // Out of boundaries on the left field side
        try {
            shipLogic_no_parameters.setMiddleShipPosition(0, fieldValues.HORIZONTAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Out of boundaries on the right field side
        try {
            shipLogic_no_parameters.setMiddleShipPosition(64, fieldValues.HORIZONTAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Out of boundaries on the top field side
        try {
            shipLogic_no_parameters.setMiddleShipPosition(7, fieldValues.VERTICAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Out of boundaries on the bottom field side
        try {
            shipLogic_no_parameters.setMiddleShipPosition(64, fieldValues.VERTICAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }
    }


    @Test
    public void checkBigShipPositionInBoundariesThrowsNoException() {
        // Check horizontal boundaries
        try {
            for (int position = 1; position < fieldValues.FIELDSIZE - 1; position++) {
                shipLogic_no_parameters.setBigShipPosition(1, fieldValues.HORIZONTAL);
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached: " + e.toString());
        }

        // Check vertical boundaries
        try {
            for (int position = 8; position < fieldValues.FIELDSIZE - 8; position++) {
                shipLogic_no_parameters.setBigShipPosition(position, fieldValues.VERTICAL);
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached: " + e.toString());
        }
    }

    @Test
    public void checkBigShipPositionOutOfBoundariesThrowsIllegalArgumentException() {
        // Horizontal boundaries left upper field side
        try {
            shipLogic_no_parameters.setBigShipPosition(0, fieldValues.HORIZONTAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Horizontal boundaries right bottom field side
        try {
            shipLogic_no_parameters.setBigShipPosition(63, fieldValues.HORIZONTAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Vertical boundaries upper field side
        try {
            shipLogic_no_parameters.setBigShipPosition(5, fieldValues.VERTICAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }

        // Vertical boundaries bottom field side
        try {
            shipLogic_no_parameters.setBigShipPosition(61, fieldValues.VERTICAL);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), "java.lang.IllegalArgumentException: Position in parameter is out of the field boundaries");
        }
    }


    /******************************************************** Boundary Tests End ********************************************************************/

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
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // first input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(null, middleArray, bigArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // second input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(smallArray, null, bigArray);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), exceptionString);
        }

        // third input parameter is null
        try {
            shipLogic_with_parameters = new ShipLogic(smallArray, middleArray, null);
            fail("IllegalArgumentException expected");
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
