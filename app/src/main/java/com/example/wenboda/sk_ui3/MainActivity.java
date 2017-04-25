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

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        button = (Button)findViewById(R.id.spielen);
        spielenlistener s = new spielenlistener();
        button.setOnClickListener(s);

        ImageView background = (ImageView) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(0,0,0));
        Glide.with(this).load(R.raw.beckgraund).asGif().centerCrop().into(background);
    }
    class spielenlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent inten = new Intent();
            inten.setClass(MainActivity.this,Map.class);
            startActivity(inten);
        }
    }
}

