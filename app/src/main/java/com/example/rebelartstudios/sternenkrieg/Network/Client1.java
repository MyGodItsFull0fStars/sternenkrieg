package com.example.rebelartstudios.sternenkrieg.Network;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.rebelartstudios.sternenkrieg.Map;
import com.example.rebelartstudios.sternenkrieg.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.QR_Reader;
import com.example.rebelartstudios.sternenkrieg.R;
import com.example.rebelartstudios.sternenkrieg.Spielfeld;

import java.io.IOException;
import java.net.Socket;

public class Client1 extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private EditText et;
    private EditText IPet;
    private Handler myhandler;
    private Socket socket;
    private String str = "";
    boolean running = false;
    private Button btnSend;
    private Button btnStart;
    private Button btnStop;
    private StartThread st ;
    private ReceiveThreadClient rt;
    String tag = "Client";
    String ip;
    String ipFromQR;
    boolean Exit = true;
    Button btnQR;
    Bundle extras;
    Button btnBeretien;
    boolean ifstart = true;
    NetworkStats stats= new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeButtonsViews();

        setButtonOnStartState(true);


        btnSend.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnQR.setOnClickListener(this);
        btnBeretien.setOnClickListener(this);

        myhandler = new myHandlerClient();

        extras = getIntent().getExtras();

        if (extras == null) {
            ipFromQR = "127.0.0.0"; // localhost
        } else {
            IPet.setText(extras.getString("QR"));
            this.ip = IPet.toString();
        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnStart:
                this.ip = IPet.getText().toString();
                st = new StartThread(socket, ip, rt, myhandler,54321);
                st.start();
                setButtonOnStartState(false);

                break;
            case R.id.btnSend:

                String info = et.getText().toString();
                socket = st.getSocket();
                Thread wirte = new writeClient(true, socket, info);

                wirte.start();
                et.setText("");

                break;
            case R.id.btnStop:
                exit();
                break;
            case R.id.QRClient:
                Intent intent = new Intent(Client1.this, QR_Reader.class);
                startActivity(intent);
            case R.id.btn_bereiten:
                try{  socket = st.getSocket();}catch (NullPointerException e){
                    Log.e(tag, "NullPointerException in Client: " + e.toString());
                }


                try {
                    info = "//Bereiten";
                    wirte = new writeClient(true, socket, info);
                    wirte.start();
                } catch (NullPointerException e) {
                    Log.e(tag, "NullPointerException in Client: " + e.toString());
                }

            default:
                break;
        }

    }


    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /**
     * Enables or disables the buttons using a boolean
     * @param flag
     */
    private void setButtonOnStartState(boolean flag) {
        btnSend.setEnabled(!flag);
        btnStop.setEnabled(!flag);
        btnStart.setEnabled(flag);
        IPet.setEnabled(flag);
    }

    /**
     * Handles the messaging, as well as the feedback while connecting/disconnecting with the server
     */
    class myHandlerClient extends Handler {
        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 1:

                    String str = (String) msg.obj;
                    System.out.println("Client: " + msg.obj);
                    tv.setText(str);
                    if (str.equals("Exit")) {
                        exit();
                    } else if (str.equals("//Starten")) {
                        Intent intentD = new Intent(Client1.this, Dice.class);
                        Intent intentS = new Intent(Client1.this, Spielfeld.class);
                        Intent intentM = new Intent(Client1.this, Map.class);

                        ifstart = false;
                        close();
                        stats.setIp(ip);
                        stats.setNet(true);
                        stats.setPhost(false);
                        stats.setMode(1);
                        startActivity(intentD);


                    }
                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
//                    displayToast("Der Server wurde getrennt");
                    tv.setText(null);
                    setButtonOnStartState(true);
                    break;
                default:
                    break;
            }

        }

    }


    /**
     * Initialization of the Buttons, TextView and EditTexts
     * Also the value ip gets the content from IPet
     */
    private void initializeButtonsViews() {
        tv = (TextView) findViewById(R.id.TV);
        et = (EditText) findViewById(R.id.et);
        IPet = (EditText) findViewById(R.id.IPet);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnQR = (Button) findViewById(R.id.QRClient);
        btnBeretien = (Button)findViewById(R.id.btn_bereiten);

        this.ip = IPet.getText().toString();
    }

    private void exit(){
        rt = st.getRt();
        try{ rt.setRunning(false);
            String Exit = "Exit";
            Thread wirte = new writeClient(false,socket,Exit);
            wirte.start();
        }catch(NullPointerException e){
            Log.e(tag, "NullPointerException in Client: " + e.toString());
        }


        setButtonOnStartState(true);
        close();
    }

    public void close(){
        try {
            st.setRunning(false);
            socket = st.getSocket();
            socket.close();
            socket = null;
            st.setTryconnect(false);
            btnStart.setEnabled(true);
            btnBeretien.setEnabled(false);

            st.interrupt();
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
            displayToast("nicht Erfolg");
        } catch (IOException e) {
            Log.e(tag, "IOException in Client: " + e.toString());
        }
    }
}