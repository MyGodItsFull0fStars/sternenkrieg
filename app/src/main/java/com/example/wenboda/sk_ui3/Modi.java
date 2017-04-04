package com.example.wenboda.sk_ui3;

import android.app.Activity;
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

public class Modi extends Activity {
    ImageButton back;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.modi);

        back = (ImageButton) findViewById(R.id.Back);
        onclickb onclickback = new onclickb();
        back.setOnClickListener(onclickback);
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
