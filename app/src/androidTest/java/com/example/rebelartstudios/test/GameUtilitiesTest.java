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
    GameUtilities game;
    private Context instrumentationCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Before
    public void before() {
        instrumentationCtx = InstrumentationRegistry.getContext();
        game = new GameUtilities(instrumentationCtx);
        pref = instrumentationCtx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

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
        game.setPoints(0);
        game.level();
        assertEquals(0, game.getScoreforlevel());
        assertEquals(1, game.getLevel());
        assertEquals(0, game.getPercent());

        game.setPoints(500);
        game.level();
        assertEquals(500, game.getScoreforlevel());
        assertEquals(1, game.getLevel());
        assertEquals(50, game.getPercent());

        game.setPoints(499);
        game.level();
        assertEquals(999, game.getScoreforlevel());
        assertEquals(1, game.getLevel());
        assertEquals(99, game.getPercent());

        game.setPoints(1);
        game.level();
        assertEquals(0, game.getScoreforlevel());
        assertEquals(2, game.getLevel());
        assertEquals(0, game.getPercent());


    }

    /**
     * Method: getHighscore()
     */
    @Test
    public void testGetHighscore() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        assertEquals(list, game.getHighscore());

        list.add("Unbekannt");
        editor.putInt("counter", 1);
        editor.commit();
        assertEquals(list, game.getHighscore());


    }

    /**
     * Method: deleteHighscore()
     */
    @Test
    public void testDeleteHighscore() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        game.setUsername("Chris");
        game.setPoints(0);
        for (int i = 0; i < 5; i++) {
            list.add("Chris 0");
            game.setHighscore();
        }
        assertEquals(list, game.getHighscore());
        list.clear();
        game.deleteHighscore();
        assertEquals(list, game.getHighscore());


    }

    /**
     * Method: setHighscore()
     */
    @Test
    public void testSetHighscore() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("Chris 100");
        game.setUsername("Chris");
        game.setPoints(100);
        game.setHighscore();
        assertEquals(list, game.getHighscore());

        list.add("Chris0 1000");
        game.setUsername("Chris0");
        game.setPoints(1000);
        game.setHighscore();
        Collections.sort(list, Collections.<String>reverseOrder());
        assertEquals(list, game.getHighscore());


    }

    @Test
    public void testsetScoreforlevel() {
        game.setScoreforlevel(100);
        assertEquals(100, game.getScoreforlevel());
    }


    /**
     * Method: setHighscoreMain(boolean highscoreMain)
     */
    @Test
    public void testSetHighscoreMain() throws Exception {
        game.setHighscoreMain(true);
        assertTrue(game.isHighscoreMain());

    }

    /**
     * Method: setSound(boolean sound)
     */
    @Test
    public void testSetSound() throws Exception {
        game.setSound(true);
        assertTrue(game.isSound());
    }

    /**
     * Method: setPercent(int percent)
     */
    @Test
    public void testSetPercent() throws Exception {
        game.setPercent(100);
        assertEquals(100,game.getPercent());
    }


    /**
     * Method: setUsername(String username)
     */
    @Test
    public void testSetUsername() throws Exception {
        game.setUsername("Chris");
        assertEquals("Chris",game.getUsername());
    }

    /**
     * Method: setLevel(int level)
     */
    @Test
    public void testSetLevel() throws Exception {
        game.setLevel(5);
        assertEquals(5,game.getLevel());
    }


    /**
     * Method: setPoints(int points)
     */
    @Test
    public void testSetPoints() throws Exception {
        game.setPoints(45);
        assertEquals(45,game.getPoints());
    }


    /**
     * Method: setDicescore(int value)
     */
    @Test
    public void testSetDicescore() throws Exception {
        game.setDicescore(1);
        assertEquals(1,game.getDicescore());
    }


    /**
     * Method: setPlayerMap(String[] playerMap)
     */
    @Test
    public void testSetPlayerMap() throws Exception {
        String[] array = new String[5];
        Arrays.fill(array,"1");
        game.setPlayerMap(array);
        assertEquals(array,game.getPlayerMap());
    }


    /**
     * Method: setEnemyMap(String[] enemyMap)
     */
    @Test
    public void testSetEnemyMap() throws Exception {
        String[] array = new String[5];
        Arrays.fill(array,"1");
        game.setEnemyMap(array);
        assertEquals(array,game.getEnemyMap());
    }


    /**
     * Method: setWhoIsStarting(int whoIsStarting)
     */
    @Test
    public void testSetWhoIsStarting() throws Exception {
        game.setWhoIsStarting(1);
        assertEquals(1,game.getWhoIsStarting());
    }


} 
