package com.example.rebelartstudios.test;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * GameUtilities Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 17, 2017</pre>
 */
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
    public void testLoad() throws Exception {
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
    public void testLevel() throws Exception {
        game.load();
        GameUtilities.setPoints(0);
        game.level();
        assertEquals(0, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(0, game.getPercent());

        GameUtilities.setPoints(500);
        game.level();
        assertEquals(500, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(50, game.getPercent());

        GameUtilities.setPoints(499);
        game.level();
        assertEquals(999, game.getScoreForLevel());
        assertEquals(1, game.getLevel());
        assertEquals(99, game.getPercent());

        GameUtilities.setPoints(1);
        game.level();
        assertEquals(0, game.getScoreForLevel());
        assertEquals(2, game.getLevel());
        assertEquals(0, game.getPercent());


    }

    /**
     * Method: getHighScore()
     */
    @Test
    public void testGetHighscore() throws Exception {
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
    public void testDeleteHighscore() throws Exception {
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
    public void testSetHighscore() throws Exception {
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
    public void testsetScoreforlevel() {
        GameUtilities.setScoreForLevel(100);
        assertEquals(100, game.getScoreForLevel());
    }


    /**
     * Method: setHighScoreMain(boolean highscoreMain)
     */
    @Test
    public void testSetHighscoreMain() throws Exception {
        GameUtilities.setHighScoreMain(true);
        assertTrue(game.isHighScoreMain());

    }

    /**
     * Method: setSound(boolean sound)
     */
    @Test
    public void testSetSound() throws Exception {
        GameUtilities.setSound(true);
        assertTrue(game.isSound());
    }

    /**
     * Method: setPercent(int percent)
     */
    @Test
    public void testSetPercent() throws Exception {
        GameUtilities.setPercent(100);
        assertEquals(100, game.getPercent());
    }


    /**
     * Method: setUsername(String username)
     */
    @Test
    public void testSetUsername() throws Exception {
        GameUtilities.setUsername("Chris");
        assertEquals("Chris", game.getUsername());
    }

    /**
     * Method: setLevel(int level)
     */
    @Test
    public void testSetLevel() throws Exception {
        GameUtilities.setLevel(5);
        assertEquals(5, game.getLevel());
    }


    /**
     * Method: setPoints(int points)
     */
    @Test
    public void testSetPoints() throws Exception {
        GameUtilities.setPoints(45);
        assertEquals(45, GameUtilities.getPoints());
    }


    /**
     * Method: setDiceScore(int value)
     */
    @Test
    public void testSetDicescore() throws Exception {
        GameUtilities.setDiceScore(1);
        assertEquals(1, GameUtilities.getDiceScore());
    }


    /**
     * Method: setPlayerMap(String[] playerMap)
     */
    @Test
    public void testSetPlayerMap() throws Exception {
        String[] array = new String[5];
        Arrays.fill(array, "1");
        GameUtilities.setPlayerMap(array);
        assertEquals(array, GameUtilities.getPlayerMap());
    }


    /**
     * Method: setEnemyMap(String[] enemyMap)
     */
    @Test
    public void testSetEnemyMap() throws Exception {
        String[] array = new String[5];
        Arrays.fill(array, "1");
        GameUtilities.setEnemyMap(array);
        assertEquals(array, GameUtilities.getEnemyMap());
    }


    /**
     * Method: setWhoIsStarting(int whoIsStarting)
     */
    @Test
    public void testSetWhoIsStarting() throws Exception {
        GameUtilities.setWhoIsStarting(1);
        assertEquals(1, GameUtilities.getWhoIsStarting());
    }


} 
