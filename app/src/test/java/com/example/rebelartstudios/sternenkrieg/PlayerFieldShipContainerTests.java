package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldShipContainer;
import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
import com.example.rebelartstudios.sternenkrieg.gamelogic.ShipLogic;
import com.example.rebelartstudios.sternenkrieg.exception.ErrorMessages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * Unit tests for the container class of PlayerFieldLogic and ShipLogic
 */

public class PlayerFieldShipContainerTests {
    private PlayerFieldShipContainer container;
    private FieldValues fieldValues;

    @Before
    public void setUp() {
        container = new PlayerFieldShipContainer();
        fieldValues = new FieldValues();
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
    public void checkInitializationConstructorWithParameter() throws IllegalArgumentException {
        try {
            container = new PlayerFieldShipContainer(null, new ShipLogic());
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
        try {
            container = new PlayerFieldShipContainer(new PlayerFieldLogic(), null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
        try {
            container = new PlayerFieldShipContainer(null, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void checkSmallShipContainerThrowsNoExceptionWithCorrectInput() {

        try {
            for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
                container.setSmallShipContainer(position, fieldValues.SET_FIELD_POSITION_B);
            }
        } catch (IllegalArgumentException e) {
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_REACHED);
        }
    }

    @Test
    public void smallShipContainerMethodOutOfBounds() throws IllegalArgumentException {

        try {
            container.setSmallShipContainer(-1, fieldValues.SET_FIELD_POSITION_B);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            container.setSmallShipContainer(fieldValues.FIELD_SIZE, fieldValues.SET_FIELD_POSITION_B);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void setSmallShipContainerWithNULLString() throws IllegalArgumentException {
        try {
            container.setSmallShipContainer(0, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void setSmallShipContainerWorksProperly() {
        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SET_FIELD_POSITION_EMPTY));
            container.setSmallShipContainer(position, fieldValues.SET_FIELD_POSITION_A);
        }

        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SET_FIELD_POSITION_A));
        }
    }


    @Test
    public void checkMiddleShipThrowsNoExceptionWithCorrectInput() {
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE; position++) {
                container.setMiddleShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE; position++) {
                container.setMiddleShipContainer(position, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_B);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void setMiddleShipContainerWithOutOfBoundsParameters() throws IllegalArgumentException {

        // Clearly out of left field boundaries
        try {
            container.setMiddleShipContainer(-1, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of right field boundaries
        try {
            container.setMiddleShipContainer(fieldValues.FIELD_SIZE, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Left sibling is out of boundaries
        try {
            container.setMiddleShipContainer(0, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void setMiddleShipContainerWithNULLParameter() throws IllegalArgumentException {
        // At the start of array
        try {
            container.setMiddleShipContainer(1, fieldValues.HORIZONTAL, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // In the middle of array
        try {
            container.setMiddleShipContainer(32, fieldValues.HORIZONTAL, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // At the end of array
        try {
            container.setMiddleShipContainer(fieldValues.FIELD_SIZE - 1, fieldValues.HORIZONTAL, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        // Test with vertical ship placement
        try {
            container.setMiddleShipContainer(8, fieldValues.VERTICAL, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void setMiddleShipContainerWithWrongDegree() throws IllegalArgumentException {
        try {
            container.setMiddleShipContainer(1, 2, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }

        try {
            container.setMiddleShipContainer(1, -1, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    @Test
    public void setBigShipContainerWorksProperlyWithCorrectParameters() {
        // Setting the values in field with bigShipMethod
        // Horizontal
        try {
            for (int position = 1; position < fieldValues.FIELD_SIZE - 1; position++) {
                container.setBigShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELD_SIZE - 8; position++) {
                container.setBigShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_B);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void setBigShipContainerWithOutOfBoundariesParams() throws IllegalArgumentException {

        // Center position out of range
        try {
            container.setBigShipContainer(-1, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
        // Sibling is out of boundaries
        try {
            container.setBigShipContainer(0, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Center position out of boundaries
        try {
            container.setBigShipContainer(64, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
        // Sibling out of boundaries
        try {
            container.setBigShipContainer(63, fieldValues.HORIZONTAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Vertical sibling is out of boundaries
        try {
            container.setBigShipContainer(5, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            container.setBigShipContainer(60, fieldValues.VERTICAL, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }


    @Test
    public void setBigShipContainerWithWrongDegree() throws IllegalArgumentException {
        try {
            container.setBigShipContainer(1, 2, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }

        try {
            container.setBigShipContainer(1, -1, fieldValues.SET_FIELD_POSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    @Test
    public void setBigShipContainerWithNULLParams() throws IllegalArgumentException {
        try {
            container.setBigShipContainer(1, fieldValues.HORIZONTAL, null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }

        try {
            container.setBigShipContainer(9, fieldValues.VERTICAL, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void checkDeleteMethod() {
        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            container.setSmallShipContainer(position, fieldValues.SET_FIELD_POSITION_A);
        }

        int[] array = {1, 2, 3};
        container.delete(array);

        // check if the deleted positions are indeed, deleted
        for (int position = 1; position < 4; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SET_FIELD_POSITION_EMPTY));
        }
        // check if other values are still the same
        Assert.assertTrue(container.positionContainsString(0, fieldValues.SET_FIELD_POSITION_A));
        for (int position = 4; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SET_FIELD_POSITION_A));
        }
    }


    @Test
    public void deleteMethodNullParams() throws IllegalArgumentException {
        try {
            container.delete(null);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.NULL_PARAMETER);
        }
    }

    @Test
    public void inRangeMethod() {
        // In range should always return true
        for (int position = 0; position < fieldValues.FIELD_SIZE; position++) {
            Assert.assertTrue(container.inRange(position, 0, fieldValues.FIELD_SIZE - 1));
        }

        // Out of range on left side should always return false
        for (int position = -1; position > -fieldValues.FIELD_SIZE; position--) {
            Assert.assertFalse(container.inRange(position, 0, fieldValues.FIELD_SIZE - 1));
        }

        for (int position = fieldValues.FIELD_SIZE; position < fieldValues.FIELD_SIZE * 2; position++) {
            Assert.assertFalse(container.inRange(position, 0, fieldValues.FIELD_SIZE - 1));
        }
    }

    @Test
    public void checkPositionWorksWithProperParams() {
        // check if first position is available for small ship
        Assert.assertTrue(container.checkPosition(0, container.getShipLogic().SMALL_SHIP_ID, fieldValues.HORIZONTAL));

        // Sibling of middle ship is out of boundaries on the left side
        Assert.assertFalse(container.checkPosition(0, container.getShipLogic().MIDDLE_SHIP_ID, fieldValues.HORIZONTAL));

        // Sibling of big ship is out of boundaries on the left side
        Assert.assertFalse(container.checkPosition(0, container.getShipLogic().BIG_SHIP_ID, fieldValues.HORIZONTAL));
        Assert.assertFalse(container.checkPosition(63, container.getShipLogic().BIG_SHIP_ID, fieldValues.HORIZONTAL));
    }

    @Test
    public void checkPositionOutOfRange() throws IllegalArgumentException {
        // Out of boundaries on the left side
        try {
            container.checkPosition(-1, container.getShipLogic().SMALL_SHIP_ID, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of boundaries on the right side
        try {
            container.checkPosition(64, container.getShipLogic().SMALL_SHIP_ID, fieldValues.HORIZONTAL);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

}
