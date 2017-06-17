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
    private String read="";
    private boolean running;
    private Handler mHandler;
    String tag = "Host";
    Socket sk;

    public ReceiveThreadHost(Socket sk, boolean running, Handler mHandler) {
        this.sk = sk;
        this.running = running;
        this.mHandler = mHandler;

        try {
            is = sk.getInputStream();
        } catch (IOException e) {
            Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Log.i(ReceiveThreadHost.class.getName(), "ReceiveTH running = " + running);
        while (running) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                Log.e(tag, "InterruptedException in AcceptThreadHost: " + e.toString());
                Thread.currentThread().interrupt();
            }
            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e(tag, "UnsupportedException in AcceptThreadHost: " + e.toString());
            }

            try {
                // braucht man laut Jenkins, Fehler ist dann dass die erste Message von Client nicht gesendet wird
               //if (null!=br.readLine())
                    read = br.readLine();


            } catch (NullPointerException e) {
                running = false;
                Message msg2 = mHandler.obtainMessage();
                msg2.what = 2;
                mHandler.sendMessage(msg2);
                Log.e(tag, "NullpointerException in AcceptThreadHost: " + e.toString());
                throw new RuntimeException(e);

            }catch (IOException e) {
                Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
                throw new RuntimeException(e);
            }

            try {
                Message msg = new Message();
                String[] a = read.split(",");
                if (a.length == 1) {
                    msg.what = 1;
                    msg.obj = read;
                    mHandler.sendMessage(msg);
                } else {
                    msg.what = 4;
                    msg.obj = read;
                    mHandler.sendMessage(msg);
                }

            } catch (NullPointerException e) {
                Log.e(tag, "NullpointerException in AcceptThreadHost: " + e.toString());
            }

        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void close() {
        this.running = false;
    }

    public Socket getSk() {
        return sk;
    }

}
