package com.example.rebelartstudios.sternenkrieg.Socket;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/8.
 */

public class StartThread extends Thread {

    Socket socket;
    Handler myHandler;
    ReceiveThread rt;
    boolean running = true;
    String ip;

    public StartThread(String ip, ReceiveThread rt,boolean running, Handler myHandler ){
        this.ip = ip;
        this.rt = rt;
        this.running = running;
        this.myHandler = myHandler;
    }



    @Override
    public void run() {
        try {

            socket = new Socket(ip, 12345); // Ipet:

            myHandler = new Handler();
            rt = new ReceiveThread(socket, running, myHandler);
            rt.start();
            running = true;
            System.out.println(socket.isConnected());
            if(socket.isConnected()){
                Message msg0 = myHandler.obtainMessage();
                msg0.what=0;
                myHandler.sendMessage(msg0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
