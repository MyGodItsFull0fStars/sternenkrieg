package com.example.rebelartstudios.sternenkrieg.Network;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by wenboda on 2017/6/5.
 */

public class ServersSocket extends Service {

    boolean running;
    AcceptThread mAcceptThread;
    ServerSocket mServerSocket;
    Socket socket;

    Handler mHandler;
    ReceiveThreadHost mReceiveThreadHost;
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("sfeqqweqweqweqweqw");
        running = true;

        mAcceptThread = new AcceptThread(running, mServerSocket, socket, mHandler, mReceiveThreadHost,54321);

        this.socket = mAcceptThread.getSocket();


        mAcceptThread.start();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
