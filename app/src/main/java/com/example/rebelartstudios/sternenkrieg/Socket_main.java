package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wenboda on 2017/5/1.
 */

public class Socket_main extends AppCompatActivity implements View.OnClickListener {

    Button S;
    Button C;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        S = (Button)findViewById(R.id.server);
        C = (Button)findViewById(R.id.client);

        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Socket_main.this, Host.class);
                startActivity(intent);
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Socket_main.this, Client1.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}