package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.example.rebelartstudios.sternenkrieg.R.drawable.two;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    Button networkBtn;
    Button optionsBtn;
    Button aboutBtn;
    Button diceBtn;
    Button socket;
    TextView txt_username;
    TextView txt_level;
    ProgressBar level_progress;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        txt_username = (TextView) findViewById(R.id.text_username);
        txt_level = (TextView) findViewById(R.id.txt_level);
        level_progress= (ProgressBar) findViewById(R.id.progressBar_level);


        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int level = sharedPreferences.getInt("level",1);
        int prozent= sharedPreferences.getInt("prozent",0);

        if (username == null) {
            name_generator();
        } else {
            txt_username.setText(username);
            txt_username.setTextColor(Color.WHITE);
            txt_level.setText("Level:"+level);
            txt_level.setTextColor(Color.WHITE);
            level_progress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            level_progress.setProgress(0);
            level_progress.setProgress(prozent);

        }

        // Background music
        // disabled for now, feel free to enable if you want to check
        //musicStuff();

        initializeButtons();
        initializeOnClickListeners();
        initializeBackground();

    }

    private void musicStuff() {
        // TODO not working yet
        ServiceConnection soundConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("SoundConnection", "connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("SoundConnection", "disconnected");
            }
        };

        boolean soundEnabled = sharedPreferences.getBoolean("sound", false);
        Intent audioIntent = new Intent(this, PlayAudio.class);
        boolean on = soundEnabled;

        if(soundEnabled) {
            bindService(audioIntent, soundConnection, Context.BIND_AUTO_CREATE);
            startService(audioIntent);
            on = true;
        } else {
            if(on) {
                stopService(audioIntent);
                unbindService(soundConnection);
            }
            on = false;
        }
    }

    private void initializeBackground() {
        ImageView background = (ImageView) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(0, 0, 0));
        Glide.with(this).load(R.raw.background).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(background);
    }

    private void initializeButtons() {
        startBtn = (Button) findViewById(R.id.start);
        aboutBtn = (Button) findViewById(R.id.about);
        optionsBtn = (Button) findViewById(R.id.options);
        diceBtn = (Button) findViewById(R.id.dice);
        socket = (Button) findViewById(R.id.Socket);
    }

    private void initializeOnClickListeners() {
        // Play
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Map.class);
                startActivity(intent);
            }
        });


        // About
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });

        // Options
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Options.class);
                startActivity(intent);
            }
        });

        // Highscore
        diceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                intent.putExtra("onlyhighscore", false);
                startActivity(intent);
            }
        });

        // socket
        socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Socket_main.class);
                startActivity(intent);
            }
        });
    }

    public void name_generator() {
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
                        txt_username.setText(name);
                        txt_username.setTextColor(Color.WHITE);
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

    @Override
    public void onResume() {
        super.onResume();
        // update username when coming back from Options activity
        txt_username.setText(sharedPreferences.getString("username", null));

        //musicStuff();
    }

}


