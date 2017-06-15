package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Dice extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    public static ImageView imageDice;
    private Random rng = new Random();
    private boolean shakeboolean = true;
    private int who_is_starting;
    private TextView text_score, text_score_enemy;
    private int value;
    Sensoren sensoren = new Sensoren();
    DiceClass diceClass = new DiceClass();
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();

    private int gegnervalue;
    boolean finish = false;
    boolean finishEnemy = false;
    int dicevalue;


    private int mode = 0; // 1 = game start, 2 = powerup
    /********************Netz**************************/
    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then Phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "Dice";
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;
    Button send;
    Button goNext;
    boolean sended = false;
    boolean came = false;
    Intent intent = new Intent();

    /********************Netz**************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        imageDice = (ImageView) findViewById(R.id.imageDice);
        text_score = (TextView) findViewById(R.id.text_score);
        text_score_enemy = (TextView) findViewById(R.id.text_enemy_score);
        /********************Netz**************************/
        send = (Button) findViewById(R.id.senddice);
        goNext = (Button) findViewById(R.id.gonext);
        /********************Netz**************************/


        mSensorManager.registerListener(AccelSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        /********************Netz**************************/

        System.out.println("Dice");
        Phost = stats.isPhost();
        System.out.println("Phost: "+ Phost);
        mode = stats.getMode();
        Net = stats.isNet();
        System.out.println("Net: " + Net);
        if (Phost == false) {
            ip = stats.getIp();
            System.out.println("Ip: " + ip);
        }


        myhandler = new Myhandler();
        util = new NetworkUtilities(Phost, mAcceptThread, mServerSocket, socket, myhandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();


        util.connection();


        /********************Netz**************************/
    }


//    @Override
//    protected void onDestroy() {
//        destroyImageView();
//        super.onDestroy();
//
//    }

    private void destroyImageView() {
        imageDice.destroyDrawingCache();
        imageDice.getBackground().setCallback(null);
        imageDice.setImageBitmap(null);
        imageDice.setImageDrawable(null);
        imageDice.setBackgroundResource(0);
    }


    private SensorEventListener AccelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Sensor mySensor = sensorEvent.sensor;
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER && shakeboolean) {

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                if (sensoren.accelUpdate(x, y, z).equals("shake")) {
                    shakeboolean = false;
                    shake();
                }
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void shake() {
        switch (mode) {
            case 1:
                value = diceClass.roll();
                util.messageSend(value + "", Phost, true);
                sended = true;
                diceClass.changeDiceImage(value);
                text_score.setText("You got:" + value + " Waiting for enemy");
                sollfinish();

                break;

            case 2:
                int valueGame = diceClass.roll();
                util.messageSend(valueGame + "", Phost, true);
                diceClass.changeDiceImage(valueGame);
                valueGame += NetworkStats.getValue();
                NetworkStats.setValue(valueGame);
                text_score.setText("You got:" + valueGame + " Waiting for enemy");
                sended = true;
                sollfinish();

                break;

            default:
                break;
        }

    }


    public void onFinish() {
        goNext.setVisibility(View.VISIBLE);
        switch (mode) {
            case 1:
                who_is_starting = diceClass.whoIsStarting(value, gegnervalue);
                NetworkStats.setWho_is_starting(who_is_starting);

                goNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish = true;
                        intent.setClass(Dice.this, EndScreen.class);
                        goNext.setText("Waiting for Enemy to Finish");
                        util.messageSend("boolean", Phost, true);
                        if (!Phost) {
                            new CountDownTimer(500, 100) {
                                public void onTick(long millisUntilFinished) {
                                    System.out.print(millisUntilFinished);
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
                break;

            case 2:
                goNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.setClass(Dice.this, Spielfeld.class);
                        finish = true;
                        goNext.setText("Waiting for Enemy to Finish");
                        util.messageSend("boolean", Phost, true);
                        if (!Phost) {
                            new CountDownTimer(500, 100) {
                                public void onTick(long millisUntilFinished) {
                                    System.out.print(millisUntilFinished);
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

                break;
        }
    }

    public void syncClose() {
        if (finish && finishEnemy) {
            util.close();
            startActivity(intent);
        }

    }


    /********************Netz**************************/


    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    int count = 0;
                    if (message == null) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (count == 3) {
                        util.close();
                    }
                    if (!(message == null)) {
                        if (message.equals("boolean")) {
                            finishEnemy = true;
                            syncClose();
                        } else {
                            gegnervalue = Integer.parseInt(message);
                            System.out.println("Gegnervalue: " + gegnervalue);
                            text_score_enemy.setText("Enemy got:" + gegnervalue);
                            came = true;
                            System.out.println("Came=True");
                        }
                    }

                    System.out.println("Message: " + message);
                    sollfinish();
                    break;
                case 0:
                    break;
                case 2:
                    break;
            }
        }

    }


    private void sollfinish() {
        if (sended && came) {
            onFinish();
        }
    }
}
