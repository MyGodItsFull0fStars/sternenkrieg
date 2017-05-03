package com.example.rebelartstudios.sternenkrieg.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.rebelartstudios.sternenkrieg.R;

/**
 * Created by wenboda on 2017/5/1.
 */

public class Socket extends AppCompatActivity implements View.OnClickListener {

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
                intent.setClass(Socket.this, Servers.class);
                startActivity(intent);
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Socket.this, Client.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
