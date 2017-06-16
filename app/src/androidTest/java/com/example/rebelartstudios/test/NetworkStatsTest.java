package com.example.rebelartstudios.test;

import com.example.rebelartstudios.sternenkrieg.NetworkStats;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public void before() {
    stats= new NetworkStats();
}

/** 
* 
* Method: getWhoIsStarting()
* 
*/ 
@Test
public void testGetWhoisstarting() {
    NetworkStats.setWhoIsStarting(1);
    assertEquals(1, NetworkStats.getWhoIsStarting());

} 

/** 
* 
* Method: getPlayerMap() 
* 
*/ 
@Test
public void testGetPlayerMap() {
    String[] field = new String[12];
    NetworkStats.setPlayerMap(field);
    assertEquals(field, NetworkStats.getPlayerMap());
} 

/** 
* 
* Method: getEnemyMap() 
* 
*/ 
@Test
public void testGetEnemyMap(){
    String[] field = new String[12];
    NetworkStats.setEnemyMap(field);
    assertEquals(field, NetworkStats.getEnemyMap());
} 

/** 
* 
* Method: getValue() 
* 
*/ 
@Test
public void testGetValue() {
    NetworkStats.setValue(1);
    assertEquals(1, NetworkStats.getValue());
} 


/** 
* 
* Method: isNet() 
* 
*/ 
@Test
public void testIsNet()  {
    stats.setNet(true);
    assertTrue(stats.isNet());
} 


/** 
* 
* Method: isPhost() 
* 
*/ 
@Test
public void testIsPhost()  {
    stats.setPhost(true);
    assertTrue(stats.isPhost());
}

/** 
* 
* Method: getIp() 
* 
*/ 
@Test
public void testGetIp() {
    stats.setIp("112");
    assertEquals("112",stats.getIp());
} 


/** 
* 
* Method: getMode() 
* 
*/ 
@Test
public void testGetMode() {
    stats.setMode(1);
    assertEquals(1,stats.getMode());
}

/** 
* 
* Method: getDicevalue() 
* 
*/ 
@Test
public void testGetDicevalue()  {
    stats.setDicevalue(1);
    assertEquals(1,stats.getDicevalue());
} 



} 
