package com.example.rebelartstudios.sternenkrieg;

import android.os.Handler;

import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;
import com.example.rebelartstudios.sternenkrieg.Network.writeClient;
import com.example.rebelartstudios.sternenkrieg.Network.writeHost;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wenboda on 2017/6/4.
 */

public class Netz {
    Socket socket;
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then Phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "Spiefeld";
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;

    public void networkbuild(){
        boolean running = true;
        if (Phost){
            mAcceptThread = new AcceptThread(running,mServerSocket,socket,myhandler,receiveThreadHost);
        }else {
            startThread = new StartThread(socket,ip,receiveThreadClient,myhandler);
        }

    }
    public void messageSend(String message, boolean obhost){
        if (obhost) {
            socket = mAcceptThread.getSocket();


            writeHost wh = new writeHost(socket, os, message);

            wh.start();


        }else{

            socket = startThread.getSocket();
            Thread wirte = new writeClient(true, socket, message);

            wirte.start();

        }
    }
}
