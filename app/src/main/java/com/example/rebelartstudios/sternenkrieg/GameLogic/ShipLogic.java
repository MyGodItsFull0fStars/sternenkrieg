package com.example.rebelartstudios.sternenkrieg.GameLogic;

import android.util.Log;

/**
 * Created by christianbauer on 14/06/2017.
 * <p>
 * ShipLogic class used in the Map Activity class.
 */

public class ShipLogic {
    private PlayerFieldValues fieldValues = new PlayerFieldValues();

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

    String positionOutOfFieldException = "Position in parameter is out of the field boundaries";

    /**
     * Ship constructor with the int arrays for the ships
     *
     * @param small_ship  int array for small ship
     * @param middle_ship int array for middle ship
     * @param big_ship    int array for big ship
     */
    public ShipLogic(int[] small_ship, int[] middle_ship, int[] big_ship) {
        if (small_ship == null || middle_ship == null || big_ship == null) {
            throw new IllegalArgumentException("IllegalArgumentException at ShipLogic constructor");
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


    private int position = 0;

    /**
     * Tag used in Log messages
     */
    private String tag = "ShipLogic";

    /**
     * Error messages used in exceptions
     */
    private String wrongArraySizeExceptionMessage = "Wrong array size in parameter";

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
        if (array != null && array.length == SMALL_SHIP_SIZE && inRange(array[0])) {
            this.small_ship = array;
        } else {
            throw new IllegalArgumentException(wrongArraySizeExceptionMessage);
        }
    }

    /**
     * Sets the position of the small ship for the player field grid.
     * Other than in middle and big ship methods, here is no degree parameter needed.
     *
     * @param position the position on the player field
     */
    public void setSmallShipPosition(int position) {
        small_ship[0] = position;
    }

    /**
     * Gets an array as parameter, and since the array must be the same size, an exception will be thrown if otherwise.
     *
     * @param array
     */
    public void setMiddleShipArray(int[] array) {
        if (array != null && array.length == MIDDLE_SHIP_SIZE) {
            this.middle_ship = array;
        } else {
            throw new ArrayIndexOutOfBoundsException(wrongArraySizeExceptionMessage);
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
        if (degree == fieldValues.HORIZONTAL || degree == fieldValues.VERTICAL) {
            middleShipPosition(position, getSibling(degree));
        } else {
            Log.e(tag, "Degree is not correctly set");
        }
    }

    /**
     * Gets a int array as parameter, and if it is the correct size, the array will be copied into the big_ship array
     * If size is false, an exception will be thrown
     *
     * @param array used to set the array big_ship
     */
    public void setBigShipArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Cannot set array as null");
        }
        if (array.length == BIG_SHIP_SIZE) {
            this.big_ship = array;
        } else {
            throw new ArrayIndexOutOfBoundsException(wrongArraySizeExceptionMessage);
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
        if (degree == fieldValues.HORIZONTAL || degree == fieldValues.VERTICAL) {
            bigShipPosition(position, getSibling(degree));
        } else {
            String degreeException = "Wrong degree value";
            throw new IllegalArgumentException(degreeException);
        }
    }


    /**
     * Returns the value of the position siblings, depending on a vertical or horizontal degree
     *
     * @param degree used to determine the return value
     * @return the value which will be returned. Horizontal returns 1 and vertical returns 8
     */
    public int getSibling(int degree) {
        if (degree == fieldValues.HORIZONTAL) {
            return 1;
        } else if (degree == fieldValues.VERTICAL) {
            return 8;
        } else {
            throw new IllegalArgumentException("Degree not correctly set");
        }
    }

    /**
     * Sets the position of the middle ship
     *
     * @param position origin of ship
     * @param amount   which will be used to set the siblings of the position
     */
    private void middleShipPosition(int position, int amount) {
        if (inRange(position) && inRange(position - amount)) {
            middle_ship[0] = position - amount;
            middle_ship[1] = position;
        } else throw new IllegalArgumentException(positionOutOfFieldException);
    }

    /**
     * Sets the position of the big ship, using an amount to set the siblings
     *
     * @param position origin of big ship
     * @param amount   used to correctly set the siblings of the origin position
     */
    private void bigShipPosition(int position, int amount) {
        if (inRange(position - amount) && inRange(position) && inRange(position + amount)) {
            big_ship[0] = position - amount;
            big_ship[1] = position;
            big_ship[2] = position + amount;
        } else throw new IllegalArgumentException(positionOutOfFieldException);
    }

    /**
     * @return the value of the current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position
     *
     * @param number the parameter to set the position, if position is in field range
     */
    public void setPosition(int number) {
        if (inRange(number)) this.position = number;
        else throw new IllegalArgumentException(positionOutOfFieldException);
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
     * @param smallShipIsSetOnField
     */
    public void setSmallShipIsSetOnField(boolean smallShipIsSetOnField) {
        this.smallShipIsSetOnField = smallShipIsSetOnField;
    }

    public void setMiddleShipIsSetOnField(boolean middleShipIsSetOnField) {
        this.middleShipIsSetOnField = middleShipIsSetOnField;
    }

    public void setBigShipIsSetOnField(boolean bigShipIsSetOnField) {
        this.bigShipIsSetOnField = bigShipIsSetOnField;
    }

    public boolean allShipsSetOnPlayerField() {
        return isSmallShipIsSetOnField() && isMiddleShipIsSetOnField() && isBigShipIsSetOnField();
    }

    private boolean inRange(int position) {
        return position >= 0 && position < fieldValues.FIELDSIZE;
    }

}