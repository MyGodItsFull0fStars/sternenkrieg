package com.example.rebelartstudios.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.example.rebelartstudios.sternenkrieg.exception.MyException;
import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameUtilitiesTest {
    private GameUtilities game;
    private Context instrumentationCtx;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Before
    public void before() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        game = new GameUtilities(instrumentationCtx);
        pref = instrumentationCtx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    //TODO: enemyusername tests getter setter
    /**
     * Method: load()
     */
    @Test
    public void testLoad() throws MyException {
        game.load();
        assertNotNull(game.getUsername());
        assertNotNull(game.getLevel());
        assertNotNull(game.getPercent());
        assertNotNull(game.isSound());

        assertEquals("", game.getUsername());
        assertEquals(1, game.getLevel());
        assertEquals(0, game.getPercent());
        assertEquals(true, game.isSound());
    }

    /**
     * Method: level()
     */
    @Test
    public void testLevel() throws MyException {
        game.load();
        GameUtilities.setPoints(0);
        GameUtilities.level();
        assertEquals(0, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(0, game.getPercent());

        GameUtilities.setPoints(500);
        GameUtilities.level();
        assertEquals(500, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(50, game.getPercent());

        GameUtilities.setPoints(499);
        GameUtilities.level();
        assertEquals(999, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(99, game.getPercent());

        GameUtilities.setPoints(1);
        GameUtilities.level();
        assertEquals(0, game.getScoreForLevel());
        assertEquals(2, game.getLevel());
        assertEquals(0, game.getPercent());


    }

    /**
     * Method: getHighScore()
     */
    @Test
    public void testGetHighScore() throws MyException {
        ArrayList<String> list = new ArrayList<>();
        assertEquals(list, game.getHighScore());

        list.add("Unbekannt");
        editor.putInt("counter", 1);
        editor.commit();
        assertEquals(list, game.getHighScore());


    }

    /**
     * Method: deleteHighScore()
     */
    @Test
    public void testDeleteHighScore() throws MyException {
        ArrayList<String> list = new ArrayList<>();
        GameUtilities.setUsername("Chris");
        GameUtilities.setPoints(0);
        for (int i = 0; i < 5; i++) {
            list.add("Chris 0");
            game.setHighScore();
        }
        assertEquals(list, game.getHighScore());
        list.clear();
        game.deleteHighScore();
        assertEquals(list, game.getHighScore());


    }

    /**
     * Method: setHighScore()
     */
    @Test
    public void testSetHighScore() throws MyException {
        ArrayList<String> list = new ArrayList<>();
        list.add("Chris 100");
        GameUtilities.setUsername("Chris");
        GameUtilities.setPoints(100);
        game.setHighScore();
        assertEquals(list, game.getHighScore());


        list.add("Chris0 1000");
        GameUtilities.setUsername("Chris0");
        GameUtilities.setPoints(1000);
        game.setHighScore();
        Collections.sort(list, Collections.<String>reverseOrder());
        assertEquals(list, game.getHighScore());


    }

    @Test
    public void testsetScoreForLevel() {
        GameUtilities.setScoreForLevel(100);
        assertEquals(100, game.getScoreForLevel());
    }


    /**
     * Method: setHighScoreMain(boolean highscoreMain)
     */
    @Test
    public void testSetHighScoreMain() throws MyException {
        GameUtilities.setHighScoreMain(true);
        assertTrue(game.isHighScoreMain());

    }

    /**
     * Method: setSound(boolean sound)
     */
    @Test
    public void testSetSound() throws MyException {
        GameUtilities.setSound(true);
        assertTrue(game.isSound());
    }

    /**
     * Method: setPercent(int percent)
     */
    @Test
    public void testSetPercent() throws MyException {
        GameUtilities.setPercent(100);
        assertEquals(100, game.getPercent());
    }


    /**
     * Method: setUsername(String username)
     */
    @Test
    public void testSetUsername() throws MyException {
        GameUtilities.setUsername("Chris");
        assertEquals("Chris", game.getUsername());
    }

    /**
     * Method: setLevel(int level)
     */
    @Test
    public void testSetLevel() throws MyException {
        GameUtilities.setLevel(5);
        assertEquals(5, game.getLevel());
    }


    /**
     * Method: setPoints(int points)
     */
    @Test
    public void testSetPoints() throws MyException {
        GameUtilities.setPoints(45);
        assertEquals(45, GameUtilities.getPoints());
    }


    /**
     * Method: setDiceScore(int value)
     */
    @Test
    public void testSetDiceScore() throws MyException {
        GameUtilities.setDiceScore(1);
        assertEquals(1, GameUtilities.getDiceScore());
    }


    /**
     * Method: setPlayerMap(String[] playerMap)
     */
    @Test
    public void testSetPlayerMap() throws MyException {
        String[] array = new String[5];
        Arrays.fill(array, "1");
        GameUtilities.setPlayerMap(array);
        assertEquals(array, GameUtilities.getPlayerMap());
    }


    /**
     * Method: setEnemyMap(String[] enemyMap)
     */
    @Test
    public void testSetEnemyMap() throws MyException {
        String[] array = new String[5];
        Arrays.fill(array, "1");
        GameUtilities.setEnemyMap(array);
        assertEquals(array, GameUtilities.getEnemyMap());
    }


    /**
     * Method: setWhoIsStarting(int whoIsStarting)
     */
    @Test
    public void testSetWhoIsStarting() throws MyException {
        GameUtilities.setWhoIsStarting(1);
        assertEquals(1, GameUtilities.getWhoIsStarting());
    }


} 
