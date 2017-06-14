package com.example.rebelartstudios.sternenkrieg;

/**
 * Created by Chris on 14.06.2017.
 */

public class NetworkStats {
    private static boolean Net;
    private static boolean Phost;
    private static String ip;
    private static int mode;
    private static int dicevalue;
    private static int value;
    private static String[] PlayerMap;
    private static String[] EnemyMap;
    private static int who_is_starting;

    public static int getWho_is_starting() {
        return who_is_starting;
    }

    public static void setWho_is_starting(int who_is_starting) {
        NetworkStats.who_is_starting = who_is_starting;
    }

    public static String[] getPlayerMap() {
        return PlayerMap;
    }

    public static void setPlayerMap(String[] playerMap) {
        PlayerMap = playerMap;
    }

    public static String[] getEnemyMap() {
        return EnemyMap;
    }

    public static void setEnemyMap(String[] enemyMap) {
        EnemyMap = enemyMap;
    }

    public static int getValue() {

        return value;
    }

    public static void setValue(int value) {
        NetworkStats.value = value;
    }

    public boolean isNet() {
        return Net;
    }

    public void setNet(boolean net) {
        Net = net;
    }

    public boolean isPhost() {
        return Phost;
    }

    public void setPhost(boolean phost) {
        Phost = phost;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        NetworkStats.ip = ip;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        NetworkStats.mode = mode;
    }

    public int getDicevalue() {
        return dicevalue;
    }

    public void setDicevalue(int dicevalue) {
        NetworkStats.dicevalue = dicevalue;
    }
}
