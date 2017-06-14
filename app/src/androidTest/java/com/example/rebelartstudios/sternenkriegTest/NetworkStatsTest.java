package com.example.rebelartstudios.sternenkriegTest;

import com.example.rebelartstudios.sternenkrieg.NetworkStats;

import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;

/** 
* NetworkStats Tester. 
* 
* @author <Authors name> 
* @since <pre>Jun 14, 2017</pre> 
* @version 1.0 
*/ 
public class NetworkStatsTest {
    NetworkStats stats;

@Before
public void before() throws Exception {
    stats= new NetworkStats();
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getWho_is_starting() 
* 
*/ 
@Test
public void testGetWho_is_starting() throws Exception { 
//TODO: Test goes here...
    stats.setWho_is_starting(1);
    assertEquals(1,stats.getWho_is_starting());

} 



/** 
* 
* Method: getPlayerMap() 
* 
*/ 
@Test
public void testGetPlayerMap() throws Exception { 
//TODO: Test goes here...
    String[] field = new String[12];
    stats.setPlayerMap(field);
    assertEquals(field,stats.getPlayerMap());
} 

/** 
* 
* Method: getEnemyMap() 
* 
*/ 
@Test
public void testGetEnemyMap() throws Exception { 
//TODO: Test goes here...
    String[] field = new String[12];
    stats.setEnemyMap(field);
    assertEquals(field,stats.getEnemyMap());
} 

/** 
* 
* Method: getValue() 
* 
*/ 
@Test
public void testGetValue() throws Exception { 
//TODO: Test goes here...
    stats.setValue(1);
    assertEquals(1,stats.getValue());
} 


/** 
* 
* Method: isNet() 
* 
*/ 
@Test
public void testIsNet() throws Exception { 
//TODO: Test goes here...
    stats.setNet(true);
    assertTrue(stats.isNet());
} 


/** 
* 
* Method: isPhost() 
* 
*/ 
@Test
public void testIsPhost() throws Exception { 
//TODO: Test goes here...
    stats.setPhost(true);
    assertTrue(stats.isPhost());
}

/** 
* 
* Method: getIp() 
* 
*/ 
@Test
public void testGetIp() throws Exception { 
//TODO: Test goes here...
    stats.setIp("112");
    assertEquals("112",stats.getIp());
} 


/** 
* 
* Method: getMode() 
* 
*/ 
@Test
public void testGetMode() throws Exception { 
//TODO: Test goes here...
    stats.setMode(1);
    assertEquals(1,stats.getMode());
}

/** 
* 
* Method: getDicevalue() 
* 
*/ 
@Test
public void testGetDicevalue() throws Exception { 
//TODO: Test goes here...
    stats.setDicevalue(1);
    assertEquals(1,stats.getDicevalue());
} 



} 
