package com.example.rebelartstudios.sternenkrieg.GameLogic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by christianbauer on 16/06/2017.
 * <p>
 * Container class for ShipLogic and PlayerFieldLogic class
 * <p>
 * A lot of things are used simultaneously an can be put in one class.
 */

public class PlayerFieldShipContainer {
    PlayerFieldLogic playerFieldLogic;
    ShipLogic shipLogic;
    PlayerFieldPositionString fieldValues;
    ArrayList<Integer> failures_right = new ArrayList<>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
    ArrayList<Integer> failures_left = new ArrayList<>(Arrays.asList(8, 16, 24, 32, 40, 48, 56));

    public PlayerFieldShipContainer() {
        playerFieldLogic = new PlayerFieldLogic();
        shipLogic = new ShipLogic();
        fieldValues = new PlayerFieldPositionString();
    }


    public PlayerFieldShipContainer(PlayerFieldLogic playerFieldLogic, ShipLogic shipLogic) {
        this.playerFieldLogic = playerFieldLogic;
        this.shipLogic = shipLogic;
        fieldValues = new PlayerFieldPositionString();
    }

    public void setSmallShipContainer(int position, String input) {
        shipLogic.setSmallShipPosition(position);
        playerFieldLogic.setPFSmallShipPosition(position, input);
    }

    public void setMiddleShipContainer(int position, int degree, String input) {
        shipLogic.setMiddleShipPosition(position, degree);
        playerFieldLogic.setPFMiddleShipPosition(position, degree, input);
    }

    public void setBigShipContainer(int position, int degree, String input) {
        shipLogic.setBigShipPosition(position, degree);
        playerFieldLogic.setPFBigShipPosition(position, degree, input);
    }

    public void delete(int[] shipArray) {
        if (shipArray != null && shipArray.length >= 0 && shipArray.length <= 3) {
            for (int position : shipArray) {
                playerFieldLogic.setPFSmallShipPosition(position, fieldValues.SETFIELDPOSITION_ZERO);
            }
        }
    }

    public ShipLogic getShipLogic() {
        return shipLogic;
    }

    public PlayerFieldLogic getPlayerFieldLogic() {
        return playerFieldLogic;
    }

    public boolean inRange(int position, int start, int end) {
        if (position >= start && position <= end) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPosition(int position, int whichShip, int degree) {

        if (!inRange(position, 0, 63)) {
            throw new IllegalArgumentException("Position is out of range");
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
            if (whichShip == shipLogic.MIDDLE_SHIP_ID && !inRange(position, 8, 63)) {
                return false;
            } else if (whichShip == shipLogic.BIG_SHIP_ID && !inRange(position, 8, 55)) {
                return false;
            }
        }


        if (whichShip == shipLogic.BIG_SHIP_ID) {
            if (playerFieldBigShipContainsString(position, degree, fieldValues.SETFIELDPOSITION_D) ||
                    playerFieldBigShipContainsString(position, degree, fieldValues.SETPLAYERPOSITION_E)) {
                return false;
            }
        }

        else if (whichShip == shipLogic.MIDDLE_SHIP_ID){
            if (playerFieldMiddleShipContainsString(position, degree, fieldValues.SETFIELDPOSITION_D) ||
                    playerFieldMiddleShipContainsString(position, degree, fieldValues.SETFIELDPOSITION_F)){
                return false;
            }
        }

        return true;
    }


    public boolean playerFieldBigShipContainsString(int position, int degree, String input) {
        return input.equals(playerFieldLogic.playerField[position - shipLogic.getSibling(degree)])
                || input.equals(playerFieldLogic.playerField[position])
                || input.equals(playerFieldLogic.playerField[position + shipLogic.getSibling(degree)]);
    }

    public boolean playerFieldMiddleShipContainsString(int position, int degree, String input) {
        return input.equals(playerFieldLogic.playerField[position - shipLogic.getSibling(degree)])
                || input.equals(playerFieldLogic.playerField[position]);
    }

    /*public boolean check_position(int pos, int size, int degree) {


		//linkes und rechtes Ende der Map
		if (degree == 180 || degree == 0) {
			if (size == 1) {
				if (failures_right.contains(pos - 1) || failures_left.contains(pos) || pos < 1 || pos > 62) {
					return false;
				}
			} else if (size == 2) {
				if (failures_right.contains(pos - 1) || failures_right.contains(pos) || failures_left.contains(pos) || pos < 1 || pos > 62) {
					return false;
				}
			}
		}
		//Oben und Unten

		if (degree == 90 || degree == 270) {
			if (size == 1) {
				if (pos < 8 || pos > 63) {
					return false;
				}
			} else if (size == 2) {
				if (pos < 8 || pos > 55) {
					return false;
				}
			}
		}

		if (which_ship == shipLogic.BIG_SHIP_ID) {
			if (degree == fieldValues.HORIZONTAL) {
				if (playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos + 1].equals(fieldValues.SETFIELDPOSITION_D)
						|| playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos + 1].equals(fieldValues.SETPLAYERPOSITION_E)) {
					return false;
				}
			} else {
				if (playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos + 8].equals(fieldValues.SETFIELDPOSITION_D)
						|| playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos + 8].equals(fieldValues.SETPLAYERPOSITION_E)) {
					return false;
				}
			}
		} else if (which_ship == shipLogic.MIDDLE_SHIP_ID) {
			if (degree == fieldValues.HORIZONTAL) {
				if (playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D)
						|| playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_F) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_F)) {
					return false;
				}
			} else {
				if (playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D)
						|| playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_F) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_F)) {
					return false;
				}
			}
		}
		return true;
	}*/

}
