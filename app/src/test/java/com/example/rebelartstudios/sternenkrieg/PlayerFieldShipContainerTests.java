package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldLogic;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldShipContainer;
import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
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
    FieldValues fieldValues;

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
    public void checkInitializationConstructorWithParameterThrowsException() {
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

    @Test
    public void setMiddleShipContainerThrowsExceptionWithOutOfBoundsParameters() {

        // Clearly out of left field boundaries
        try {
            container.setMiddleShipContainer(-1, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Out of right field boundaries
        try {
            container.setMiddleShipContainer(fieldValues.FIELDSIZE, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Left sibling is out of boundaries
        try {
            container.setMiddleShipContainer(0, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }

    @Test
    public void setMiddleShipContainerThrowsExceptionWithNULLParameter() {
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
            container.setMiddleShipContainer(fieldValues.FIELDSIZE - 1, fieldValues.HORIZONTAL, null);
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
    public void setMiddleShipContainerThrowsExceptionWithWrongDegree() {
        try {
            container.setMiddleShipContainer(1, 2, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }

        try {
            container.setMiddleShipContainer(1, -1, fieldValues.SETFIELDPOSITION_A);
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
            for (int position = 1; position < fieldValues.FIELDSIZE - 1; position++) {
                container.setBigShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }

        try {
            for (int position = 8; position < fieldValues.FIELDSIZE - 8; position++) {
                container.setBigShipContainer(position, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_B);
            }
        } catch (Exception e) {
            fail(ErrorMessages.EXCEPTION_REACHED);
        }
    }

    @Test
    public void setBigShipContainerThrowsExceptionWithOutOfBoundariesParams() {

        // Center position out of range
        try {
            container.setBigShipContainer(-1, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
        // Sibling is out of boundaries
        try {
            container.setBigShipContainer(0, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Center position out of boundaries
        try {
            container.setBigShipContainer(64, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
        // Sibling out of boundaries
        try {
            container.setBigShipContainer(63, fieldValues.HORIZONTAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        // Vertical sibling is out of boundaries
        try {
            container.setBigShipContainer(5, fieldValues.VERTICAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }

        try {
            container.setBigShipContainer(60, fieldValues.VERTICAL, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.POSITION_OUT_OF_RANGE);
        }
    }


    @Test
    public void setBigShipContainerThrowsExceptionWithWrongDegree() {
        try {
            container.setBigShipContainer(1, 2, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }

        try {
            container.setBigShipContainer(1, -1, fieldValues.SETFIELDPOSITION_A);
            fail(ErrorMessages.ILLEGAL_ARGUMENT_EXCEPTION_EXPECTED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    @Test
    public void setBigShipContainerThrowsExceptionWithNULLParams() {
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
        for (int position = 0; position < fieldValues.FIELDSIZE; position++) {
            container.setSmallShipContainer(position, fieldValues.SETFIELDPOSITION_A);
        }

        int[] array = {1, 2, 3};
        container.delete(array);

        // check if the deleted positions are indeed, deleted
        for (int position = 1; position < 4; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SETFIELDPOSITION_EMPTY));
        }
        // check if other values are still the same
        Assert.assertTrue(container.positionContainsString(0, fieldValues.SETFIELDPOSITION_A));
        for (int position = 4; position < fieldValues.FIELDSIZE; position++) {
            Assert.assertTrue(container.positionContainsString(position, fieldValues.SETFIELDPOSITION_A));
        }

    }
}
