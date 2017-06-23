package com.example.rebelartstudios.sternenkrieg;

import android.util.Log;

import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
import com.example.rebelartstudios.sternenkrieg.gamelogic.ShipLogic;
import com.example.rebelartstudios.sternenkrieg.exception.ErrorMessages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * ShipLogic UnitTest class
 */

public class ShipLogicTests {
    private ShipLogic shipLogicNoParameters;
    private ShipLogic shipLogicWithParameters;
    private FieldValues fieldValues;

    private int[] smallArray;
    private int[] middleArray;
    private int[] bigArray;

    String tag = "ShipLogicTests";

    @Before
    public void setUp() {
        shipLogicNoParameters = new ShipLogic();
        smallArray = new int[1];
        middleArray = new int[2];
        bigArray = new int[3];
        shipLogicWithParameters = new ShipLogic(smallArray, middleArray, bigArray);
        fieldValues = new FieldValues();
    }

    @Test
    public void checkInitialisation() {
        try {
            Assert.assertEquals(smallArray.length, shipLogicNoParameters.getSmallShipArray().length, shipLogicWithParameters.getSmallShipArray().length);
            Assert.assertEquals(middleArray.length, shipLogicNoParameters.getMiddleShipArray().length, shipLogicWithParameters.getMiddleShipArray().length);
            Assert.assertEquals(bigArray.length, shipLogicNoParameters.getBigShipArray().length, shipLogicWithParameters.getBigShipArray().length);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkGetterSetterMethods() {
        shipLogicNoParameters.setSmallShipArray(smallArray);
        shipLogicNoParameters.setMiddleShipArray(middleArray);
        shipLogicNoParameters.setBigShipArray(bigArray);

        int[] arraySmall = {5};
        int[] arrayMiddle = {5, 6};
        int[] arrayBig = {5, 6, 7};

        shipLogicWithParameters.setSmallShipArray(arraySmall);
        shipLogicWithParameters.setMiddleShipArray(arrayMiddle);
        shipLogicWithParameters.setBigShipArray(arrayBig);

        Assert.assertEquals(smallArray, shipLogicNoParameters.getSmallShipArray());
        Assert.assertEquals(middleArray, shipLogicNoParameters.getMiddleShipArray());
        Assert.assertEquals(bigArray, shipLogicNoParameters.getBigShipArray());

        Assert.assertEquals(arraySmall, shipLogicWithParameters.getSmallShipArray());
        Assert.assertEquals(arrayMiddle, shipLogicWithParameters.getMiddleShipArray());
        Assert.assertEquals(arrayBig, shipLogicWithParameters.getBigShipArray());
    }

    @Test
    public void insertIntoSmallShipCorrectSize() {
        int[] array = {1};
        shipLogicNoParameters.setSmallShipArray(array);
        shipLogicWithParameters.setSmallShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogicNoParameters.getSmallShipArray(), shipLogicWithParameters.getSmallShipArray());
        Assert.assertEquals(shipLogicNoParameters.getSmallShipArray()[0], 1);
        Assert.assertEquals(shipLogicWithParameters.getSmallShipArray()[0], 1);

    }


    /******************************************************** Boundary Tests Start********************************************************************/

    @Test
    public void checkSetSmallShipPositionInBoundariesThrowsNoException() {
        try {
            for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
                shipLogicNoParameters.setSmallShipPosition(position);
                shipLogicWithParameters.setSmallShipPosition(position);
            }
        } catch (IllegalArgumentException e) {
            Log.d(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkSetSmallShipPositionOutOfBoundariesThrowsException() {
        try {
            shipLogicNoParameters.setSmallShipPosition(-1);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
            shipLogicWithParameters.setSmallShipPosition(-1);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            shipLogicNoParameters.setSmallShipPosition(64);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);

        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void checkMiddleShipPositionInBoundariesThrowsNoException() {
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE; position++) {
                shipLogicNoParameters.setMiddleShipPosition(position, fieldValues.HORIZONTAL);
                shipLogicWithParameters.setMiddleShipPosition(position, fieldValues.HORIZONTAL);
            }

        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE; position++) {
                shipLogicNoParameters.setMiddleShipPosition(position, fieldValues.VERTICAL);
                shipLogicWithParameters.setMiddleShipPosition(position, fieldValues.VERTICAL);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkMiddleShipPositionOutOfBoundariesThrowsException() {

        // Out of boundaries on the left field side
        try {
            shipLogicNoParameters.setMiddleShipPosition(0, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of boundaries on the right field side
        try {
            shipLogicNoParameters.setMiddleShipPosition(64, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of boundaries on the top field side
        try {
            shipLogicNoParameters.setMiddleShipPosition(7, fieldValues.VERTICAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of boundaries on the bottom field side
        try {
            shipLogicNoParameters.setMiddleShipPosition(64, fieldValues.VERTICAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }


    @Test
    public void checkBigShipPositionInBoundariesThrowsNoException() {
        // Check horizontal boundaries
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE - 1; position++) {
                shipLogicNoParameters.setBigShipPosition(1, fieldValues.HORIZONTAL);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }

        // Check vertical boundaries
        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE - 8; position++) {
                shipLogicNoParameters.setBigShipPosition(position, fieldValues.VERTICAL);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkBigShipPositionOutOfBoundariesThrowsIllegalArgumentException() {
        // Horizontal boundaries left upper field side
        try {
            shipLogicNoParameters.setBigShipPosition(0, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Horizontal boundaries right bottom field side
        try {
            shipLogicNoParameters.setBigShipPosition(63, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Vertical boundaries upper field side
        try {
            shipLogicNoParameters.setBigShipPosition(5, fieldValues.VERTICAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Vertical boundaries bottom field side
        try {
            shipLogicNoParameters.setBigShipPosition(61, fieldValues.VERTICAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }


    /******************************************************** Boundary Tests End ********************************************************************/

    @Test
    public void insertIntoMiddleShipWithCorrectSize() {
        int[] array = {1, 2};
        shipLogicNoParameters.setMiddleShipArray(array);
        shipLogicWithParameters.setMiddleShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogicNoParameters.getMiddleShipArray(), shipLogicWithParameters.getMiddleShipArray());
        Assert.assertEquals(shipLogicNoParameters.getMiddleShipArray(), array);
        Assert.assertEquals(shipLogicWithParameters.getMiddleShipArray(), array);

        // test for each position as well
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(shipLogicNoParameters.getMiddleShipArray()[i], array[i]);
            Assert.assertEquals(shipLogicWithParameters.getMiddleShipArray()[i], array[i]);
        }
    }

    @Test
    public void insertIntoBigShipWithCorrectSize() {
        int[] array = {1, 2, 3};
        shipLogicWithParameters.setBigShipArray(array);
        shipLogicNoParameters.setBigShipArray(array);

        // test if arrays are the same
        Assert.assertEquals(shipLogicNoParameters.getBigShipArray(), shipLogicWithParameters.getBigShipArray());
        Assert.assertEquals(shipLogicNoParameters.getBigShipArray(), array);
        Assert.assertEquals(shipLogicWithParameters.getBigShipArray(), array);

        // test for each position as well
        for (int i = 0; i < array.length; i++) {
            Assert.assertEquals(shipLogicNoParameters.getBigShipArray()[i], array[i]);
            Assert.assertEquals(shipLogicWithParameters.getBigShipArray()[i], array[i]);
        }
    }

    @Test
    public void shipLogicConstructorThrowsException() {

        // input parameters all null
        try {
            shipLogicWithParameters = new ShipLogic(null, null, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // first input parameter is null
        try {
            shipLogicWithParameters = new ShipLogic(null, middleArray, bigArray);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // second input parameter is null
        try {
            shipLogicWithParameters = new ShipLogic(smallArray, null, bigArray);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // third input parameter is null
        try {
            shipLogicWithParameters = new ShipLogic(smallArray, middleArray, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void checkGetterSetterSmallShipIsSetOnField() {

        // If setter never used, false is expected
        Assert.assertEquals(false, shipLogicNoParameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isSmallShipIsSetOnField());

        // Basic setter test
        shipLogicNoParameters.setSmallShipIsSetOnField(true);
        shipLogicWithParameters.setSmallShipIsSetOnField(true);
        Assert.assertEquals(true, shipLogicNoParameters.isSmallShipIsSetOnField());
        Assert.assertEquals(true, shipLogicWithParameters.isSmallShipIsSetOnField());

        shipLogicNoParameters.setSmallShipIsSetOnField(false);
        shipLogicWithParameters.setSmallShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogicNoParameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isSmallShipIsSetOnField());

    }

    @Test
    public void checkGetterSetterMiddleShipIsSetOnField() {

        // If setter never used, false is expected
        Assert.assertEquals(false, shipLogicNoParameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isMiddleShipIsSetOnField());

        // Basic getter setter tests
        shipLogicNoParameters.setMiddleShipIsSetOnField(true);
        shipLogicWithParameters.setMiddleShipIsSetOnField(true);
        Assert.assertEquals(true, shipLogicNoParameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(true, shipLogicWithParameters.isMiddleShipIsSetOnField());

        shipLogicNoParameters.setMiddleShipIsSetOnField(false);
        shipLogicWithParameters.setMiddleShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogicNoParameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isMiddleShipIsSetOnField());

    }

    @Test
    public void checkAllShipsSetOnFieldMethod() {
        // allShipsSetOnPlayerField returns true if all ships are set on field.
        // Expected to return false at start
        Assert.assertEquals(false, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogicWithParameters.allShipsSetOnPlayerField());

        // small ship on field, still expected to return false
        shipLogicNoParameters.setSmallShipIsSetOnField(true);
        shipLogicWithParameters.setSmallShipIsSetOnField(true);

        Assert.assertEquals(false, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogicWithParameters.allShipsSetOnPlayerField());

        // middle ship set as well, still expected to return false
        shipLogicNoParameters.setMiddleShipIsSetOnField(true);
        shipLogicWithParameters.setMiddleShipIsSetOnField(true);

        Assert.assertEquals(false, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogicWithParameters.allShipsSetOnPlayerField());

        // big ship set as well, expected to return true
        shipLogicNoParameters.setBigShipIsSetOnField(true);
        shipLogicWithParameters.setBigShipIsSetOnField(true);

        Assert.assertEquals(true, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(true, shipLogicWithParameters.allShipsSetOnPlayerField());

        // If one value will be set to false again, method should return false
        shipLogicNoParameters.setMiddleShipIsSetOnField(false);
        shipLogicWithParameters.setMiddleShipIsSetOnField(false);
        Assert.assertEquals(false, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogicWithParameters.allShipsSetOnPlayerField());

        // test as well with small ship
        shipLogicNoParameters.setMiddleShipIsSetOnField(true);
        shipLogicWithParameters.setMiddleShipIsSetOnField(true);
        shipLogicNoParameters.setSmallShipIsSetOnField(false);
        shipLogicWithParameters.setSmallShipIsSetOnField(false);

        Assert.assertEquals(false, shipLogicNoParameters.allShipsSetOnPlayerField());
        Assert.assertEquals(false, shipLogicWithParameters.allShipsSetOnPlayerField());
    }

    @Test
    public void checkShipsOnFieldInitializeMethod() {

        // set all onField booleans to true to check the method
        shipLogicNoParameters.setSmallShipIsSetOnField(true);
        shipLogicNoParameters.setMiddleShipIsSetOnField(true);
        shipLogicNoParameters.setBigShipIsSetOnField(true);

        shipLogicWithParameters.setSmallShipIsSetOnField(true);
        shipLogicWithParameters.setMiddleShipIsSetOnField(true);
        shipLogicWithParameters.setBigShipIsSetOnField(true);

        // shipsOnFieldInitialize() should set all boolean values for isSetOnField to false
        shipLogicNoParameters.shipsOnFieldInitialize();
        shipLogicWithParameters.shipsOnFieldInitialize();

        Assert.assertEquals(false, shipLogicNoParameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogicNoParameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogicNoParameters.isBigShipIsSetOnField());

        Assert.assertEquals(false, shipLogicWithParameters.isSmallShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isMiddleShipIsSetOnField());
        Assert.assertEquals(false, shipLogicWithParameters.isBigShipIsSetOnField());

    }

}
