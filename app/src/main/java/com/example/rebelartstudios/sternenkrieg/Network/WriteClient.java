package com.example.rebelartstudios.sternenkrieg.Network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wenboda on 2017/5/20.
 */

public class WriteClient extends Thread {


    Socket socket;
    boolean Exit;
    StartThread st;
    String tag = "Client";
    String info;
    boolean t;

    public WriteClient(boolean Exit, Socket socket, String info) {
        this.Exit = Exit;
        this.socket = socket;

        this.info = info;
        this.t = t;

    }

    public void run() {
        OutputStream os = null;
        Log.w("CLIENT", info);
        try {

            if (t){
                sleep(1000);
            }
//            socket = st.getSocket();
            os = socket.getOutputStream();
            if (Exit) {
                Log.w("CLIENT", "write Client: " + info );
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
