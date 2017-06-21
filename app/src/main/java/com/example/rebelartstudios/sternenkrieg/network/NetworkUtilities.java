package com.example.rebelartstudios.sternenkrieg.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkUtilities {

    private boolean pHost;
    private AcceptThread mAcceptThread;
    private ServerSocket mServerSocket;
    private Socket socket;
    private Handler myHandler;
    private ReceiveThreadHost receiveThreadHost;
    private OutputStream os = null;
    private StartThread startThread;
    private String ip;
    private ReceiveThreadClient receiveThreadClient;
    private String tag = "Dice";
    private String message = "";

    public NetworkUtilities(boolean pHost,
                            AcceptThread mAcceptThread,
                            ServerSocket mServerSocket,
                            Socket socket,
                            Handler myHandler,
                            ReceiveThreadHost receiveThreadHost,
                            StartThread startThread,
                            String ip,
                            ReceiveThreadClient receiveThreadClient) {
        this.pHost = pHost;
        this.mAcceptThread = mAcceptThread;
        this.mServerSocket = mServerSocket;
        this.socket = socket;
        this.myHandler = myHandler;
        this.receiveThreadHost = receiveThreadHost;
        this.startThread = startThread;
        this.ip = ip;
        this.receiveThreadClient = receiveThreadClient;
    }

    public void connection() {
        if (pHost){

            boolean running = true;
            mAcceptThread = new AcceptThread(running, mServerSocket, socket, myHandler, receiveThreadHost, 12345);
            mAcceptThread.start();
        }
    }

    public void messageSend(String message, boolean pHost) {
        if (pHost) {
            Socket socket1 = mAcceptThread.getSocket();
            WriteHost wh = new WriteHost(socket1, os, message);
            wh.start();
        } else {
            Socket socket1;
            socket1 = startThread.getSocket();
            WriteClient write = new WriteClient(true, socket1, message);
            write.start();
        }
    }

    public void networkbuild() {
        if (!pHost) {
            this.startThread = new StartThread(socket, ip, receiveThreadClient, myHandler, 12345);
            startThread.start();
        }
    }

    public void close() {
        if (pHost) {
            try {
                mAcceptThread.setRunning(false);
                mAcceptThread.setSocket(null);
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());
            }
            try {
                mAcceptThread.getMReceiveThreadHost().close();
                mAcceptThread.getMServerSocket().close();
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
                startThread.setTryConnect(false);
                startThread.interrupt();
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());

            } catch (IOException e) {
                Log.e(tag, "IOException in Client: " + e.toString());
            }
        }
    }

    public String handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                message = (String) msg.obj;
                int count = 0;
                if (message == null) {
                    count++;
                } else {
                    count = 0;
                }
                if (count == 10) {
                    close();
                }
                if (!(message == null)) {
                    return message;
                }

                break;
            case 0:

            case 2:
                break;
        }

        return "";
    }
}
