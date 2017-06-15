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


	String playerFieldWrongSizeErrorMessage = "Parameter is of wrong size";

	/**
	 * Standard constructor which initializes an 'empty' player field
	 */
	public PlayerFieldLogic() {
		playerfield = new String[PLAYERFIELDSIZE];
		Arrays.fill(playerfield, "0");
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
}
