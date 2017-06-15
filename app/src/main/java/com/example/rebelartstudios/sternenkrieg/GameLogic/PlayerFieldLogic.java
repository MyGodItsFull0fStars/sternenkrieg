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

	/**
	 * Sets the position of the player field in the Map Activity class
	 * @param position the position which will we changed to wanted state
	 * @param input
	 */
	public void setPFSmallShipPosition(int position, String input) {
			playerfield[position] = input;
	}

	/**
	 * Uses the degree in the Map Activity class to decide whether the player field will be set horizontally or vertically
	 * For further description see setMiddleShipPositionWithDegree() method
	 *
	 * @param position used to set the position on player field
	 * @param degree   used to decide between horizontal/vertical setting of player field state
	 * @param input    string used to signalize the state
	 */
	public void setPFMiddleShipPosition(int position, int degree, String input) {
		if (degree == 0) {
			setMiddleShipPositionWithDegree(position, 1, input);
		} else if (degree == 1) {
			setMiddleShipPositionWithDegree(position, 8, input);
		}
	}

	/**
	 * Sets the position of the player field with the logic of the middle ship
	 * @param position the position of the player field position
	 * @param sibling used to set the position of the siblings of position
	 *                siblings can be set horizontally or vertically to position
	 * @param input is the string input used to signal, which state the field will get
	 *              character 'e' for setting the middle ship
	 */


	private void setMiddleShipPositionWithDegree(int position, int sibling, String input) {
		playerfield[position - sibling] = input;
		playerfield[position] = input;
	}

	/**
	 * Uses the degree in the Map Activity class to decide whether the player field will be set horizontally or vertically
	 * For further description see setBigShipPositionWithDegree() method
	 *
	 * @param position used to set the position on player field
	 * @param degree   used to decide between horizontal/vertical setting of player field state
	 * @param input    string used to signalize the state
	 */
	public void setPFBigShipPosition(int position, int degree, String input) {
		if (degree == 0) {
			setBigShipPositionWithDegree(position, 1, input);
		} else if (degree == 1) {
			setBigShipPositionWithDegree(position, 8, input);
		}
	}

	/**
	 * Sets the position of the player field with the logic of the big ship
	 *
	 * @param position the position of the middle piece
	 * @param sibling  used to set the position of the siblings of position
	 *                 siblings can be set horizontally or vertically to position
	 * @param input    is the string input used to signal, which state the field will get
	 *                 character 'f' for setting the big ship
	 */
	private void setBigShipPositionWithDegree(int position, int sibling, String input) {
		/* DRY */
		setMiddleShipPositionWithDegree(position, sibling, input);
		playerfield[position + sibling] = input;
	}
}
