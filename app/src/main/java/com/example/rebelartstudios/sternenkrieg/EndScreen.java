package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;
import com.example.rebelartstudios.sternenkrieg.Network.writeClient;
import com.example.rebelartstudios.sternenkrieg.Network.writeHost;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EndScreen extends AppCompatActivity {
    Button button;
    TextView who_is_starting;
    Intent intent = new Intent();


    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then Phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "End";
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;
    int i = 1;
    boolean sended = false;
    boolean finish = false;
    boolean finishEnemy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button = (Button) findViewById(R.id.button_gotoStart);
        who_is_starting = (TextView) findViewById(R.id.text_first);

        /********************Netz**************************/
        Intent intent = getIntent();
        try {
            if (intent.getStringExtra("Net").equals("t")) {
                Net = true;
            }
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Dice: " + e.toString());
        }

        if (Net) {
            // if the player is host.
            try {
                if (intent.getStringExtra("host").equals("1")) {
                    Phost = true;
                }
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Dice: " + e.toString());
            }
            //if the player is client, then needs the ip to build a new socket.


            if (Phost == false) {
                this.ip = intent.getStringExtra("ip");
            }


            myhandler = new Myhandler();

            networkbuild();

        } else {
        }
        connection();


        /********************Netz**************************/


        int value = intent.getIntExtra("who_is_starting", 0);
        if (value == 0) {
            who_is_starting.setText("You are first");
            this.intent.setClass(EndScreen.this, Map.class);
            System.out.println("EndValue: "+value);
            this.intent.putExtra("who_is_starting", value);
        } else if (value == 1) {
            who_is_starting.setText("Enemy is first");
            this.intent.setClass(EndScreen.this, Map.class);
            System.out.println("EndValue: "+1);
            this.intent.putExtra("who_is_starting", value);
        } else if (value == 2) {
            who_is_starting.setText("Tie");
            this.intent.setClass(EndScreen.this, Dice.class);
            this.intent.putExtra("mode", 1);
            button.setText("Neu Würfeln");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish = true;
                getinfofD();
                button.setText("Waiting for Enemy");

                messageSend("boolean", Phost, true);
                if (!Phost) {
                    new CountDownTimer(500, 100) {
                        public void onTick(long millisUntilFinished) {
                            System.out.println("Mili: "+millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            syncClose();
                        }

                    }.start();
                } else
                    syncClose();

            }
        });

    }

    public void connection() {
        if (Phost) {
            boolean running = true;

            mAcceptThread = new AcceptThread(running, mServerSocket, socket, myhandler, receiveThreadHost, 12345);
            mAcceptThread.start();
        }
    }

    public void syncClose() {
        if (finish && finishEnemy) {

            close();
            startActivity(intent);

        }
    }


    /********************Netz**************************/
    public void networkbuild() {
        boolean running = true;
        if (Phost) {//  if you are host, here should Button Start click.


//            messageSend("Hello",true, true);
        } else {

            this.startThread = new StartThread(socket, ip, receiveThreadClient, myhandler, 12345);
            startThread.start();

//            messageSend("Hello",false, true);
        }

    }

    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    message = (String) msg.obj;
                    int count = 0;
                    if (message == null) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (count == 3) {
                        close();
                    }
                    if (!(message == null)) {
                        if (message.equals("boolean")) {
                            finishEnemy = true;
                            syncClose();

                        }
                    }

                    System.out.println("Message: " + message);

                    break;
                case 0:
                    break;
                case 2:
                    break;
            }
        }

    }


    // Here is the messageSend method. By call this method can player message send.
    public void messageSend(String message, boolean obhost, boolean t) {
        if (obhost) {

            Socket socket1 = mAcceptThread.getSocket();

            writeHost wh = new writeHost(socket1, os, message);
            sended = true;
            System.out.println("Sended Host=True");

            wh.start();


        } else {
            Socket socket1;
            socket1 = startThread.getSocket();
            writeClient wirte = new writeClient(true, socket1, message);
            sended = true;
            System.out.println("Sended Client=True");
            wirte.start();


        }
    }


    public void close() {

        if (Phost) {

            try {

                mAcceptThread.setRunning(false);

                mAcceptThread.setSocket(null);

            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());


            }
            try {

                mAcceptThread.getmReceiveThreadHost().close();
                mAcceptThread.getmServerSocket().close();
                mAcceptThread.getSocket().close();
                mAcceptThread.interrupt();

            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());

            } catch (IOException e) {
                Log.e(tag, "IOPointerException in Client: " + e.toString());
            }
        } else {
            try {
                startThread.setRunning(false);
                socket = startThread.getSocket();
                socket.close();
                socket = null;
                startThread.setTryconnect(false);

                startThread.interrupt();
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());

            } catch (IOException e) {
                Log.e(tag, "IOException in Client: " + e.toString());
            }
        }
    }

    private void getinfofD() {
        Intent i = getIntent();
        System.out.println("Net = " + i.getStringExtra("Net"));


        try {
            if (i.getStringExtra("Net").equals("t")) {
                Net = true;
            }
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Spielfeld: " + e.toString());
        }


        if (Net) {
            // if the player is host.
            try {
                if (i.getStringExtra("host").equals("1")) {
                    Phost = true;
                    intent.putExtra("Net", "t");
                    intent.putExtra("host", "1");

                }
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Dice: " + e.toString());
            }
            //if the player is client, then needs the ip to build a new socket.
            if (Phost == false) {
                this.ip = i.getStringExtra("ip");
                intent.putExtra("Net", "t");
                intent.putExtra("ip", ip);
            }
        }
    }
}
