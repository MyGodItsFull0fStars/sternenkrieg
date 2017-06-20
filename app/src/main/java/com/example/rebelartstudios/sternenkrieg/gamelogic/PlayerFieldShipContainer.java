package com.example.rebelartstudios.sternenkrieg.gamelogic;

import android.util.Log;

import com.example.rebelartstudios.sternenkrieg.ErrorMessages;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Container class for ShipLogic and PlayerFieldLogic class
 * A lot of things are used simultaneously an can be put in one class.
 */

public class PlayerFieldShipContainer {
    private PlayerFieldLogic playerFieldLogic;
    private ShipLogic shipLogic;
    private String tag = "FieldShipContainer";
    private FieldValues fieldValues;
    private ArrayList<Integer> failures_right = new ArrayList<>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
    private ArrayList<Integer> failures_left = new ArrayList<>(Arrays.asList(8, 16, 24, 32, 40, 48, 56));

    /**
     * Standard constructor
     */
    public PlayerFieldShipContainer() {
        playerFieldLogic = new PlayerFieldLogic();
        shipLogic = new ShipLogic();
        fieldValues = new FieldValues();
    }


    /**
     * Constructor with PlayerFieldLogic and ShipLogic parameter
     *
     * @param playerFieldLogic used to initialize the PlayerFieldLogic class
     * @param shipLogic        used to initialize the ShipLogic class
     */
    public PlayerFieldShipContainer(PlayerFieldLogic playerFieldLogic, ShipLogic shipLogic) {
        Log.i(tag, "Crash");
        if (playerFieldLogic == null || shipLogic == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }
        this.playerFieldLogic = playerFieldLogic;
        this.shipLogic = shipLogic;
        fieldValues = new FieldValues();
    }

    /**
     * Container method for setting the position of the small ship in both classes
     *
     * @param position used to set at given position
     * @param input    string which will be written in the player field position
     */
    public void setSmallShipContainer(int position, String input) {
        Log.i(tag, "Crash");
        shipLogic.setSmallShipPosition(position);
        playerFieldLogic.setSmallShipPosition(position, input);
    }

    /**
     * Container method for setting the position of the middle ship in both classes
     *
     * @param position used to set at given position
     * @param degree   used to determine if ship will be positioned horizontal/vertical -> 0/1
     * @param input    string which will be written in the player field position
     */
    public void setMiddleShipContainer(int position, int degree, String input) {
        Log.i(tag, "Crash");
        shipLogic.setMiddleShipPosition(position, degree);
        playerFieldLogic.setMiddleShipPositionWithSiblingIndex(position, degree, input);
    }

    /**
     * Container method for setting the position of the big ship in both classes
     *
     * @param position used to set at given position
     * @param degree   used to determine if ship will be positioned horizontal/vertical -> 0/1
     * @param input    string which will be written in the player field position
     */
    public void setBigShipContainer(int position, int degree, String input) {
        shipLogic.setBigShipPosition(position, degree);
        playerFieldLogic.setBigShipPositionWithSiblingIndex(position, degree, input);
    }

    /**
     * Deletes the positions in the player field with given int array
     *
     * @param shipArray each position will be used to empty the player field
     */
    public void delete(int[] shipArray) {
        if (shipArray == null) {
            throw new IllegalArgumentException(ErrorMessages.NULL_PARAMETER);
        }

        if (shipArray.length > 0 && shipArray.length <= 3) {
            Log.i(tag, "Crash");
            if (shipArray.length >= 0 && shipArray.length <= 3) {
                for (int position : shipArray) {
                    playerFieldLogic.setSmallShipPosition(position, fieldValues.SETFIELDPOSITION_EMPTY);
                }
            }
        }
    }

    /**
     * @param position the position which will be checked
     * @param input    the input which will be checked with the position
     * @return if the value at position and the input string are equal, then return true, otherwise false
     */

    public boolean positionContainsString(int position, String input) {
        Log.i(tag, "Crash");
        return input.equals(playerFieldLogic.getPlayerField()[position]);
    }

    /**
     * Getter method for ShipLogic class
     *
     * @return ShipLogic class
     */
    public ShipLogic getShipLogic() {
        return shipLogic;
    }

    /**
     * Getter method for PlayerFieldLogic class
     *
     * @return PlayerFieldLogic class
     */
    public PlayerFieldLogic getPlayerFieldLogic() {
        return playerFieldLogic;
    }

    /**
     * @param position the position which will be checked
     * @param start    the starting boundaries
     * @param end      the ending boundaries
     * @return returns true if position is in the boundaries, false if out of boundaries
     */
    public boolean inRange(int position, int start, int end) {
        return position >= start && position <= end;
    }

