package com.example.rebelartstudios.sternenkrieg.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class StartThread extends Thread {

    private Socket socket;
    private String ip;
    private ReceiveThreadClient rt;
    private boolean running = true;
    private Handler myHandler;
    private String tag = "Client";
    private boolean tryConnect = true;
    private int port;

    public StartThread(Socket socket, String ip, ReceiveThreadClient rt, Handler myHandler, int port) {
        this.socket = socket;
        this.ip = ip;
        this.rt = rt;
        this.myHandler = myHandler;
        this.port = port;
    }

    @Override
    public void run() {

        while (tryConnect) {
            try {
                socket = new Socket(ip, port);
                System.out.println("Connected" + socket);
                Log.i(StartThread.class.getName(), "Connect success");
                tryConnect = false;
                rt = new ReceiveThreadClient(socket, running, myHandler);
                rt.start();
                running = true;
                if (socket.isConnected()) {
                    Message msg0 = myHandler.obtainMessage();
                    msg0.what = 0;
                    myHandler.sendMessage(msg0);
                }
            } catch (IOException e) {
                Log.e(tag, "IOException in StartThread: " + e.toString());
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ReceiveThreadClient getRt() {
        return this.rt;
    }

    public void closeSocket() {
        try {
            this.socket.close();
            this.socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setTryConnect(boolean tryConnect) {
        this.tryConnect = tryConnect;
        rt.setRunning(false);
    }

}
