package com.example.rebelartstudios.sternenkrieg;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

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
    protected static int countdice;
    ImageView imageDice;
    Context context;

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
        this.context = context;
    }

    public int roll() {
        Random r = new Random();
        return r.nextInt(6)+1;
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
        return String.valueOf(one*100/countdice)+"%";
    }

    public String getTwoprobability() {
       return String.valueOf(two*100/countdice)+"%";
    }
    public String getThreeprobability() {
        return String.valueOf(three*100/countdice)+"%";
    }
    public String getFourprobability() {
        return String.valueOf(four*100/countdice)+"%";
    }
    public String getFiveprobability() {
        return String.valueOf(five*100/countdice)+"%";
    }
    public String getSixprobability() {
        return String.valueOf(six*100/countdice)+"%";
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


