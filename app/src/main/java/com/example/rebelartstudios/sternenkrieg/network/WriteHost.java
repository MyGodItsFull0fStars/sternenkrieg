package com.example.rebelartstudios.sternenkrieg.network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WriteHost extends Thread {

    Socket socket;
    OutputStream os = null;
    String info;
    String tag = "host";

    public WriteHost(Socket socket, OutputStream os, String info) {
        this.socket = socket;
        this.os = os;
        this.info = info;
    }

    @Override
    public void run() {
        try {
            os = socket.getOutputStream();//kriege socket outputstream
            String msg = info + "\n";
            System.out.println("WriteHost: " + msg);
            os.write(msg.getBytes("utf-8"));
            os.flush();

        } catch (IOException e) {
            Log.e(tag, "IOException in WriteHost: " + e.toString());
        } catch (NullPointerException e) {
            Log.e(tag, "NUllException in WriteHost: " + e.toString());
        }
    }

    public String getMessage() {
        return info;
    }

    public Socket getSocket() {
        return socket;
    }

}
