package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/10.
 */

public class ReceiveThreadHost extends Thread {


    private InputStream is = null;
    private String read;
    private boolean running;
    private Handler mHandler;
    String tag = "Host";

    public ReceiveThreadHost(Socket sk, boolean running, Handler mHandler){
        this.running = running;
        this.mHandler = mHandler;

        try {
            is = sk.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        while (running) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                Log.e(tag, "InterruptedException in ReceiveThreadHost: " + e.toString());
            }
            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e(tag, "UnsupportedEncodingException in ReceiveThreadHost: " + e.toString());
            }

            try {

                read = br.readLine();
                System.out.println((String)read+running);
            } catch (IOException e) {
                Log.e(tag, "IOException in ReceiveThreadHost: " + e.toString());
                System.out.println(!running);
            } catch (NullPointerException e) {
                running = false;
                Message msg2 = mHandler.obtainMessage();
                msg2.what = 2;
                mHandler.sendMessage(msg2);
                Log.e(tag, "NULLException in ReceiveThreadHost: " + e.toString());
                break;
            }

//            Message msg = mHandler.obtainMessage();
            Message msg = new Message();
            msg.what = 1;
            msg.obj = read;
            mHandler.sendMessage(msg);

        }

    }

    public void setRunning(boolean running){
        this.running = running;
    }

}
