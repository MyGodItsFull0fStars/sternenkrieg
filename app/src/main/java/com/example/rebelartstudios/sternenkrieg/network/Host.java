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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.Dice;
import com.example.rebelartstudios.sternenkrieg.MainSocket;
import com.example.rebelartstudios.sternenkrieg.R;
import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.gamelogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.res.QRReader;
import com.example.rebelartstudios.sternenkrieg.res.animationClass;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends AppCompatActivity {

    private TextView iPtv = null;
    private Button btnQR = null;
    private Socket socket = new Socket();
    private ServerSocket mServerSocket = null;
    boolean running = true;
    private AcceptThread mAcceptThread;
    private Handler mHandler = null;
    private ReceiveThreadHost mReceiveThreadHost;
    private TextView ip;
    String ipS;
    OutputStream os = null;
    String tag = "Host";
    String emuip="0.0.0.0";
    Button btnStart;
    boolean ifStart = true;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    ImageView back;
    animationClass animationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_servers);

        mHandler = new MyHandler();
        game = new GameUtilities(getApplicationContext());
        animationClass = new animationClass();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ipS = intToIp(ipAddress);

        initializeButtons();
        initializeOnClickListeners();
        btnStart.setBackgroundColor(getResources().getColor(R.color.black_overlay));

        running = true;
        mAcceptThread = new AcceptThread(running, mServerSocket, socket, mHandler, mReceiveThreadHost, 54321);
        mAcceptThread.start();
        iPtv.setText("Warte auf Verbindung");
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

    public Handler getmHandler() {
        return mHandler;
    }

    class MyHandler extends Handler {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    try {
                        if ("exit".equals(str)) {
                            ExitHost();
                        } else if (("Bereit!").equals(str)) {
                            btnStart.setEnabled(true);
                            animationClass.glowAnimation(btnStart);
                        } else if (null != str) {
                            Log.i(tag, "Enemy username");
                            GameUtilities.setEnemyUsername(str);
                            String text = "Connected with " + GameUtilities.getEnemyUsername();
                            iPtv.setText(text);
                            socket = mAcceptThread.getSocket();
                            WriteHost writeHost = new WriteHost(socket, os, game.getUsername());
                            writeHost.start();

                        }
                    } catch (NullPointerException e) {
                        Log.e(tag, "NullpointerException in ReceiveThreadHost: " + e.toString());

                    }
                    break;
                case 0:
                    displayToast("Erfolg!");
                    break;
                case 2:
                    displayToast("Client getrennt");

                    iPtv.setText(null);
                    try {
                        socket.close();
                        mServerSocket.close();
                    } catch (IOException e) {
                        Log.e(tag, "IOException in ReceiveThreadHost: " + e.toString());
                    }
                    break;
                default:
                    break;
            }

        }

    }

    private void initializeButtons() {
        iPtv = (TextView) findViewById(R.id.tvIP);
        ip = (TextView) findViewById(R.id.ip);
        btnQR = (Button) findViewById(R.id.qr_button);
        btnStart = (Button) findViewById(R.id.btn_starten);
        back = (ImageView) findViewById(R.id.imageServerBack);

        if ((emuip).equals(ipS)) {
            ip.setText("Funktion im Emulator nicht verfügbar!");// diese Funktion geht nur Handy mit Wifi. Emulator geht nicht
        } else {
            ip.setText(ipS);
        }
    }

    private void initializeOnClickListeners() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Host.this, MainSocket.class);
                startActivity(intent);
            }
        });


        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Host.this, QRReader.class);
                intent.putExtra("IP", ipS);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "Start!";
                socket = mAcceptThread.getSocket();
                WriteHost writeHost = new WriteHost(socket, os, info);
                writeHost.start();

                Intent intentD = new Intent(Host.this, Dice.class);
                ifStart = false;
                close();
                NetworkStats.setNet(true);
                NetworkStats.setPhost(true);
                NetworkStats.setMode(1);
                animationClass.stop();
                startActivity(intentD);

            }
        });
    }

    private class onclicklistenerExit implements View.OnClickListener {
        public void onClick(View view) {
            ExitHost();
        }
    }

    public void ExitHost() {
        String info = "//exit";
        socket = mAcceptThread.getSocket();
        WriteHost writeHost = new WriteHost(socket, os, info);
        writeHost.start();

        close();
        iPtv.setText("Host beendet");
    }

    public void close() {
        try {
            mAcceptThread.setRunning(false);
            mAcceptThread.setSocket(null);
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
            displayToast("nicht Erfolg");

            btnStart.setEnabled(false);
        }

        try {
            mAcceptThread.getMReceiveThreadHost().close();
            mAcceptThread.getMServerSocket().close();
            mAcceptThread.getSocket().close();
            mAcceptThread.interrupt();
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
        } catch (IOException e) {
            Log.e(tag, "IOPointerException in Client: " + e.toString());
        }
    }

}