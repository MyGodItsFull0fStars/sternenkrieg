package com.example.rebelartstudios.sternenkrieg;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.GameLogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.GameLogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.network.StartThread;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
    int value;


    int pointsPlayer = 0;
    boolean check; //checks whether powerups are currently displayed;
    Vibrator vib;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;

    /******Networking******/
    // this Views can be also a chat system. That mean player can talk with other player with it.
    // But now we can use it to check if the Socket program right.
    // what we do next: set Start Button and make connect.
    // You can call methode messageSend to send String message parameter mit (message, phost)
    // And in class Myhandler you get message form enemy, msg.what = 1 is what enemy say
    // msg.what = 4 , sendMsg[1] is enemy shoot position.

    Button send;

    EditText player1_say;
    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then phost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;
    boolean sended;
    boolean sendMap = true;
    boolean shoot = false;
    boolean oneshoot = true;
    boolean dice = false;
    boolean dice2 = false;
    Intent intent = new Intent();
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    int who_is_starting;

    /*******Networking*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gameplay);

        game  = new GameUtilities(getApplicationContext());
        /****Networking****/


        send = (Button) findViewById(R.id.player1_send);
        player1_say = (EditText) findViewById(R.id.player1_say);
        System.out.println("Spielfeld");
        Phost = stats.isPhost();
        System.out.println("phost: " + Phost);
        who_is_starting = game.getWhoIsStarting();
        System.out.println("Whoisstarting: " + who_is_starting);
        Net = stats.isNet();
        System.out.println("net: " + Net);
        if (Phost == false) {
            ip = stats.getIp();
            System.out.println("Ip: " + ip);
        }


        myhandler = new Myhandler();
        util = new NetworkUtilities(Phost, mAcceptThread, mServerSocket, socket, myhandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();

        util.connection();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = player1_say.getText().toString();
                if (Phost) { //If this is host, so use writehost to sand message.
                    util.messageSend(info, Phost);
                } else {// Client.
                    util.messageSend(info, Phost);
                }
            }
        });
        /****Networking***/


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


                if (mLightQuantity >= 300)
                    background.setBackgroundResource(R.drawable.amhellsten);
                else if (mLightQuantity > 180 && mLightQuantity < 300)
                    background.setBackgroundResource(R.drawable.dunkel);
                else
                    background.setBackgroundResource(R.drawable.amdunkelsten);
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
                boolean ship2Rotated = true;
                boolean ship3Rotated = true;
                final boolean ship2RotatedFinal;
                final boolean ship3RotatedFinal;

                options1.setVisibility(View.INVISIBLE);
                options2.setVisibility(View.INVISIBLE);
                options3.setVisibility(View.INVISIBLE);
                options4.setVisibility(View.INVISIBLE);

                check = false;
                LinkedList<Integer> countD = new LinkedList<>();
                LinkedList<Integer> countE = new LinkedList<>();
                LinkedList<Integer> countF = new LinkedList<>();

                for (int i = 0; i < map1.length; i++) {
                    switch (map1[i]) {
                        case "d":
                            countD.add(i);
                            break;
                        case "e":
                            countE.add(i);
                            if (map1[i + 1].equals("e")) {
                                ship2Rotated = false;
                            }
                            break;
                        case "f":
                            countF.add(i);
                            if (map1[i + 1].equals("f")) {
                                ship3Rotated = false;
                            }
                            break;
                        default:
                            break;
                    }


                    if (countD.size() == 1) {
                        for (int j = 0; j < countD.size(); j++) {
                            map1[countD.get(j)] = "g";
                        }
                        countD.clear();

                    }

                    if (countE.size() == 2) {
                        for (int j = 0; j < countE.size(); j++) {
                            map1[countE.get(j)] = "h";
                        }
                        countE.clear();
                    }

                    if (countF.size() == 3) {
                        for (int j = 0; j < countF.size(); j++) {
                            map1[countF.get(j)] = "i";
                        }
                        countF.clear();
                    }
                }

                ship2RotatedFinal = ship2Rotated;
                ship3RotatedFinal = ship3Rotated;

                draw(map1, gridView1);


                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                        // final int posi=position;
                        final String posis = map1[position];

                        if (posis.equals("g") || posis.equals("h") || posis.equals("i")) {
                            for (int i = 0; i < map1.length; i++) {
                                if (map1[i].equals(posis)) {

                                    map1[i] = 4 + "";
                                }
                                draw(map1, gridView1);
                            }


                            relocate(posis, ship2RotatedFinal, ship3RotatedFinal);

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
                intent.setClass(Spielfeld.this, Options.class);
                // intent.putExtra("gameOn", 1);
                startActivity(intent);

            }
        });

        amountShips = 3;

        map1 = game.getPlayerMap();

        if (sendMap) {
            String sendField = "";
            for (String data : map1)
                sendField += data;

            map2 = new String[64];
            Arrays.fill(map2, "0");
            util.messageSend("Map," + sendField, Phost);
            System.out.println("Send" + sendField);
        }


        final Display display = getWindowManager().getDefaultDisplay();
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
        start();


        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (shoot) {

                    util.messageSend("shoot," + position, Phost);
                    oneshoot = false;
                    sended = true;

                    //   messageSend("map,"+position,phost);

                    // Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                    //       Toast.LENGTH_SHORT).show();
                    String shipType = map2[position];
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
                    shoot = false;
                    dice = true;
                    System.out.println("Dice True");
                    if (dice2) {
                        if (!Phost) {
                            new CountDownTimer(200, 100) {
                                public void onTick(long millisUntilFinished) {
                                    System.out.print(millisUntilFinished);
                                }

                                @Override
                                public void onFinish() {
                                    dice();
                                }

                            }.start();
                        } else
                            dice();

                    }


                    if (gameOver(shipType, map2)) { //check whether a complete ship of the enemy has been destroyed
                        decrementAmount();
                    }

                }
            }

        });


    }


    public void start() {
        System.out.println("Spielfeld Value" + who_is_starting);
        //Player beginns
        System.out.println("Shoot: " + shoot);
        pointsPlayer += game.getDicescore();
        System.out.println("POints:" + pointsPlayer);
        if (who_is_starting == 0 && oneshoot) {
            shoot = true;
        }


    }

    public void dice() {
        intent.setClass(Spielfeld.this, Dice.class);
        game.setPlayerMap(map1);
        game.setEnemyMap(map2);
        stats.setMode(2);
        System.out.println("Spielfeld ENde Value" + value);
        util.close();
        startActivity(intent);
    }


    public void checkShoot(int position, int player) {
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
        dice2 = true;
        System.out.println("Dice2 True");

        if (gameOver("d", map1) && gameOver("e", map1) && gameOver("f", map1)) { //determine whether all ships are already destroyed
            alert("2");
        }


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

    public void relocate(final String posis, final boolean ship2Rotated, final boolean ship3Rotated) {
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                for (int i = 0; i < map1.length; i++) { //ship is no longer located here; set to 0
                    if (map1[i].equals("4")) {
                        map1[i] = 0 + "";
                    }
                }
                boolean shipPlaced = false;

                switch (posis) { //check whether ship can be placed here;
                    case "g":
                        if (relocateShip(position, posis, true)) {
                            map1[position] = "d";
                            shipPlaced = true;

                        }
                        break;
                    case "h":
                        if (relocateShip(position, posis, ship2Rotated) && !ship2Rotated) { //check if ship is placeable for not rotated ship
                            map1[position] = "e";
                            map1[position + 1] = "e"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship2Rotated) && ship2Rotated) { //check if ship is placeable for rotated ship
                            map1[position] = "e";
                            map1[position + 8] = "e"; //relocate ship rotated
                            shipPlaced = true;

                        }
                        break;
                    case "i":
                        if (relocateShip(position, posis, ship3Rotated) && !ship3Rotated) { //check if ship is placeable for not rotated ship
                            map1[position] = "f";
                            map1[position + 1] = "f";
                            map1[position - 1] = "f"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship3Rotated) && ship3Rotated) { //check if ship is placeable for rotated ship
                            map1[position - 8] = "f";
                            map1[position] = "f";
                            map1[position + 8] = "f"; //relocate ship rotated
                            shipPlaced = true;
                        }
                        break;
                    default:
                        break;
                }

                if (shipPlaced) {

                    for (int i = 0; i < map1.length; i++) {
                        switch (map1[i]) {
                            case "g":
                                map1[i] = "d";
                                break;
                            case "h":
                                map1[i] = "e";
                                break;
                            case "i":
                                map1[i] = "f";
                                break;
                            default:
                                break;
                        }

                    }
                    draw(map1, gridView1);


                    //if ship is placed successfully, cheat function ends and normal "onClickListener" is restored

                    clickMap();
                }
            }
        });
    }

    public boolean relocateShip(int position, String posis, boolean shipRotated) {
        //TODO: check if ship can actually be placed here, e.g. if there is not another ship and if this position hasn't been targeted by the enemy before

        ArrayList<Integer> failures_right = new ArrayList<Integer>(Arrays.asList(-1, 7, 15, 23, 31, 39, 47, 55, 63));
        ArrayList<Integer> failures_left = new ArrayList<Integer>(Arrays.asList(8, 16, 24, 32, 40, 48, 56, 64));

        switch (posis) {
            case "g":
                if (checkAvailability(position)) {
                    return true;
                }
            case "h":
                if (shipRotated == false) {
                    return !(failures_left.contains(position + 1) || !checkAvailability(position) || !checkAvailability(position + 1));
                } else{
                    return !(position + 1 > 63 || !checkAvailability(position) || !checkAvailability(position + 8));
                }
            case "i":
                if (shipRotated == false) {
                    return !(failures_left.contains(position + 1) || failures_right.contains(position - 1)
                            || !checkAvailability(position) || !checkAvailability(position - 1) || !checkAvailability(position + 1));
                } else{
                    return !(position - 8 < 0 || position + 8 > 63
                            || !checkAvailability(position) || !checkAvailability(position + 8) || !checkAvailability(position - 8));
                }
            default:
                return true;
        }

    }


    public boolean checkAvailability(int position) {
        //  if(map1[position].equals(posis)){ return true;} else {

        switch (map1[position]) {
            case "d":
                return false;
            case "e":
                return false;
            case "f":
                return false;
            case "g":
                return false;
            case "h":
                return false;
            case "i":
                return false;
            case "3":
                return false;
            case "5":
                return false;
            default:
                return true;
        }
        //  }

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
                            game.setPoints(highScore);
                            game.setHighscoreMain(true);
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
                            game.setPoints(highScore);
                            game.setHighscoreMain(true);
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

        return isthegameoveryet == 0;

    }

    public void decrementAmount() { //if one entire ship of enemy has been destroyed, update Score
        amountShips--;
        TextView tex = ((TextView) findViewById(R.id.amountShips));
        tex.setText(amountShips + "/3");

        if (amountShips == 0) {
            alert("a");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        util.close();
    }

    /***************Network******************************/

    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    int count = 0;
                    if (message == null) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (count == 5) {
                        util.close();
                    }
//                    player2_say.setVisibility(View.VISIBLE);
//                    player2_say.setText(message);
                    break;
                case 4: //map schiessen
                    message = (String) msg.obj;
                    System.out.println(message);
                    String[] mapMsg = message.split(",");
                    if (mapMsg[0].equals("Map")) {
                        map2 = mapMsg[1].split("");
                        String[] map3 = new String[64];
                        for (int i = 0; i < map3.length; i++)
                            map3[i] = map2[i + 1];

                        map2 = map3;
                        draw(map2, gridView2);
                        util.messageSend("Gotit,1", Phost);
                        System.out.println("Map" + mapMsg[1]);

                    }
                    if (mapMsg[0].equals("Gotit")) {
                        System.out.println("Gotit");
                        sendMap = false;
                    }
                    if (mapMsg[0].equals("shoot")) {
                        String position = mapMsg[1];
                        checkShoot(Integer.parseInt(position), 2);

                        shoot = true;

                    }

                    if (dice && dice2)
                        dice();

                    break;

                case 0:
                    if (sendMap) {
                        String sendMap = "";
                        for (String data : map1)
                            sendMap += data;

                        map2 = new String[64];
                        Arrays.fill(map2, "0");
                        util.messageSend("Map," + sendMap, Phost);
                        System.out.println("Send" + sendMap);
                    }

                    break;
                case 2:
                    break;
            }
        }

    }

}