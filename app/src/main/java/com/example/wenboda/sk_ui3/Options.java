package com.example.wenboda.sk_ui3;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Options extends AppCompatActivity {

    ToggleButton toggleSoundBtn;
    TextView textViewStatus;
    Button playSoundBtn;

    Boolean soundStatus; // sound enabled = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        // https://developer.android.com/guide/topics/ui/controls/togglebutton.html
        toggleSoundBtn = (ToggleButton) findViewById(R.id.soundButton);
        toggleSoundBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundStatus = isChecked;
                if (soundStatus) {
                    textViewStatus.setText("Enabled");
                } else {
                    textViewStatus.setText("Disabled");
                }
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        playSoundBtn = (Button) findViewById(R.id.buttonPlaySound);
        playSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (soundStatus) {
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
