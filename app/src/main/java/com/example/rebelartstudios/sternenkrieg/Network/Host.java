package com.example.rebelartstudios.sternenkrieg.Network;

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

import com.example.rebelartstudios.sternenkrieg.QR_Reader;
import com.example.rebelartstudios.sternenkrieg.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends AppCompatActivity {
    private TextView tv = null;
    private EditText et = null;
    private TextView IPtv = null;
    private Button btnSend = null;
    private Button btnAccept = null;
    private Button btnQR = null;
    public Socket socket = new Socket();
    private ServerSocket mServerSocket = null;
    boolean running = false;
    private AcceptThread mAcceptThread;
    private ReceiveThreadHost mReceiveThreadHost;
    private Handler mHandler = null;
    private Button btnServersEnd = null;
    private TextView ip;
    String ipS;
    OutputStream os = null;
    String tag = "Host";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_servers);

        mHandler = new MyHandler();


        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
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

    class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    tv.setText(str);
                    try {
                        if (str.equals("Exit")) {
                            ExitHost();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.printf("nicht");
                    }
                    break;
                case 0:
                    IPtv.setText("Client" + msg.obj + "Verbunden");
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
                    btnAccept.setEnabled(true);
                    btnSend.setEnabled(false);
                    break;
            }
        }
    }

    private void initializeButtons() {
        tv = (TextView) findViewById(R.id.tv);
        et = (EditText) findViewById(R.id.etSend);
        IPtv = (TextView) findViewById(R.id.tvIP);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnServersEnd = (Button) findViewById(R.id.btnHostEnd);
        ip = (TextView) findViewById(R.id.ip);
        btnQR = (Button) findViewById(R.id.qr_button);

        if (ipS.equals("0.0.0.0")) {
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

                mAcceptThread = new AcceptThread(running, mServerSocket, socket, mHandler, mReceiveThreadHost);


                mAcceptThread.start();
                btnSend.setEnabled(true);
                btnAccept.setEnabled(false);
                btnServersEnd.setEnabled(true);
                IPtv.setText("Warte auf Verbindung");


            }
        });
        //Send
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                socket = mAcceptThread.getSocket();

                String info = et.getText().toString();
                writeHost wh = new writeHost(socket, os, info);

                wh.start();

                et.setText("");

//                try {
//                    socket = mAcceptThread.getSocket();
//                    os = socket.getOutputStream();//kriege socket outputstream
//                    String msg = et.getText().toString() + "\n";
////                    System.out.println(msg);
//                    os.write(msg.getBytes("utf-8"));
//                    et.setText("");
//                    os.flush();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (NullPointerException e) {
//                    displayToast("Kann nicht verbinden");
//                }
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

        View.OnClickListener exit = new onclicklistenerExit();
        btnServersEnd.setOnClickListener(exit);
    }

    private class onclicklistenerExit implements View.OnClickListener {
        public void onClick(View view) {
            ExitHost();
        }
    }


    public void ExitHost() {
        try {
            mAcceptThread.setRunning(false);
            mAcceptThread.setSocket(null);
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
            displayToast("nicht Erfolg");
        }
        try {
            mAcceptThread.interrupt();
            mAcceptThread.closeServers();
            IPtv.setText("Host beendet");
            btnSend.setEnabled(false);
            btnServersEnd.setEnabled(false);
            btnAccept.setEnabled(true);
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
        }

    }


}