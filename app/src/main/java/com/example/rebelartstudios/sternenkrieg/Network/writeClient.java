package com.example.rebelartstudios.sternenkrieg.Network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/20.
 */

public class writeClient extends Thread {


    Socket socket;
    boolean Exit;
    StartThread st;
    String tag = "Client";
    String info;

    public writeClient(boolean Exit, Socket socket, StartThread st, String info) {
        this.Exit = Exit;
        this.socket = socket;
        this.st = st;
        this.info = info;

    }

    public void run() {
        OutputStream os = null;
        System.out.printf(info);
        try {
//            socket = st.getSocket();
            os = socket.getOutputStream();
            if (Exit) {
                System.out.println(info);
                os.write((info + "\n").getBytes("utf-8"));

            } else {
                os.write(("Exit" + "\n").getBytes("utf-8"));
            }

        } catch (IOException e) {
            Log.e(tag, "IOException in WriteThread: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in WriteThread: " + e.toString());


        }

    }

}
