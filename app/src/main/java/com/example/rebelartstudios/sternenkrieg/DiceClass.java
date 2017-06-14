package com.example.rebelartstudios.sternenkrieg;


import android.os.CountDownTimer;

/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {



    public int roll() {
        int value = (int) (Math.random() * 6 + 1);
        return value;
    }

    public void changeDiceImage(int value) {
        Dice dice = new Dice();

        switch (value) {
            case 1:
                dice.imageDice.setImageResource(R.drawable.one);
                break;
            case 2:
                dice.imageDice.setImageResource(R.drawable.two);
                break;
            case 3:
                dice.imageDice.setImageResource(R.drawable.three);
                break;
            case 4:
                dice.imageDice.setImageResource(R.drawable.four);
                break;
            case 5:
                dice.imageDice.setImageResource(R.drawable.five);
                break;
            case 6:
                dice.imageDice.setImageResource(R.drawable.six);
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




}


