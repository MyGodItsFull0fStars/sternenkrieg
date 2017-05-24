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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Wuerfeltest extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuerfeltest);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        imageDice = (ImageView) findViewById(R.id.imageDice);
        text_score = (TextView) findViewById(R.id.text_score);
        text_score_enemy = (TextView) findViewById(R.id.text_enemy_score);

        mSensorManager.registerListener(AccelSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);


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

    private void changeDiceImage(int value){
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
    }

    public void shake() {
        value = rng.nextInt(6) + 1;
        changeDiceImage(value);
        text_score.setText("You got:" + value + " Waiting for enemy");
        new CountDownTimer(3000 + 1000 * (rng.nextInt(3) + 1), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                int value_enemy = rng.nextInt(6) + 1;
                text_score_enemy.setText("Enemy got:" + value_enemy);
                if (value > value_enemy) {
                    who_is_starting = 0;
                } else if(value < value_enemy){
                    who_is_starting = 1;
                }else  {
                    who_is_starting=2;
                }

                new CountDownTimer(4500, 1000) {

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


    }


}
