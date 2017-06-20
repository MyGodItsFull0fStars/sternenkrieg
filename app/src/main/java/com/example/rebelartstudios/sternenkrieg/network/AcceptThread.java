package com.example.rebelartstudios.sternenkrieg.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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


    public AcceptThread(boolean running, ServerSocket mServerSocket, Socket socket, Handler mHandler, ReceiveThreadHost mReceiveThreadHost, int port) {
        this.running = running;
        this.mServerSocket = mServerSocket;
        this.socket = socket;
        this.mHandler = mHandler;
        this.mReceiveThreadHost = mReceiveThreadHost;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            mServerSocket = new ServerSocket(port);//ein Server erstellen
            socket = mServerSocket.accept();//accept
            Log.i(AcceptThread.class.getName(), "Verbunden");
            if (socket != null) {
                this.resocket = socket;

                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = socket.getInetAddress().getHostAddress();
                mHandler.sendMessage(msg);
                //start receive Thread
                running = true;

                mReceiveThreadHost = new ReceiveThreadHost(socket, running, mHandler);
                mReceiveThreadHost.start();
            }

        } catch (IOException e) {
//                Log.w(AcceptThread.class.getName(),e.getMessage());
        }
    }

    public Socket getSocket() {
//       Log.i(AcceptThread.class.getName(),"getSocket : " + socket);
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerSocket getmServerSocket() {
        return this.mServerSocket;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    //TODO: vlt löschen
    public void closeServers() {
        try {
            socket.close();
            mServerSocket.close();
            mReceiveThreadHost.close();
        } catch (IOException e) {
            Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
            Thread.currentThread().interrupt();
        } catch (NullPointerException e) {
            Log.e(tag, "NULLPointerException in AcceptThreadHost: " + e.toString());
            Thread.currentThread().interrupt();
        }
    }

    public ReceiveThreadHost getmReceiveThreadHost() {
        Log.i(AcceptThread.class.getName(), "Rce running = " + running);
        return mReceiveThreadHost;
    }

    public boolean test() {
        return testB;
    }
}
