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
    boolean t;

    public writeClient(boolean Exit, Socket socket,  String info, boolean t) {
        this.Exit = Exit;
        this.socket = socket;

        this.info = info;
        this.t = t;

    }

    public void run() {
        OutputStream os = null;
        System.out.printf(info);
        try {

            if (t){
                sleep(1000);
            }
//            socket = st.getSocket();
            os = socket.getOutputStream();
            if (Exit) {
                System.out.println("wirte Client: "+info);
                os.write((info + "\n").getBytes("utf-8"));

            } else {
                os.write(("Exit" + "\n").getBytes("utf-8"));
            }

        } catch (IOException e) {
            Log.e(tag, "IOException in WriteThread: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in WriteThread: " + e.toString());


        } catch (InterruptedException e) {
            Log.e(tag, "InterruptedException in WriteThread: " + e.toString());
        }

    }

}
