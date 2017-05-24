package com.example.rebelartstudios.sternenkrieg.Network;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/24.
 */

public class MessageSend {

    Socket socket;
    AcceptThread mAcceptThread;
    OutputStream os = null;

    public void sendMessage(){
        String info = "//Starten";
        socket = mAcceptThread.getSocket();
        writeHost writeHost = new writeHost(socket,os,info);
        writeHost.start();

    }

}
