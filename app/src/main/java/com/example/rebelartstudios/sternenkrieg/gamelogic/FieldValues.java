package com.example.rebelartstudios.sternenkrieg.gamelogic;

import java.util.LinkedList;

public final class FieldValues {

    public LinkedList<String> smallShipStringList;
    public LinkedList<String> middleShipStringList;
    public LinkedList<String> bigShipStringList;
    public LinkedList<String> smallShipArmourStringList;
    public LinkedList<String> middleShipArmourStringList;
    public LinkedList<String> bigShipArmourStringList;
    public LinkedList<String> h_list;
    public LinkedList<String> i_list;

    public FieldValues() {
        initialize_H_list();
        initialize_I_list();
        initialiseShipLists();
    }

    public void initialiseShipLists() {
        smallShipStringList = new LinkedList<>();
        middleShipStringList = new LinkedList<>();
        bigShipStringList = new LinkedList<>();

        smallShipArmourStringList = new LinkedList<>();
        middleShipArmourStringList = new LinkedList<>();
        bigShipArmourStringList = new LinkedList<>();

        smallShipStringList.add(SET_PLAYER_POSITION_SMALL);

        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE1);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE2);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE1R);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE2R);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE1R);
        middleShipStringList.add(SET_PLAYER_POSITION_MIDDLE2R);
        middleShipStringList.add(SET_FIELD_POSITION_K1);
        middleShipStringList.add(SET_FIELD_POSITION_K2);
        middleShipStringList.add(SET_FIELD_POSITION_K3);
        middleShipStringList.add(SET_FIELD_POSITION_K4);


        bigShipStringList.add(SET_FIELD_POSITION_BIG);
        bigShipStringList.add(SET_FIELD_POSITION_BIG1);
        bigShipStringList.add(SET_FIELD_POSITION_BIG2);
        bigShipStringList.add(SET_FIELD_POSITION_BIG3);
        bigShipStringList.add(SET_FIELD_POSITION_BIG1R);
        bigShipStringList.add(SET_FIELD_POSITION_BIG2R);
        bigShipStringList.add(SET_FIELD_POSITION_BIG3R);

        bigShipStringList.add(SET_FIELD_POSITION_BIG1R);
        bigShipStringList.add(SET_FIELD_POSITION_BIG2R);
        bigShipStringList.add(SET_FIELD_POSITION_BIG3R);

        /* ships with armour */
        smallShipArmourStringList.add(SET_FIELD_POSITION_J);

        middleShipArmourStringList.add(SET_FIELD_POSITION_K1);
        middleShipArmourStringList.add(SET_FIELD_POSITION_K2);
        middleShipArmourStringList.add(SET_FIELD_POSITION_K3);
        middleShipArmourStringList.add(SET_FIELD_POSITION_K4);

        bigShipArmourStringList.add(SET_FIELD_POSITION_L1);
        bigShipArmourStringList.add(SET_FIELD_POSITION_L2);
        bigShipArmourStringList.add(SET_FIELD_POSITION_L3);
        bigShipArmourStringList.add(SET_FIELD_POSITION_L4);
        bigShipArmourStringList.add(SET_FIELD_POSITION_L5);
        bigShipArmourStringList.add(SET_FIELD_POSITION_L6);
    }

    public void initialize_H_list() {
        h_list = new LinkedList<>();
        h_list.add(SET_FIELD_POSITION_H1);
        h_list.add(SET_FIELD_POSITION_H2);
        h_list.add(SET_FIELD_POSITION_H3);
        h_list.add(SET_FIELD_POSITION_H4);
    }

    public void initialize_I_list() {
        i_list = new LinkedList<>();
        i_list.add(SET_FIELD_POSITION_I1);
        i_list.add(SET_FIELD_POSITION_I2);
        i_list.add(SET_FIELD_POSITION_I3);
        i_list.add(SET_FIELD_POSITION_I4);
        i_list.add(SET_FIELD_POSITION_I5);
        i_list.add(SET_FIELD_POSITION_I6);

    }

    public final String SET_FIELD_POSITION_A = "a";
    public final String SET_FIELD_POSITION_B = "b";
    public final String SET_FIELD_POSITION_C = "c";

    /**
     * Used to set the position color in the playerfield array to d
     * The character d is used for the position of the small ship
     */
    public final String SET_PLAYER_POSITION_SMALL = "d";

    /**
     * Used to set the position color in the playerfield array to e
     * The character 'e' is used for the position of the middle ship
     */
    public final String SET_PLAYER_POSITION_MIDDLE = "e";
    public final String SET_PLAYER_POSITION_MIDDLE1 = "e1";
    public final String SET_PLAYER_POSITION_MIDDLE2 = "e2";

    public final String SET_PLAYER_POSITION_MIDDLE1R = "e3";
    public final String SET_PLAYER_POSITION_MIDDLE2R = "e4";


    /**
     * Used to set the position color in the playerfield array to 'f'
     * The character 'f' is used for the position of the big ship
     */
    public final String SET_FIELD_POSITION_BIG = "f";
    public final String SET_FIELD_POSITION_BIG1 = "f1";
    public final String SET_FIELD_POSITION_BIG2 = "f2";
    public final String SET_FIELD_POSITION_BIG3 = "f3";

    public final String SET_FIELD_POSITION_BIG1R = "f4";
    public final String SET_FIELD_POSITION_BIG2R = "f5";
    public final String SET_FIELD_POSITION_BIG3R = "f6";


    public final String SET_FIELD_POSITION_G = "g";

    public final String SET_FIELD_POSITION_H1 = "h1";
    public final String SET_FIELD_POSITION_H2 = "h2";
    public final String SET_FIELD_POSITION_H3 = "h3";
    public final String SET_FIELD_POSITION_H4 = "h4";

    public final String SET_FIELD_POSITION_I1 = "i1";
    public final String SET_FIELD_POSITION_I2 = "i2";
    public final String SET_FIELD_POSITION_I3 = "i3";
    public final String SET_FIELD_POSITION_I4 = "i4";
    public final String SET_FIELD_POSITION_I5 = "i5";
    public final String SET_FIELD_POSITION_I6 = "i6";

    public final String SET_FIELD_POSITION_EMPTY = "0";
    public final String SET_FIELD_POSITION_J = "j";

    public final String SET_FIELD_POSITION_K1 = "k1";
    public final String SET_FIELD_POSITION_K2 = "k2";
    public final String SET_FIELD_POSITION_K3 = "k3";
    public final String SET_FIELD_POSITION_K4 = "k4";

    public final String SET_FIELD_POSITION_L1 = "l1";
    public final String SET_FIELD_POSITION_L2 = "l2";
    public final String SET_FIELD_POSITION_L3 = "l3";
    public final String SET_FIELD_POSITION_L4 = "l4";
    public final String SET_FIELD_POSITION_L5 = "l5";
    public final String SET_FIELD_POSITION_L6 = "l6";

    public final String SET_FIELD_POSITION_MISS = "1";
    public final String SET_FIELD_POSITION_TWO = "2";
    public final String SET_FIELD_POSITION_ENEMY_HIT = "3";
    public final String SET_FIELD_POSITION_PLAYER_HIT = "4";
    public final String SET_FIELD_POSITION_ENEMY_MISS = "5";


//    private void initialiseCheckAvailabilityList() {
//        LinkedList<String> checkAvailabilityList = new LinkedList<>();
//        checkAvailabilityList.add(SET_PLAYER_POSITION_SMALL);
//        checkAvailabilityList.add(SET_PLAYER_POSITION_MIDDLE);
//        checkAvailabilityList.add(SET_PLAYER_POSITION_MIDDLE1);
//        checkAvailabilityList.add(SET_PLAYER_POSITION_MIDDLE2);
//        checkAvailabilityList.add(SET_FIELD_POSITION_BIG);
//        checkAvailabilityList.add(SET_FIELD_POSITION_BIG1);
//        checkAvailabilityList.add(SET_FIELD_POSITION_BIG2);
//        checkAvailabilityList.add(SET_FIELD_POSITION_BIG3);
//        checkAvailabilityList.add(SET_FIELD_POSITION_ENEMY_HIT);
//        checkAvailabilityList.add(SET_FIELD_POSITION_ENEMY_MISS);
//    }


    /**
     * Used for the degree value in ship positioning
     */
    public final int HORIZONTAL = 0;
    public final int VERTICAL = 1;

    /**
     * The field size of a players field
     */
    public final int FIELD_SIZE = 64;
}
