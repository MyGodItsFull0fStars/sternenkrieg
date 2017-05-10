package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    Button testSoundBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Just a playSound example
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        testSoundBtn = (Button) findViewById(R.id.testSound);
        testSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("sound", false)) {
                    // enabled, play sound
                    System.out.println("Sound should be played");
                    mp.start();
                }
            }
        });
    }
}
