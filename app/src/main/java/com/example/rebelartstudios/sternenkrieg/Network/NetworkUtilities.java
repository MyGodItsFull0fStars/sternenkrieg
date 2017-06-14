package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Chris on 14.06.2017.
 */

public class NetworkUtilities {

    boolean Phost;
    AcceptThread mAcceptThread;
    ServerSocket mServerSocket;
    Socket socket;
    Handler myhandler;
    ReceiveThreadHost receiveThreadHost;
    OutputStream os= null;
    StartThread startThread;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "Dice";

    public NetworkUtilities(boolean phost, AcceptThread mAcceptThread, ServerSocket mServerSocket, Socket socket, Handler myhandler, ReceiveThreadHost receiveThreadHost, StartThread startThread, String ip, ReceiveThreadClient receiveThreadClient) {
        Phost = phost;
        this.mAcceptThread = mAcceptThread;
        this.mServerSocket = mServerSocket;
        this.socket = socket;
        this.myhandler = myhandler;
        this.receiveThreadHost = receiveThreadHost;
        this.startThread = startThread;
        this.ip = ip;
        this.receiveThreadClient = receiveThreadClient;
    }





    public void connection(){
            boolean running = true;
            mAcceptThread = new AcceptThread(running, mServerSocket, socket, myhandler, receiveThreadHost, 12345);
            mAcceptThread.start();
        }




    public void messageSend(String message, boolean Phost, boolean t) {
        if (Phost) {
            Socket socket1 = mAcceptThread.getSocket();
            writeHost wh = new writeHost(socket1, os, message);
            wh.start();


        } else {
            Socket socket1;
            socket1 = startThread.getSocket();
            writeClient wirte = new writeClient(true, socket1, message);
            wirte.start();


        }
    }
    public void networkbuild() {
        if (Phost) {

        } else {

            this.startThread = new StartThread(socket, ip, receiveThreadClient, myhandler, 12345);
            startThread.start();

        }

    }



    public void close() {

        if (Phost) {

            try {

                mAcceptThread.setRunning(false);

                mAcceptThread.setSocket(null);

            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());


            }
            try {

                mAcceptThread.getmReceiveThreadHost().close();
                mAcceptThread.getmServerSocket().close();
                mAcceptThread.getSocket().close();
                mAcceptThread.interrupt();

            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());

            } catch (IOException e) {
                Log.e(tag, "IOPointerException in Client: " + e.toString());
            }
        } else {
            try {
                startThread.setRunning(false);
                socket = startThread.getSocket();
                socket.close();
                socket = null;
                startThread.setTryconnect(false);

                startThread.interrupt();
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());

            } catch (IOException e) {
                Log.e(tag, "IOException in Client: " + e.toString());
            }
        }
    }
}
