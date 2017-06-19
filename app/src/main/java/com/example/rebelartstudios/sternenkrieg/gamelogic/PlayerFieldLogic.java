package com.example.rebelartstudios.sternenkrieg.gamelogic;

import java.util.Arrays;

/**
 * Class is used to set the player field logic in the Map Activity class
 * The player field values are stored in a string array
 * Depending on the info in the single fields, the field will be colored to give feedback to the player
 */

public class PlayerFieldLogic {
    public String[] playerField;
    private final int PLAYERFIELDSIZE = 64;
    PlayerFieldValues fieldStrings = new PlayerFieldValues();

    private final String ONE = "1";
    private final String TWO = "2";
    private final String THREE = "3";


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
     *
     *  CURRENTLY NOT USED
     */
//    public PlayerFieldLogic(String[] playerfield) {
//        if (playerfield.length == PLAYERFIELDSIZE) {
//            setPlayerField(playerfield);
//        } else {
//            throw new IllegalStateException(playerFieldWrongSizeErrorMessage);
//        }
//    }


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
        Arrays.fill(playerField, fieldStrings.SETFIELDPOSITION_EMPTY);
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
     * For further description see setMiddleShipPositionWithSiblingIndex() method
     *
     * @param position used to set the position on player field
     * @param degree   used to decide between horizontal/vertical setting of player field state
     * @param input    string used to signalize the state
     */
    public void setPFMiddleShipPositionWithSiblingIndex(int position, int degree, String input) {
        setMiddleShipPositionWithSiblingIndex(position, getSibling(degree), input);
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
    private void setMiddleShipPositionWithSiblingIndex(int position, int sibling, String input) {
        if (inRange(position) && inRange(position - sibling)) {
            playerField[position - sibling] = input + ONE;
            playerField[position] = input + TWO;
        } else {
            throw new IllegalStateException(playerFieldPositionOutOfRange);
        }
    }

    /**
     * Uses the degree in the Map Activity class to decide whether the player field will be set horizontally or vertically
     * For further description see setBigShipPositionWithSiblingIndex() method
     *
     * @param position used to set the position on player field
     * @param degree   used to decide between horizontal/vertical setting of player field state
     * @param input    string used to signalize the state
     */
    public void setPFBigShipPositionWithSiblingIndex(int position, int degree, String input) {
        setBigShipPositionWithSiblingIndex(position, getSibling(degree), input);
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
    private void setBigShipPositionWithSiblingIndex(int position, int sibling, String input) {
        /* DRY */
        setMiddleShipPositionWithSiblingIndex(position, sibling, input);
        if (inRange(position + sibling)) {
            playerField[position + sibling] = input + THREE;
        } else throw new IllegalArgumentException(playerFieldPositionOutOfRange);
    }

    /**
     * Checks if the given parameter is in range of the player field
     *
     * @param position which will be checked
     * @return TRUE if position is in the field, FALSE if position is out of range
     */
    private boolean inRange(int position) {
        return position >= 0 && position < PLAYERFIELDSIZE;
    }

    /**
     * Returns the string at the position of given parameter
     * inRange() checks if parameter is in the range of player field
     *
     * @param position used to find the string in the array
     * @return if successful, returns the string at array position
     */
    public String getStringInPosition(int position) {
        if (inRange(position)) {
            return getPlayerField()[position];
        } else {
            throw new IllegalArgumentException(playerFieldPositionOutOfRange);
        }
    }

    /**
     * @param degree parameter which is either horizontal = 0 or vertical = 1
     * @return if horizontal return 1, if vertical, return 8, else throw exception
     */
    private int getSibling(int degree) {
        if (degree == fieldStrings.HORIZONTAL) {
            return 1;
        } else if (degree == fieldStrings.VERTICAL) {
            return 8;
        } else {
            throw new IllegalArgumentException("Given degree is not allowed");
        }
    }
}
