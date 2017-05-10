package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    Button networkBtn;
    Button optionsBtn;
    Button aboutBtn;
    Button diceBtn;
    Button socket;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Background music
        // disabled for now, feel free to enable if you want to check
        // Intent audioIntent = new Intent(this, PlayAudio.class);
        // startService(audioIntent);
        initializeButtons();
        initializeOnClickListeners();
        initializeBackground();

    }

    private void initializeBackground() {
        ImageView background = (ImageView) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(0, 0, 0));
        Glide.with(this).load(R.raw.background).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(background);
    }

    private void initializeButtons(){
        startBtn = (Button) findViewById(R.id.start);
        aboutBtn = (Button) findViewById(R.id.about);
        optionsBtn = (Button) findViewById(R.id.options);
        diceBtn = (Button) findViewById(R.id.dice);
        socket = (Button)findViewById(R.id.Socket);
    }

    private void initializeOnClickListeners(){
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

        // Dice
        diceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Wuerfeltest.class);
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


}


