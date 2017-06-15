package com.example.rebelartstudios.sternenkrieg.GameLogic;

import java.util.Arrays;

/**
 * Created by christianbauer on 15/06/2017.
 * <p>
 * Class is used to set the player field logic in the Map Activity class
 * The player field values are stored in a string array
 * Depending on the info in the single fields, the field will be colored to give feedback to the player
 */

public class PlayerFieldLogic {
	String[] playerfield;
	private final int PLAYERFIELDSIZE = 64;
	PlayerFieldPositionString positionString = new PlayerFieldPositionString();

	/**
	 * Error messages for the Exception output.
	 */
	String playerFieldWrongSizeErrorMessage = "Parameter is of wrong size";
	String playerFieldPositionOutOfRange = "Parameter is not in range of player field";

	/**
	 * Standard constructor which initializes an 'empty' player field
	 */
	public PlayerFieldLogic() {
		playerfield = new String[PLAYERFIELDSIZE];
		Arrays.fill(playerfield, positionString.SETPLAYERPOSITION_ZERO);
	}

	/**
	 * Constructor for using an already defined string array as player field
	 *
	 * @param playerfield used to set the player field to the given string array
	 *                    must be of the same size
	 */
	public PlayerFieldLogic(String[] playerfield) {
		if (playerfield.length == PLAYERFIELDSIZE) {
			this.playerfield = playerfield;
		} else {
			throw new IllegalStateException(playerFieldWrongSizeErrorMessage);
		}
	}

	/**
	 * Getter method for the playerfield variable
	 *
	 * @return the playerfield
	 */
	public String[] getPlayerfield() {
		return playerfield;
	}

	/**
	 * Setter method to set the playerfield variable
	 * Throws exception if the parameter is not of the correct size
	 *
	 * @param playerfield used to set the playerfield variable of the PlayerFieldLogic class
	 */
	public void setPlayerfield(String[] playerfield) {
		if (playerfield.length == PLAYERFIELDSIZE) {
			this.playerfield = playerfield;
		} else {
			throw new IllegalStateException(playerFieldWrongSizeErrorMessage);
		}
	}


	public void setSmallShipPosition(int position, String input) {
		if (position >= 0 && position <= PLAYERFIELDSIZE - 1) {
			playerfield[position] = input;
		} else {
			throw new IllegalStateException(playerFieldPositionOutOfRange);
		}
	}

	public void setMiddleShipPosition(int position, int degree, String input){
		if (degree == 0){
			setMiddleShipPositionWithDegree(position, 1, input);
		} else if (degree == 1){
			setMiddleShipPositionWithDegree(position, 8, input);
		}
	}

	private void setMiddleShipPositionWithDegree(int position, int sibling, String input){
		playerfield[position - sibling] = input;
		playerfield[position] = input;
	}
}
