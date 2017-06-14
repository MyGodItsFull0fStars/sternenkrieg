package com.example.rebelartstudios.sternenkrieg;

import android.util.Log;

/**
 * Created by christianbauer on 14/06/2017.
 */

public class ShipLogic {
	int[] small_ship = new int[1];
	int[] middle_ship = new int[2];
	int[] big_ship = new int[3];
	int position = 0;

	String tag = "ShipLogig";

	String arrayOutOfBounds = "Wrong array size in parameter";

	public int[] getOldSmallShip() {
		return small_ship;
	}

	public int[] getOldMiddleShip() {
		return middle_ship;
	}

	public int[] getBigShip() {
		return big_ship;
	}

	public void setSmallShip(int[] array) {
		if (array.length == 1) {
			this.small_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBounds);
		}
	}

	public void setSmallShipPosition(int position) {
		small_ship[0] = position;
	}

	public void setMiddleShip(int[] array) {
		if (array.length == 2) {
			this.middle_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBounds);
		}
	}

	public void setMiddleShipPosition(int position, int degree) {
		if (degree == 0) {
			middle_ship[0] = position - 1;
			middle_ship[1] = position;
		} else if (degree == 1) {
			middle_ship[0] = position - 8;
			middle_ship[1] = position;
		} else {
			Log.e(tag, "Degree is not correctly set");
		}
	}

	public void setBigShip(int[] array) {
		if (array.length == 3) {
			this.big_ship = array;
		} else {
			throw new ArrayIndexOutOfBoundsException(arrayOutOfBounds);
		}
	}

	public void setBigShipPosition(int position) {
		big_ship[0] = position - 1;
		big_ship[1] = position;
		big_ship[2] = position + 1;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int number) {
		this.position = number;
	}

}
