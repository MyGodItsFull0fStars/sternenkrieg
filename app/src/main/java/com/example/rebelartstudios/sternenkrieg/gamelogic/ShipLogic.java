package com.example.rebelartstudios.sternenkrieg.gamelogic;

import android.util.Log;

import com.example.rebelartstudios.sternenkrieg.res.ErrorMessages;

/**
 * ShipLogic class used in the Map Activity class.
 */

public class ShipLogic {
    private FieldValues fieldValues = new FieldValues();

    /**
     * Ship arrays used in the player field to save the positions of the ships
     */
    private int[] small_ship;
    private int[] middle_ship;
    private int[] big_ship;

    /**
     * Ship indexes of the small ships
     */
    public final int SMALL_SHIP_ID = 0;
    public final int MIDDLE_SHIP_ID = 1;
    public final int BIG_SHIP_ID = 2;

    /**
     * Size of the ships
     */
    private final int SMALL_SHIP_SIZE = 1;
    private final int MIDDLE_SHIP_SIZE = 2;
    private final int BIG_SHIP_SIZE = 3;

    /**
     * Booleans for checking if ship is already set on the field
     */
    private boolean smallShipIsSetOnField;
    private boolean middleShipIsSetOnField;
    private boolean bigShipIsSetOnField;

    /**
     * Ship constructor with the int arrays for the ships
     *
     * @param small_ship  int array for small ship
     * @param middle_ship int array for middle ship
     * @param big_ship    int array for big ship
     */
    public ShipLogic(int[] small_ship, int[] middle_ship, int[] big_ship) {
        if (small_ship == null || middle_ship == null || big_ship == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        setSmallShipArray(small_ship);
        setMiddleShipArray(middle_ship);
        setBigShipArray(big_ship);
    }

    /**
     * Standard constructor with initialization of the ship arrays
     */
    public ShipLogic() {
        setSmallShipArray(new int[SMALL_SHIP_SIZE]);
        setMiddleShipArray(new int[MIDDLE_SHIP_SIZE]);
        setBigShipArray(new int[BIG_SHIP_SIZE]);
    }

    /**
     * Tag used in Log messages
     */
    private final String tag = "ShipLogic";


    /**
     * Returns the array of the small sized ship
     *
     * @return int array with size 1
     */
    public int[] getSmallShipArray() {
        return small_ship;
    }

    /**
     * Returns the array of the middle sized ship
     *
     * @return int array with size 2
     */
    public int[] getMiddleShipArray() {
        return middle_ship;
    }

    /**
     * Returns the array of the big sized ship
     *
     * @return int array with size 3
     */
    public int[] getBigShipArray() {
        return big_ship;
    }

    /**
     * Gets an int array as parameter
     * Copies the array into the small ship array
     *
     * @param array used to set the array small_ship
     */
    public void setSmallShipArray(int[] array) {
        Log.i(tag, "Crash");
        if (array != null && array.length == SMALL_SHIP_SIZE && inRange(array[0])) {
            this.small_ship = array;
        } else {
            throw new IllegalArgumentException(ErrorMessages.ARRAY_WRONG_SIZE);
        }
    }

    /**
     * Sets the position of the small ship for the player field grid.
     * Other than in middle and big ship methods, here is no degree parameter needed.
     *
     * @param position the position on the player field
     */
    public void setSmallShipPosition(int position) {
        Log.i(tag, "Crash");
        if (inRange(position)) small_ship[0] = position;
        else throw new IllegalArgumentException(ErrorMessages.POSITION_OUT_OF_RANGE);
    }

    /**
     * Gets an array as parameter, and since the array must be the same size, an exception will be thrown if otherwise.
     *
     * @param array integer array used to place the ship on the field
     */
    public void setMiddleShipArray(int[] array) {
        Log.i(tag, "Crash");
        if (array != null && array.length == MIDDLE_SHIP_SIZE) {
            this.middle_ship = array;
        } else {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_WRONG_SIZE);
        }
    }

