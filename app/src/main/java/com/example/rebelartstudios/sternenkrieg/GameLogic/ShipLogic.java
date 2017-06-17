package com.example.rebelartstudios.sternenkrieg.GameLogic;

import android.util.Log;

/**
 * Created by christianbauer on 14/06/2017.
 * <p>
 * ShipLogic class used in the Map Activity class.
 */

public class ShipLogic {
	PlayerFieldPositionString fieldStrings = new PlayerFieldPositionString();

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
	public final int SMALL_SHIP_SIZE = 1;
	public final int MIDDLE_SHIP_SIZE = 2;
	public final int BIG_SHIP_SIZE = 3;

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
		if (small_ship == null || middle_ship == null || big_ship == null){
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


	int position = 0;

	/**
	 * Tag used in Log messages
	 */
	String tag = "ShipLogic";

	/**
	 * Error messages used in exceptions
	 */
	String arrayOutOfBoundsExceptionMessage = "Wrong array size in parameter";
	String degreeException = "Wrong degree value";

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
		if (array.length == SMALL_SHIP_SIZE) {
			this.small_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBoundsExceptionMessage);
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
		if (array.length == MIDDLE_SHIP_SIZE) {
			this.middle_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBoundsExceptionMessage);
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
		if (degree == fieldStrings.HORIZONTAL) {
			middleShipPosition(position, 1);
		} else if (degree == fieldStrings.VERTICAL) {
			middleShipPosition(position, 8);
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
		if (array.length == BIG_SHIP_SIZE) {
			this.big_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBoundsExceptionMessage);
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
		if (degree == fieldStrings.HORIZONTAL) {
			bigShipPosition(position, 1);
		} else if (degree == fieldStrings.VERTICAL) {
			bigShipPosition(position, 8);
		} else {
			throw new IllegalArgumentException(degreeException);
		}
	}

	public int getSibling(int degree){
		if (degree == fieldStrings.HORIZONTAL){
			return 1;
		} else if (degree == fieldStrings.VERTICAL){
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
		middle_ship[0] = position - amount;
		middle_ship[1] = position;
	}

	/**
	 * Sets the position of the big ship, using an amount to set the siblings
	 *
	 * @param position origin of big ship
	 * @param amount   used to correctly set the siblings of the origin position
	 */
	private void bigShipPosition(int position, int amount) {
		big_ship[0] = position - amount;
		big_ship[1] = position;
		big_ship[2] = position + amount;
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
	 * @param number the parameter to set the position
	 */
	public void setPosition(int number) {
		this.position = number;
	}

    public boolean isSmallShipIsSetOnField() {
        return smallShipIsSetOnField;
    }

    public boolean isMiddleShipIsSetOnField() {
        return middleShipIsSetOnField;
    }

    public boolean isBigShipIsSetOnField() {
        return bigShipIsSetOnField;
    }

    public void shipsOnFieldInitialize(){
        setSmallShipIsSetOnField(false);
        setMiddleShipIsSetOnField(false);
        setBigShipIsSetOnField(false);
    }

	public void setSmallShipIsSetOnField(boolean smallShipIsSetOnField){
        this.smallShipIsSetOnField = smallShipIsSetOnField;
    }

    public void setMiddleShipIsSetOnField(boolean middleShipIsSetOnField){
        this.middleShipIsSetOnField = middleShipIsSetOnField;
    }

    public void setBigShipIsSetOnField(boolean bigShipIsSetOnField){
        this.bigShipIsSetOnField = bigShipIsSetOnField;
    }

    public boolean allShipsSetOnPlayerField(){
        return isSmallShipIsSetOnField() && isMiddleShipIsSetOnField() && isBigShipIsSetOnField();
    }

}