package com.example.rebelartstudios.sternenkrieg;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by Chris on 13.06.2017.
 */

public class DiceClass {

    private static int one;
    private static int two;
    private static int three;
    private static int four;
    private static int five;
    private static int six;
    static int countDice;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public DiceClass(Context context) {
        DiceClass.sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        DiceClass.editor = sharedPreferences.edit();
        one = sharedPreferences.getInt("one", 0);
        two = sharedPreferences.getInt("two", 0);
        three = sharedPreferences.getInt("three", 0);
        four = sharedPreferences.getInt("four", 0);
        five = sharedPreferences.getInt("five", 0);
        six = sharedPreferences.getInt("six", 0);
        countDice = sharedPreferences.getInt("countDice", 0);

    }

    public int roll() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }


    public int whoIsStarting(int playerSelf, int playerEnemy) {
        if (playerSelf > playerEnemy) {      // Player starts
            return 0;
        } else if (playerSelf < playerEnemy) { // Enemy starts
            return 1;
        } else {
            return 2;          // Deuce, both must roll the dice again
        }
    }

    public void closeDice() {
//in produktion
    }

    String getOneProbability() {
        return String.valueOf(one * 100 / countDice) + "%";
    }

    String getTwoProbability() {
        return String.valueOf(two * 100 / countDice) + "%";
    }

    String getThreeProbability() {
        return String.valueOf(three * 100 / countDice) + "%";
    }

    String getFourProbability() {
        return String.valueOf(four * 100 / countDice) + "%";
    }

    String getFiveProbability() {
        return String.valueOf(five * 100 / countDice) + "%";
    }

    String getSixProbability() {
        return String.valueOf(six * 100 / countDice) + "%";
    }


    public static int getOne() {
        return one;
    }

    public static void setOne(int one) {
        editor.putInt("one", one);
        editor.commit();
        DiceClass.one = one;
    }

    public static int getTwo() {
        return two;
    }

    static void setTwo(int two) {
        editor.putInt("two", two);
        DiceClass.two = two;
    }

    public static int getThree() {
        return three;
    }

    static void setThree(int three) {
        editor.putInt("three", three);
        editor.commit();
        DiceClass.three = three;
    }

    public static int getFour() {
        return four;
    }

    static void setFour(int four) {
        editor.putInt("four", four);
        editor.commit();
        DiceClass.four = four;
    }

    public static int getFive() {
        return five;
    }

    static void setFive(int five) {
        editor.putInt("five", five);
        editor.commit();
        DiceClass.five = five;
    }

    public static int getSix() {
        return six;
    }

    static void setSix(int six) {
        editor.putInt("six", six);
        editor.commit();
        DiceClass.six = six;
    }

    public static int getCountdice() {
        return countDice;
    }

    static void setCountDice(int countDice) {
        editor.putInt("countDice", countDice);
        editor.commit();
        DiceClass.countDice = countDice;
    }
}


