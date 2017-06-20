package com.example.rebelartstudios.sternenkrieg;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {

    private final int WIN = 0;
    private final int LOOSE = 1;
    private final int DEUCE = 2;
    private static int one;
    private static int two;
    private static int three;
    private static int four;
    private static int five;
    private static int six;
    private static int countdice;

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public DiceClass(Context context) {
        DiceClass.sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        DiceClass.editor = sharedPreferences.edit();
        one = sharedPreferences.getInt("one",0);
        two=sharedPreferences.getInt("two",0);
        three =sharedPreferences.getInt("three",0);
        four = sharedPreferences.getInt("four",0);
        five=sharedPreferences.getInt("five",0);
        six=sharedPreferences.getInt("six",0);
        countdice=sharedPreferences.getInt("countdice",0);

    }

    public int roll() {
        Random r = new Random();
        return r.nextInt(6)+1;
    }

    public static void changeDiceImage(int value) {

        switch (value) {
            case 1:
                Dice.imageDice.setImageResource(R.drawable.one);
                setOne(one+1);
                setCountdice(countdice+1);
                break;
            case 2:
                Dice.imageDice.setImageResource(R.drawable.two);
                setTwo(two+1);
                setCountdice(countdice+1);
                break;
            case 3:
                Dice.imageDice.setImageResource(R.drawable.three);
                setThree(three+1);
                setCountdice(countdice+1);
                break;
            case 4:
                Dice.imageDice.setImageResource(R.drawable.four);
                setFour(four+1);
                setCountdice(countdice+1);
                break;
            case 5:
                Dice.imageDice.setImageResource(R.drawable.five);
                setFive(five+1);
                setCountdice(countdice+1);
                break;
            case 6:
                Dice.imageDice.setImageResource(R.drawable.six);
                setSix(six+1);
                setCountdice(countdice+1);
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

    public String getOneprobability() {
        return String.valueOf(Math.round(one*100/countdice))+"%";
    }

    public String getTwoprobability() {
       return String.valueOf(Math.round(two*100/countdice))+"%";
    }
    public String getThreeprobability() {
        return String.valueOf(Math.round(three*100/countdice))+"%";
    }
    public String getFourprobability() {
        return String.valueOf(Math.round(four*100/countdice))+"%";
    }
    public String getFiveprobability() {
        return String.valueOf(Math.round(five*100/countdice))+"%";
    }
    public String getSixprobability() {
        return String.valueOf(Math.round(six*100/countdice))+"%";
    }


    public static int getOne() {
        return one;
    }

    public static void setOne(int one) {
        editor.putInt("one",one);
        editor.commit();
        DiceClass.one = one;
    }

    public static int getTwo() {
        return two;
    }

    public static void setTwo(int two) {
        editor.putInt("two",two);
        DiceClass.two = two;
    }

    public static int getThree() {
        return three;
    }

    public static void setThree(int three) {
        editor.putInt("three",three);
        editor.commit();
        DiceClass.three = three;
    }

    public static int getFour() {
        return four;
    }

    public static void setFour(int four) {
        editor.putInt("four",four);
        editor.commit();
        DiceClass.four = four;
    }

    public static int getFive() {
        return five;
    }

    public static void setFive(int five) {
        editor.putInt("five",five);
        editor.commit();
        DiceClass.five = five;
    }

    public static int getSix() {
        return six;
    }

    public static void setSix(int six) {
        editor.putInt("six",six);
        editor.commit();
        DiceClass.six = six;
    }

    public static int getCountdice() {
        return countdice;
    }

    public static void setCountdice(int countdice) {
        editor.putInt("countdice",countdice);
        editor.commit();
        DiceClass.countdice = countdice;
    }
}


