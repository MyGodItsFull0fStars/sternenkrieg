package com.example.rebelartstudios.sternenkrieg.GameLogic;

/**
 * Created by christianbauer on 15/06/2017.
 */

public final class PlayerFieldValues {

	/**
	 * Used to set the position color in the playerfield array to d
	 * The character d is used for the position of the small ship
	 */
	public final String SETFIELDPOSITION_D = "d";

	/**
	 * Used to set the position color in the playerfield array to e
	 * The character 'e' is used for the position of the middle ship
	 */
	public final String SETPLAYERPOSITION_E = "e";

	/**
	 * Used to set the position color in the playerfield array to 'f'
	 * The character 'f' is used for the position of the big ship
	 */
	public final String SETFIELDPOSITION_F = "f";

	/**
	 * Used to set the position color in the playerfield array to '0' "ZERO"
	 * The character '0' is used to set the position to an empty field
	 */
	public final String SETFIELDPOSITION_ZERO = "0";


	public final int HORIZONTAL = 0;
	public final int VERTICAL = 1;

	public final int FIELDSIZE = 64;
}
