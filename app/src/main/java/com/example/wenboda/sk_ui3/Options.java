package com.example.wenboda.sk_ui3;

import android.media.MediaPlayer;
import android.support.annotation.IdRes;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Options extends AppCompatActivity {

    ToggleButton toggleButton;
    TextView textView;
    Boolean enabled;

    Button playSoundBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        textView = (TextView) findViewById(R.id.textView3);

        // https://developer.android.com/guide/topics/ui/controls/togglebutton.html
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enabled = isChecked;
                if(enabled) {
                    textView.setText("Enabled");
                } else {
                    textView.setText("Disabled");
                }
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        playSoundBtn = (Button) findViewById(R.id.buttonPlaySound);
        playSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(enabled) {
                    // enabled, play sound
                    mp.start();
                } else {
                    // disabled, don't play sound
                }
            }
        });

    }







}
