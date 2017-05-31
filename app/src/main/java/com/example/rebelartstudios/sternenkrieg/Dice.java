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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    private void initializeViews(){
        imageDice = (ImageView) findViewById(R.id.imageDice);
        text_score = (TextView) findViewById(R.id.text_score);
        text_score_enemy = (TextView) findViewById(R.id.text_enemy_score);
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

}
