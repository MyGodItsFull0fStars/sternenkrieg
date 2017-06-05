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
import android.widget.Toast;

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
import java.util.Random;

public class Dice extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ImageView imageDice;
    private Random rng = new Random();
    private boolean shakeboolean = true;
    private int who_is_starting;
    private TextView text_score, text_score_enemy;
    private int value;
    private int counter = 4;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 690;

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
    int i = 1;
    Button startdice;
    boolean sended;
    boolean came;
    /********************Netz**************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuerfeltest);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initializeViews();

        mSensorManager.registerListener(AccelSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Bundle b = getIntent().getExtras();
        mode = b.getInt("mode");
        Log.d(this.getLocalClassName(), ""+mode);
        /********************Netz**************************/
        Intent intent = getIntent();
        try{
            if (intent.getStringExtra("Net").equals("t")){
                Net  = true;
            }
        }catch (NullPointerException e){
            Log.e(tag, "NullPointerException in Dice: " + e.toString());
        }

        if (Net){
            // if the player is host.
            try{
                if (intent.getStringExtra("host").equals("1")){
                    Phost = true;
                }
            }catch (NullPointerException e){
                Log.e(tag, "NullPointerException in Dice: " + e.toString());
            }
                //if the player is client, then needs the ip to build a new socket.

            System.out.println("Phost = " + Phost);

            if (Phost == false){
                    this.ip = intent.getStringExtra("ip");
            }


            myhandler = new Myhandler();

            networkbuild();

        }else {
            displayToast("Kein Internet verbinden");
        }


        startdice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean running = true;
                System.out.println("Host,host,hot "+ i++);

                mAcceptThread = new AcceptThread(running,mServerSocket,socket,myhandler,receiveThreadHost,12345);
                mAcceptThread.start();
                System .out.println("Host socket: "+mAcceptThread.getSocket());
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Phost)
                    messageSend("Hello",true, false);
                else
                    messageSend("Hello",false, false);
            }
        });

        /********************Netz**************************/
    }

    private void initializeViews(){
        imageDice = (ImageView) findViewById(R.id.imageDice);
        text_score = (TextView) findViewById(R.id.text_score);
        text_score_enemy = (TextView) findViewById(R.id.text_enemy_score);
        /********************Netz**************************/
        send = (Button)findViewById(R.id.senddice);
        startdice = (Button)findViewById(R.id.startdice);
        /********************Netz**************************/

    }

    private SensorEventListener AccelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Sensor mySensor = sensorEvent.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER && shakeboolean) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        shakeboolean = false;
                        shake();
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * Changes the dice image using an integer value corresponding with the dice number
     */
    public void changeDiceImage(int value){
        switch (value) {
            case 1:
                imageDice.setImageResource(R.drawable.one);
                break;
            case 2:
                imageDice.setImageResource(R.drawable.two);
                break;
            case 3:
                imageDice.setImageResource(R.drawable.three);
                break;
            case 4:
                imageDice.setImageResource(R.drawable.four);
                break;
            case 5:
                imageDice.setImageResource(R.drawable.five);
                break;
            case 6:
                imageDice.setImageResource(R.drawable.six);
                break;
            default:
                break;
        }
    }

    public void shake() {
        switch(mode) {
            case 1:
                // TODO fix values
                //value = rng.nextInt(6) + 1;
                value = 6; // TODO this is only to make sure we start
                changeDiceImage(value);
                text_score.setText("You got:" + value + " Waiting for enemy");
                // new CountDownTimer(3000 + 1000 * (rng.nextInt(3) + 1), 1000) {
                new CountDownTimer(2000, 1000) { // TODO temp change to speed up

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        //final int value_enemy = rng.nextInt(6) + 1;
                        final int value_enemy = 1; // TODO this is only to make sure we start
                        text_score_enemy.setText("Enemy got:" + value_enemy);

                        if (value > value_enemy) {      // Player starts
                            who_is_starting = 0;
                        } else if(value < value_enemy){ // Enemy starts
                            who_is_starting = 1;
                        }else  {
                            who_is_starting=2;          // Deuce, both must roll the dice again
                        }

                        new CountDownTimer(2000, 1000) { // TODO 2000 instead of 4500 to speed up

                            @Override
                            public void onTick(long millisUntilFinished) {
                                changeDiceImage(counter);
                                counter--;
                            }

                            @Override
                            public void onFinish() {
                                Intent nextScreen = new Intent(getApplicationContext(), EndScreen.class);
                                nextScreen.putExtra("who_is_starting", who_is_starting);

                                close();

                                startActivity(nextScreen);

                            }
                        }.start();
                    }
                }.start();
                break;

            case 2:
                value = rng.nextInt(6) + 1;
                changeDiceImage(value);
                text_score.setText("You got:" + value);

                new CountDownTimer(3000, 1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(Dice.this, PowerUp.class);
                        intent.putExtra("points", value);
                        startActivity(intent);
                    }

                }.start();
                break;

            default:
                break;
        }

    }


    /********************Netz**************************/
    public void networkbuild(){
        boolean running = true;
        if (Phost){//  if you are host, here should Button Start click.


//            messageSend("Hello",true, true);
        }else {
            System.out.println("Client,client,cline");
            System.out.println("Dice ip = "+ ip);
            displayToast("Client,client,cline");
            this.startThread = new StartThread(socket,ip,receiveThreadClient,myhandler,12345);
            startThread.start();
            System .out.println("net: "+startThread.getSocket());

//            messageSend("Hello",false, true);
        }

    }
    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler{


        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    displayToast(message);
                    System.out.println(message);
                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
                    displayToast("!");


                    break;
            }
        }

    }
    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    // Here is the messageSend methode. By call this methode can player message send.
    public void messageSend(String message,boolean obhost, boolean t){
        if (obhost) {

            Socket socket1 = mAcceptThread.getSocket();


            System.out.println("A message socket : "+socket1);

            writeHost wh = new writeHost(socket1, os, message, t);

            wh.start();


        }else{
            Socket socket1;
            socket1 = startThread.getSocket();
            System.out.println("S message socket : "+socket1);
            writeClient wirte = new writeClient(true, socket1, message,t);

            wirte.start();

        }
    }

    public void close(){



        if (Phost){

            try {

                mAcceptThread.setRunning(false);

                mAcceptThread.setSocket(null);

            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());
                displayToast("nicht Erfolg");


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
        }else{
            try {
                startThread.setRunning(false);
                socket = startThread.getSocket();
                socket.close();
                socket = null;
                startThread.setTryconnect(false);

                startThread.interrupt();
            } catch (NullPointerException e) {
                Log.e(tag, "NullPointerException in Client: " + e.toString());
                displayToast("nicht Erfolg");
            } catch (IOException e) {
                Log.e(tag, "IOException in Client: " + e.toString());
            }
        }
    }
    /********************Netz**************************/
}
