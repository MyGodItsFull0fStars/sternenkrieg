package com.example.rebelartstudios.sternenkrieg.gamelogic;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

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

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public GameUtilities(Context context) {
        GameUtilities.sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        GameUtilities.editor = sharedPreferences.edit();
    }



    public void onDestroy() {
        //onDestroy

    }

    public void load() {
        setUsername(sharedPreferences.getString("username", ""));
        setLevel(sharedPreferences.getInt("level", 1));
        setPercent(sharedPreferences.getInt("percent", 0));
        setSound(sharedPreferences.getBoolean("sound", true));
    }

    public void level() {
        GameUtilities.scoreforlevel = sharedPreferences.getInt("score", 0) + points;
        int levelgrenze = (1000 + 400 * (level - 1)) * level;

        if (scoreforlevel >= levelgrenze) {
            GameUtilities.scoreforlevel -= levelgrenze;
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


    public int getScoreforlevel() {
        return scoreforlevel;
    }

    public static void setScoreforlevel(int scoreforlevel) {
        GameUtilities.scoreforlevel = scoreforlevel;
        editor.putInt("score", scoreforlevel);
        editor.commit();
    }

    public boolean isHighscoreMain() {
        return highscoreMain;
    }

    public static void setHighscoreMain(boolean highscoreMain) {
        GameUtilities.highscoreMain = highscoreMain;
    }

    public boolean isSound() {
        return sound;
    }

    public static void setSound(boolean sound) {
        GameUtilities.sound = sound;
    }

    public int getPercent() {
        return percent;
    }

    public static void setPercent(int percent) {
        GameUtilities.percent = percent;
        editor.putInt("percent", percent);
        editor.commit();
    }


    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GameUtilities.username = username;
        editor.putString("username", username);
        editor.commit();
    }

    public int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        GameUtilities.level = level;
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
