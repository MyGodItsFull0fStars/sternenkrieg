package com.example.wenboda.sk_ui3;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Options extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ToggleButton toggleSoundBtn;
    TextView textViewStatus;
    Button playSoundBtn;

    Boolean soundEnabled; // sound enabled = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        soundEnabled = sharedPreferences.getBoolean("sound", true);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        // https://developer.android.com/guide/topics/ui/controls/togglebutton.html
        toggleSoundBtn = (ToggleButton) findViewById(R.id.soundButton);
        toggleSoundBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundEnabled = isChecked;
                SharedPreferences sharedPreferences = getSharedPreferences("boolean", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                System.out.println(sharedPreferences.getAll());
                if (soundEnabled) {
                    textViewStatus.setText(getString(R.string.enabled));
                    editor.putBoolean("sound", true);
                } else {
                    textViewStatus.setText(getString(R.string.disabled));
                    editor.putBoolean("sound", false);
                }
                editor.commit();
                System.out.println(sharedPreferences.getAll());
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        playSoundBtn = (Button) findViewById(R.id.buttonPlaySound);
        playSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (soundEnabled) {
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
