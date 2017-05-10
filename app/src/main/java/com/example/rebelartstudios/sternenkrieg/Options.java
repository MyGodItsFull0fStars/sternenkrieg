package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // get sharedPreferences with name "prefs"
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        // sound is disabled by default
        soundEnabled = sharedPreferences.getBoolean("sound", false);

        // if state of toggle button is different from soundEnabled, change it
        toggleSoundBtn = (ToggleButton) findViewById(R.id.soundButton);
        if(toggleSoundBtn.isChecked() != soundEnabled) {
            toggleSoundBtn.toggle();
            System.out.println("Sound Button toggled!");
        }

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        // https://developer.android.com/guide/topics/ui/controls/togglebutton.html
        toggleSoundBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundEnabled = isChecked;
                // Put the setting into the sharedPreferences by using the editor
                SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                System.out.println(sharedPreferences.getAll());
                if (soundEnabled) {
                    // Sound was enabled
                    textViewStatus.setText(getString(R.string.enabled));
                    editor.putBoolean("sound", true);
                } else {
                    // Sound was disabled
                    textViewStatus.setText(getString(R.string.disabled));
                    editor.putBoolean("sound", false);
                }
                editor.apply();
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
                }
            }
        });

    }
}
