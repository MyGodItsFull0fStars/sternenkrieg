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
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.gamelogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldShipContainer;
import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
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

public class Gameplay extends AppCompatActivity {
    GridView gridView1;
    GridView gridView2;
    ImageView imageView, options, options1, options2, options3, options4;


    /****************Refactoring objects start ****************************************************************************/
    PlayerFieldShipContainer playerFieldShipContainer;
    PlayerFieldShipContainer enemyFieldShipContainer;
    FieldValues fieldValues = new FieldValues();

    private void initializeContainerObjects() {
        playerFieldShipContainer = new PlayerFieldShipContainer();
        enemyFieldShipContainer = new PlayerFieldShipContainer();
    }

    /****************Refactoring objects end *****************************************************************************/

    String map1[];
    String map2[];
    int width;
    int height;
    int amountShips;
    int highScore = 0;
    int value;


    int pointsPlayer = 0;
    boolean check; //checks whether powerUps are currently displayed;
    Vibrator vib;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;

    /******Networking******/
    // this Views can be also a chat system. That mean player can talk with other player with it.
    // But now we can use it to check if the Socket program right.
    // what we do next: set Start Button and make connect.
    // You can call methode messageSend to send String message parameter mit (message, pHost)
    // And in class Myhandler you get message form enemy, msg.what = 1 is what enemy say
    // msg.what = 4 , sendMsg[1] is enemy shoot position.

    Button send;

    EditText player1_say;
    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myHandler;
    boolean Phost = false; // if this is host then pHost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;
    boolean sendGridView2;
    boolean sendMap = true;
    boolean shoot = false;
    boolean oneShoot = true;
    boolean dice = false;
    boolean dice2 = false;
    Intent intent = new Intent();
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    int who_is_starting;
    ImageView shootPlayer;
    ImageView shootEnemy;

    String mapString = "Map";

    /*******Networking*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gameplay);

        game = new GameUtilities(getApplicationContext());

        /****** Networking ****/

        send = (Button) findViewById(R.id.player1_send);
        player1_say = (EditText) findViewById(R.id.player1_say);
        System.out.println("Gameplay");
        Phost = stats.isPhost();
        System.out.println("pHost: " + Phost);
        who_is_starting = GameUtilities.getWhoIsStarting();
        System.out.println("Who is starting: " + who_is_starting);
        Net = stats.isNet();
        System.out.println("net: " + Net);
        if (Phost == false) {
            ip = stats.getIp();
            System.out.println("Ip: " + ip);
        }


