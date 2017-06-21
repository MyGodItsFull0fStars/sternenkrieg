package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;

public class Options extends AppCompatActivity {
    ImageView back;

    SharedPreferences sharedPreferences;
    GameUtilities game;

    ToggleButton toggleSoundBtn;
    TextView textViewStatus;
    TextView textViewName;
    Button playSoundBtn;
    Button name;
    Button hackBtn;

    Spinner spinnerLanguage;

    String tag = "Options";

    Boolean soundEnabled; // sound enabled = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewName = (TextView) findViewById(R.id.textViewName);

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
                generateName();
            }
        });

        // get sharedPreferences with name "prefs"
        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        // sound is disabled by default
        soundEnabled = sharedPreferences.getBoolean("sound", false);

        if(soundEnabled) {
            textViewStatus.setText(getString(R.string.enabled));
        }

        // if state of toggle button is different from soundEnabled, change it
        toggleSoundBtn = (ToggleButton) findViewById(R.id.soundButton);
        if (toggleSoundBtn.isChecked() != soundEnabled) {
            toggleSoundBtn.toggle();
            Log.w(tag, "Sound Button toggled!");
        }

        // https://developer.android.com/guide/topics/ui/controls/togglebutton.html
        toggleSoundBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                soundEnabled = isChecked;
                // Put the setting into the sharedPreferences by using the editor
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.w(tag, sharedPreferences.getAll().toString());
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
                Log.w(tag, sharedPreferences.getAll().toString());
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.shake_dice);

        playSoundBtn = (Button) findViewById(R.id.buttonPlaySound);
        playSoundBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (soundEnabled) {
                    // enabled, play sound
                    Log.w(tag, "Sound should be played");
                    mp.start();
                }
            }
        });

        textViewName.setText(sharedPreferences.getString("username", "kein Name"));

        spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setSelection(0); // default

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // TODO language stuff
                switch (position) {
                    case 0: // german
                        break;
                    case 1: // english
                        break;
                    default:
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

        hackBtn = (Button) findViewById(R.id.hackBTN);
        hackBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Options.this, Map.class);
                startActivity(intent);
            }
        });
    }

    public void generateName() {
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
                        game.setUsername(one.getText().toString());
                        textViewName.setText(sharedPreferences.getString("username", "kein Name2"));
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
