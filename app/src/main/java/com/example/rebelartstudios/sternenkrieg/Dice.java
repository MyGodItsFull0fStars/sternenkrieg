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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.GameLogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.GameLogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.network.StartThread;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dice extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    protected static ImageView imageDice;
    private boolean shakeboolean = true;
    private int whoStarts;
    private TextView textscore;
    private TextView textscoreenemy;
    private int value;
    Sensoren sensoren = new Sensoren();
    DiceClass diceClass = new DiceClass();
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;

    private int gegnervalue;
    boolean finish = false;
    boolean finishEnemy = false;
    private int mode = 0; // 1 = game start, 2 = powerup
    /********************Netz**************************/
    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean phost = false; // if this is host then phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean net = false;
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

        Dice.imageDice = (ImageView) findViewById(R.id.imageDice);
        textscore = (TextView) findViewById(R.id.text_score);
        textscoreenemy = (TextView) findViewById(R.id.text_enemy_score);
        game = new GameUtilities(getApplicationContext());
        /********************Netz**************************/
        send = (Button) findViewById(R.id.senddice);
        goNext = (Button) findViewById(R.id.gonext);

        phost = stats.isPhost();
        mode = stats.getMode();
        net = stats.isNet();
        if (!phost) {
            ip = stats.getIp();
        }
        myhandler = new Myhandler();
        util = new NetworkUtilities(phost, mAcceptThread, mServerSocket, socket, myhandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();
        util.connection();

        mSensorManager.registerListener(accelSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        /********************Netz**************************/
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext.setText("Waiting for Enemy to Finish");
                util.messageSend("boolean", phost);
                finish = true;
                if (!phost) {
                    new CountDownTimer(200, 100) {
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
    }


    private SensorEventListener accelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if ("shake".equals(sensoren.accelUpdate(sensorEvent)) && shakeboolean) {
                value = diceClass.roll();
                shakeboolean = false;
                shake();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //not important
        }
    };


    public void shake() {
        switch (mode) {
            case 1:
                textscore.setText("You got:" + value + " Waiting for enemy");
                break;

            case 2:
                value += game.getDicescore();
                textscore.setText("You got:" + value + " Waiting for enemy");
                game.setDicescore(value);
                break;

            default:
                break;
        }
        util.messageSend(Integer.toString(value), phost);
        diceClass.changeDiceImage(value);
        sended = true;
        sollfinish();

    }

    public void onFinish() {
        goNext.setVisibility(View.VISIBLE);
        intent.setClass(Dice.this, Map.class);
        switch (mode) {
            case 1:
                whoStarts = diceClass.whoIsStarting(value, gegnervalue);
               game.setWhoIsStarting(whoStarts);
                if (whoStarts == 2)
                    intent.setClass(Dice.this, Dice.class);
                break;

            case 2:
                intent.setClass(Dice.this, Spielfeld.class);
                break;
            default:
                break;
        }
    }

    private void sollfinish() {
        if (sended && came) {
            onFinish();
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
            message = util.handleMessage(msg);
            if ("boolean".equals(message)) {
                finishEnemy = true;
                Log.i(Dice.class.getName(),"Boolean");
                syncClose();
            } else if (!("".equals(message))) {
                gegnervalue = Integer.parseInt(message);
                textscoreenemy.setText("Enemy got:" + gegnervalue);
                came = true;
                sollfinish();

            }
        }

    }


}
