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

import static com.example.rebelartstudios.sternenkrieg.DiceClass.countDice;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getFive;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getFour;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getOne;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getSix;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getThree;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.getTwo;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setCountDice;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setFive;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setFour;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setOne;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setSix;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setThree;
import static com.example.rebelartstudios.sternenkrieg.DiceClass.setTwo;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.five;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.four;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.one;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.six;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.three;
import static com.example.rebelartstudios.sternenkrieg.R.drawable.two;

public class Dice extends AppCompatActivity {

    String tag = "Dice";

    private Sensor mSensor;
    protected ImageView imageDice;
    private boolean shakeBoolean = true;
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
    TextView statistic1;
    TextView statistic2;
    TextView statistic3;
    TextView statistic4;
    TextView statistic5;
    TextView statistic6;
    TableLayout tableDice;
    private int enemyValue;
    boolean finish = false;
    boolean finishEnemy = false;
    Intent intent = new Intent();
    ProgressBar prog1;
    ImageView diceGoto;
    ImageView diceEnemy;
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
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        diceClass = new DiceClass(getApplicationContext());
        imageDice = (ImageView) findViewById(R.id.imageDice);
        diceGoto = (ImageView) findViewById(R.id.dicegoto);
        diceGoto.setVisibility(View.INVISIBLE);
        diceEnemy = (ImageView) findViewById(R.id.diceenemy);
        diceEnemy.setVisibility(View.INVISIBLE);
        statistic = (TextView) findViewById(R.id.textWarscheinlichkeit);
        statistic1 = (TextView) findViewById(R.id.textDiceOne);
        statistic2 = (TextView) findViewById(R.id.textDiceTwo);
        statistic3 = (TextView) findViewById(R.id.textDiceThree);
        statistic4 = (TextView) findViewById(R.id.textDiceFour);
        statistic5 = (TextView) findViewById(R.id.textDiceFive);
        statistic6 = (TextView) findViewById(R.id.textDiceSix);
        tableDice = (TableLayout) findViewById(R.id.tableDice);
        goNext = (ImageView) findViewById(R.id.imageGoNext);
        pulsator = (PulsatorLayout) findViewById(R.id.pulsatorPlay);
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
                pulsator.setVisibility(View.INVISIBLE);
                String text = "Waiting for " + GameUtilities.getEnemyUsername();
                waiting.setText(text);
                waiting.setVisibility(View.VISIBLE);
                progWaiting.setVisibility(View.VISIBLE);
                syncClose();
            }
        });
    }


    private SensorEventListener acceleratorSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if ("shake".equals(sensors.accelUpdate(sensorEvent)) && shakeBoolean) {
                value = diceClass.roll();
                shakeBoolean = false;
                Log.i("Shake", "");
                shake();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //not important
        }
    };


    public void shake() {
        Log.i("shake mode", mode + "");
        switch (mode) {
            case 1:
                new CountDownTimer(2000, 100) {
                    public void onTick(long millisUntilFinished) {
                        // nothing to do here
                    }

                    @Override
                    public void onFinish() {
                        animation();
                    }

                }.start();
                break;

            case 2:

                new CountDownTimer(2000, 100) {
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        Log.i("animation+value", value + "");
                        animation();
                        value += GameUtilities.getDiceScore();
                        GameUtilities.setDiceScore(value);
                    }

                }.start();

                break;

            default:
                break;
        }
        Log.i("shake mode danach", mode + "");
        util.messageSend(Integer.toString(value), pHost);
        changeDiceImage(value);
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
                // nothing to do here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i("animation ende+came", came + "");
                textScore.setText("You got:" + value);
                prog1.setVisibility(View.VISIBLE);
                statisticVisibility();
                if (!came)
                    textScoreEnemy.setText("Waiting for " + game.getEnemyUsername());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // nothing to do here
            }
        });

    }

    public void onFinish() {
        Log.i("onFinish", "");
        intent.setClass(Dice.this, Map.class);
        switch (mode) {
            case 1:
                whoStarts = diceClass.whoIsStarting(value, enemyValue);
                game.setWhoIsStarting(whoStarts);
                if (whoStarts == 2) {
                    goNext.setImageResource(R.drawable.dice3droll);
                    intent.setClass(Dice.this, Dice.class);
                }
                break;

            case 2:
                intent.setClass(Dice.this, Gameplay.class);
                break;
            default:
                break;

        }
        new CountDownTimer(3000, 100) {
            public void onTick(long millisUntilFinished) {
                // nothing to do here
            }

            @Override
            public void onFinish() {
                Log.i("onFinish CD", "");
                goNext.setVisibility(View.VISIBLE);
                pulsator.start();

            }

        }.start();
    }

    private void sollfinish() {
        Log.i("sollfinish", "");
        if (sendBoolean && came) {
            Log.i("sollfinish if", "");
            changeDiceImageEnemy(enemyValue);
            prog1.setVisibility(View.INVISIBLE);
            diceEnemy.setVisibility(View.VISIBLE);
            onFinish();
        }
    }

    public void syncClose() {
        if (finish && finishEnemy) {
            if (!pHost) {
                new CountDownTimer(400, 100) {
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        util.close();
                        pulsator.stop();
                        startActivity(intent);

                    }

                }.start();
            } else {
                util.close();
                pulsator.stop();
                startActivity(intent);
            }

        }

    }

    private String getONE() {
        return "1: " + diceClass.getOneProbability();
    }

    private String getTWO() {
        return "2: " + diceClass.getTwoProbability();
    }

    private String getTHREE() {
        return "3: " + diceClass.getThreeProbability();
    }

    private String getFOUR() {
        return "4: " + diceClass.getFourProbability();
    }

    private String getFIVE() {
        return "5: " + diceClass.getFiveProbability();
    }

    private String getSIX() {
        return "6: " + diceClass.getSixProbability();
    }


    public void statisticVisibility() {
        statistic.setVisibility(View.VISIBLE);
        tableDice.setVisibility(View.VISIBLE);


        statistic1.setText(getONE());
        statistic2.setText(getTWO());
        statistic3.setText(getTHREE());
        statistic4.setText(getFOUR());
        statistic5.setText(getFIVE());
        statistic6.setText(getSIX());
    }


    /********************Netz**************************/


