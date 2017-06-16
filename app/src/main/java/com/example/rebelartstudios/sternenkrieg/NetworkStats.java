package com.example.rebelartstudios.sternenkrieg;

/**
 * Created by Chris on 14.06.2017.
 */

public class NetworkStats {
    private static boolean net;
    private static boolean phost;
    private static String ip;
    private static int mode;
    private static int dicevalue;
    private static int value;
    private static String[] playerMap;
    private static String[] enemyMap;
    private static int whoIsStarting;

    public static int getWhoIsStarting() {
        return whoIsStarting;
    }

    public static void setWhoIsStarting(int whoIsStarting) {
        NetworkStats.whoIsStarting = whoIsStarting;
    }

    public static String[] getPlayerMap() {
        return playerMap;
    }

    public static void setPlayerMap(String[] playerMap) {
        NetworkStats.playerMap = playerMap;
    }

    public static String[] getEnemyMap() {
        return enemyMap;
    }

    public static void setEnemyMap(String[] enemyMap) {
        NetworkStats.enemyMap = enemyMap;
    }

    public static int getValue() {

        return value;
    }

    public static void setValue(int value) {
        NetworkStats.value = value;
    }

    public boolean isNet() {
        return net;
    }

    public static void setNet(boolean net) {
        NetworkStats.net = net;
    }

    public boolean isPhost() {
        return phost;
    }

    public static void setPhost(boolean phost) {
        NetworkStats.phost = phost;
    }

    public String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        NetworkStats.ip = ip;
    }

    public int getMode() {
        return mode;
    }

    public static void setMode(int mode) {
        NetworkStats.mode = mode;
    }

    public int getDicevalue() {
        return dicevalue;
    }

    public static void setDicevalue(int dicevalue) {
        NetworkStats.dicevalue = dicevalue;
    }
}
