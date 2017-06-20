package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldShipContainer;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldValues;
import com.example.rebelartstudios.sternenkrieg.gamelogic.ShipLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * Unit tests for the container class of PlayerFieldLogic and ShipLogic
 */

public class PlayerFieldShipContainerTests {
    PlayerFieldShipContainer container;
    PlayerFieldValues fieldValues;

    @Before
    public void setUp() {
        container = new PlayerFieldShipContainer();
        fieldValues = new PlayerFieldValues();
    }

    @Test
    public void checkInitializationStandardConstructor() {
        try {
            container.getPlayerFieldLogic();
            container.getShipLogic();
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkInitializationConstructorWithParameterThrowsNoException() {
        try {
            container = new PlayerFieldShipContainer(new PlayerFieldLogic(), new ShipLogic());
        } catch (IllegalArgumentException e) {
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void checkInitializationConstructorWithParameterThrowsException() {
        try {
            container = new PlayerFieldShipContainer(null, new ShipLogic());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
        try {
            container = new PlayerFieldShipContainer(new PlayerFieldLogic(), null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
        try {
            container = new PlayerFieldShipContainer(null, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void checkSmallShipContainerThrowsNoExceptionWithCorrectInput() {

        try {
            for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
                container.setSmallShipContainer(position, fieldValues.SETFIELDPOSITION_B);
            }
        } catch (IllegalArgumentException e) {
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void smallShipContainerMethodOutOfBoundsThrowsException() {

        try {
            container.setSmallShipContainer(-1, fieldValues.SETFIELDPOSITION_B);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            container.setSmallShipContainer(fieldValues.FIELDSIZE, fieldValues.SETFIELDPOSITION_B);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void setSmallShipContainerThrowsExceptionWithNULLString() {
        try {
            container.setSmallShipContainer(0, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void setSmallShipContainerWorksProperly() {
        for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SETFIELDPOSITION_EMPTY));
            container.setSmallShipContainer(position, fieldValues.SETFIELDPOSITION_A);
        }

        for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SETFIELDPOSITION_A));
        }
    }

    @Test
    public void checkMiddleShipThrowsNoExceptionWithCorrectInput() {
        try {
            for (int position = 1; position < fieldValues.FIELDSIZE; position++) {
                container.setMiddleShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELDSIZE; position++) {
                container.setMiddleShipContainer(position, fieldValues.VERTICAL, fieldValues.SETFIELDPOSITION_B);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }
}
