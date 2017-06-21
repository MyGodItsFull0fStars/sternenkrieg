package com.example.rebelartstudios.sternenkrieg.network;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.net.Socket;

public class Client extends AppCompatActivity implements View.OnClickListener {
    private EditText IPet;
    private Handler myHandler;
    private Socket socket;
    private Button btnStart;
    private Button btnStop;
    private StartThread st;
    private ReceiveThreadClient rt;
    String tag = "Client";
    String ip;
    String ipFromQR;
    Button btnQR;
    Bundle extras;
    boolean ifStart = true;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    ImageView back;
    animationClass animationClass;
    TextView textConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeButtonsViews();
        game = new GameUtilities(getApplicationContext());
        animationClass = new animationClass();
        animationClass.glowAnimation(btnStart);
        setButtonOnStartState(true);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnQR.setOnClickListener(this);

        myHandler = new myhandlerclient();

        extras = getIntent().getExtras();

        if (extras == null) {
            ipFromQR = System.getProperty("myapplication.ip");
        } else {
            IPet.setText(extras.getString("QR"));
            this.ip = IPet.toString();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client.this, MainSocket.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnStart:
                this.ip = IPet.getText().toString();
                st = new StartThread(socket, ip, rt, myHandler, 54321);
                st.start();
                animationClass.stop();
                setButtonOnStartState(false);
                btnStart.clearAnimation();
                new CountDownTimer(400, 100) {
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        try {
                            socket = st.getSocket();
                        } catch (NullPointerException e) {
                            Log.e(tag, "NullPointerException in Client: " + e.toString());
                            Log.i(tag, "Crash");
                        }

                        try {
                            Thread write = new WriteClient(true, socket, game.getUsername());
                            write.start();
                        } catch (NullPointerException e) {
                            Log.e(tag, "NullPointerException in Client: " + e.toString());
                        }

                        new CountDownTimer(300, 100) {
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                try {
                                    Thread write = new WriteClient(true, socket, "Bereit!");
                                    write.start();
                                } catch (NullPointerException e) {
                                    Log.e(tag, "NullPointerException in Client: " + e.toString());
                                }
                            }
                        }.start();
                    }
                }.start();
                break;
            case R.id.btnStop:
                exit();
                break;
            case R.id.QRClient:
                Intent intent = new Intent(Client.this, QRReader.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Enables or disables the buttons using a boolean
     *
     * @param flag
     */
    private void setButtonOnStartState(boolean flag) {
        btnStop.setEnabled(!flag);
        btnStart.setEnabled(flag);
        IPet.setEnabled(flag);
    }

    /**
     * Handles the messaging, as well as the feedback while connecting/disconnecting with the server
     */
    class myhandlerclient extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    Log.i(Client.class.getName(), "Client: " + str);
                    if (("exit").equals(str)) {
                        exit();
                    } else if (("Start!").equals(str)) {
                        Intent intentD = new Intent(Client.this, Dice.class);

                        ifStart = false;
                        close();
                        NetworkStats.setIp(ip);
                        NetworkStats.setNet(true);
                        NetworkStats.setPhost(false);
                        NetworkStats.setMode(1);
                        startActivity(intentD);

                    } else if (null != str) {
                        GameUtilities.setEnemyUsername(str);
                        String text = "Connected with: " + GameUtilities.getEnemyUsername();
                        textConnection.setText(text);
                    }
                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
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
        IPet = (EditText) findViewById(R.id.IPet);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnQR = (Button) findViewById(R.id.QRClient);
        back = (ImageView) findViewById(R.id.imageClientBack);
        textConnection = (TextView) findViewById(R.id.textconnected);
        this.ip = IPet.getText().toString();
    }

    private void exit() {
        rt = st.getRt();
        try {
            rt.setRunning(false);
            Thread write = new WriteClient(false, socket, "exit");
            write.start();
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
        }

        setButtonOnStartState(true);
        close();
    }

    public void close() {
        try {
            st.setRunning(false);
            socket = st.getSocket();
            socket.close();
            socket = null;
            st.setTryConnect(false);
            btnStart.setEnabled(true);

            st.interrupt();
        } catch (NullPointerException e) {
            Log.e(tag, "NullPointerException in Client: " + e.toString());
            displayToast("Fehlgeschlagen");
        } catch (IOException e) {
            Log.e(tag, "IOException in Client: " + e.toString());
        }
    }

}