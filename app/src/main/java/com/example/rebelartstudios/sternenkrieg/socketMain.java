package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.rebelartstudios.sternenkrieg.Network.Client1;
import com.example.rebelartstudios.sternenkrieg.Network.Host;

/**
 * Created by wenboda on 2017/5/1.
 */

public class socketMain extends AppCompatActivity {

    Button s;
    Button c;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeFindByView();
        initializeButton();
    }

    private void initializeFindByView() {
        s = (Button) findViewById(R.id.server);
        c = (Button) findViewById(R.id.client);

    }

    private void initializeButton() {

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(socketMain.this, Host.class);
                startActivity(intent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(socketMain.this, Client1.class);
                startActivity(intent);
            }
        });

    }

}
