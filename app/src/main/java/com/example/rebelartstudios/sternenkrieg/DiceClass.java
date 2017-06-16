package com.example.rebelartstudios.sternenkrieg;


/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {

    private final int WIN = 0;
    private final int LOOSE = 1;
    private final int DEUCE = 2;


    public int roll() {
        int value = (int) (Math.random() * 6 + 1);
        return value;
    }

    public void changeDiceImage(int value) {

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

    public int whoIsStarting(int playerSelf, int playerEnemy) {
        if (playerSelf > playerEnemy) {      // Player starts
            return WIN;
        } else if (playerSelf < playerEnemy) { // Enemy starts
            return LOOSE;
        } else {
            return DEUCE;          // Deuce, both must roll the dice again
        }
    }




}


