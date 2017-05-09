//package com.example.rebelartstudios.sternenkrieg.Socket;
//
//import android.os.Handler;
//import android.os.Message;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
///**
// * Created by wenboda on 2017/5/8.
// */
//
//public class ReceiveThread extends Thread{
//
//    private InputStream is;
//    boolean running;
//    Handler myhandler;
//    String str = "";
//
//    public ReceiveThread(Socket socket, boolean running, Handler myhandler ) throws IOException {
//        is = socket.getInputStream();
//        this.running = running;
//        this.myhandler = myhandler;
//       ;
//    }
//
//    @Override
//    public void run() {
//        while (running) {
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            try {
//
//               str = br.readLine();
//
//            } catch (NullPointerException e) {
//                running = false;
//                Message msg2 = myhandler.obtainMessage();
//                msg2.what = 2;
//                myhandler.sendMessage(msg2);
////                e.printStackTrace();
//                break;
//
//            } catch (IOException e) {
////                e.printStackTrace();
//            }
//
//
//            Message msg = myhandler.obtainMessage();
//
//
//            msg.what = 1;
//
//            msg.obj = str;
//            myhandler.sendMessage(msg);
//            try {
//                sleep(400);
//            } catch (InterruptedException e) {
////                e.printStackTrace();
//            }
//
//        }
//        Message msg2 = myhandler.obtainMessage();
//        msg2.what = 2;
//        myhandler.sendMessage(msg2);
//
//    }
//}