// There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {
            message = util.handleMessage(msg);
            Log.i("Message", message);
            if ("boolean".equals(message)) {
                Log.i("MessageBoolean", message);
                finishEnemy = true;
                Log.i(Dice.class.getName(), "Boolean");
                syncClose();
            } else if (!("".equals(message))) {
                Log.i("MessageValue", message);
                enemyValue = Integer.parseInt(message);
                textScoreEnemy.setText(game.getEnemyUsername() + " got:" + enemyValue);
                changeDiceImageEnemy(enemyValue);
                diceEnemy.setVisibility(View.VISIBLE);
                came = true;
                sollfinish();

            }
        }

    }

    public void changeDiceImage(int value) {

        switch (value) {
            case 1:
                imageDice.setImageResource(one);
                setOne(getOne() + 1);
                setCountDice(countDice + 1);
                break;
            case 2:
                imageDice.setImageResource(two);
                setTwo(getTwo() + 1);
                setCountDice(countDice + 1);
                break;
            case 3:
                imageDice.setImageResource(three);
                setThree(getThree() + 1);
                setCountDice(countDice + 1);
                break;
            case 4:
                imageDice.setImageResource(four);
                setFour(getFour() + 1);
                setCountDice(countDice + 1);
                break;
            case 5:
                imageDice.setImageResource(five);
                setFive(getFive() + 1);
                setCountDice(countDice + 1);
                break;
            case 6:
                imageDice.setImageResource(six);
                setSix(getSix() + 1);
                setCountDice(countDice + 1);
                break;
            default:
                break;
        }
    }

    public void changeDiceImageEnemy(int value) {

        switch (value) {
            case 1:
                diceEnemy.setImageResource(one);
                break;
            case 2:
                diceEnemy.setImageResource(two);
                break;
            case 3:
                diceEnemy.setImageResource(three);
                break;
            case 4:
                diceEnemy.setImageResource(four);
                break;
            case 5:
                diceEnemy.setImageResource(five);
                break;
            case 6:
                diceEnemy.setImageResource(six);
                break;
            default:
                break;
        }
    }

}
