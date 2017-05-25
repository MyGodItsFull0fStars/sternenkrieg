package com.example.rebelartstudios.sternenkrieg;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayAudio extends Service {

    MediaPlayer mp;
    SharedPreferences sharedPreferences;
    boolean soundEnabled;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        soundEnabled = sharedPreferences.getBoolean("sound", false);
        if (soundEnabled) {
            mp = MediaPlayer.create(this, R.raw.mystic);
            mp.setLooping(true);
        } else {

        }
    }

    public void onDestroy() {
        mp.stop();
    }

    // https://developer.android.com/reference/android/app/Service.html#onStartCommand(android.content.Intent,%20int,%20int)
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return START_STICKY;
    }

}
