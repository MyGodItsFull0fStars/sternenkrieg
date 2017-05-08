package com.example.rebelartstudios.sternenkrieg;

import android.os.Handler;

import com.example.rebelartstudios.sternenkrieg.Socket.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Socket.Client;
import com.example.rebelartstudios.sternenkrieg.Socket.ReceiveThread;
import com.example.rebelartstudios.sternenkrieg.Socket.ReceiveThreadServer;
import com.example.rebelartstudios.sternenkrieg.Socket.Servers;
import com.example.rebelartstudios.sternenkrieg.Socket.StartThread;

import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    ReceiveThread rt;
    boolean running = true;
    Socket socket = new Socket();
    ServerSocket mServerSocket;
    Handler mHandler;
    ReceiveThreadServer mReceiveThread1;


//    @Test
//    public void test_ClientServers(){
//        AcceptThread acceptThread = new AcceptThread(socket,mServerSocket,mHandler,mReceiveThread1);
//        acceptThread.run();
//        String ip = "127.0.1.1";
//        StartThread startThread = new StartThread(ip, rt,running,mHandler);
//        startThread.run();
//        boolean a = acceptThread.test();
//        assertEquals(true, a);
//    }



}