package com.example.wenboda.sk_ui3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Map extends AppCompatActivity {
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String[] map = new String[64];
        Random r = new Random();
        for (int i = 0; i < 64; i++) {
            map[i] = r.nextInt(3) + "";
        }

        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(new MapLoad(this, map));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),"Random TextMsg" ,
                        Toast.LENGTH_SHORT).show();

            }
        });


    }
}
