package com.example.wenboda.sk_ui3.Socket;

/**
 * Created by wenboda on 2017/4/26.
 */

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenboda.sk_ui3.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servers extends AppCompatActivity {
    private TextView tv = null;
    private EditText et = null;
    private TextView IPtv = null;
    private Button btnSend = null;
    private Button btnAcept = null;
    private Socket socket;
    private ServerSocket mServerSocket = null;
    private boolean running = false;
    private AcceptThread mAcceptThread;
    private ReceiveThread mReceiveThread;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.etSend);
        IPtv = (TextView) findViewById(R.id.tvIP);
        btnAcept = (Button) findViewById(R.id.btnAccept);
        btnSend = (Button) findViewById(R.id.btnSend);
        mHandler = new MyHandler();
        btnSend.setEnabled(false);//lass Button Send unenabled
        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAcceptThread = new AcceptThread();
                running = true;
                mAcceptThread.start();
                btnSend.setEnabled(true);
                IPtv.setText("Warte auf Verbindung");
                btnAcept.setEnabled(false);

            }
        });
        //Send
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream os = null;
                try {
                    os = socket.getOutputStream();//kriege socket outputstream
                    String msg = et.getText().toString()+"\n";
//                    System.out.println(msg);
                    os.write(msg.getBytes("utf-8"));
                    et.setText("");
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    displayToast("Kann nicht verbinden");
                }
            }
        });
    }
    //Server
    private class AcceptThread extends Thread{
        @Override
        public void run() {
//           while (running) {
            try {
                mServerSocket = new ServerSocket(12345);//ein Server erstellen
                socket = mServerSocket.accept();//accept
//                System.out.println("erfolg");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                msg.obj = socket.getInetAddress().getHostAddress();
                mHandler.sendMessage(msg);
                //start receive Thread
                mReceiveThread = new ReceiveThread(socket);
                mReceiveThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
//           }
        }
    }


    private class ReceiveThread extends Thread{
        private InputStream is = null;
        private String read;

        public ReceiveThread(Socket sk){
            try {
                is = sk.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (running) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {

                    read = br.readLine();
                    System.out.println(read);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    running = false;
                    Message msg2 = mHandler.obtainMessage();
                    msg2.what = 2;
                    mHandler.sendMessage(msg2);
                    e.printStackTrace();
                    break;
                }

                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.obj = read;
                mHandler.sendMessage(msg);

            }
        }
    }

    private void displayToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String str = (String) msg.obj;
                    tv.setText(str);
                    break;
                case 0:
                    IPtv.setText("Client"+msg.obj+"Verbunden");
                    displayToast("Erfolg");
                    break;
                case 2:
                    displayToast("Client getrennt");

                    tv.setText(null);//
                    IPtv.setText(null);
                    try {
                        socket.close();
                        mServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btnAcept.setEnabled(true);
                    btnSend.setEnabled(false);
                    break;
            }
        }
    }


}