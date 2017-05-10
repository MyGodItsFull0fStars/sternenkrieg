package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {
    Button button;
    TextView who_is_starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button = (Button) findViewById(R.id.button_gotoStart);
        who_is_starting = (TextView) findViewById(R.id.text_first);

        Intent intent = getIntent();
        int value = intent.getIntExtra("who_is_starting",0);
        if (value ==0) {
            who_is_starting.setText("You are first");
        } else if(value==1) {
            who_is_starting.setText("Enemy is first");
        }else if ( value==2){
            who_is_starting.setText("Tie");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), Map.class);
                startActivity(nextScreen);
            }
        });

    }
}
