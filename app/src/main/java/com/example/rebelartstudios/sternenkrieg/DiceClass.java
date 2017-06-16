package com.example.rebelartstudios.sternenkrieg;


import java.util.Random;

/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {


    public int roll() {
        Random r = new Random();
        return r.nextInt(6)+1;
    }

    public static synchronized void changeDiceImage(int value) {

        switch (value) {
            case 1:
                Dice.imageDice.setImageResource(R.drawable.one);
                break;
            case 2:
                Dice.imageDice.setImageResource(R.drawable.two);
                break;
            case 3:
                Dice.imageDice.setImageResource(R.drawable.three);
                break;
            case 4:
                Dice.imageDice.setImageResource(R.drawable.four);
                break;
            case 5:
                Dice.imageDice.setImageResource(R.drawable.five);
                break;
            case 6:
                Dice.imageDice.setImageResource(R.drawable.six);
                break;
            default:
                break;
        }
    }

    public int whoIsStarting(int player, int player2) {
        if (player > player2) {      // Player starts
            return 0;
        } else if (player < player2) { // Enemy starts
            return 1;
        } else {
            return 2;          // Deuce, both must roll the dice again
        }
    }

    public void closeDice() {
//in produktion
    }


}


