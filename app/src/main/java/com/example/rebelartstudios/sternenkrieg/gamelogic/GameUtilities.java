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
    private static int points;
    private static int diceScore;
    private static String[] playerMap;
    private static String[] enemyMap;
    private static int whoIsStarting;
    private static boolean highScoreMain;
    private static int scoreForLevel;
    private static String enemyUsername;


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

    public static int setScoreForLevel(){
        return sharedPreferences.getInt("score", 0) + points;
    }

    public void level() {
        GameUtilities.scoreForLevel = setScoreForLevel();
        int levelBoundaries = (1000 + 400 * (level - 1)) * level;

        if (scoreForLevel >= levelBoundaries) {
            GameUtilities.scoreForLevel -= levelBoundaries;
            setLevel(level + 1);

        }
        levelBoundaries = (1000 + 400 * (level - 1)) * level;
        setPercent((int) (((double) scoreForLevel / ((double) levelBoundaries)) * 100));

        setScoreForLevel(scoreForLevel);


    }

    public ArrayList<String> getHighScore() {
        ArrayList<String> highScore = new ArrayList<>();
        int counter = sharedPreferences.getInt("counter", 0);
        for (int i = 0; i < counter; i++) {
            highScore.add(sharedPreferences.getString("highScore" + i + "", "Unbekannt"));
        }
        Collections.sort(highScore, Collections.<String>reverseOrder());
        return highScore;
    }

    public void deleteHighScore() {
        int counter = sharedPreferences.getInt("counter", 0);
        for (int i = 0; i < counter; i++)
            editor.remove("highScore" + i + "");
        editor.putInt("counter", 0);
        editor.commit();


    }

    public void setHighScore() {
        int counter = sharedPreferences.getInt("counter", 0);
        editor.putString("highScore" + counter + "", username + " " + points);
        counter++;
        editor.putInt("counter", counter);
        editor.commit();
    }


    public int getScoreForLevel() {
        return scoreForLevel;
    }

    public static void setScoreForLevel(int scoreForLevel) {
        GameUtilities.scoreForLevel = scoreForLevel;
        editor.putInt("score", scoreForLevel);
        editor.commit();
    }

    public boolean isHighScoreMain() {
        return highScoreMain;
    }

    public static void setHighScoreMain(boolean highScoreMain) {
        GameUtilities.highScoreMain = highScoreMain;
    }

    public static String getEnemyUsername() {
        return enemyUsername;
    }

    public static void setEnemyUsername(String enemyUsername) {
        GameUtilities.enemyUsername = enemyUsername;
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

    public static int getDiceScore() {
        return diceScore;
    }

    public static void setDiceScore(int value) {
        GameUtilities.diceScore = value;
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