    /**
     * @param position  the position which will be checked with its siblings
     * @param whichShip identifies the ship
     * @param degree    used to check the boundaries for horizontal/vertical ship placement
     * @return true if the position is in the boundaries, false if out of boundaries
     */
    public boolean checkPosition(int position, int whichShip, int degree) {
        Log.i(tag, "Crash");

        if (!inRange(position, 0, 63)) {
            throw new IllegalArgumentException(ErrorMessages.POSITION_OUT_OF_RANGE);
        }


        if (degree == fieldValues.HORIZONTAL) {
            if (whichShip == shipLogic.MIDDLE_SHIP_ID) {
                if (
                        failures_right.contains(position - 1) ||
                                failures_left.contains(position) ||
                                !inRange(position, 1, 62)) {
                    return false;
                }
            } else if (whichShip == shipLogic.BIG_SHIP_ID) {
                if (
                        failures_right.contains(position - 1) ||
                                failures_right.contains(position) ||
                                failures_left.contains(position) ||
                                !inRange(position, 1, 62)
                        ) {
                    return false;
                }
            }
        }

        if (degree == fieldValues.VERTICAL) {
            Log.i(tag, "Crash");
            if (whichShip == shipLogic.MIDDLE_SHIP_ID && !inRange(position, 8, 63)) {
                return false;
            } else if (whichShip == shipLogic.BIG_SHIP_ID && !inRange(position, 8, 55)) {
                return false;
            }
        }


        if (whichShip == shipLogic.BIG_SHIP_ID) {
            Log.i(tag, "Crash");
            if (playerFieldBigShipContainsString(position, degree, fieldValues.SETPLAYERPOSITION_SMALL) ||
                    playerFieldBigShipContainsString(position, degree, fieldValues.SETPLAYERPOSITION_MIDDLE)) {
                return false;
            }
        } else if (whichShip == shipLogic.MIDDLE_SHIP_ID) {
            if (playerFieldMiddleShipContainsString(position, degree, fieldValues.SETPLAYERPOSITION_SMALL) ||
                    playerFieldMiddleShipContainsString(position, degree, fieldValues.SETFIELDPOSITION_BIG)) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param position the position with siblings which will we checked to contain the input string in parameter
     * @param degree   to check the siblings horizontally/vertically
     * @param input    the input string checked with the string at position
     * @return if input string is equal to string at position or siblings, return true, otherwise return false
     */
    private boolean playerFieldBigShipContainsString(int position, int degree, String input) {
        Log.i(tag, "Crash");
        return input.equals(playerFieldLogic.playerField[position - shipLogic.getSibling(degree)])
                || input.equals(playerFieldLogic.playerField[position])
                || input.equals(playerFieldLogic.playerField[position + shipLogic.getSibling(degree)]);
    }

    /**
     * @param position the position with siblings which will we checked to contain the input string in parameter
     * @param degree   to check the siblings horizontally/vertically
     * @param input    the input string checked with the string at position
     * @return if input string is equal to string at position or siblings, return true, otherwise return false
     */
    private boolean playerFieldMiddleShipContainsString(int position, int degree, String input) {
        Log.i(tag, "Crash");
        return input.equals(playerFieldLogic.playerField[position - shipLogic.getSibling(degree)])
                || input.equals(playerFieldLogic.playerField[position]);
    }

    /**
     * Method used in Map Activity class for the drag and drop function to set the ships on the field
     *
     * @param position  the position, at which the ship will be placed
     * @param whichShip the identifier for the ship
     * @param degree    to determine if ship will be placed horizontally/vertically
     */
    public void setShipOnPlayerFieldWithDragAndDrop(int position, int whichShip, int degree) {
        Log.i(tag, "Crash");

        if (whichShip == shipLogic.SMALL_SHIP_ID                // Small ship
                && checkPosition(position, whichShip, degree)   // if small ship is in boundaries
                && playerFieldAtPositionEmpty(position, whichShip, degree))        // if field is empty

        {
            delete(shipLogic.getSmallShipArray());
            setSmallShipContainer(position, fieldValues.SETPLAYERPOSITION_SMALL);
            getShipLogic().setSmallShipIsSetOnField(true);
        }


        if (whichShip == shipLogic.MIDDLE_SHIP_ID &&            // Middle ship
                checkPosition(position, whichShip, degree) &&   // if middle ship is in boundaries
                playerFieldAtPositionEmpty(position, whichShip, degree)) {         // if field is empty

            delete(shipLogic.getMiddleShipArray());
            setMiddleShipContainer(position, degree, fieldValues.SETPLAYERPOSITION_MIDDLE);
            shipLogic.setMiddleShipIsSetOnField(true);
        }

        if (whichShip == shipLogic.BIG_SHIP_ID                  // Big ship
                && checkPosition(position, whichShip, degree)   // If big ship is in boundaries
                && playerFieldAtPositionEmpty(position, whichShip, degree))        // if field is empty

        {
            delete(shipLogic.getBigShipArray());
            setBigShipContainer(position, degree, fieldValues.SETFIELDPOSITION_BIG);
            getShipLogic().setBigShipIsSetOnField(true);
        }
    }

    /**
     * Used to determine if the positions of a ship are all empty
     *
     * @param position  the center position of a ship
     * @param whichShip the identifier, used to declare the ship
     * @param degree    used to declare if ship position is horizontal/vertical
     * @return returns true, if all fields of a ship are empty, false if at least one position is not empty
     */
    private boolean playerFieldAtPositionEmpty(int position, int whichShip, int degree) {
        Log.i(tag, "Crash");
        if (whichShip == shipLogic.SMALL_SHIP_ID) {
            return getPlayerFieldLogic().getStringInPosition(position).equals(fieldValues.SETFIELDPOSITION_EMPTY);
        } else if (whichShip == shipLogic.MIDDLE_SHIP_ID) {
            return getPlayerFieldLogic().middleShipFieldContainsString(position, degree, fieldValues.SETFIELDPOSITION_EMPTY);
        } else if (whichShip == shipLogic.BIG_SHIP_ID) {
            return getPlayerFieldLogic().bigShipFieldContainsString(position, degree, fieldValues.SETFIELDPOSITION_EMPTY);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ILLEGAL_SHIP_ID);
        }
    }

}
