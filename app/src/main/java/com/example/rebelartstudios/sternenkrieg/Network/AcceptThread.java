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
    boolean testB = false;
    int port;
    Socket resocket;



    public AcceptThread(boolean running, ServerSocket mServerSocket, Socket socket, Handler mHandler, ReceiveThreadHost mReceiveThreadHost,int port){
        this.running = running;
        this.mServerSocket = mServerSocket;
        this.socket = socket;
        this.mHandler = mHandler;
        this.mReceiveThreadHost = mReceiveThreadHost;
        this.port = port;
    }



    public void run() {

//        while(running) {

            try {

                System.out.println("A running = "+running);
                mServerSocket = new ServerSocket(port);//ein Server erstellen
                socket = mServerSocket.accept();//accept
//                this.testB = true;
//                test();
                System.out.println("Accpthread: "+socket);
                System.out.println("erfolg");
                if (socket != null){
                    this.resocket = socket;
                }

                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = socket.getInetAddress().getHostAddress();
                mHandler.sendMessage(msg);
                //start receive Thread
                running = true;

                mReceiveThreadHost = new ReceiveThreadHost(socket, running, mHandler);
                mReceiveThreadHost.start();

            } catch (IOException e) {
                Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());

                System.out.println("geht nicht");
            }
//               catch (InterruptedException e) {
//               Log.e(tag, "InterruptedException in AcceptThreadHost: " + e.toString());
//
//            }


//        }
    }

    public Socket getSocket (){
        System.out.println("getSocket : " + this.socket);
        return this.socket;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public ServerSocket getmServerSocket(){return this.mServerSocket;}
    public void setRunning(boolean running){
        this.running = running;
    }
    public void closeServers(){
        try {
            socket.close();
            mServerSocket.close();
            mReceiveThreadHost.close();
        } catch (IOException e) {
            Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
            Thread.currentThread().interrupt();
        }catch (NullPointerException e){
            Log.e(tag, "NULLPointerException in AcceptThreadHost: " + e.toString());
            Thread.currentThread().interrupt();
        }
    }

    public ReceiveThreadHost getmReceiveThreadHost(){
        System.out.println("Rce running = " + running);
        return mReceiveThreadHost;
    }
    public boolean test(){
        return testB;
    }
}