    /**
     * Sets the position of the middle ship on the player field
     *
     * @param position the wanted position used to set the ship
     * @param degree   the degree, which is used to set the position horizontally or vertically
     *                 if degree is wrongly set, a Log message will be send
     */
    public void setMiddleShipPosition(int position, int degree) {
        Log.i(tag, "Crash");
        if (degree == fieldValues.HORIZONTAL || degree == fieldValues.VERTICAL) {
            middleShipPosition(position, getSibling(degree));
        } else {
            throw new IllegalArgumentException(ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    /**
     * Gets a int array as parameter, and if it is the correct size, the array will be copied into the big_ship array
     * If size is false, an exception will be thrown
     *
     * @param array used to set the array big_ship
     */
    public void setBigShipArray(int[] array) {
        Log.i(tag, "Crash");
        if (array == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        if (array.length == BIG_SHIP_SIZE) {
            this.big_ship = array;
        } else {
            throw new ArrayIndexOutOfBoundsException(ErrorMessages.ARRAY_WRONG_SIZE);
        }
    }

    /**
     * Sets the position of the big ship on the player field
     *
     * @param position the position on the player field
     * @param degree   Is used to set position horizontally or vertically
     *                 if degree is wrong, the method will send an exception
     */
    public void setBigShipPosition(int position, int degree) {
        Log.i(tag, "Crash");
        if (degree == fieldValues.HORIZONTAL || degree == fieldValues.VERTICAL) {
            bigShipPosition(position, getSibling(degree));
        } else {
            throw new IllegalArgumentException(ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }


    /**
     * Returns the value of the position siblings, depending on a vertical or horizontal degree
     *
     * @param degree used to determine the return value
     * @return the value which will be returned. Horizontal returns 1 and vertical returns 8
     */
    int getSibling(int degree) {
        Log.i(tag, "Crash");
        if (degree == fieldValues.HORIZONTAL) {
            return 1;
        } else if (degree == fieldValues.VERTICAL) {
            return 8;
        } else {
            throw new IllegalArgumentException(ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    /**
     * Sets the position of the middle ship
     *
     * @param position origin of ship
     * @param amount   which will be used to set the siblings of the position
     */
    private void middleShipPosition(int position, int amount) {
        Log.i(tag, "Crash");
        if (inRange(position) && inRange(position - amount)) {
            middle_ship[0] = position - amount;
            middle_ship[1] = position;
        } else throw new IllegalArgumentException(ErrorMessages.POSITION_OUT_OF_RANGE);
    }

    /**
     * Sets the position of the big ship, using an amount to set the siblings
     *
     * @param position origin of big ship
     * @param amount   used to correctly set the siblings of the origin position
     */
    private void bigShipPosition(int position, int amount) {
        Log.i(tag, "Crash");
        if (inRange(position - amount) && inRange(position) && inRange(position + amount)) {
            big_ship[0] = position - amount;
            big_ship[1] = position;
            big_ship[2] = position + amount;
        } else throw new IllegalArgumentException(ErrorMessages.POSITION_OUT_OF_RANGE);
    }


    /**
     * Getter Method, used in Map Activity class when a ship is set on the player field
     *
     * @return when the ship is set on the player field, returns true, else false
     */
    public boolean isSmallShipIsSetOnField() {
        return smallShipIsSetOnField;
    }
    public boolean isMiddleShipIsSetOnField() {
        return middleShipIsSetOnField;
    }
    public boolean isBigShipIsSetOnField() {
        return bigShipIsSetOnField;
    }


    /**
     * At creation of the Map Activity class, this method will be called and all shipIsSetOnField booleans will be set to false
     */
    public void shipsOnFieldInitialize() {
        setSmallShipIsSetOnField(false);
        setMiddleShipIsSetOnField(false);
        setBigShipIsSetOnField(false);
    }

    /**
     * Setter method for the boolean smallShipIsSetOnField, called in Map Activity after
     *
     * @param smallShipIsSetOnField returns if the smallShip is set on the field
     */
    public void setSmallShipIsSetOnField(boolean smallShipIsSetOnField) {
        this.smallShipIsSetOnField = smallShipIsSetOnField;
    }

    /**
     * Setter method for the boolean smallShipIsSetOnField, called in Map Activity after
     *
     * @param middleShipIsSetOnField returns if the smallShip is set on the field
     */
    public void setMiddleShipIsSetOnField(boolean middleShipIsSetOnField) {
        this.middleShipIsSetOnField = middleShipIsSetOnField;
    }

    /**
     * Setter method for the boolean smallShipIsSetOnField, called in Map Activity after
     *
     * @param bigShipIsSetOnField returns if the smallShip is set on the field
     */
    public void setBigShipIsSetOnField(boolean bigShipIsSetOnField) {
        this.bigShipIsSetOnField = bigShipIsSetOnField;
    }

    /**
     * Used in Map Activity class to determine if all ships are placed on the player field
     * @return if all ships are placed, this method will return true, else return false
     */
    public boolean allShipsSetOnPlayerField() {
        return isSmallShipIsSetOnField() && isMiddleShipIsSetOnField() && isBigShipIsSetOnField();
    }

    /**
     * Checks if the position of the ship is in range of the player field
     * @param position the given parameter for ship placement
     * @return if the position is in boundaries, method will return true, otherwise return false
     */
    private boolean inRange(int position) {
        return position >= 0 && position < fieldValues.FIELDSIZE;
    }
}