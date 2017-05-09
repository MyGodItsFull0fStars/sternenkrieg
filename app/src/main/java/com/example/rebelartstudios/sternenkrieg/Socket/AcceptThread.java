package com.example.rebelartstudios.sternenkrieg.Socket;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/8.
 */

public class AcceptThread extends Thread{

    Socket socket;
    ServerSocket mServerSocket;
    Handler mHandler;
    ReceiveThreadServer mReceiveThread1;

    public AcceptThread(Socket socket, ServerSocket mServerSocket, Handler mHandler,  ReceiveThreadServer mReceiveThread1){
        this.socket =socket;
        this.mServerSocket = mServerSocket;
        this.mHandler = mHandler;
        this.mReceiveThread1 = mReceiveThread1;
    }
    @Override

    public void run() {

        try {
            mServerSocket = new ServerSocket(12345);//ein Server erstellen
            socket = mServerSocket.accept();//accept

            if (socket != null){
                test();
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = mHandler.obtainMessage();
            msg.what = 0;
            msg.obj = socket.getInetAddress().getHostAddress();
            mHandler.sendMessage(msg);
            //start receive Thread
            mReceiveThread1 = new ReceiveThreadServer(socket);
            mReceiveThread1.start();
        } catch (IOException e) {
//            e.printStackTrace();
        }catch (NullPointerException e){

        }

    }


    public boolean test(){
        return true;
    }
}

