package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;



/**
 * Created by wenboda on 2017/5/11.
 */

public class StartThread extends Thread {

    Socket socket;
    String ip;
    ReceiveThreadClient rt;
    boolean running = true;
    Handler myhandler;
    String tag = "Client";
    boolean tryconnect = true;

    public StartThread(Socket socket, String ip, ReceiveThreadClient rt, Handler myhandler){
        this.socket = socket;
        this.ip = ip;
        this.rt = rt;
        this.myhandler = myhandler;

    }

    public void run() {


        while (tryconnect) {
            try {
                socket = new Socket(ip, 54321);
                tryconnect = false;
                rt = new ReceiveThreadClient(socket, running, myhandler);
                rt.start();
                running = true;
                System.out.println(socket.isConnected());
                if (socket.isConnected()) {
                    Message msg0 = myhandler.obtainMessage();
                    msg0.what = 0;
                    myhandler.sendMessage(msg0);
                }
            } catch (IOException e) {
                Log.e(tag, "IOException in StartThread: " + e.toString());
            }
        }

    }

    public Socket getSocket(){
        return socket;
    }
    public void setRunning(boolean running){
        this.running = running;
    }
    public ReceiveThreadClient getRt(){
        return this.rt;
    }
    public void closeSocket(){
        try {
            this.socket.close();
            this.socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setTryconnect(boolean tryconnect){
        this.tryconnect = tryconnect;
        rt.setRunning(false);
    }

}
