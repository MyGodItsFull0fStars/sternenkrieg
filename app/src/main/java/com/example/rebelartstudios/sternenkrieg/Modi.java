package com.example.rebelartstudios.sternenkrieg;

import android.app.Activity;
import android.content.Intent;
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

public class Modi extends Activity {
    ImageButton back;
    Button btn_gotocube;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.modi);

        btn_gotocube = (Button) findViewById(R.id.btn_spielen);
        back = (ImageButton) findViewById(R.id.Back);


        btn_gotocube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), Wuerfeltest.class);
                nextScreen.putExtra("shake", true);
                startActivity(nextScreen);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
