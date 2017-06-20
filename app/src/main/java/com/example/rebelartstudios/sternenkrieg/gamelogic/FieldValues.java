package com.example.rebelartstudios.sternenkrieg.gamelogic;

import java.util.LinkedList;

public final class FieldValues {

    public LinkedList<String> checkAvailabilityList;
    public LinkedList<String> smallShipStringList;
    public LinkedList<String> middleShipStringList;
    public LinkedList<String> bigShipStringList;
    public LinkedList<String> h_list;
    public LinkedList<String> i_list;

    public FieldValues() {
        initialize_H_list();
        initialize_I_list();
        initialiseShipLists();
        initialiseCheckAvailabilityList();
    }

    public void initialiseShipLists() {
        smallShipStringList = new LinkedList<>();
        middleShipStringList = new LinkedList<>();
        bigShipStringList = new LinkedList<>();

        smallShipStringList.add(SETPLAYERPOSITION_SMALL);

        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE1);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE2);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE1R);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE2R);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE1R);
        middleShipStringList.add(SETPLAYERPOSITION_MIDDLE2R);
        middleShipStringList.add(SETFIELDPOSITION_K1);
        middleShipStringList.add(SETFIELDPOSITION_K2);
        middleShipStringList.add(SETFIELDPOSITION_K3);
        middleShipStringList.add(SETFIELDPOSITION_K4);


        bigShipStringList.add(SETFIELDPOSITION_BIG);
        bigShipStringList.add(SETFIELDPOSITION_BIG1);
        bigShipStringList.add(SETFIELDPOSITION_BIG2);
        bigShipStringList.add(SETFIELDPOSITION_BIG3);
        bigShipStringList.add(SETFIELDPOSITION_BIG1R);
        bigShipStringList.add(SETFIELDPOSITION_BIG2R);
        bigShipStringList.add(SETFIELDPOSITION_BIG3R);

        bigShipStringList.add(SETFIELDPOSITION_BIG1R);
        bigShipStringList.add(SETFIELDPOSITION_BIG2R);
        bigShipStringList.add(SETFIELDPOSITION_BIG3R);

        /* ships with armour */
        smallShipStringList.add(SETFIELDPOSITION_J);

        bigShipStringList.add(SETFIELDPOSITION_L1);
        bigShipStringList.add(SETFIELDPOSITION_L2);
        bigShipStringList.add(SETFIELDPOSITION_L3);
        bigShipStringList.add(SETFIELDPOSITION_L4);
        bigShipStringList.add(SETFIELDPOSITION_L5);
        bigShipStringList.add(SETFIELDPOSITION_L6);
    }

    public void initialize_H_list() {
        h_list = new LinkedList<>();
        h_list.add(SETFIELDPOSITION_H1);
        h_list.add(SETFIELDPOSITION_H2);
        h_list.add(SETFIELDPOSITION_H3);
        h_list.add(SETFIELDPOSITION_H4);
    }

    public void initialize_I_list() {
        i_list = new LinkedList<>();
        i_list.add(SETFIELDPOSITION_I1);
        i_list.add(SETFIELDPOSITION_I2);
        i_list.add(SETFIELDPOSITION_I3);
        i_list.add(SETFIELDPOSITION_I4);
        i_list.add(SETFIELDPOSITION_I5);
        i_list.add(SETFIELDPOSITION_I6);

    }

    public final String SETFIELDPOSITION_A = "a";
    public final String SETFIELDPOSITION_B = "b";
    public final String SETFIELDPOSITION_C = "c";

    /**
     * Used to set the position color in the playerfield array to d
     * The character d is used for the position of the small ship
     */
    public final String SETPLAYERPOSITION_SMALL = "d";

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

    public final String SETFIELDPOSITION_EMPTY = "0";
    public final String SETFIELDPOSITION_J = "j";

    public final String SETFIELDPOSITION_K1 = "k1";
    public final String SETFIELDPOSITION_K2 = "k2";
    public final String SETFIELDPOSITION_K3 = "k3";
    public final String SETFIELDPOSITION_K4 = "k4";

    public final String SETFIELDPOSITION_L1 = "l1";
    public final String SETFIELDPOSITION_L2 = "l2";
    public final String SETFIELDPOSITION_L3 = "l3";
    public final String SETFIELDPOSITION_L4 = "l4";
    public final String SETFIELDPOSITION_L5 = "l5";
    public final String SETFIELDPOSITION_L6 = "l6";

    public final String SETFIELDPOSITION_MISS = "1";
    public final String SETFIELDPOSITION_TWO = "2";
    public final String SETFIELDPOSITION_ENEMYHIT = "3";
    public final String SETFIELDPOSITION_PLAYERHIT = "4";
    public final String SETFIELDPOSITION_ENEMYMISS = "5";


    public void initialiseCheckAvailabilityList() {
        checkAvailabilityList = new LinkedList<>();
        checkAvailabilityList.add(SETPLAYERPOSITION_SMALL);
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
     * Used for the degree value in ship positioning
     */
    public final int HORIZONTAL = 0;
    public final int VERTICAL = 1;

    /**
     * The field size of a players field
     */
    public final int FIELDSIZE = 64;
}
