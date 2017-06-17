package com.example.rebelartstudios.sternenkrieg;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created by Chris on 14.06.2017.
 */

public class Sensoren {
    private static final int SHAKE_THRESHOLD = 690;
    private long lastUpdate = 0;
    private float lastx;
    private float lasty;
    private float lastz;

    public int lightSensor(){

        return 0;
    }

    public String accelUpdate(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = curTime - lastUpdate;
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - lastx - lasty - lastz) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    return "shake";
                }

                lastx = x;
                lasty = y;
                lastz = z;
            }


        }
        return "";
    }
}
