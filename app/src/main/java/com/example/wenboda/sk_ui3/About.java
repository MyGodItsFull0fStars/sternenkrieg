package com.example.wenboda.sk_ui3;

import android.content.Context;
import android.content.SharedPreferences;
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

        //TODO // FIXME: 03.05.2017 
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final boolean condition = sharedPreferences.getBoolean("sound", true);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        testSoundBtn = (Button) findViewById(R.id.testSound);
        testSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (condition) {
                    // enabled, play sound
                    System.out.println("Sound should be played");
                    mp.start();
                } else {
                    // disabled, don't play sound
                }
            }
        });
    }
}
