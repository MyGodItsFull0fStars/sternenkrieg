package com.example.rebelartstudios.sternenkrieg.gamelogic;

public class NetworkStats {
    private static boolean net;
    private static boolean phost;
    private static String ip;
    private static int mode;


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


}