package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.gamelogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.network.StartThread;
import com.example.rebelartstudios.sternenkrieg.res.Sensors;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class Dice extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    protected static ImageView imageDice;
    private boolean shakeboolean = true;
    private int whoStarts;
    private TextView textScore;
    private TextView textScoreEnemy;
    private int value;
    Sensors sensors = new Sensors();
    DiceClass diceClass;
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    TextView statistic;
    TextView statistic_1;
    TextView statistic_2;
    TextView statistic_3;
    TextView statistic_4;
    TextView statistic_5;
    TextView statistic_6;
    TableLayout tableDice;
    private int enemyValue;
    boolean finish = false;
    boolean finishEnemy = false;
    Intent intent = new Intent();
    ProgressBar prog1;
    ImageView diceGoto;
    static ImageView diceEnemy;
    ImageView goNext;
    PulsatorLayout pulsator;
    ProgressBar progWaiting;
    private int mode = 0; // 1 = game start, 2 = powerup
    /********************Netz**************************/
    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myHandler;
    boolean pHost = false; // if this is host then pHost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean net = false;
    boolean sendBoolean = false;
    boolean came = false;
    TextView waiting;


    /********************Netz**************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        diceClass = new DiceClass(getApplicationContext());
        Dice.imageDice = (ImageView) findViewById(R.id.imageDice);
        diceGoto = (ImageView) findViewById(R.id.dicegoto);
        diceGoto.setVisibility(View.INVISIBLE);
        diceEnemy = (ImageView) findViewById(R.id.diceenemy);
        diceEnemy.setVisibility(View.INVISIBLE);
        statistic = (TextView) findViewById(R.id.textWarscheinlichkeit);
        statistic_1 = (TextView) findViewById(R.id.textDiceOne);
        statistic_2 = (TextView) findViewById(R.id.textDiceTwo);
        statistic_3 = (TextView) findViewById(R.id.textDiceThree);
        statistic_4 = (TextView) findViewById(R.id.textDiceFour);
        statistic_5 = (TextView) findViewById(R.id.textDiceFive);
        statistic_6 = (TextView) findViewById(R.id.textDiceSix);
        tableDice = (TableLayout) findViewById(R.id.tableDice);
        goNext = (ImageView) findViewById(R.id.imageGoNext);
        pulsator = (PulsatorLayout) findViewById(R.id.pulsatorMap);
        waiting = (TextView) findViewById(R.id.textMapWaiting);
        progWaiting = (ProgressBar) findViewById(R.id.progressBarWaiting);
        prog1 = (ProgressBar) findViewById(R.id.progressBar);
        prog1.setVisibility(View.INVISIBLE);

        textScore = (TextView) findViewById(R.id.text_score);
        textScoreEnemy = (TextView) findViewById(R.id.text_enemy_score);
        game = new GameUtilities(getApplicationContext());

        /* ********************Networking************************* */
        pHost = stats.isPhost();
        mode = stats.getMode();
        net = stats.isNet();
        if (!pHost) {
            ip = stats.getIp();
        }
        myHandler = new Myhandler();
        util = new NetworkUtilities(pHost, mAcceptThread, mServerSocket, socket, myHandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();
        util.connection();

        mSensorManager.registerListener(acceleratorSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        /* *******************Networking**************************/
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.messageSend("boolean", pHost);
                finish = true;
                if (!pHost) {
                    new CountDownTimer(200, 100) {
                        public void onTick(long millisUntilFinished) {
                            System.out.print(millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            syncClose();
                            pulsator.setVisibility(View.INVISIBLE);
                            String text = "Waiting for " + GameUtilities.getEnemyusername();

                            waiting.setText(text);
                            waiting.setVisibility(View.VISIBLE);
                            progWaiting.setVisibility(View.VISIBLE);

                        }

                    }.start();
                } else {
                    pulsator.setVisibility(View.INVISIBLE);
                    waiting.setVisibility(View.VISIBLE);
                    progWaiting.setVisibility(View.VISIBLE);
                    syncClose();
                }
            }
        });
    }


    private SensorEventListener acceleratorSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if ("shake".equals(sensors.accelUpdate(sensorEvent)) && shakeboolean) {
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
                new CountDownTimer(2000, 100) {
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        animation();
                    }

                }.start();
                break;

            case 2:
                value += GameUtilities.getDicescore();
                textScore.setText("You got:" + value);
                GameUtilities.setDicescore(value);
                break;

            default:
                break;
        }
        util.messageSend(Integer.toString(value), pHost);
        DiceClass.changeDiceImage(value);
        sendBoolean = true;
        sollfinish();

    }

    public void animation() {
        Animation scale = new ScaleAnimation(imageDice.getScaleX(), imageDice.getScaleX() / 2, imageDice.getScaleY(), imageDice.getScaleY() / 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        TranslateAnimation slideUp = new TranslateAnimation(-diceGoto.getX(), -imageDice.getX(), 0, 0);
        slideUp.setDuration(1000);
        AnimationSet animSet = new AnimationSet(true);
        animSet.setFillEnabled(true);
        animSet.addAnimation(scale);
        animSet.addAnimation(slideUp);
        imageDice.startAnimation(animSet);
        animSet.setFillAfter(true);

        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textScore.setText("You got:" + value);
                prog1.setVisibility(View.VISIBLE);
                statistikVisibility();
                if (!came)
                    textScoreEnemy.setText("Waiting for " + game.getEnemyusername());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void onFinish() {
        intent.setClass(Dice.this, Map.class);
        switch (mode) {
            case 1:
                whoStarts = diceClass.whoIsStarting(value, enemyValue);
                game.setWhoIsStarting(whoStarts);
                if (whoStarts == 2) {
                    goNext.setImageResource(R.drawable.dice3droll);
                    pulsator.setColor(Color.RED);
                    intent.setClass(Dice.this, Dice.class);
                }
                break;

            case 2:
                intent.setClass(Dice.this, Spielfeld.class);
                break;
            default:
                break;

        }
        new CountDownTimer(3000, 100) {
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                goNext.setVisibility(View.VISIBLE);
                pulsator.start();

            }

        }.start();
    }

    private void sollfinish() {
        if (sendBoolean && came) {
            diceClass.changeDiceImageEnemy(enemyValue);
            prog1.setVisibility(View.INVISIBLE);
            diceEnemy.setVisibility(View.VISIBLE);
            onFinish();
        }
    }

    public void syncClose() {
        if (finish && finishEnemy) {
            util.close();
            pulsator.stop();
            startActivity(intent);
        }

    }

    public void statistikVisibility() {
        statistic.setVisibility(View.VISIBLE);
        tableDice.setVisibility(View.VISIBLE);

        statistic_1.setText("1: " + diceClass.getOneprobability());
        statistic_2.setText("2: " + diceClass.getTwoprobability());
        statistic_3.setText("3: " + diceClass.getThreeprobability());
        statistic_4.setText("4: " + diceClass.getFourprobability());
        statistic_5.setText("5: " + diceClass.getFiveprobability());
        statistic_6.setText("6: " + diceClass.getSixprobability());
    }


    /********************Netz**************************/


    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {
            message = util.handleMessage(msg);
            if ("boolean".equals(message)) {
                finishEnemy = true;
                Log.i(Dice.class.getName(), "Boolean");
                syncClose();
            } else if (!("".equals(message))) {
                enemyValue = Integer.parseInt(message);
                textScoreEnemy.setText(game.getEnemyusername() + " got:" + enemyValue);
                diceClass.changeDiceImageEnemy(enemyValue);
                diceEnemy.setVisibility(View.VISIBLE);
                came = true;
                sollfinish();

            }
        }

    }


}
