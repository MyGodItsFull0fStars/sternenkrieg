package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class wuerfeltest extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ImageView imageDice;
    private Random rng = new Random();
    private boolean shakeboolean,who_is_starting = false;
    private TextView text_score, text_score_enemy;
    private int value;
    private int counter =4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuerfeltest);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        imageDice = (ImageView) findViewById(R.id.imageDice);
        Intent intent = getIntent();
        shakeboolean = intent.getExtras().getBoolean("shake");
        text_score = (TextView) findViewById(R.id.text_score);
        text_score_enemy = (TextView) findViewById(R.id.text_enemy_score);

        mSensorManager.registerListener(AccelSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    private SensorEventListener AccelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {


            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (event.values[0] > 5) {
                    if (shakeboolean) {
                        shakeboolean=false;
                        value = rng.nextInt(6) + 1;
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
                        }
                        text_score.setText("You got:" + value + " Waiting for enemy");
                        new CountDownTimer(1000*(rng.nextInt(6) + 1), 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                int value_enemy=rng.nextInt(6) + 1;
                                text_score_enemy.setText("Enemy got:"+value_enemy);
                                if(value>value_enemy)
                                    who_is_starting=true;
                                new CountDownTimer(4500, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        switch (counter) {
                                            case 1:
                                                imageDice.setImageResource(R.drawable.one);
                                                break;
                                            case 2:
                                                imageDice.setImageResource(R.drawable.two);
                                                break;
                                            case 3:
                                                imageDice.setImageResource(R.drawable.three);
                                                break;
                                        }
                                        counter--;
                                    }

                                    @Override
                                    public void onFinish() {
                                        Intent nextScreen =  new Intent(getApplicationContext(),EndScreen.class);
                                        nextScreen.putExtra("who_is_starting",who_is_starting);
                                        startActivity(nextScreen);

                                    }
                                }.start();
                            }
                        }.start();




                    }
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
