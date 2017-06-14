package com.example.rebelartstudios.sternenkrieg;

/**
 * Created by Chris on 14.06.2017.
 */

public class Sensoren {
    private static final int SHAKE_THRESHOLD = 690;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    public int LightSensor(){

        return 0;
    }

    public String accelUpdate(float x, float y, float z){

        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                return "shake";
            }

            last_x = x;
            last_y = y;
            last_z = z;
        }

        return "";
    }
}
