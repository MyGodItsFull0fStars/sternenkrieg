package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rebelartstudios.sternenkrieg.network.Client;
import com.example.rebelartstudios.sternenkrieg.network.Host;

public class MainSocket extends AppCompatActivity {

    Button hostBtn;
    Button clientBtn;
    ImageView back;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeFindByView();
        initializeButton();
    }

    private void initializeFindByView() {
        hostBtn = (Button) findViewById(R.id.server);
        clientBtn = (Button) findViewById(R.id.client);
        back=(ImageView) findViewById(R.id.imageSocketReturn);

    }

    private void initializeButton() {

        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainSocket.this, Host.class);
                startActivity(intent);
            }
        });

        clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainSocket.this, Client.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSocket.this, Main.class);
                startActivity(intent);
            }
        });

    }

}
