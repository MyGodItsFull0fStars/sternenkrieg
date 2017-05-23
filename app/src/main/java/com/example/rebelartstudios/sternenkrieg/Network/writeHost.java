package com.example.rebelartstudios.sternenkrieg.Network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/20.
 */

public class writeHost extends Thread {

    Socket socket;
    OutputStream os = null;
    String info;
    String tag = "host";

    public writeHost(Socket socket, OutputStream os, String info){
        this.socket = socket;
        this.os = os;
        this. info = info;
    }

    @Override
    public void run() {
        try {

            os = socket.getOutputStream();//kriege socket outputstream
            String msg = info + "\n";
//                    System.out.println(msg);
            os.write(msg.getBytes("utf-8"));

            os.flush();

        } catch (IOException e) {
            Log.e(tag, "IOException in AcceptThreadHost: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(tag, "NUllException in AcceptThreadHost: " + e.toString());
        }
    }

}
