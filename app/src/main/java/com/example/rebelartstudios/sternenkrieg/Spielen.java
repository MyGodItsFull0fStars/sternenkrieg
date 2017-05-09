package com.example.rebelartstudios.sternenkrieg;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by wenboda on 17/4/2.
 */

public class Spielen extends Activity {
    Button button;
    ImageButton back;
    //Button networkBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.spielen);
        button = (Button)findViewById(R.id.Einzelspieler);
        back = (ImageButton) findViewById(R.id.Back);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //networkBtn = (Button) findViewById(R.id.network);

        onclickl onclickModi = new onclickl();
        button.setOnClickListener(onclickModi);

        onclickb onclickback = new onclickb();
        back.setOnClickListener(onclickback);

//        networkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Spielen.this, Networking.class);
//                startActivity(intent);
//            }
//        });



    }

    class onclickl implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Spielen.this,Modi.class);
            startActivity(intent);
        }
    }
    class onclickb implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
