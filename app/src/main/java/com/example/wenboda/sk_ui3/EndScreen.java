package com.example.wenboda.sk_ui3;

import android.content.Intent;
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

        button = (Button) findViewById(R.id.button_gotoStart);
        who_is_starting=(TextView) findViewById(R.id.text_first);

        Intent intent= getIntent();
        if(intent.getExtras().getBoolean("who_is_starting")){
            who_is_starting.setText("You are first");
        }else{
            who_is_starting.setText("Enemy is first or tie");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen =  new Intent(getApplicationContext(),MainActivity.class);
                startActivity(nextScreen);
            }
        });

    }
}
