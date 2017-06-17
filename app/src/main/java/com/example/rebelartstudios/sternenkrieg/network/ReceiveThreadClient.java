package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/11.
 */

public class ReceiveThreadClient extends Thread {

    private InputStream is;
    boolean running;
    String str = "";
    Handler myhandler;
    String tag = "Client";



    public ReceiveThreadClient(Socket socket, boolean running, Handler myhandler) throws IOException {
        is = socket.getInputStream();
        this.running = running;
        this.myhandler = myhandler;
    }

    @Override
    public void run() {
        while (running) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            try {

                str = br.readLine();


            } catch (NullPointerException e) {
                running = false;
                Message msg2 = myhandler.obtainMessage();
                msg2.what = 2;
                myhandler.sendMessage(msg2);
                Log.e(tag, "NullpointerException in ReceiveThreadHost: " + e.toString());
                Thread.currentThread().interrupt();
                break;

            } catch (IOException e) {
                Log.e(tag, "IOException in ReceiveThreadHost: " + e.toString());
                Thread.currentThread().interrupt();
            }

            try {

                Message msg = myhandler.obtainMessage();
                String[] a = str.split(",");
                if (a.length == 1) {
                    msg.what = 1;
                    msg.obj = str;
                    myhandler.sendMessage(msg);
                } else {
                    msg.what = 4;
                    msg.obj = str;
                    myhandler.sendMessage(msg);
                }
            }catch (NullPointerException e){
                Log.e(tag, "NullpointerException in ReceiveThreadHost: " + e.toString());
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                Log.e(tag, "InterruptedException in ReceiveThreadHost: " + e.toString());
                Thread.currentThread().interrupt();
            }

        }
        Message msg2 = myhandler.obtainMessage();
        msg2.what = 2;
        myhandler.sendMessage(msg2);

    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public Handler getMessage(){
        return myhandler;
    }

}
