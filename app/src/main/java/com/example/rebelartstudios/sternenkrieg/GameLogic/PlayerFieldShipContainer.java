package com.example.rebelartstudios.sternenkrieg.GameLogic;

/**
 * Created by christianbauer on 16/06/2017.
 * <p>
 * Container class for
 */

public class PlayerFieldShipContainer {
    PlayerFieldLogic playerFieldLogic;
    ShipLogic shipLogic;
    PlayerFieldPositionString fieldStrings;

    public PlayerFieldShipContainer() {
        playerFieldLogic = new PlayerFieldLogic();
        shipLogic = new ShipLogic();
        fieldStrings = new PlayerFieldPositionString();
    }


    public PlayerFieldShipContainer(PlayerFieldLogic playerFieldLogic, ShipLogic shipLogic) {
        this.playerFieldLogic = playerFieldLogic;
        this.shipLogic = shipLogic;
        fieldStrings = new PlayerFieldPositionString();
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
            for (int position: shipArray) {
                playerFieldLogic.setPFSmallShipPosition(position, fieldStrings.SETFIELDPOSITION_ZERO);
            }
        }
    }


}
