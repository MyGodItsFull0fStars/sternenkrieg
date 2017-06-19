package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.fail;

/**
 * PlayerFieldLogic class unit tests
 */

public class PlayerFieldLogicTests {

    PlayerFieldLogic playerFieldLogic;
    PlayerFieldValues fieldValues;


    @Before
    public void setUp() {
        playerFieldLogic = new PlayerFieldLogic();
        fieldValues = new PlayerFieldValues();
    }

    @Test
    public void checkInitializationThrowsNoException() {

        try {
            playerFieldLogic.initializePlayerField();
        } catch (Exception e) {
            fail("Exception should not be reached");
        }

        for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
            Assert.assertTrue(fieldValues.SETFIELDPOSITION_EMPTY.equals(playerFieldLogic.getPlayerField()[position]));
        }
    }

    @Test
    public void checkGetPlayerFieldMethod() {
        String[] array = new String[64];
        Arrays.fill(array, "0");

        Assert.assertTrue(Arrays.equals(array, playerFieldLogic.getPlayerField()));
    }

    @Test
    public void checkSetPlayerFieldMethodThrowsNOException() {
        String[] array = new String[fieldValues.FIELDSIZE];
        Arrays.fill(array, "test");

        try {
            playerFieldLogic.setPlayerField(array);
            Assert.assertTrue(Arrays.equals(array, playerFieldLogic.getPlayerField()));

            for (int i = 0; i < fieldValues.FIELDSIZE; i++) {
                Assert.assertEquals("test", playerFieldLogic.getStringInPosition(i));
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached");
        }
    }

    @Test
    public void checkSetPlayerFieldMethodThrowsException() {
        String errorMessage = "java.lang.IllegalArgumentException: Parameter has the wrong size";
        try {
            playerFieldLogic.setPlayerField(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), errorMessage);
        }

        try {
            playerFieldLogic.setPlayerField(new String[1]);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), errorMessage);
        }

        try {
            playerFieldLogic.setPlayerField(new String[100]);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.toString(), errorMessage);
        }

    }


    @Test
    public void checkGetSiblingMethodThrowsNoException() {
        Assert.assertEquals(1, playerFieldLogic.getSibling(fieldValues.HORIZONTAL));
        Assert.assertEquals(8, playerFieldLogic.getSibling(fieldValues.VERTICAL));
    }

    @Test
    public void checkGetSiblingMethodThrowsException() {
        try {
            playerFieldLogic.getSibling(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Given degree is not allowed");
        }

        try {
            playerFieldLogic.getSibling(8);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Given degree is not allowed");
        }
    }


    @Test
    public void checkSetPFSmallShipPositionThrowsNOException() {
        try {
            for (int i = -100; i < fieldValues.FIELDSIZE * 2; i++) {
                playerFieldLogic.setPFSmallShipPosition(i, fieldValues.SETFIELDPOSITION_A);
            }
        } catch (Exception e) {
            fail("Exception should not be reached");
        }
    }

    @Test
    public void checkInRangeMethod() {
        Assert.assertFalse(playerFieldLogic.inRange(-1));
        Assert.assertFalse(playerFieldLogic.inRange(65));

        for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
            Assert.assertTrue(playerFieldLogic.inRange(position));
        }
    }

    @Test
    public void checkSetPFMiddleShipPositionWithSiblingIndexThrowsNOException() {
        try {
            for (int position = 1; position < fieldValues.FIELDSIZE; position++) {
                playerFieldLogic.setPFMiddleShipPositionWithSiblingIndex(position, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            }
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be reached");
        }
    }

    @Test
    public void checkSetPFMiddleShipPositionWithSiblingIndexThrowsException(){
        try {
            playerFieldLogic.setPFMiddleShipPositionWithSiblingIndex(0, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Parameter is not in range of player field");
        }
    }


}
