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
	private String[] playerField;
	private final int PLAYERFIELDSIZE = 64;
	PlayerFieldPositionString fieldStrings = new PlayerFieldPositionString();

	/**
	 * Error messages for the Exception output.
	 */
	String playerFieldWrongSizeErrorMessage = "Parameter is of wrong size";
	String playerFieldPositionOutOfRange = "Parameter is not in range of player field";

	/**
	 * Standard constructor which initializes an 'empty' player field
	 */
	public PlayerFieldLogic() {
		initializePlayerField();
	}

	/**
	 * Constructor for using an already defined string array as player field
	 *
	 * @param playerfield used to set the player field to the given string array
	 *                    must be of the same size
	 */
	public PlayerFieldLogic(String[] playerfield) {
		if (playerfield.length == PLAYERFIELDSIZE) {
			setPlayerField(playerfield);
		} else {
			throw new IllegalStateException(playerFieldWrongSizeErrorMessage);
		}
	}

	/**
	 * Getter method for the playerField variable
	 *
	 * @return the playerField
	 */
	public String[] getPlayerField() {
		return playerField;
	}

	/**
	 * Setter method to set the playerField variable
	 * Throws exception if the parameter is not of the correct size
	 *
	 * @param playerfield used to set the playerField variable of the PlayerFieldLogic class
	 */
	public void setPlayerField(String[] playerfield) {
		if (playerfield.length == PLAYERFIELDSIZE) {
			this.playerField = playerfield;
		} else {
			throw new IllegalStateException(playerFieldWrongSizeErrorMessage);
		}
	}

	public void initializePlayerField() {
		if (playerField == null) {
			playerField = new String[PLAYERFIELDSIZE];
		}
		Arrays.fill(playerField, fieldStrings.SETFIELDPOSITION_ZERO);
	}

	/**
	 * Sets the position of the player field in the Map Activity class
	 *
	 * @param position the position which will we changed to wanted state
	 * @param input
	 */
	public void setPFSmallShipPosition(int position, String input) {
		if (inRange(position)) {
			playerField[position] = input;
		}

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
		if (degree == fieldStrings.HORIZONTAL) {
			setMiddleShipPositionWithDegree(position, 1, input);
		} else if (degree == fieldStrings.VERTICAL) {
			setMiddleShipPositionWithDegree(position, 8, input);
		}
	}

	/**
	 * Sets the position of the player field with the logic of the middle ship
	 *
	 * @param position the position of the player field position
	 * @param sibling  used to set the position of the siblings of position
	 *                 siblings can be set horizontally or vertically to position
	 * @param input    is the string input used to signal, which state the field will get
	 *                 character 'e' for setting the middle ship
	 */
	private void setMiddleShipPositionWithDegree(int position, int sibling, String input) {
		if (inRange(position)) {
			playerField[position - sibling] = input;
			playerField[position] = input;
		} else {
			throw new IllegalStateException(playerFieldPositionOutOfRange);
		}
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
		if (degree == fieldStrings.HORIZONTAL) {
			setBigShipPositionWithDegree(position, 1, input);
		} else if (degree == fieldStrings.VERTICAL) {
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
		playerField[position + sibling] = input;
	}

	/**
	 * Checks if the given parameter is in range of the player field
	 *
	 * @param position which will be checked
	 * @return TRUE if position is in the field, FALSE if position is out of range
	 */
	private boolean inRange(int position) {
		if (position >= 0 && position < PLAYERFIELDSIZE) {
			return true;
		} else return false;
	}

	public boolean isPlayerFieldPositionE(int position){
		if (fieldStrings.SETPLAYERPOSITION_E.equals(playerField[position])) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPlayerFieldPositionF(int position){
		if (fieldStrings.SETFIELDPOSITION_F.equals(playerField[position])){
			return true;
		} else {
			return false;
		}
	}
}