        myHandler = new Myhandler();
        util = new NetworkUtilities(Phost, mAcceptThread, mServerSocket, socket, myHandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();

        util.connection();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = player1_say.getText().toString();
                if (Phost) { //If this is host, so use WriteHost to sand message.
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
                String text = mLightQuantity + "";
                tex.setText(text);
                text = null;

                ImageView background = (ImageView) findViewById(R.id.background_stars);


                if (mLightQuantity >= 600)
                    background.setBackgroundResource(R.drawable.dunkel);
                else if (mLightQuantity > 300 && mLightQuantity < 600)
                    background.setBackgroundResource(R.drawable.heller2);
                else if (mLightQuantity > 180 && mLightQuantity < 300)
                    background.setBackgroundResource(R.drawable.heller);
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

        initializeImageViews();


        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* determine whether options or powerUps should be displayed */
                if (check) {
                    setOptionButtonsInvisible();
                    check = false;
                } else {
                    options1.setImageDrawable(getResources().getDrawable(R.drawable.cheat_sternenkriege));
                    options2.setImageDrawable(getResources().getDrawable(R.drawable.powerups_sternenkriege));
                    options3.setImageDrawable(getResources().getDrawable(R.drawable.options_sternenkriege));
                    options4.setImageDrawable(getResources().getDrawable(R.drawable.help_sternenkriege));

                    setOptionButtonsVisible();
                    check = true;
                }
            }
        });


        options1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setOptionButtonsInvisible();

                check = false;

                //highlight all intact ships

                final boolean[] shipR = findIntactShips();


                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        fieldValues.initialize_H_list();
                        fieldValues.initialize_I_list();

                        final String positionString = map1[position];

                        if (positionString.equals(fieldValues.SETFIELDPOSITION_G)
                                || fieldValues.h_list.contains(positionString)
                                || fieldValues.i_list.contains(positionString))


                        {

                            if (positionString.equals(fieldValues.SETFIELDPOSITION_G)) {
                                map1[position] = fieldValues.SETFIELDPOSITION_PLAYERHIT;
                                draw(map1, gridView1);
                            } else if (fieldValues.h_list.contains(positionString)) {
                                for (int i = 0; i < map1.length; i++) {
                                    if (
                                        //map1[i].equals("h1") || map1[i].equals("h2") || map1[i].equals("h3") || map1[i].equals("h4")
                                            fieldValues.h_list.contains(map1[i])) {

                                        map1[i] = fieldValues.SETFIELDPOSITION_PLAYERHIT;
                                    }

                                    draw(map1, gridView1);
                                }
                            } else if (fieldValues.i_list.contains(positionString)) {
                                for (int i = 0; i < map1.length; i++) {
                                    //    if (map1[i].equals("i1") || map1[i].equals("i2") || map1[i].equals("i3")
                                    //            || map1[i].equals("i4") || map1[i].equals("i5") || map1[i].equals("i6"))
                                    if (fieldValues.i_list.contains(map1[i])) {

                                        map1[i] = fieldValues.SETFIELDPOSITION_PLAYERHIT;
                                    }

                                    draw(map1, gridView1);
                                }
                            }


//                            switch (posis) {
//                                case "g":
//                                    map1[position] = 4 + "";
//                                    draw(map1, gridView1);
//                                    break;
//                                case "h1":
//                                case "h2":
//                                case "h3":
//                                case "h4":
//                                    for (int i = 0; i < map1.length; i++) {
//                                        if (map1[i].equals("h1") || map1[i].equals("h2") || map1[i].equals("h3") || map1[i].equals("h4")) {
//
//                                            map1[i] = fieldValues.SETFIELDPOSITION_PLAYERHIT;
//                                        }
//
//                                        draw(map1, gridView1);
//                                    }
//                                    break;
//                                case "i1":
//                                case "i2":
//                                case "i3":
//                                case "i4":
//                                case "i5":
//                                case "i6":
//                                    for (int i = 0; i < map1.length; i++) {
//                                        if (map1[i].equals("i1") || map1[i].equals("i2") || map1[i].equals("i3")
//                                                || map1[i].equals("i4") || map1[i].equals("i5") || map1[i].equals("i6")) {
//
//                                            map1[i] = 4 + "";
//                                        }
//
//                                        draw(map1, gridView1);
//                                    }
//                                    break;
//                            }

                            final boolean ship2RotatedFinal;
                            final boolean ship3RotatedFinal;


                            ship2RotatedFinal = shipR[0];
                            ship3RotatedFinal = shipR[1];

                            relocate(positionString, ship2RotatedFinal, ship3RotatedFinal);

                        }
                    }
                });
            }
        });

        options2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* show power-ups - options2==powerUps */
                options1.setImageDrawable(getResources().getDrawable(R.drawable.powerup1));
                options2.setImageDrawable(getResources().getDrawable(R.drawable.powerup2));
                options3.setImageDrawable(getResources().getDrawable(R.drawable.powerup3));
                options4.setImageDrawable(getResources().getDrawable(R.drawable.powerup4));

                check = false;

                powerUps();
            }
        });

        options3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* go to options-menu */
                intent.setClass(Gameplay.this, Options.class);
                // intent.putExtra("gameOn", 1);
                startActivity(intent);

            }
        });

        amountShips = 3;

        map1 = GameUtilities.getPlayerMap();

        if (sendMap) {
            String sendField = "";
            for (String data : map1) {
                sendField += data + "x";
                System.out.println(sendField);
            }

            map2 = new String[fieldValues.FIELDSIZE];
            Arrays.fill(map2, fieldValues.SETFIELDPOSITION_EMPTY);
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
                    oneShoot = false;
                    sendGridView2 = true;

                    //   messageSend("map,"+position, pHost);

                    // Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                    //       Toast.LENGTH_SHORT).show();
                    String shipType = map2[position];
           /* hit ship of enemy */
                    if (map2[position].equals("a") || map2[position].equals("b") || map2[position].equals("c")) {
                        map2[position] = fieldValues.SETFIELDPOSITION_PLAYERHIT;

                        vib.vibrate(500);
                        highScore += 80;

                /* miss enemy's ships */
                    } else if (map2[position].equals(fieldValues.SETFIELDPOSITION_EMPTY)) {
                        map2[position] = fieldValues.SETFIELDPOSITION_MISS;
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
        System.out.println("Gameplay Value" + who_is_starting);
        //Player beginns
        System.out.println("Shoot: " + shoot);
        pointsPlayer += GameUtilities.getDiceScore();
        System.out.println("Points:" + pointsPlayer);
        if (who_is_starting == 0 && oneShoot) {
            shoot = true;
        }


    }

    public void dice() {
        intent.setClass(Gameplay.this, Dice.class);
        GameUtilities.setPlayerMap(map1);
        GameUtilities.setEnemyMap(map2);
        NetworkStats.setMode(2);
        System.out.println("Gameplay Ende Value" + value);
        util.close();
        startActivity(intent);
    }


    public void checkShoot(int position, int player) {
        fieldValues.initialiseShipLists();
        if (fieldValues.smallShipStringList.contains(map1[position])
                || fieldValues.middleShipStringList.contains(map1[position])
                || fieldValues.bigShipStringList.contains(map1[position])) {
            map1[position] = fieldValues.SETFIELDPOSITION_ENEMYHIT;
            vib.vibrate(500);
            highScore = highScore - 30; }

        //hits ship with armour
        else if (fieldValues.smallShipArmourStringList.contains(map1[position])
                    || fieldValues.middleShipArmourStringList.contains(map1[position])
                    || fieldValues.bigShipArmourStringList.contains(map1[position])) {
            switch(map1[position]) {
                case "j": map1[position] = fieldValues.SETPLAYERPOSITION_SMALL;
                    break;
                case "k1": map1[position] = fieldValues.SETPLAYERPOSITION_MIDDLE1;
                    break;
                case "k2": map1[position] = fieldValues.SETPLAYERPOSITION_MIDDLE2;
                    break;
                case "k3": map1[position] = fieldValues.SETPLAYERPOSITION_MIDDLE1R;
                    break;
                case "k4": map1[position] = fieldValues.SETPLAYERPOSITION_MIDDLE2R;
                    break;
                case "l1": map1[position] = fieldValues.SETFIELDPOSITION_BIG1;
                    break;
                case "l2": map1[position] = fieldValues.SETFIELDPOSITION_BIG2;
                    break;
                case "l3": map1[position] = fieldValues.SETFIELDPOSITION_BIG3;
                    break;
                case "l4": map1[position] = fieldValues.SETFIELDPOSITION_BIG1R;
                    break;
                case "l5": map1[position] = fieldValues.SETFIELDPOSITION_BIG2R;
                    break;
                case "l6": map1[position] = fieldValues.SETFIELDPOSITION_BIG3R;
                    break;
            }
            vib.vibrate(500);
                    /* opponent misses */
        } else if (map1[position].equals(fieldValues.SETFIELDPOSITION_EMPTY)) {
            map1[position] = fieldValues.SETFIELDPOSITION_ENEMYMISS;
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
                if (
                    //map1[position].equals("d") || map1[position].equals("e1") || map1[position].equals("e2")
                    //|| map1[position].equals("e3") || map1[position].equals("e4") || map1[position].equals("f1")
                    //|| map1[position].equals("f2") || map1[position].equals("f3") || map1[position].equals("f4")
                    //|| map1[position].equals("f5") || map1[position].equals("f6")
                        fieldValues.smallShipStringList.contains(map1[position])
                                || fieldValues.middleShipStringList.contains(map1[position])
                                || fieldValues.bigShipStringList.contains(map1[position])) {
                    map1[position] = fieldValues.SETFIELDPOSITION_ENEMYHIT;
                    vib.vibrate(500);
                    highScore = highScore - 30;

                    /* opponent misses */
                } else if (map1[position].equals(fieldValues.SETFIELDPOSITION_EMPTY)) {
                    map1[position] = 5 + "";
                    highScore = highScore + 10;
                }
                draw(map1, gridView1); // update map


                if (gameOver(fieldValues.SETPLAYERPOSITION_SMALL, map1)
                        && gameOver(fieldValues.SETPLAYERPOSITION_MIDDLE, map1)
                        && gameOver(fieldValues.SETFIELDPOSITION_BIG, map1)) { //determine whether all ships are already destroyed
                    alert("2");
                }

            }
        });
    }

    private void initializeImageViews() {
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        options = (ImageView) findViewById(R.id.options);
        shootEnemy = (ImageView) findViewById(R.id.imageShootEnemy);
        shootPlayer = (ImageView) findViewById(R.id.imageShootPlayer);

        /* set option-buttons */
        options1 = (ImageView) findViewById(R.id.options1);
        options2 = (ImageView) findViewById(R.id.options2);
        options3 = (ImageView) findViewById(R.id.options3);
        options4 = (ImageView) findViewById(R.id.options4);
    }

    private void setOptionButtonsInvisible() {
        options1.setVisibility(View.INVISIBLE);
        options2.setVisibility(View.INVISIBLE);
        options3.setVisibility(View.INVISIBLE);
        options4.setVisibility(View.INVISIBLE);
    }

    private void setOptionButtonsVisible() {
        options1.setVisibility(View.VISIBLE);
        options2.setVisibility(View.VISIBLE);
        options3.setVisibility(View.VISIBLE);
        options4.setVisibility(View.VISIBLE);
    }

    public void relocate(final String posis, final boolean ship2Rotated, final boolean ship3Rotated) {
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                for (int i = 0; i < map1.length; i++) { //ship is no longer located here; set to 0
                    if (map1[i].equals("4")) {
                        map1[i] = fieldValues.SETFIELDPOSITION_EMPTY;
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
                    case "h1":
                    case "h2":
                    case "h3":
                    case "h4":
                        if (relocateShip(position, posis, ship2Rotated) && !ship2Rotated) { //check if ship is placeable for not rotated ship
                            map1[position] = "e1";
                            map1[position + 1] = "e2"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship2Rotated) && ship2Rotated) { //check if ship is placeable for rotated ship
                            map1[position] = "e3";
                            map1[position + 8] = "e4"; //relocate ship rotated
                            shipPlaced = true;

                        }
                        break;
                    case "i1":
                    case "i2":
                    case "i3":
                    case "i4":
                    case "i5":
                    case "i6":
                        if (relocateShip(position, posis, ship3Rotated) && !ship3Rotated) { //check if ship is placeable for not rotated ship
                            map1[position] = "f2";
                            map1[position + 1] = "f3";
                            map1[position - 1] = "f1"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship3Rotated) && ship3Rotated) { //check if ship is placeable for rotated ship
                            map1[position - 8] = "f4";
                            map1[position] = "f5";
                            map1[position + 8] = "f6"; //relocate ship rotated
                            shipPlaced = true;
                        }
                        break;
                    default:
                        break;
                }

                if (shipPlaced) {
                     restoreShips();

                    draw(map1, gridView1);


                    //if ship is placed successfully, cheat function ends and normal "onClickListener" is restored

                    clickMap();
                }
            }
        });
    }

    public boolean relocateShip(int position, String input, boolean shipRotated) {

        ArrayList<Integer> failures_right = new ArrayList<>(Arrays.asList(-1, 7, 15, 23, 31, 39, 47, 55, 63));
        ArrayList<Integer> failures_left = new ArrayList<>(Arrays.asList(8, 16, 24, 32, 40, 48, 56, 64));

        switch (input) {
            case "g":
                if (checkAvailability(position)) {
                    return true;
                }
            case "h1":
            case "h2":
            case "h3":
            case "h4":
                if (shipRotated == false) {
                    return !(failures_left.contains(position + 1) || !checkAvailability(position) || !checkAvailability(position + 1));
                } else {
                    return !(position + 1 > 63 || !checkAvailability(position) || !checkAvailability(position + 8));
                }
            case "i1":
            case "i2":
            case "i3":
            case "i4":
            case "i5":
            case "i6":
                if (shipRotated == false) {
                    return !(failures_left.contains(position + 1) || failures_right.contains(position - 1)
                            || !checkAvailability(position) || !checkAvailability(position - 1) || !checkAvailability(position + 1));
                } else {
                    return !(position - 8 < 0 || position + 8 > 63
                            || !checkAvailability(position) || !checkAvailability(position + 8) || !checkAvailability(position - 8));
                }
            default:
                return true;
        }

    }


    public boolean checkAvailability(int position) {
        //  if(map1[position].equals(posis)){ return true;} else {
        //  fieldValues.initialiseCheckAvailabilityList();
        if (fieldValues.smallShipStringList.contains(map1[position])
                || fieldValues.middleShipStringList.contains(map1[position])
                || fieldValues.bigShipStringList.contains(map1[position])
                || fieldValues.SETFIELDPOSITION_G.equals(map1[position])
                || fieldValues.h_list.contains(map1[position])
                || fieldValues.i_list.contains(map1[position])
                || fieldValues.SETFIELDPOSITION_ENEMYHIT.equals(map1[position])
                || fieldValues.SETFIELDPOSITION_ENEMYMISS.equals(map1[position])) {
            return false;
        } else {
            return true;
        }
//            switch (map1[position]) {
//                case "d":
//                    return false;
//                case "e1":
//                case "e2":
//                case "e3":
//                case "e4":
//                    return false;
//                case "f1":
//                case "f2":
//                case "f3":
//                case "41":
//                case "f5":
//                case "f6":
//                    return false;
//                case "g":
//                    return false;
//                case "h1":
//                case "h2":
//                case "h3":
//                case "h4":
//                    return false;
//                case "i1":
//                case "i2":
//                case "i3":
//                case "i4":
//                case "i5":
//                case "i6":
//                    return false;
//                case "3":
//                    return false;
//                case "5":
//                    return false;
//                default:
//                    return true;
//            }
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
                            Intent intent = new Intent(Gameplay.this, Highscore.class);
                            GameUtilities.setPoints(highScore);
                            GameUtilities.setHighScoreMain(true);
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
                            Intent intent = new Intent(Gameplay.this, Highscore.class);
                            GameUtilities.setPoints(highScore);
                            GameUtilities.setHighScoreMain(true);
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

    // TODO: 20/06/2017
    public boolean gameOver(String ship, String[] map) {
            /* checks if String "ship" (either checks whether player has any ships left or if an
            entire ship of the opponent has been destroyed) is still present in array "map";
            if not, method returns true */
        int isTheGameOverYet = 0;
        for (int i = 0; i < fieldValues.FIELDSIZE; i++) {
            if ((map[i].equals(ship))) {
                isTheGameOverYet++;
            }
        }

        return isTheGameOverYet == 0;

    }

    public void decrementAmount() { //if one entire ship of enemy has been destroyed, update Score
        amountShips--;
        TextView tex = ((TextView) findViewById(R.id.amountShips));
        String text = amountShips + "/3";
        tex.setText(text);

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
                    if (mapMsg[0].equals(mapString)) {
                        map2 = mapMsg[1].split("x");
                        draw(map2, gridView2);
                        util.messageSend("Gotit,1", Phost);
                        System.out.println(mapString + mapMsg[1]);

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
                            sendMap += data + "x";

                        map2 = new String[fieldValues.FIELDSIZE];
                        Arrays.fill(map2, fieldValues.SETFIELDPOSITION_EMPTY);
                        util.messageSend("Map," + sendMap, Phost);
                        System.out.println("Send" + sendMap);
                    }

                    break;
                case 2:
                    break;
            }
        }

    }

    public void animation(float x, float y) {
        //TODO: at work
        //animationClass(v.getX(),v.getY());
        System.out.println("Animation");
        shootPlayer.setVisibility(View.VISIBLE);
        TranslateAnimation slideUp = new TranslateAnimation(-x, -y, 0, 0);
        slideUp.setDuration(1000);
        shootPlayer.setAnimation(slideUp);


    }

    public boolean[] findIntactShips() {
        boolean ship2Rotated = true;
        boolean ship3Rotated = true;

        LinkedList<Integer> countD = new LinkedList<>();
        LinkedList<Integer> countE = new LinkedList<>();
        LinkedList<Integer> countF = new LinkedList<>();

        for (int i = 0; i < map1.length; i++) {
            switch (map1[i]) {
                case "d":
                    countD.add(i);
                    break;
                case "e1":
                case "e2":
                case "e3":
                case "e4":
                    countE.add(i);
                    if (map1[i + 1].equals("e2")) {
                        ship2Rotated = false;
                    }
                    break;
                case "f1":
                case "f2":
                case "f3":
                case "f4":
                case "f5":
                case "f6":
                    countF.add(i);
                    if (map1[i + 1].equals("f2")) {
                        ship3Rotated = false;
                    }
                    break;
                default:
                    break;
            }


            if (countD.size() == 1) {
                for (int j = 0; j < countD.size(); j++) {
                    map1[countD.get(j)] = fieldValues.SETFIELDPOSITION_G;
                }
                countD.clear();

            }

            if (countE.size() == 2) {
                if (ship2Rotated) {
                    map1[countE.get(0)] = fieldValues.SETFIELDPOSITION_H3;
                    map1[countE.get(1)] = fieldValues.SETFIELDPOSITION_H4;
                } else {
                    map1[countE.get(0)] = fieldValues.SETFIELDPOSITION_H1;
                    map1[countE.get(1)] = fieldValues.SETFIELDPOSITION_H2;
                }

                countE.clear();
            }

            if (countF.size() == 3) {
                if (ship3Rotated) {
                    map1[countF.get(0)] = fieldValues.SETFIELDPOSITION_I4;
                    map1[countF.get(1)] = fieldValues.SETFIELDPOSITION_I5;
                    map1[countF.get(2)] = fieldValues.SETFIELDPOSITION_I6;
                } else {
                    map1[countF.get(0)] = fieldValues.SETFIELDPOSITION_I1;
                    map1[countF.get(1)] = fieldValues.SETFIELDPOSITION_I2;
                    map1[countF.get(2)] = fieldValues.SETFIELDPOSITION_I3;
                }

                countF.clear();
            }
        }

        draw(map1, gridView1);

        boolean[] shipRotated = new boolean[2];
        shipRotated[0] = ship2Rotated;
        shipRotated[1] = ship3Rotated;

        return shipRotated;

    }

    public void restoreShips() {
        for (int i = 0; i < map1.length; i++) {
            switch (map1[i]) {
                case "g":
                    map1[i] = "d";
                    break;
                case "h1":
                    map1[i] = "e1";
                    break;
                case "h2":
                    map1[i] = "e2";
                    break;
                case "h3":
                    map1[i] = "e3";
                    break;
                case "h4":
                    map1[i] = "e4";
                    break;
                case "i1":
                    map1[i] = "f1";
                    break;
                case "i2":
                    map1[i] = "f2";
                    break;
                case "i3":
                    map1[i] = "f3";
                    break;
                case "i4":
                    map1[i] = "f4";
                    break;
                case "i5":
                    map1[i] = "f5";
                    break;
                case "i6":
                    map1[i] = "f6";
                    break;
                default:
                    break;
            }

        }
    }

    public void powerUps() {
        options4.setOnClickListener(new View.OnClickListener() {  //in this case: options4 = powerup4: ship armour
            @Override
            public void onClick(View v) {

                findIntactShips();

                gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                        // final int posi=position;
                        final String posis = map1[position];

                        if (posis.equals("g")) {
                            map1[position] = "j";
                        } else if (posis.equals("h1") || posis.equals("h2") || posis.equals("h3") || posis.equals("h4")) {
                            for (int i = 0; i < map1.length; i++) {
                                switch (map1[i]) {
                                    case "h1":
                                        map1[i] = "k1";
                                        break;
                                    case "h2":
                                        map1[i] = "k2";
                                        break;
                                    case "h3":
                                        map1[i] = "k3";
                                        break;
                                    case "h4":
                                        map1[i] = "k4";
                                        break;
                                }
                            }
                        } else if (fieldValues.i_list.contains(posis)) {
                            for (int i = 0; i < map1.length; i++) {
                                switch (map1[i]) {
                                    case "i1":
                                        map1[i] = "l1";
                                        break;
                                    case "i2":
                                        map1[i] = "l2";
                                        break;
                                    case "i3":
                                        map1[i] = "l3";
                                        break;
                                    case "i4":
                                        map1[i] = "l4";
                                        break;
                                    case "i5":
                                        map1[i] = "l5";
                                        break;
                                    case "i6":
                                        map1[i] = "l6";
                                        break;
                                }
                            }
                        }

                        restoreShips();

                        draw(map1, gridView1);

                        clickMap();

                    }

                });

                setOptionButtonsInvisible();


            }
        });
    }


}