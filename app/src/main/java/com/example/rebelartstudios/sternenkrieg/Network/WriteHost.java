package com.example.rebelartstudios.sternenkrieg.Network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/20.
 */

public class WriteHost extends Thread {

    Socket socket;
    OutputStream os = null;
    String info;
    String tag = "host";
    boolean t;

    public WriteHost(Socket socket, OutputStream os, String info){
        this.socket = socket;
        this.os = os;
        this. info = info;
        this.t = t;
    }

    @Override
    public void run() {


        try {

//            if (t){
//                sleep(1000);
//            }

            os = socket.getOutputStream();//kriege socket outputstream
            String msg = info + "\n";
            System.out.println("WriteHost: "+msg);
            os.write(msg.getBytes("utf-8"));

            os.flush();

        } catch (IOException e) {
            Log.e(tag, "IOException in WriteHost: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(tag, "NUllException in WriteHost: " + e.toString());
        }
//        catch (InterruptedException e) {
//            Log.e(tag, "InterruptedException in WriteHost: " + e.toString());
//        }
    }

}