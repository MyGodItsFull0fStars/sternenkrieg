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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;
import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.gamelogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.gamelogic.PlayerFieldShipContainer;
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

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class Gameplay extends AppCompatActivity {
    String tag = "Gameplay";

    GridView gridViewPlayer;
    GridView gridViewEnemy;
    ImageView imageView;
    ImageView options;
    ImageView options1;
    ImageView options2;
    ImageView options3;
    ImageView options4;

    PlayerFieldShipContainer playerFieldShipContainer;
    PlayerFieldShipContainer enemyFieldShipContainer;
    FieldValues fieldValues = new FieldValues();

    String[] mapPlayer;
    String[] mapEnemy;
    String[] mapEnemyFog;

    int width;
    int height;
    int amountShips;
    int highScore = 0;
    int value;
    int pointsPlayer = 0;
    boolean check; // checks whether powerUps are currently displayed
    Vibrator vib;

    /******Networking******/
    // this Views can be also a chat system. That mean player can talk with other player with it.
    // But now we can use it to check if the Socket program right.
    // what we do next: set Start Button and make connect.
    // You can call method messageSend to send String message parameter mit (message, pHost)
    // And in class MyHandler you get message form enemy, msg.what = 1 is what enemy say
    // msg.what = 4 , sendMsg[1] is enemy shoot position.

    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myHandler;
    boolean playerIsHost = false; // if this is host then pHost is true; if not is false.
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
    boolean powerUp2 = false;
    boolean oneShoot = true;
    boolean dice = false;
    boolean dice2 = false;
    boolean sendBool = false;
    boolean came = false;
    Intent intent = new Intent();
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();
    GameUtilities game;
    int who_is_starting;
    ImageView shootEnemy;
    ImageView shootPlayer;
    ImageView imageDice;
    ProgressBar progressWaiting;
    PulsatorLayout pulsatorLayout;
    int count = 0;
    TextView textPoints;
    String mapString = "Map";

    // used-counter of PowerUps
    int pu1used = 0; // PowerUp 1 was used 0 times yet
    int pu2used = 0;
    int pu3used = 0;
    int pu4used = 0;
    // max use of PowerUps
    int pu1max = 2; // PowerUp 1 may be used up to 3 times
    int pu2max = 2;
    int pu3max = 2;
    int pu4max = 2;
    // points of PowerUps
    int pu1points = 0;
    int pu2points = 2;
    int pu3points = 0;
    int pu4points = 1;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private float mLightQuantity;

    private void initializeContainerObjects() {
        playerFieldShipContainer = new PlayerFieldShipContainer();
        enemyFieldShipContainer = new PlayerFieldShipContainer();
    }

    /*******Networking*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gameplay);
        game = new GameUtilities(getApplicationContext());
        /* ***** Networking ****/
        playerIsHost = stats.isPlayerHost();
        who_is_starting = GameUtilities.getWhoIsStarting();
        Net = stats.isNet();
        if (!playerIsHost)
            ip = stats.getIp();
        myHandler = new Myhandler();
        util = new NetworkUtilities(playerIsHost, mAcceptThread, mServerSocket, socket, myHandler, receiveThreadHost, startThread, ip, receiveThreadClient);
        util.networkbuild();
        util.connection();

        /* ***Networking ***/

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
                String text = Float.toString(mLightQuantity);
                tex.setText(text);

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
                // nothing to do here
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

                gridViewPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        fieldValues.initialize_H_list();
                        fieldValues.initialize_I_list();

                        final String positionString = mapPlayer[position];

                        if (positionString.equals(fieldValues.SET_FIELD_POSITION_G)
                                || fieldValues.h_list.contains(positionString)
                                || fieldValues.i_list.contains(positionString))
                        {

                            if (positionString.equals(fieldValues.SET_FIELD_POSITION_G)) {
                                mapPlayer[position] = fieldValues.SET_FIELD_POSITION_PLAYER_HIT;
                                draw(mapPlayer, gridViewPlayer);
                            } else if (fieldValues.h_list.contains(positionString)) {
                                for (int i = 0; i < mapPlayer.length; i++) {
                                    if (
                                        //mapPlayer[i].equals("h1") || mapPlayer[i].equals("h2") || mapPlayer[i].equals("h3") || mapPlayer[i].equals("h4")
                                            fieldValues.h_list.contains(mapPlayer[i])) {

                                        mapPlayer[i] = fieldValues.SET_FIELD_POSITION_PLAYER_HIT;
                                    }

                                    draw(mapPlayer, gridViewPlayer);
                                }
                            } else if (fieldValues.i_list.contains(positionString)) {
                                for (int i = 0; i < mapPlayer.length; i++) {

                                    if (fieldValues.i_list.contains(mapPlayer[i])) {

                                        mapPlayer[i] = fieldValues.SET_FIELD_POSITION_PLAYER_HIT;
                                    }

                                    draw(mapPlayer, gridViewPlayer);
                                }
                            }

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

        powerUpOptions2();

        options3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* go to options-menu */
                intent.setClass(Gameplay.this, Options.class);
                // intent.putExtra("gameOn", 1);
                startActivity(intent);

            }
        });

        imageDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.messageSend("boolean," + "boolean", playerIsHost);
                dice = true;
                pulsatorLayout.stop();
                pulsatorLayout.setVisibility(View.INVISIBLE);
                imageDice.setVisibility(View.INVISIBLE);
                progressWaiting.setVisibility(View.VISIBLE);

                dice();
            }
        });


        amountShips = 3;

        mapPlayer = GameUtilities.getPlayerMap();

        if (sendMap) {
            String sendField = "";
            for (String data : mapPlayer) {
                sendField += data + "x";
                Log.d(tag, sendField);
            }

            mapEnemy = new String[fieldValues.FIELD_SIZE];
            Arrays.fill(mapEnemy, fieldValues.SET_FIELD_POSITION_EMPTY);
            util.messageSend("Map," + sendField, playerIsHost);
            Log.d(tag, "Send " + sendField);
        }


        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        /* initialize grids according based on screen size */
        width = size.x;  //x=1794 -- y=1080
        height = size.y;

        gridViewPlayer = (GridView) findViewById(R.id.player1_grid);
        gridViewPlayer.getLayoutParams().height = height - 350;
        gridViewPlayer.getLayoutParams().width = height - 350;
        gridViewPlayer.setAdapter(new MapLoad(this, mapPlayer));

        gridViewEnemy = (GridView) findViewById(R.id.player2_grid);
        gridViewEnemy.getLayoutParams().height = height - 350;
        gridViewEnemy.getLayoutParams().width = height - 350;
        gridViewEnemy.setAdapter(new MapLoad(this, mapEnemy));
        clickMap();
        start();

        gridViewEnemy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (shoot && count < 1) {
                    util.messageSend("enemy," + v.getX() + " " + v.getY(), playerIsHost);
                    animationPlayer(v.getX() + gridViewEnemy.getX(), v.getY() + gridViewEnemy.getY());

                    util.messageSend("shoot," + position, playerIsHost);
                    oneShoot = false;
                    sendBool = true;
                    sendGridView2 = true;
                    count++;

                    //   messageSend("map,"+position, pHost);

                    // Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                    //       Toast.LENGTH_SHORT).show();
                    String shipType = mapEnemy[position];
           /* hit ship of enemy */
                    if (mapEnemy[position].equals("a") || mapEnemy[position].equals("b") || mapEnemy[position].equals("clientBtn")) {
                        mapEnemy[position] = fieldValues.SET_FIELD_POSITION_PLAYER_HIT;

                        vib.vibrate(500);
                        highScore += 80;

                /* miss enemy'hostBtn ships */
                    } else if (mapEnemy[position].equals(fieldValues.SET_FIELD_POSITION_EMPTY)) {
                        mapEnemy[position] = fieldValues.SET_FIELD_POSITION_MISS;
                        highScore -= 20;
                    }

                    draw(mapEnemy, gridViewEnemy); // update map
                    shoot = false;

                    if (powerUp2) {
                        shoot = true;
                        powerUp2 = false;
                        count = 0;
                    }

                   /* if (gameOver(, map2)) { //check whether a complete ship of the enemy has been destroyed
                        decrementAmount();
                    }*/

                }
                sollFinish();
            }

        });


    }

    private void sollFinish() {
        Log.i("sollFinish", "");
        if (sendBool && came) {
            Log.i("sollFinish if", "");
            imageDice.setVisibility(View.VISIBLE);
            pulsatorLayout.start();
            pulsatorLayout.setVisibility(View.VISIBLE);

        }
    }

    public void start() {
        //Player begins
        pointsPlayer += GameUtilities.getDiceScore();
        String text = Integer.toString(pointsPlayer);
        textPoints.setText(text);
        if (who_is_starting == 0 && oneShoot) {
            shoot = true;
        }


    }

    public void dice() {
        if (dice && dice2) {
            intent.setClass(Gameplay.this, Dice.class);
            GameUtilities.setPlayerMap(mapPlayer);
            GameUtilities.setEnemyMap(mapEnemy);
            NetworkStats.setMode(2);
            if (playerIsHost)
                util.close();
            pulsatorLayout.stop();
            startActivity(intent);


        }

    }

    public void checkShoot(int position, int player) {
        fieldValues.initialiseShipLists();
        if (fieldValues.smallShipStringList.contains(mapPlayer[position])
                || fieldValues.middleShipStringList.contains(mapPlayer[position])
                || fieldValues.bigShipStringList.contains(mapPlayer[position])) {
            mapPlayer[position] = fieldValues.SET_FIELD_POSITION_ENEMY_HIT;
            vib.vibrate(500);
            highScore = highScore - 30;
        }

        //hits ship with armour
        else if (fieldValues.smallShipArmourStringList.contains(mapPlayer[position])
                || fieldValues.middleShipArmourStringList.contains(mapPlayer[position])
                || fieldValues.bigShipArmourStringList.contains(mapPlayer[position])) {
            switch (mapPlayer[position]) {
                case "j":
                    mapPlayer[position] = fieldValues.SET_PLAYER_POSITION_SMALL;
                    break;
                case "k1":
                    mapPlayer[position] = fieldValues.SET_PLAYER_POSITION_MIDDLE1;
                    break;
                case "k2":
                    mapPlayer[position] = fieldValues.SET_PLAYER_POSITION_MIDDLE2;
                    break;
                case "k3":
                    mapPlayer[position] = fieldValues.SET_PLAYER_POSITION_MIDDLE1R;
                    break;
                case "k4":
                    mapPlayer[position] = fieldValues.SET_PLAYER_POSITION_MIDDLE2R;
                    break;
                case "l1":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG1;
                    break;
                case "l2":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG2;
                    break;
                case "l3":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG3;
                    break;
                case "l4":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG1R;
                    break;
                case "l5":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG2R;
                    break;
                case "l6":
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_BIG3R;
                    break;
                default:
                    break;
            }
            vib.vibrate(500);
            draw(mapPlayer, gridViewPlayer);
            draw(mapEnemy, gridViewEnemy);
                    /* opponent misses */
        } else if (mapPlayer[position].equals(fieldValues.SET_FIELD_POSITION_EMPTY)) {
            mapPlayer[position] = fieldValues.SET_FIELD_POSITION_ENEMY_MISS;
            highScore = highScore + 10;
        }
        draw(mapPlayer, gridViewPlayer); // update map


        if (gameOver(fieldValues.smallShipStringList, mapPlayer) && gameOver(fieldValues.middleShipStringList, mapPlayer)
                && gameOver(fieldValues.bigShipStringList, mapPlayer) && gameOver(fieldValues.smallShipArmourStringList, mapPlayer)
                && gameOver(fieldValues.middleShipArmourStringList, mapPlayer) && gameOver(fieldValues.bigShipArmourStringList, mapPlayer)) { //determine whether all ships are already destroyed
            alert("2");
        }
    }

    public void clickMap() {
        gridViewPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                /* enemy hits one of player'hostBtn ships */
                if (
                        fieldValues.smallShipStringList.contains(mapPlayer[position])
                                || fieldValues.middleShipStringList.contains(mapPlayer[position])
                                || fieldValues.bigShipStringList.contains(mapPlayer[position])) {
                    mapPlayer[position] = fieldValues.SET_FIELD_POSITION_ENEMY_HIT;
                    vib.vibrate(500);
                    highScore = highScore - 30;

                    /* opponent misses */
                } else if (mapPlayer[position].equals(fieldValues.SET_FIELD_POSITION_EMPTY)) {
                    mapPlayer[position] = 5 + "";
                    highScore = highScore + 10;
                }
                draw(mapPlayer, gridViewPlayer); // update map


                if (gameOver(fieldValues.smallShipStringList, mapPlayer) && gameOver(fieldValues.smallShipArmourStringList, mapPlayer)
                        && gameOver(fieldValues.middleShipStringList, mapPlayer) && gameOver(fieldValues.middleShipArmourStringList, mapPlayer)
                        && gameOver(fieldValues.bigShipStringList, mapPlayer) && gameOver(fieldValues.bigShipArmourStringList, mapPlayer)) { //determine whether all ships are already destroyed
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
        imageDice = (ImageView) findViewById(R.id.imagePlayNext);
        progressWaiting = (ProgressBar) findViewById(R.id.progressBarPlayWaiting);
        pulsatorLayout = (PulsatorLayout) findViewById(R.id.pulsatorPlay);
        textPoints = (TextView) findViewById(R.id.pointsPlayer);

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
        gridViewPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                for (int i = 0; i < mapPlayer.length; i++) { //ship is no longer located here; set to 0
                    if (mapPlayer[i].equals("4")) {
                        mapPlayer[i] = fieldValues.SET_FIELD_POSITION_EMPTY;
                    }
                }
                boolean shipPlaced = false;

                switch (posis) { //check whether ship can be placed here
                    case "g":
                        if (relocateShip(position, posis, true)) {
                            mapPlayer[position] = "d";
                            shipPlaced = true;

                        }
                        break;
                    case "h1":
                    case "h2":
                    case "h3":
                    case "h4":
                        if (relocateShip(position, posis, ship2Rotated) && !ship2Rotated) { //check if ship is placeable for not rotated ship
                            mapPlayer[position] = "e1";
                            mapPlayer[position + 1] = "e2"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship2Rotated) && ship2Rotated) { //check if ship is placeable for rotated ship
                            mapPlayer[position] = "e3";
                            mapPlayer[position + 8] = "e4"; //relocate ship rotated
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
                            mapPlayer[position] = "f2";
                            mapPlayer[position + 1] = "f3";
                            mapPlayer[position - 1] = "f1"; //relocate ship unrotated
                            shipPlaced = true;
                        } else if (relocateShip(position, posis, ship3Rotated) && ship3Rotated) { //check if ship is placeable for rotated ship
                            mapPlayer[position - 8] = "f4";
                            mapPlayer[position] = "f5";
                            mapPlayer[position + 8] = "f6"; //relocate ship rotated
                            shipPlaced = true;
                        }
                        break;
                    default:
                        break;
                }

                if (shipPlaced) {
                    restoreShips();

                    draw(mapPlayer, gridViewPlayer);

                    //if ship is placed successfully, cheat function ends and normal "onClickListener" is restored

                    clickMap();
                }
            }
        });
    }

    public boolean relocateShip(int position, String input, boolean shipRotated) {

        ArrayList<Integer> failuresRight = new ArrayList<>(Arrays.asList(-1, 7, 15, 23, 31, 39, 47, 55, 63));
        ArrayList<Integer> failuresLeft = new ArrayList<>(Arrays.asList(8, 16, 24, 32, 40, 48, 56, 64));

        switch (input) {
            case "g":
                if (checkAvailability(position)) {
                    return true;
                }
            case "h1":
            case "h2":
            case "h3":
            case "h4":
                if (!shipRotated) {
                    return !(failuresLeft.contains(position + 1) || !checkAvailability(position) || !checkAvailability(position + 1));
                } else {
                    return !(position + 1 > 63 || !checkAvailability(position) || !checkAvailability(position + 8));
                }
            case "i1":
            case "i2":
            case "i3":
            case "i4":
            case "i5":
            case "i6":
                if (!shipRotated) {
                    return !(failuresLeft.contains(position + 1) || failuresRight.contains(position - 1)
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
        //  if(mapPlayer[position].equals(posis)){ return true;} else {
        //  fieldValues.initialiseCheckAvailabilityList();
        if (fieldValues.smallShipStringList.contains(mapPlayer[position])
                || fieldValues.middleShipStringList.contains(mapPlayer[position])
                || fieldValues.bigShipStringList.contains(mapPlayer[position])
                || fieldValues.SET_FIELD_POSITION_G.equals(mapPlayer[position])
                || fieldValues.h_list.contains(mapPlayer[position])
                || fieldValues.i_list.contains(mapPlayer[position])
                || fieldValues.SET_FIELD_POSITION_ENEMY_HIT.equals(mapPlayer[position])
                || fieldValues.SET_FIELD_POSITION_ENEMY_MISS.equals(mapPlayer[position])) {
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
                            Intent intent = new Intent(Gameplay.this, Highscore.class);
                            GameUtilities.setPoints(highScore);
                            GameUtilities.setHighScoreMain(true);
                            startActivity(intent);
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
                            Intent intent = new Intent(Gameplay.this, Highscore.class);
                            GameUtilities.setPoints(highScore);
                            GameUtilities.setHighScoreMain(true);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

        // Create the AlertDialog object and return it
        builder.create();

    }

    // TODO: 20/06/2017
    public boolean gameOver(LinkedList<String> hi, String[] map) {
            /* checks if String "ship" (either checks whether player has any ships left or if an
            entire ship of the opponent has been destroyed) is still present in array "map";
            if not, method returns true */
        int isTheGameOverYet = 0;
        for (int i = 0; i < fieldValues.FIELD_SIZE; i++) {
            if ((hi.contains(map[i]))) {
                isTheGameOverYet++;
            }
        }

        return isTheGameOverYet == 0;

    }

    public void decrementAmount() { //if one entire ship of enemy has been destroyed, update Score
        amountShips--;
        TextView tex = (TextView) findViewById(R.id.amountShips);
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

    public void animationPlayer(float x, float y) {
        shootPlayer.setVisibility(View.VISIBLE);
        TranslateAnimation slideUp = new TranslateAnimation(0, x - shootPlayer.getX(), 0, y - shootPlayer.getY());
        slideUp.setDuration(1000);
        slideUp.setFillAfter(true);
        shootPlayer.setAnimation(slideUp);


        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.shootsound);
                mp.start();
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                Glide.with(getApplicationContext()).load(R.raw.explosion).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shootPlayer);
                new CountDownTimer(1000, 100) {

                    public void onTick(long millisUntilFinished) {
                        //wird nicht benötigt
                    }

                    @Override
                    public void onFinish() {
                        shootPlayer.setVisibility(View.INVISIBLE);
                        animation.setFillAfter(false);

                    }

                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // nothing to do here
            }
        });


    }

    public void animationEnemy(float x, float y) {
        shootEnemy.setVisibility(View.VISIBLE);
        TranslateAnimation slideUp = new TranslateAnimation(0, -shootEnemy.getX() + (x + gridViewPlayer.getX()), 0, -shootEnemy.getY() + (y + gridViewPlayer.getY()));
        slideUp.setDuration(1000);
        slideUp.setFillAfter(true);
        shootEnemy.setAnimation(slideUp);

        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.shootsound);
                mp.start();
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                Glide.with(getApplicationContext()).load(R.raw.explosion).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shootEnemy);
                new CountDownTimer(1000, 100) {

                    public void onTick(long millisUntilFinished) {
                        //wird nicht benötigt
                    }

                    @Override
                    public void onFinish() {
                        shootEnemy.setVisibility(View.INVISIBLE);
                        animation.setFillAfter(false);

                    }

                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // nothing to do here
            }
        });

    }

    public boolean[] findIntactShips() {
        boolean ship2Rotated = true;
        boolean ship3Rotated = true;

        LinkedList<Integer> countD = new LinkedList<>();
        LinkedList<Integer> countE = new LinkedList<>();
        LinkedList<Integer> countF = new LinkedList<>();

        for (int i = 0; i < mapPlayer.length; i++) {
            switch (mapPlayer[i]) {
                case "d":
                    countD.add(i);
                    break;
                case "e1":
                case "e2":
                case "e3":
                case "e4":
                    countE.add(i);
                    if (mapPlayer[i + 1].equals("e2")) {
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
                    if (mapPlayer[i + 1].equals("f2")) {
                        ship3Rotated = false;
                    }
                    break;
                default:
                    break;
            }


            if (countD.size() == 1) {
                for (int j = 0; j < countD.size(); j++) {
                    mapPlayer[countD.get(j)] = fieldValues.SET_FIELD_POSITION_G;
                }
                countD.clear();

            }

            if (countE.size() == 2) {
                if (ship2Rotated) {
                    mapPlayer[countE.get(0)] = fieldValues.SET_FIELD_POSITION_H3;
                    mapPlayer[countE.get(1)] = fieldValues.SET_FIELD_POSITION_H4;
                } else {
                    mapPlayer[countE.get(0)] = fieldValues.SET_FIELD_POSITION_H1;
                    mapPlayer[countE.get(1)] = fieldValues.SET_FIELD_POSITION_H2;
                }

                countE.clear();
            }

            if (countF.size() == 3) {
                if (ship3Rotated) {
                    mapPlayer[countF.get(0)] = fieldValues.SET_FIELD_POSITION_I4;
                    mapPlayer[countF.get(1)] = fieldValues.SET_FIELD_POSITION_I5;
                    mapPlayer[countF.get(2)] = fieldValues.SET_FIELD_POSITION_I6;
                } else {
                    mapPlayer[countF.get(0)] = fieldValues.SET_FIELD_POSITION_I1;
                    mapPlayer[countF.get(1)] = fieldValues.SET_FIELD_POSITION_I2;
                    mapPlayer[countF.get(2)] = fieldValues.SET_FIELD_POSITION_I3;
                }

                countF.clear();
            }
        }

        draw(mapPlayer, gridViewPlayer);

        boolean[] shipRotated = new boolean[2];
        shipRotated[0] = ship2Rotated;
        shipRotated[1] = ship3Rotated;

        return shipRotated;

    }

    public void restoreShips() {
        for (int i = 0; i < mapPlayer.length; i++) {
            switch (mapPlayer[i]) {
                case "g":
                    mapPlayer[i] = "d";
                    break;
                case "h1":
                    mapPlayer[i] = "e1";
                    break;
                case "h2":
                    mapPlayer[i] = "e2";
                    break;
                case "h3":
                    mapPlayer[i] = "e3";
                    break;
                case "h4":
                    mapPlayer[i] = "e4";
                    break;
                case "i1":
                    mapPlayer[i] = "f1";
                    break;
                case "i2":
                    mapPlayer[i] = "f2";
                    break;
                case "i3":
                    mapPlayer[i] = "f3";
                    break;
                case "i4":
                    mapPlayer[i] = "f4";
                    break;
                case "i5":
                    mapPlayer[i] = "f5";
                    break;
                case "i6":
                    mapPlayer[i] = "f6";
                    break;
                default:
                    break;
            }

        }
    }

    public void powerUpOptions2() {
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
    }

    public void powerUps() {
        options2.setOnClickListener(new View.OnClickListener() {  //in this case: options4 = powerup4: ship armour
            @Override
            public void onClick(View v) {
                setOptionButtonsInvisible();

                if (pu2used < pu2max) {
                    if (removePowerUpPoints(pu2points)) {
                        GameUtilities.setDiceScore(GameUtilities.getDiceScore() - pu2points);
                        Log.d(tag, "Using PowerUp2, new points: " + GameUtilities.getDiceScore());
                        pu2used++;
                        powerUp2 = true;
                    } else {
                        powerUpDialog("Zu wenig Punkte!");
                        Log.d(tag, "PowerUp2: Zu wenig Punkte");
                    }
                } else {
                    powerUpDialog("PowerUp already used!");
                    Log.d(tag, "PowerUp2: Bereits verwendet");
                }

                powerUpOptions2();
            }
        });
        options4.setOnClickListener(new View.OnClickListener() {  //in this case: options4 = powerup4: ship armour
            @Override
            public void onClick(View v) {
                Log.d(tag, "PowerUp4 chosen, Current points: " + GameUtilities.getDiceScore());

                // checking if PowerUp still may be used
                if (pu4used < pu4max) {
                    if (removePowerUpPoints(pu4points)) {
                        GameUtilities.setDiceScore(GameUtilities.getDiceScore() - pu4points);
                        Log.d(tag, "Using PowerUp4, new points: " + GameUtilities.getDiceScore());
                        pu4used++;

                        findIntactShips();

                        gridViewPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                                // final int posi=position;
                                final String posis = mapPlayer[position];

                                if (posis.equals("g")) {
                                    mapPlayer[position] = "j";
                                } else if (posis.equals("h1") || posis.equals("h2") || posis.equals("h3") || posis.equals("h4")) {
                                    for (int i = 0; i < mapPlayer.length; i++) {
                                        switch (mapPlayer[i]) {
                                            case "h1":
                                                mapPlayer[i] = "k1";
                                                break;
                                            case "h2":
                                                mapPlayer[i] = "k2";
                                                break;
                                            case "h3":
                                                mapPlayer[i] = "k3";
                                                break;
                                            case "h4":
                                                mapPlayer[i] = "k4";
                                                break;
                                        }
                                    }
                                } else if (fieldValues.i_list.contains(posis)) {
                                    for (int i = 0; i < mapPlayer.length; i++) {
                                        switch (mapPlayer[i]) {
                                            case "i1":
                                                mapPlayer[i] = "l1";
                                                break;
                                            case "i2":
                                                mapPlayer[i] = "l2";
                                                break;
                                            case "i3":
                                                mapPlayer[i] = "l3";
                                                break;
                                            case "i4":
                                                mapPlayer[i] = "l4";
                                                break;
                                            case "i5":
                                                mapPlayer[i] = "l5";
                                                break;
                                            case "i6":
                                                mapPlayer[i] = "l6";
                                                break;
                                        }
                                    }
                                }

                                restoreShips();

                                draw(mapPlayer, gridViewPlayer);

                                options4.setOnClickListener(new View.OnClickListener() { //options4 is "help" button again. but it doesn't do anything.
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                clickMap();

                            }

                        });

                        setOptionButtonsInvisible();


                    } else {
                        powerUpDialog("Zu wenig Punkte!");
                        Log.d(tag, "PowerUp4: Zu wenig Punkte");
                    }
                } else {
                    powerUpDialog("PowerUp already used!");
                    Log.d(tag, "PowerUp4: Bereits verwendet");
                }
            }
        });
    }

    public boolean removePowerUpPoints(int points) {
        if (GameUtilities.getDiceScore() - points >= 0) {
            GameUtilities.setDiceScore(GameUtilities.getDiceScore() - points);
            return true;
        }
        return false;
    }

    public AlertDialog powerUpDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage(text);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing to do here
            }
        });
        builder.show();

        return builder.create();
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
                        mapEnemy = mapMsg[1].split("x");
                        draw(mapEnemy, gridViewEnemy);
                        util.messageSend("Gotit,1", playerIsHost);
                        System.out.println(mapString + mapMsg[1]);

                    }
                    if (mapMsg[0].equals("Gotit")) {
                        System.out.println("Gotit");
                        sendMap = false;
                    }
                    if (mapMsg[0].equals("shoot")) {
                        String position = mapMsg[1];
                        came = true;
                        checkShoot(Integer.parseInt(position), 2);

                        shoot = true;

                        draw(mapPlayer, gridViewPlayer);
                        draw(mapEnemy, gridViewEnemy);

                    }
                    if (mapMsg[0].equals("boolean")) {
                        Log.i("MessageBoolean", message);
                        Log.i(Dice.class.getName(), "Boolean");
                        dice2 = true;
                        dice();
                    }
                    if ("enemy".equals(mapMsg[0])) {
                        String[] cord = mapMsg[1].split(" ");
                        animationEnemy(Float.valueOf(cord[0]), Float.valueOf(cord[1]));
                    }
                    sollFinish();

                    break;

                case 0:
                    if (sendMap) {
                        String sendMap = "";
                        for (String data : mapPlayer)
                            sendMap += data + "x";

                        mapEnemy = new String[fieldValues.FIELD_SIZE];
                        Arrays.fill(mapEnemy, fieldValues.SET_FIELD_POSITION_EMPTY);
                        util.messageSend("Map," + sendMap, playerIsHost);
                        System.out.println("Send" + sendMap);
                    }

                    break;
                case 2:
                    break;
            }
        }

    }

}