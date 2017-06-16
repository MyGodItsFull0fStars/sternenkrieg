package com.example.rebelartstudios.sternenkrieg.network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/20.
 */

public class writeclient extends Thread {


    Socket socket;
    boolean exit;
    String tag = "Client";
    String info;
    boolean t;

    public writeclient(boolean exit, Socket socket, String info) {
        this.exit = exit;
        this.socket = socket;
        this.info = info;
    }

    @Override
    public void run() {
        OutputStream os = null;
        Log.w("CLIENT", info);
        try {

            if (t){
                sleep(1000);
            }
            os = socket.getOutputStream();
            if (exit) {
                Log.w("CLIENT", "write Client: " + info );
                os.write((info + "\n").getBytes("utf-8"));

            } else {
                os.write(("exit" + "\n").getBytes("utf-8"));
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
