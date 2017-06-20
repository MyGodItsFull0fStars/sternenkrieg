package com.example.rebelartstudios.sternenkrieg.gamelogic;

import android.util.Log;

import com.example.rebelartstudios.sternenkrieg.res.ErrorMessages;

import java.util.Arrays;

/**
 * Class is used to set the player field logic in the Map Activity class`
 * The player field values are stored in a string array
 * Depending on the info in the single fields, the field will be colored to give feedback to the player
 */

public class PlayerFieldLogic {
    String[] playerField;
    private final int PLAYER_FIELD_SIZE = 64;
    private FieldValues fieldStrings = new FieldValues();

    private final String ONE = "1";
    private final String TWO = "2";
    private final String THREE = "3";
    private final String FOUR = "4";
    private final String FIVE = "5";
    private final String SIX = "6";
    String tag = "PlayerfieldLogic";


    /**
     * Standard constructor which initializes an 'empty' player field
     */
    public PlayerFieldLogic() {
        initializePlayerField();
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
        Log.i(tag, "setPlayerField");
        if (playerfield != null && playerfield.length == PLAYER_FIELD_SIZE) {
            this.playerField = playerfield;
        } else {
            throw new IllegalArgumentException(ErrorMessages.PLAYERFIELD_WRONG_SIZE);
        }
    }

    public void initializePlayerField() {
        Log.i(tag, "initPlayerField");
        if (playerField == null) {
            playerField = new String[PLAYER_FIELD_SIZE];
        }
        Arrays.fill(playerField, fieldStrings.SETFIELDPOSITION_EMPTY);
    }

    /**
     * Sets the position of the player field in the Map Activity class
     *
     * @param position the position which will we changed to wanted state
     * @param input    string input used to set the value at position
     */
    public void setSmallShipPosition(int position, String input) {
        Log.i(tag, "setSmallShipPosition");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
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
    public void setMiddleShipPositionWithSiblingIndex(int position, int degree, String input) {
        Log.i(tag, "setMiddleShipPosWithSilbingIndex");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        if (inRange(position - getSibling(degree)) && inRange(position)) {
            if (degree == fieldStrings.HORIZONTAL) {
                playerField[position - getSibling(degree)] = input + ONE;
                playerField[position] = input + TWO;
            } else if (degree == fieldStrings.VERTICAL) {
                playerField[position - getSibling(degree)] = input + THREE;
                playerField[position] = input + FOUR;
            }
        }
    }


    public void setMiddleShipPositionToString(int position, int degree, String input) {
        Log.i(tag, "setMiddleShipPositionToString(");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        if (inRange(position) && inRange(position - getSibling(degree))) {
            playerField[position - getSibling(degree)] = input;
            playerField[position] = input;
        }
    }

    public void setBigShipPositionToString(int position, int degree, String input) {
        Log.i(tag, "setBigShipPositionToStrin");
        setMiddleShipPositionToString(position, degree, input);
        if (inRange(position + getSibling(degree))) {
            playerField[position + getSibling(degree)] = input;
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
    public void setBigShipPositionWithSiblingIndex(int position, int degree, String input) {
        Log.i(tag, "setBigShipPositionWithSiblingIndex");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        if (inRange(position - getSibling(degree)) && inRange(position) && inRange(position + getSibling(degree))) {
            if (degree == fieldStrings.HORIZONTAL) {
                playerField[position - getSibling(degree)] = input + ONE;
                playerField[position] = input + TWO;
                playerField[position + getSibling(degree)] = input + THREE;
            } else if (degree == fieldStrings.VERTICAL) {
                playerField[position - getSibling(degree)] = input + FOUR;
                playerField[position] = input + FIVE;
                playerField[position + getSibling(degree)] = input + SIX;
            }
        }

    }


    /**
     * Checks if the given parameter is in range of the player field
     *
     * @param position which will be checked
     * @return TRUE if position is in the field, FALSE if position is out of range
     */
    public boolean inRange(int position) {
        Log.i(tag, "inRange");
        if (position >= 0 && position < fieldStrings.FIELDSIZE) {
            return true;
        } else {
            throw new IllegalArgumentException(ErrorMessages.POSITION_OUT_OF_RANGE);
        }

    }

    /**
     * Returns the string at the position of given parameter
     * inRange() checks if parameter is in the range of player field
     *
     * @param position used to find the string in the array
     * @return if successful, returns the string at array position
     */
    public String getStringInPosition(int position) {
        Log.i(tag, "getStringInPosition");
        if (inRange(position)) {
            return getPlayerField()[position];

            // can't be reached, because of thrown Exception in inRange()
        } else return null;
    }

    /**
     * @param degree parameter which is either horizontal = 0 or vertical = 1
     * @return if horizontal return 1, if vertical, return 8, else throw exception
     */
    public int getSibling(int degree) {
        Log.i(tag, "getSibling");
        if (degree == fieldStrings.HORIZONTAL) {
            return 1;
        } else if (degree == fieldStrings.VERTICAL) {
            return 8;
        } else {
            throw new IllegalArgumentException(ErrorMessages.DEGREE_NOT_DEFINED);
        }
    }

    public boolean middleShipFieldContainsString(int position, int degree, String input) {
        Log.i(tag, "middleShipFieldContainsString");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        return getStringInPosition(position).equals(input) && getStringInPosition(position - getSibling(degree)).equals(input);
    }

    public boolean bigShipFieldContainsString(int position, int degree, String input) {
        Log.i(tag, "bigShipFieldContainsString");
        if (input == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        return middleShipFieldContainsString(position, degree, input) && getStringInPosition(position).equals(input);
    }


}
