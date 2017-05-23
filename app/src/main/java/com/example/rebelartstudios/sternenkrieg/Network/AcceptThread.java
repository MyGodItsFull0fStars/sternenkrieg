package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/10.
 */

public class AcceptThread extends Thread {
    boolean running;
    ServerSocket mServerSocket;
    Socket socket;
    Handler mHandler;
    ReceiveThreadHost mReceiveThreadHost;
    String tag = "Host";


    public AcceptThread(boolean running, ServerSocket mServerSocket, Socket socket, Handler mHandler, ReceiveThreadHost mReceiveThreadHost){
        this.running = running;
        this.mServerSocket = mServerSocket;
        this.socket = socket;
        this.mHandler = mHandler;
        this.mReceiveThreadHost = mReceiveThreadHost;
    }



    public void run() {


        while (running) {
            try {
                mServerSocket = new ServerSocket(54321);//ein Server erstellen
                socket = mServerSocket.accept();//accept

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    Log.e(tag, "InterruptedException in AcceptThreadHost: " + e.toString());
                }

                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = socket.getInetAddress().getHostAddress();
                mHandler.sendMessage(msg);
                //start receive Thread
                running = true;
                mReceiveThreadHost = new ReceiveThreadHost(socket, running,mHandler);
                mReceiveThreadHost.start();
            } catch (IOException e) {
                Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
                try {
                    sleep(3000);
                } catch (InterruptedException e1) {
                    Log.e(tag, "InterruptedException in AcceptThreadHost: " + e.toString());
                }
                System.out.println("geht nicht");
            }
        }
        try{
            mReceiveThreadHost.setRunning(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public Socket getSocket (){
        return this.socket;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public void setRunning(boolean running){
        this.running = running;
    }
    public void closeServers(){
        try {
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
