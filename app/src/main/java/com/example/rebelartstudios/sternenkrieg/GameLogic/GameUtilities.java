package com.example.rebelartstudios.sternenkrieg.GameLogic;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chris on 15.06.2017.
 */

public class GameUtilities {

    private static String username;
    private static int level;
    private static int percent;
    private static boolean sound;
    private static ArrayList<String> highscore;
    private static int points;
    private static int dicescore;
    private static String[] playerMap;
    private static String[] enemyMap;
    private static int whoIsStarting;
    private static boolean highscoreMain;
    private static int scoreforlevel;
    private static Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public GameUtilities(Context context) {
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.context = context;
    }

    public void onDestroy() {
        //onDestroy

    }

    public void load() {
        setUsername(sharedPreferences.getString("username", "Unbekannt"));
        setLevel(sharedPreferences.getInt("level", 1));
        setPercent(sharedPreferences.getInt("percent", 0));
        setSound(sharedPreferences.getBoolean("sound", true));
    }

    public void level() {
        scoreforlevel = sharedPreferences.getInt("score", 0) + points;
        int levelgrenze = (1000 + 400 * (level - 1)) * level;

        if (scoreforlevel >= levelgrenze) {
            scoreforlevel -= levelgrenze;
            setLevel(level + 1);

        }
        levelgrenze = (1000 + 400 * (level - 1)) * level;
        setPercent((int) (((double) scoreforlevel / ((double) levelgrenze)) * 100));

        setScoreforlevel(scoreforlevel);


    }

    public ArrayList<String> getHighscore() {
        highscore = new ArrayList<>();
        int counter = sharedPreferences.getInt("counter", 0);
        for (int i = 0; i < counter; i++) {
            highscore.add(sharedPreferences.getString("highscore" + i + "", "Unbekannt"));
        }
        Collections.sort(highscore, Collections.<String>reverseOrder());
        return highscore;
    }

    public void deleteHighscore() {
        int counter = sharedPreferences.getInt("counter", 0);
        for (int i = 0; i < counter; i++)
            editor.remove("highscore" + i + "");
        editor.putInt("counter", 0);
        editor.commit();


    }

    public void setHighscore() {
        int counter = sharedPreferences.getInt("counter", 0);
        editor.putString("highscore" + counter + "", username + " " + points);
        counter++;
        editor.putInt("counter", counter);
        editor.commit();
    }


    public static int getScoreforlevel() {
        return scoreforlevel;
    }

    public void setScoreforlevel(int scoreforlevel) {
        this.scoreforlevel = scoreforlevel;
        editor.putInt("score", scoreforlevel);
        editor.commit();
    }

    public static boolean isHighscoreMain() {
        return highscoreMain;
    }

    public void setHighscoreMain(boolean highscoreMain) {
        this.highscoreMain = highscoreMain;
    }

    public static boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public static int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
        editor.putInt("percent", percent);
        editor.commit();
    }


    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        editor.putString("username", username);
        editor.commit();
    }

    public static int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        editor.putInt("level", level);
        editor.commit();
    }

    public static int getPoints() {
        return points;
    }

    public static void setPoints(int points) {
        GameUtilities.points = points;
    }

    public static int getDicescore() {
        return dicescore;
    }

    public static void setDicescore(int value) {
        GameUtilities.dicescore = value;
    }

    public static String[] getPlayerMap() {
        return playerMap;
    }

    public static void setPlayerMap(String[] playerMap) {
        GameUtilities.playerMap = playerMap;
    }

    public static String[] getEnemyMap() {
        return enemyMap;
    }

    public static void setEnemyMap(String[] enemyMap) {
        GameUtilities.enemyMap = enemyMap;
    }

    public static int getWhoIsStarting() {
        return whoIsStarting;
    }

    public static void setWhoIsStarting(int whoIsStarting) {
        GameUtilities.whoIsStarting = whoIsStarting;
    }
}
