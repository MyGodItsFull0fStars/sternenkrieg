package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {
    Button button;
    TextView who_is_starting;
    Intent intent = new Intent();
    boolean Net;
    String tag = "End";
    boolean Phost;
    Intent nextScreen = new Intent();
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button = (Button) findViewById(R.id.button_gotoStart);
        who_is_starting = (TextView) findViewById(R.id.text_first);

        Intent intent = getIntent();
        final int value = intent.getIntExtra("who_is_starting", 0);
        if (value == 0) {
            who_is_starting.setText("You are first");
            nextScreen.setClass(EndScreen.this, Map.class);
        } else if (value == 1) {
            who_is_starting.setText("Enemy is first");
            nextScreen.setClass(EndScreen.this, Map.class);
        } else if (value == 2) {
            who_is_starting.setText("Tie");
            nextScreen.setClass(EndScreen.this, Dice.class);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextScreen.setClass(EndScreen.this, Map.class);
                getinfofD();
                nextScreen.putExtra("who_is_starting",value);
                startActivity(nextScreen);
            }
        });

    }
    private void getinfofD(){
        Intent i = getIntent();
        System.out.println("Net = "+i.getStringExtra("Net"));


        try{
            if (i.getStringExtra("Net").equals("t")){
                Net  = true;
            }
        }catch (NullPointerException e){
            Log.e(tag, "NullPointerException in Spielfeld: " + e.toString());
        }


        if (Net){
            // if the player is host.
            try{
                if (i.getStringExtra("host").equals("1")){
                    Phost = true;
                    nextScreen.putExtra("Net","t");
                    nextScreen.putExtra("host","1");

                }
            }catch (NullPointerException e){
                Log.e(tag, "NullPointerException in Dice: " + e.toString());
            }
            //if the player is client, then needs the ip to build a new socket.
            if (Phost == false){
                this.ip = i.getStringExtra("ip");
                nextScreen.putExtra("Net","t");
                nextScreen.putExtra("ip",ip);
            }
        }
    }
}
