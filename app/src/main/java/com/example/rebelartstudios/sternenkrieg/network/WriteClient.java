package com.example.rebelartstudios.sternenkrieg.network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WriteClient extends Thread {

    Socket socket;
    boolean exit;
    String tag = "Client";
    String info;

    public WriteClient(boolean exit, Socket socket, String info) {
        this.exit = exit;
        this.socket = socket;
        this.info = info;
    }

    @Override
    public void run() {
        OutputStream os = null;
        Log.w("CLIENT", info);
        try {
            os = socket.getOutputStream();
            if (exit) {
                Log.w("CLIENT", "write Client: " + info);
                os.write((info + "\n").getBytes("utf-8"));
            } else {
                os.write(("exit" + "\n").getBytes("utf-8"));
            }

        } catch (IOException e) {
            Log.e(tag, "IOException in WriteThread: " + e.getMessage(), e);
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in WriteThread: " + e.getMessage(), e);
        }
    }

    public String getMessage() {
        return info;
    }

    public Socket getSocket() {
        return socket;
    }

}
