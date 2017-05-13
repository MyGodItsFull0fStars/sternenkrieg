package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;

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
                e.printStackTrace();
            }
            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {

                read = br.readLine();
                System.out.println((String)read+running);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(!running);
            } catch (NullPointerException e) {
                running = false;
                Message msg2 = mHandler.obtainMessage();
                msg2.what = 2;
                mHandler.sendMessage(msg2);
                e.printStackTrace();
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
