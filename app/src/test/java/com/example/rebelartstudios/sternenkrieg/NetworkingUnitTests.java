package com.example.rebelartstudios.sternenkrieg;

import android.os.Handler;

import com.example.rebelartstudios.sternenkrieg.network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.network.StartThread;
import com.example.rebelartstudios.sternenkrieg.network.WriteClient;
import com.example.rebelartstudios.sternenkrieg.network.WriteHost;

import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class NetworkingUnitTests {

    Socket socketH;
    Socket socketC;
    ServerSocket serverSocket;
    AcceptThread acceptThread;
    boolean running = true;
    Handler handlerH = new Handler();
    ReceiveThreadHost receiveThreadHost;
    StartThread startThread;
    ReceiveThreadClient receiveThreadClient;
    Handler handlerC = new Handler();
    WriteClient wc;
    WriteHost wh;
    OutputStream os;

    @Before
    public void setUp(){
        socketH = new Socket();
        socketC = new Socket();
        acceptThread  = new AcceptThread(running,serverSocket,socketH,handlerH,receiveThreadHost,12345);
        startThread = new StartThread(socketC,"10.0.2.2",receiveThreadClient,handlerC,12345);
        acceptThread.start();
        startThread.start();
        wc = new WriteClient(true,socketC,"richtig");
        wh = new WriteHost(socketH,os,"richtig");
    }
    @Test
    public void testClientServers(){
        assertEquals(acceptThread.getSocket(),socketH);
        assertEquals(startThread.getSocket(),socketC);
    }

    @Test
    public void testReceive(){
        assertEquals(startThread.getRt(),receiveThreadClient);
    }

    @Test
    public void testWriteMessage(){
        assertEquals(wc.getMessage(),"richtig");
        assertEquals(wh.getMessage(),"richtig");
    }

    @Test
    public void testWriteSocket(){
        assertEquals(wc.getSocket(),socketC);
        assertEquals(wh.getSocket(),socketH);
    }
}
