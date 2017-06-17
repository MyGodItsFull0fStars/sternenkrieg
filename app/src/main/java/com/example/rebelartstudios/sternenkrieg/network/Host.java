package com.example.rebelartstudios.sternenkrieg.network;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.Dice;
import com.example.rebelartstudios.sternenkrieg.GameLogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.QR_Reader;
import com.example.rebelartstudios.sternenkrieg.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends AppCompatActivity {

    private TextView tv = null;
    private EditText et = null;
    private TextView iPtv = null;
    private Button btnSend = null;
    private Button btnAccept = null;
    private Button btnQR = null;
    private Socket socket = new Socket();
    private ServerSocket mServerSocket = null;
    boolean running = true;
    private AcceptThread mAcceptThread;
    private Handler mHandler = null;
    private Button btnServersEnd = null;
    private ReceiveThreadHost mReceiveThreadHost;
    private TextView ip;
    String ipS;
    OutputStream os = null;
    String tag = "Host";
    Button btnStarten;
    boolean ifstart = true;
    NetworkStats stats= new NetworkStats();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_servers);

        mHandler = new MyHandler();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ipS = intToIp(ipAddress);


        initializeButtons();
        btnSend.setEnabled(false); // lass Button Send unenabled
        initializeOnClickListeners();




    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public Handler getmHandler(){
        return mHandler;
    }

    class MyHandler extends Handler {

        public void handleMessage(Message msg) {


                switch (msg.what) {
                    case 1:
                        String str = (String) msg.obj;
                        tv.setText(str);
                        try {
                            if ("exit".equals(str)) {
                                ExitHost();
                            }else if (("//Bereiten").equals(str)){
                                btnStarten.setEnabled(true);
                            }
                        } catch (NullPointerException e) {
                            Log.e(tag, "NullpointerException in ReceiveThreadHost: " + e.toString());

                        }
                        break;
                    case 0:
                        iPtv.setText("Client" + msg.obj + "Verbunden");
                        displayToast("Erfolg");
                        break;
                    case 2:
                        displayToast("Client getrennt");

                        tv.setText(null);//
                        iPtv.setText(null);
                        try {
                            socket.close();
                            mServerSocket.close();
                        } catch (IOException e) {
                            Log.e(tag, "IOException in ReceiveThreadHost: " + e.toString());
                        }
                        btnAccept.setEnabled(true);
                        btnSend.setEnabled(false);
                        break;
                    default:
                        break;
                }

            }

    }

    private void initializeButtons() {
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.etSend);
        iPtv = (TextView) findViewById(R.id.tvIP);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnServersEnd = (Button) findViewById(R.id.btnHostEnd);
        ip = (TextView) findViewById(R.id.ip);
        btnQR = (Button) findViewById(R.id.qr_button);
        btnStarten = (Button)findViewById(R.id.btn_starten);


        if (("0.0.0.0").equals(ipS)) {
            ip.setText("不要用模拟器测试，否则是0。0。0。0");// diese Funktion geht nur Handy mit Wifi. Emulator geht nicht
        } else {
            ip.setText(ipS);
        }
    }

    private void initializeOnClickListeners() {

            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    running = true;

                    mAcceptThread = new AcceptThread(running, mServerSocket, socket, mHandler, mReceiveThreadHost,54321);


                    mAcceptThread.start();
                    btnSend.setEnabled(true);
                    btnAccept.setEnabled(false);
                    btnServersEnd.setEnabled(true);
                    iPtv.setText("Warte auf Verbindung");


                }
            });
            //Send
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    socket = mAcceptThread.getSocket();

                    String info = et.getText().toString();
                    writehost wh = new writehost(socket, os, info);

                    wh.start();

                    et.setText("");

                }
            });

            btnQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Host.this, QR_Reader.class);
                    intent.putExtra("IP", ipS);
                    startActivity(intent);
                }
            });

            btnStarten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info = "//Starten";
                    socket = mAcceptThread.getSocket();
                    writehost writeHost = new writehost(socket, os, info);
                    writeHost.start();
                    Intent intentD = new Intent(Host.this, Dice.class);
                    ifstart = false;
                    close();
                    stats.setNet(true);
                    stats.setPhost(true);
                    stats.setMode(1);
                    startActivity(intentD);

                }
            });

            View.OnClickListener exit = new onclicklistenerExit();
            btnServersEnd.setOnClickListener(exit);

    }

    private class onclicklistenerExit implements View.OnClickListener {

        public void onClick(View view) {
            ExitHost();
        }
    }


    public void ExitHost() {

        String info = "//exit";
        socket = mAcceptThread.getSocket();
        writehost writeHost = new writehost(socket,os,info);
        writeHost.start();

        close();
        iPtv.setText("Host beendet");
        btnSend.setEnabled(false);
        btnServersEnd.setEnabled(false);
        btnAccept.setEnabled(true);

    }

    public void close(){

        try {

            mAcceptThread.setRunning(false);

            mAcceptThread.setSocket(null);

        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
            displayToast("nicht Erfolg");


            btnAccept.setEnabled(true);
            btnStarten.setEnabled(false);
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
    }

}