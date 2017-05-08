package com.example.rebelartstudios.sternenkrieg.Socket;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/8.
 */

public class ReceiveThreadServer extends Thread{
    private InputStream is = null;
    private String read;
    boolean running = true;
    Handler mHandler;

    public ReceiveThreadServer(Socket sk){
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
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
            }
            try {

                read = br.readLine();
//                System.out.println(read);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                running = false;
                Message msg2 = mHandler.obtainMessage();
                msg2.what = 2;
                mHandler.sendMessage(msg2);
//                e.printStackTrace();
                break;
            }

            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            msg.obj = read;
            mHandler.sendMessage(msg);

        }
    }
}