package com.example.rebelartstudios.sternenkrieg;


import java.util.Random;

/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {

    private final int WIN = 0;
    private final int LOOSE = 1;
    private final int DEUCE = 2;

    public int roll() {
        Random r = new Random();
        return r.nextInt(6)+1;
    }

    public static void changeDiceImage(int value) {

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
    public static void changeDiceImageEnemy(int value) {

        switch (value) {
            case 1:
                Dice.diceenemy.setImageResource(R.drawable.one);
                break;
            case 2:
                Dice.diceenemy.setImageResource(R.drawable.two);
                break;
            case 3:
                Dice.diceenemy.setImageResource(R.drawable.three);
                break;
            case 4:
                Dice.diceenemy.setImageResource(R.drawable.four);
                break;
            case 5:
                Dice.diceenemy.setImageResource(R.drawable.five);
                break;
            case 6:
                Dice.diceenemy.setImageResource(R.drawable.six);
                break;
            default:
                break;
        }
    }

    public int whoIsStarting(int playerSelf, int playerEnemy) {
        if (playerSelf > playerEnemy) {      // Player starts
            return WIN;
        } else if (playerSelf < playerEnemy) { // Enemy starts
            return LOOSE;
        } else {
            return DEUCE;          // Deuce, both must roll the dice again
        }
    }

    public void closeDice() {
//in produktion
    }


}


