package com.example.rebelartstudios.sternenkrieg.gamelogic;

import java.util.LinkedList;

public final class PlayerFieldValues {

    public LinkedList<String> checkAvailabilityList;
    public LinkedList<String> smallShipStringList;
    public LinkedList<String> middleShipStringList;
    public LinkedList<String> bigShipStringList;

    public void initialiseShipLists(){
        smallShipStringList.add(SETFIELDPOSITION_SMALL);

        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE1);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE2);

        bigShipStringList.add(SETFIELDPOSITION_BIG);
        bigShipStringList.add(SETFIELDPOSITION_BIG1);
        bigShipStringList.add(SETFIELDPOSITION_BIG2);
        bigShipStringList.add(SETFIELDPOSITION_BIG3);
    }

    public final String SETFIELDPOSITION_A = "a";
    public final String SETFIELDPOSITION_B = "b";
    public final String SETFIELDPOSITION_C = "c";

    /**
     * Used to set the position color in the playerfield array to d
     * The character d is used for the position of the small ship
     */
    public final String SETFIELDPOSITION_SMALL = "d";

    /**
     * Used to set the position color in the playerfield array to e
     * The character 'e' is used for the position of the middle ship
     */
    public final String SETPLAYERPOSITION_MIDDLE = "e";
    public final String SETPLAYERPOSITION_MIDDLE1 = "e1";
    public final String SETPLAYERPOSITION_MIDDLE2 = "e2";

    public final String SETPLAYERPOSITION_MIDDLE1R = "e3";
    public final String SETPLAYERPOSITION_MIDDLE2R = "e4";


    /**
     * Used to set the position color in the playerfield array to 'f'
     * The character 'f' is used for the position of the big ship
     */
    public final String SETFIELDPOSITION_BIG = "f";
    public final String SETFIELDPOSITION_BIG1 = "f1";
    public final String SETFIELDPOSITION_BIG2 = "f2";
    public final String SETFIELDPOSITION_BIG3 = "f3";

    public final String SETFIELDPOSITION_BIG1R = "f4";
    public final String SETFIELDPOSITION_BIG2R = "f5";
    public final String SETFIELDPOSITION_BIG3R = "f6";



    public final String SETFIELDPOSITION_G = "g";

    public final String SETFIELDPOSITION_H1 = "h1";
    public final String SETFIELDPOSITION_H2 = "h2";
    public final String SETFIELDPOSITION_H3 = "h3";
    public final String SETFIELDPOSITION_H4 = "h4";

    public final String SETFIELDPOSITION_I1 = "i1";
    public final String SETFIELDPOSITION_I2 = "i2";
    public final String SETFIELDPOSITION_I3 = "i3";
    public final String SETFIELDPOSITION_I4 = "i4";
    public final String SETFIELDPOSITION_I5 = "i5";
    public final String SETFIELDPOSITION_I6 = "i6";

    public final String SETFIELDPOSITION_MISS = "1";
    public final String SETFIELDPOSITION_TWO = "2";
    public final String SETFIELDPOSITION_ENEMYHIT = "3";
    public final String SETFIELDPOSITION_PLAYERHIT = "4";
    public final String SETFIELDPOSITION_ENEMYMISS = "5";

    public void initialiseCheckAvailabilityList(){
        checkAvailabilityList.add(SETFIELDPOSITION_SMALL);
        checkAvailabilityList.add(SETPLAYERPOSITION_MIDDLE);
        checkAvailabilityList.add(SETPLAYERPOSITION_MIDDLE1);
        checkAvailabilityList.add(SETPLAYERPOSITION_MIDDLE2);
        checkAvailabilityList.add(SETFIELDPOSITION_BIG);
        checkAvailabilityList.add(SETFIELDPOSITION_BIG1);
        checkAvailabilityList.add(SETFIELDPOSITION_BIG2);
        checkAvailabilityList.add(SETFIELDPOSITION_BIG3);
        checkAvailabilityList.add(SETFIELDPOSITION_ENEMYHIT);
        checkAvailabilityList.add(SETFIELDPOSITION_ENEMYMISS);
    }

    /**
     * Used to set the position color in the playerfield array to '0' "ZERO"
     * The character '0' is used to set the position to an empty field
     */
    public final String SETFIELDPOSITION_EMPTY = "0";


    /**
     * Used for the degree value in ship positioning
     */
    public final int HORIZONTAL = 0;
    public final int VERTICAL = 1;

    /**
     * The field size of a players field
     */
    public final int FIELDSIZE = 64;
}
