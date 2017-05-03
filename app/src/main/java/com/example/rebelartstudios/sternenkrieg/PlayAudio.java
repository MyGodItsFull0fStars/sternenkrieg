package com.example.rebelartstudios.sternenkrieg;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayAudio extends Service {

    MediaPlayer mp;
    // TODO sharedPreferences
    SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
    boolean soundEnabled = sharedPreferences.getBoolean("sound", false);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        mp = MediaPlayer.create(this, R.raw.mystic);
        mp.setLooping(true);
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
