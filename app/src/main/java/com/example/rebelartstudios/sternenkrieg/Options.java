package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Options extends AppCompatActivity {
    ImageView back;


    SharedPreferences sharedPreferences;

    ToggleButton toggleSoundBtn;
    TextView textViewStatus;
    Button playSoundBtn;
    Button name;

    Boolean soundEnabled; // sound enabled = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        back = (ImageView) findViewById(R.id.back);
        name = (Button) findViewById(R.id.btn_namechanger);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Options.super.onBackPressed();


            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_generator();
            }
        });
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
    public void name_generator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText one = new EditText(this);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        builder.setView(lay);
        builder.setMessage("Enter your Name")
                .setTitle("Name")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = one.getText().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", name);
                        editor.apply();
                        // CONFIRM
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                })
                .show();
    }
}
