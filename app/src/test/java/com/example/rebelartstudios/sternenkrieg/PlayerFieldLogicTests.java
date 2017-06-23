package com.example.rebelartstudios.sternenkrieg;

import android.util.Log;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
import com.example.rebelartstudios.sternenkrieg.exception.ErrorMessages;

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
    FieldValues fieldValues;

    String tag = "PlayerFieldLogicTests";

    @Before
    public void setUp() {
        playerFieldLogic = new PlayerFieldLogic();
        fieldValues = new FieldValues();
    }

    @Test
    public void checkInitializationThrowsNoException() {

        try {
            playerFieldLogic.initializePlayerField();
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_EMPTY.equals(playerFieldLogic.getPlayerField()[position]));
        }
    }

    @Test
    public void checkGetPlayerFieldMethod() {
        String[] array = new String[fieldValues.FIELD_SIZE];
        Arrays.fill(array, fieldValues.SET_FIELD_POSITION_EMPTY);

        Assert.assertTrue(Arrays.equals(array, playerFieldLogic.getPlayerField()));
    }

    @Test
    public void checkSetPlayerFieldMethodThrowsNOException() {
        String[] array = new String[fieldValues.FIELD_SIZE];
        Arrays.fill(array, "test");

        try {
            playerFieldLogic.setPlayerField(array);
            Assert.assertTrue(Arrays.equals(array, playerFieldLogic.getPlayerField()));

            for (int i = 0; i < fieldValues.FIELD_SIZE; i++) {
                Assert.assertEquals("test", playerFieldLogic.getStringInPosition(i));
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkSetPlayerFieldMethodThrowsException() {
        try {
            playerFieldLogic.setPlayerField(null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.PLAYER_FIELD_WRONG_SIZE);
        }

        try {
            playerFieldLogic.setPlayerField(new String[1]);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.PLAYER_FIELD_WRONG_SIZE);
        }

        try {
            playerFieldLogic.setPlayerField(new String[100]);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.PLAYER_FIELD_WRONG_SIZE);
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
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }

        try {
            playerFieldLogic.getSibling(8);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }


    @Test
    public void checkSetPFSmallShipPositionThrowsNOException() {
        try {
            for (int i = 0; i < fieldValues.FIELD_SIZE; i++) {
                playerFieldLogic.setSmallShipPosition(i, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkInRangeMethod() {
        try {
            playerFieldLogic.inRange(-1);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            playerFieldLogic.inRange(65);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(playerFieldLogic.inRange(position));
        }
    }

    @Test
    public void checkSetPFMiddleShipPositionWithSiblingIndexThrowsNOException() {
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE; position++) {
                playerFieldLogic.setMiddleShipPositionWithSiblingIndex(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE; position++) {
                playerFieldLogic.setMiddleShipPositionWithSiblingIndex(position, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkSetPFMiddleShipPositionWithSiblingIndexThrowsException() {
        try {
            playerFieldLogic.setMiddleShipPositionWithSiblingIndex(-1, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            playerFieldLogic.setMiddleShipPositionWithSiblingIndex(4, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
        try {
            playerFieldLogic.setMiddleShipPositionWithSiblingIndex(65, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Exception comes from getSibling
        try {
            playerFieldLogic.setMiddleShipPositionWithSiblingIndex(1, 3, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    @Test
    public void checkSetMiddleShipPositionToString() {
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE; position++) {
                playerFieldLogic.setMiddleShipPositionToString(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE; position++) {
                playerFieldLogic.setMiddleShipPositionToString(position, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkSetMiddleShipPositionToStringThrowsException() {
        try {
            playerFieldLogic.setMiddleShipPositionToString(0, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            playerFieldLogic.setMiddleShipPositionToString(65, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

    }

    @Test
    public void checkBigShipPositionWithSiblingIndexThrowsNoException() {

        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE - 1; position++) {
                playerFieldLogic.setBigShipPositionWithSiblingIndex(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            }

        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE - 8; position++) {
                playerFieldLogic.setBigShipPositionWithSiblingIndex(position, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

    }

    @Test
    public void checkBigShipPositionWithSiblingIndexThrowsException() {

    }

    @Test
    public void checkGetStringInPosition() {
        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertEquals(fieldValues.SET_FIELD_POSITION_EMPTY, playerFieldLogic.getStringInPosition(position));
        }

        playerFieldLogic.setSmallShipPosition(5, fieldValues.SET_FIELD_POSITION_A);
        Assert.assertTrue(fieldValues.SET_FIELD_POSITION_A.equals(playerFieldLogic.getStringInPosition(5)));

        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            playerFieldLogic.setSmallShipPosition(position, fieldValues.SET_FIELD_POSITION_B);
        }

        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_B.equals(playerFieldLogic.getStringInPosition(position)));
        }
    }

    @Test
    public void checkGetStringPositionThrowsException() {
        // out of boundaries on the left side
        try {
            playerFieldLogic.getStringInPosition(-1);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // out of boundaries on the right side
        try {
            playerFieldLogic.getStringInPosition(64);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Log.e(tag, e.getMessage());
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void checkMiddleShipFieldContainsString() {

        // Iterate through the array, and check if initialization was correctly executed
        for (int position = 1; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(playerFieldLogic.middleShipFieldContainsString(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_EMPTY));
        }

        for (int position = 8; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(playerFieldLogic.middleShipFieldContainsString(position, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_EMPTY));
        }

        playerFieldLogic.setMiddleShipPositionToString(2, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_B);

        // The two values, which where set, return the correct string
        for (int position = 1; position < 2; position++) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_B.equals(playerFieldLogic.getStringInPosition(position)));
        }

        // All other values in the array are still set to empty
        Assert.assertTrue(fieldValues.SET_FIELD_POSITION_EMPTY.equals(playerFieldLogic.getStringInPosition(0)));
        for (int position = 3; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_EMPTY.equals(playerFieldLogic.getStringInPosition(position)));
        }
    }

    @Test
    public void checkBigShipFieldContainsString() {
        for (int position = 1; position < fieldValues.FIELD_SIZE - 1; position++) {
            Assert.assertTrue(playerFieldLogic.bigShipFieldContainsString(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_EMPTY));
        }

        for (int position = 8; position < fieldValues.FIELD_SIZE - 8; position++) {
            Assert.assertTrue(playerFieldLogic.bigShipFieldContainsString(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_EMPTY));
        }

        playerFieldLogic.setBigShipPositionToString(1, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);

        for (int position = 0; position < 3; position++) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_A.equals(playerFieldLogic.getStringInPosition(position)));
        }

        playerFieldLogic.setBigShipPositionToString(12, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_B);

        for (int position = 4; position < 20; position += 8) {
            Assert.assertTrue(fieldValues.SET_FIELD_POSITION_B.equals(playerFieldLogic.getStringInPosition(position)));
        }
    }
}
