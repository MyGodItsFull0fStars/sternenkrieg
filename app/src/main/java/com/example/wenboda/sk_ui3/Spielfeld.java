package com.example.wenboda.sk_ui3;


import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by anja on 25.04.17.
 */

public class Spielfeld extends AppCompatActivity {
    GridView gridView1;
    GridView gridView2;
    ImageView imageView;
    String map1[];
    String map2[];
    int width;
    int height;

    int pointsPlayer=0;
    int pointsEnemy=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_gameplay);

        imageView = (ImageView) findViewById(R.id.grid_item_image);

        map1 = getIntent().getExtras().getStringArray("oldmap");

        map2 = new String[64];
        for (int i = 0; i < 64; i++) {
            map2[i] = 0 + "";
        }

        map2[31] = "a"; //just temporary fill for opponents ships
        map2[32] = "a";
        map2[33] = "a";

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x - 90;  //x=1794 -- y=1080
        height = size.y - 120;

        gridView1 = (GridView) findViewById(R.id.player1_grid);
        //gridView1.getLayoutParams().height = height;
      //  gridView1.getLayoutParams().width = height;
        gridView1.setAdapter(new MapLoad(this, map1));

        gridView2 = (GridView) findViewById(R.id.player2_grid);
        gridView2.getLayoutParams().height = height;
        gridView2.getLayoutParams().width = height;
        gridView2.setAdapter(new MapLoad(this, map2));

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                        Toast.LENGTH_SHORT).show();
                if (map1[position].equals("2")) {
                    map1[position] = 3 + "";

                } else if (map2[position].equals("0")){
                map1[position] = 5 + "";
            }
                draw(map1, gridView1);

            }
        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                        Toast.LENGTH_SHORT).show();
                if(map2[position].equals("a")) {
                    map2[position] = 4 + "";

                } else if (map2[position].equals("0")){
                    map2[position] = 1 + "";
                }
                draw(map2, gridView2);

                Random rand = new Random();
                int n = rand.nextInt(64);


               // gridView1.getChildAt(31).performClick();

                int isthegameoveryet = 0;
                for (int i = 0; i < 64; i++) {
                    if((map1[i].equals("2"))) {
                        isthegameoveryet++;
                    }
                }


                if(isthegameoveryet == 0) {
                    System.out.println("Game Over bitches.");
                }

            }
        });


    }

      public void draw(String[] array, GridView gridView) {
        gridView.setAdapter(new MapLoad(this, array));
        }
    }





