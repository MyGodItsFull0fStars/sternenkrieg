package com.example.rebelartstudios.sternenkrieg.Network;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by wenboda on 2017/6/5.
 */

public class SingelSocket {

    private static Socket socket;
    private static String ip;
    private static int port;


    private SingelSocket(){}

    public Socket getSocket(){
        if (socket == null){
            try {
                socket =  new Socket(ip,port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }



}
