package com.example.wenboda.sk_ui3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.spielen);
        spielenlistener s = new spielenlistener();
        button.setOnClickListener(s);

    }
    class spielenlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent inten = new Intent();
            inten.setClass(MainActivity.this,Spielen.class);
            startActivity(inten);
        }
    }
}
