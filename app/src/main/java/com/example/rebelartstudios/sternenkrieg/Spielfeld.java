package com.example.rebelartstudios.sternenkrieg;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;
import com.example.rebelartstudios.sternenkrieg.Network.writeClient;
import com.example.rebelartstudios.sternenkrieg.Network.writeHost;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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
    int highScore = 0;

    int pointsPlayer = 0;

    boolean check; //checks whether powerups are currently displayed;
    Vibrator vib;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;

    /******Networking******/
    // this Views can be also a chat system. That mean player can talk with other player with it.
    // But now we can use it to check if the Socket program right.
    Button send;
    TextView player2_say;
    EditText player1_say;
    Socket socket;
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then Phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "Spiefeld";
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;
    /*******Networking*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_gameplay);

        /* --- START LIGHT SENSOR -- */

        // Obtain references to the SensorManager and the Light Sensor
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Implement a listener to receive updates
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mLightQuantity = event.values[0];
                TextView tex = ((TextView) findViewById(R.id.amountShips));
                tex.setText(mLightQuantity + "");

                ImageView background = (ImageView) findViewById(R.id.background_stars);


                if (mLightQuantity >= 300) {

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
                if (check) {
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


        options1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                options1.setVisibility(View.INVISIBLE);
                options2.setVisibility(View.INVISIBLE);
                options3.setVisibility(View.INVISIBLE);
                options4.setVisibility(View.INVISIBLE);

                check = false;
                LinkedList<Integer> countD = new LinkedList<>();
                LinkedList<Integer> countE = new LinkedList<>();
                LinkedList<Integer> countF = new LinkedList<>();

                for(int i = 0; i < map1.length; i++) {
                    switch (map1[i]) {
                        case "d": countD.add(i);
                            break;
                        case "e": countE.add(i);
                            break;
                        case "f": countF.add(i);
                            break;
                        default:
                            break;
                    }

                    if(countD.size()==1) {
                        for(int j=0; j < countD.size(); j++) {
                            map1[countD.get(j)] = "g";
                        }
                        countD.clear();

                    }

                    if(countE.size()==2) {
                        for(int j=0; j < countE.size(); j++) {
                            map1[countE.get(j)] = "h";
                        }
                        countE.clear();
                    }

                    if(countF.size()==3) {
                        for(int j=0; j < countF.size(); j++) {
                            map1[countF.get(j)] = "i";
                        }
                        countF.clear();
                    }
                }

                draw(map1, gridView1);


                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            final int posi=position;
                            final String posis = map1[position];

                            for(int i=0; i < map1.length; i++){
                                if(map1[i].equals(posis)){

                                    map1[i]=4+"";
                                }


                            draw(map1, gridView1);
                            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                                    for(int i=0; i < map1.length; i++){
                                        if(map1[i].equals("4")){
                                            map1[i]=0+"";
                                        }
                                    }

                                    switch(posis) {
                                        case "g":
                                            map1[position]="d";
                                            break;
                                        case "h":
                                            map1[position]="e";
                                            map1[position+1]="e";
                                            break;
                                        case "i":
                                            map1[position]="f";
                                            map1[position+1]="f";
                                            map1[position-1]="f";
                                            break;
                                        default:
                                            break;
                                    }

                                    draw(map1, gridView1);

                                    for(int i=0; i < map1.length; i++){
                                        switch (map1[i]) {
                                            case"g": map1[i]="d";
                                                break;
                                            case "h": map1[i]="e";
                                                break;
                                            case "i": map1[i]="f";
                                                break;
                                            default: break;
                                        }

                                    }

                                    clickMap();
                                }
                            });
                            }
                    }
                });
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

                check = false;

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

        amountShips = 3;

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
        gridView1.getLayoutParams().height = height - 350;
        gridView1.getLayoutParams().width = height - 350;
        gridView1.setAdapter(new MapLoad(this, map1));

        gridView2 = (GridView) findViewById(R.id.player2_grid);
        gridView2.getLayoutParams().height = height - 350;
        gridView2.getLayoutParams().width = height - 350;
        gridView2.setAdapter(new MapLoad(this, map2));

       clickMap();

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String shipType = map2[position];

                // Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                //       Toast.LENGTH_SHORT).show();

                /* hit ship of enemy */
                if (map2[position].equals("a") || map2[position].equals("b") || map2[position].equals("c")) {
                    map2[position] = 4 + "";
                    vib.vibrate(500);
                    highScore += 80;

                /* miss enemy's ships */
                } else if (map2[position].equals("0")) {
                    map2[position] = 1 + "";
                    highScore -= 20;
                }

                draw(map2, gridView2); // update map

                Random rand = new Random();
                int n = rand.nextInt(64);


                // gridView1.getChildAt(31).performClick();


                if (gameOver(shipType, map2)) { //check whether a complete ship of the enemy has been destroyed
                    decrementAmount();
                }

            }
        });

        /****Networking****/

        send  = (Button)findViewById(R.id.player1_send);
        player2_say = (TextView)findViewById(R.id.player2_say);
        player1_say = (EditText )findViewById(R.id.player1_say);

        if (Net){
            // if the player is host.
            Intent intent = getIntent();
            if (intent.getStringExtra("host").equals("1")){
                Phost = true;
            }
            //if the player is client, then needs the ip to build a new socket.
            if (Phost = false){
                this.ip = intent.getStringExtra("ip");
            }
            myhandler = new Myhandler();
            networkbuild();
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info = player1_say.getText().toString();
                    if (Phost){ //If this is host, so use writeHost to sand message.
                        messageSend(info,Phost);
                    }else{// Client.
                        messageSend(info,!Phost);
                    }
                }
            });
        }else {
            displayToast("Kein Internet verbinden");
        }
        /****Networking***/

    }



    public void clickMap() {
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                //      Toast.LENGTH_SHORT).show();

                /* enemy hits one of player's ships */
                if (map1[position].equals("d") || map1[position].equals("e") || map1[position].equals("f")) {
                    map1[position] = 3 + "";
                    vib.vibrate(500);
                    highScore = highScore - 30;

                    /* opponent misses */
                } else if (map1[position].equals("0")) {
                    map1[position] = 5 + "";
                    highScore = highScore + 10;
                }
                draw(map1, gridView1); // update map


                if (gameOver("d", map1) && gameOver("e", map1) && gameOver("f", map1)) { //determine whether all ships are already destroyed
                    alert("2");
                }

            }
        });
    }

    public void draw(String[] array, GridView gridView) {
        gridView.setAdapter(new MapLoad(this, array));
    }

    public void alert(String player) {
            /* "player" determines which message should be displayed
                2 = enemy won; a = player won */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (player.equals("2")) {
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
        } else if (player.equals("a")) {
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
            if ((map[i].equals(ship))) {
                isthegameoveryet++;
            }
        }

        if (isthegameoveryet == 0) {
            return true;
        }

        return false;
    }

    public void decrementAmount() { //if one entire ship of enemy has been destroyed, update Score
        amountShips--;
        TextView tex = ((TextView) findViewById(R.id.amountShips));
        tex.setText(amountShips + "/3");

        if (amountShips == 0) {
            alert("a");
        }
    }

/***************Network******************************/
// build network must be called in Oncreate, to build a new Socket connect.
    public void networkbuild(){
        boolean running = true;
        if (Phost){
            mAcceptThread = new AcceptThread(running,mServerSocket,socket,myhandler,receiveThreadHost);
        }else {
            startThread = new StartThread(socket,ip,receiveThreadClient,myhandler);
        }

    }
    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler{


        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    player2_say.setText(message);
                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
                    displayToast("Client getrennt");

                    player2_say.setText(null);//
                    try {
                        socket.close();
                        mServerSocket.close();
                    } catch (IOException e) {
                        Log.e(tag, "IOException in ReceiveThreadHost: " + e.toString());
                    }

                    break;
            }
        }

    }
    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    // Here is the messageSend methode. By call this methode can player message send.
    public void messageSend(String message,boolean obhost){
        if (obhost) {
            socket = mAcceptThread.getSocket();


            writeHost wh = new writeHost(socket, os, message);

            wh.start();

            player1_say.setText("");
        }else{

            socket = startThread.getSocket();
            Thread wirte = new writeClient(true, socket, message);

            wirte.start();
            player1_say.setText("");
        }
    }
    /***************Netzworking***********************/
}





