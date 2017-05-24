package com.example.rebelartstudios.sternenkrieg;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

/**
 * Created by anja on 25.04.17.
 */

public class Spielfeld extends AppCompatActivity {
    GridView gridView1;
    GridView gridView2;
    ImageView imageView, options, options1, options2, options3, options4;
    String map1[];
    String map2[];
    int width;
    int height;
    int amountShips;
    int highScore=0;

    int pointsPlayer=0;

    boolean check; //checks whether powerups are currently displayed;
    Vibrator vib;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_gameplay);

        /* --- START LIGHT SENSOR -- */

        // Obtain references to the SensorManager and the Light Sensor
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Implement a listener to receive updates
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mLightQuantity = event.values[0];
                TextView tex = ((TextView)findViewById(R.id.amountShips));
                tex.setText(mLightQuantity+"");

                ImageView background = (ImageView) findViewById(R.id.background_stars);


               if(mLightQuantity >= 300) {

                   background.setBackgroundResource(R.drawable.sky_bright);
                } else {
                   background.setBackgroundResource(R.drawable.sky_dark);

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

        // Register the listener with the light sensor -- choosing
        // one of the SensorManager.SENSOR_DELAY_* constants.
        mSensorManager.registerListener(listener, mLightSensor, SensorManager.SENSOR_DELAY_UI);

        /* --- END OF LIGHT SENSOR --- */


        imageView = (ImageView) findViewById(R.id.grid_item_image);
        options = (ImageView) findViewById(R.id.options);

        /* set option-buttons */
        options1 = (ImageView) findViewById(R.id.options1);
        options2 = (ImageView) findViewById(R.id.options2);
        options3 = (ImageView) findViewById(R.id.options3);
        options4 = (ImageView) findViewById(R.id.options4);


        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* determine whether options or powerups should be displayed */
                if(check) {
                    options1.setVisibility(View.INVISIBLE);
                    options2.setVisibility(View.INVISIBLE);
                    options3.setVisibility(View.INVISIBLE);
                    options4.setVisibility(View.INVISIBLE);
                    check = false;
                } else {
                    options1.setImageDrawable(getResources().getDrawable(R.drawable.cheat_sternenkriege));
                    options2.setImageDrawable(getResources().getDrawable(R.drawable.powerups_sternenkriege));
                    options3.setImageDrawable(getResources().getDrawable(R.drawable.options_sternenkriege));
                    options4.setImageDrawable(getResources().getDrawable(R.drawable.help_sternenkriege));

                    options1.setVisibility(View.VISIBLE);
                    options2.setVisibility(View.VISIBLE);
                    options3.setVisibility(View.VISIBLE);
                    options4.setVisibility(View.VISIBLE);
                    check = true;
                }
            }
        });

        options2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* show power-ups - options2==powerups */
                options1.setImageDrawable(getResources().getDrawable(R.drawable.scanner));
                options2.setImageDrawable(getResources().getDrawable(R.drawable.doubleshot));
                options3.setImageDrawable(getResources().getDrawable(R.drawable.explosionradius));
                options4.setImageDrawable(getResources().getDrawable(R.drawable.armour));

                check=false;

            }
        });

        options3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* go to options-menu */
                Intent intent = new Intent();
                intent.setClass(Spielfeld.this, Options.class);
               // intent.putExtra("gameOn", 1);
                startActivity(intent);

            }
        });

        amountShips=3;

        map1 = getIntent().getExtras().getStringArray("oldmap"); //get information of placed ships from previous screen

        map2 = new String[64];
        for (int i = 0; i < 64; i++) {
            map2[i] = 0 + "";
        }

        map2[32] = "a"; //just temporary fill for opponents ships
        map2[33] = "a";
        map2[34] = "a";

        map2[62] = "b";
        map2[63] = "b";

        map2[5] = "c";


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        /* initialize grids according based on screen size */
        width = size.x;  //x=1794 -- y=1080
        height = size.y;

        gridView1 = (GridView) findViewById(R.id.player1_grid);
        gridView1.getLayoutParams().height = height-350;
        gridView1.getLayoutParams().width = height-350;
        gridView1.setAdapter(new MapLoad(this, map1));

        gridView2 = (GridView) findViewById(R.id.player2_grid);
        gridView2.getLayoutParams().height = height-350;
        gridView2.getLayoutParams().width = height-350;
        gridView2.setAdapter(new MapLoad(this, map2));

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                  //      Toast.LENGTH_SHORT).show();

                /* enemy hits one of player's ships */
                if (map1[position].equals("2")) {
                    map1[position] = 3 + "";
                    vib.vibrate(500);
                    highScore=highScore-30;

                    /* opponent misses */
                } else if (map2[position].equals("0")){
                map1[position] = 5 + "";
                    highScore=highScore+10;
            }
                draw(map1, gridView1); // update map


              if (gameOver("2", map1)) { //determine whether all ships are already destroyed
                  alert("2");
              }

            }
        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String shipType = map2[position];

               // Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                 //       Toast.LENGTH_SHORT).show();

                /* hit ship of enemy */
                if(map2[position].equals("a") || map2[position].equals("b") || map2[position].equals("c")) {
                    map2[position] = 4 + "";
                    vib.vibrate(500);
                    highScore+=80;

                /* miss enemy's ships */
                } else if (map2[position].equals("0")){
                    map2[position] = 1 + "";
                    highScore-=20;
                }

                draw(map2, gridView2); // update map

                Random rand = new Random();
                int n = rand.nextInt(64);


               // gridView1.getChildAt(31).performClick();


                if(gameOver(shipType, map2)){ //check whether a complete ship of the enemy has been destroyed
                    decrementAmount();
                }

            }
        });


    }

      public void draw(String[] array, GridView gridView) {
        gridView.setAdapter(new MapLoad(this, array));
        }

        public void alert(String player){
            /* "player" determines which message should be displayed
                2 = enemy won; a = player won */
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if(player.equals("2")) {
                builder.setMessage("Your enemy destroyed all your ships.")
                        .setTitle("Game Over!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CONFIRM
                                Intent intent = new Intent(Spielfeld.this, HighScore.class);
                                intent.putExtra("highScore", highScore);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Whatever.", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL
                            }
                        })
                        .show();
            } else if(player.equals("a")){
                builder.setMessage("You successfully destroyed all hostile ships!")
                        .setTitle("You win!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CONFIRM
                                Intent intent = new Intent(Spielfeld.this, HighScore.class);
                                intent.putExtra("highScore", highScore);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("I know, I am awesome.", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL
                            }
                        })
                        .show();
            }

            // Create the AlertDialog object and return it
                    builder.create();

        }

        public boolean gameOver(String ship, String[] map) {
            /* checks if String "ship" (either checks whether player has any ships left or if an
            etire ship of the opponent has been destroyed) is still present in array "map";
            if not, method returns true */
            int isthegameoveryet = 0;
            for (int i = 0; i < 64; i++) {
                if((map[i].equals(ship))) {
                    isthegameoveryet++;
                }
            }

            if(isthegameoveryet == 0) {
               return true;
            }

            return false;
        }

        public void decrementAmount() { //if one entire ship of enemy has been destroyed, update Score
            amountShips--;
            TextView tex = ((TextView)findViewById(R.id.amountShips));
            tex.setText(amountShips+"/3");

            if(amountShips==0){
                alert("a");
            }
        }
    }





