package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * PlayerFieldLogic class unit tests
 */

public class PlayerFieldLogicTest {

    PlayerFieldLogic playerFieldLogic;
    PlayerFieldValues fieldValues;


    @Before
    public void setUp() {
        playerFieldLogic = new PlayerFieldLogic();
        fieldValues = new PlayerFieldValues();
    }

    @Test
    public void checkInitialization() {
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
    public void checkSetPlayerFieldMethod(){
        String[] array = new String[64];
        Arrays.fill(array, "test");

        playerFieldLogic.setPlayerField(array);
        Assert.assertTrue(Arrays.equals(array, playerFieldLogic.getPlayerField()));
    }




}
