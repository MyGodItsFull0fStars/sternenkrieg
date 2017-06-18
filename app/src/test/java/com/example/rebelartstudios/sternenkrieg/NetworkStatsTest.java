package com.example.rebelartstudios.sternenkrieg;

import com.example.rebelartstudios.sternenkrieg.gamelogic.NetworkStats;

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

} 
