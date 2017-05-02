package com.example.wenboda.sk_ui3;

import android.content.Intent;
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

    Button button;
    Button networkBtn;
    Button optionsBtn;
    Button aboutBtn;
    Button wuerfelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.spielen);
        spielenlistener s = new spielenlistener();
        button.setOnClickListener(s);

        networkBtn = (Button) findViewById(R.id.network);
        networkListener networkListener = new networkListener();
        networkBtn.setOnClickListener(networkListener);

        aboutBtn = (Button) findViewById(R.id.about);
        aboutListener aboutListener = new aboutListener();
        aboutBtn.setOnClickListener(aboutListener);

        optionsBtn = (Button) findViewById(R.id.options);
        optionsListener optionsListener = new optionsListener();
        optionsBtn.setOnClickListener(optionsListener);

        wuerfelBtn = (Button) findViewById(R.id.wuerfel);
        wuerfelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, wuerfeltest.class);
                intent.putExtra("shake", false);
                startActivity(intent);
            }
        });

        ImageView background = (ImageView) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(0, 0, 0));
        Glide.with(this).load(R.raw.background).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(background);
    }

    private class spielenlistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Map.class);
            startActivity(intent);
        }
    }

    private class networkListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Networking.class);
            startActivity(intent);
        }
    }

    private class aboutListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }
    }

    private class optionsListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Options.class);
            startActivity(intent);
        }
    }
}

