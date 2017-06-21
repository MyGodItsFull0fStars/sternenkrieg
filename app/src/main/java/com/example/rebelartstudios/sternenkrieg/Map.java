package com.example.rebelartstudios.sternenkrieg;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Map Class gets started after clicking the Main class.
 * In this class, the player sets the position of the ships using drag and drop.
 */
public class Map extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ImageView ship1, ship2, ship3, turn;
    TextView textMapWaiting;
    PulsatorLayout pulsatorLayout;
    ImageView imageMapGoNext;
    ProgressBar progressWaiting;

    int width;
    int height;
    int which_ship;

    MapLoad mapLoad;
    GameUtilities game;


    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myHandler;

    boolean playerHost = false; // if this is host then pHost is ture; if not is false.
    String message;
    ReceiveThreadHost receiveThreadHost;
    String ip;
    ReceiveThreadClient receiveThreadClient;
    String tag = "Map";
    AcceptThread mAcceptThread;
    StartThread startThread;
    OutputStream os = null;
    boolean Net = false;

    Intent intent = new Intent();
    boolean finish = false;
    boolean finishEnemy = false;
    NetworkUtilities util;
    NetworkStats stats = new NetworkStats();

    FieldValues fieldValues = new FieldValues();
    int degree = fieldValues.HORIZONTAL;
    PlayerFieldShipContainer playerFieldShipContainer;

    public void initializeMap() {
        mapLoad = new MapLoad(this, playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
    }

    private void initializeLogicClasses() {

        playerFieldShipContainer = new PlayerFieldShipContainer();
        playerFieldShipContainer.getShipLogic().shipsOnFieldInitialize();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_map);
        initializeImageViews();
        initializeLogicClasses();
        // initializePlayerField();

        initializeMap();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        //werden für die größer der Map benötigt
        display.getSize(size);
        width = size.x;
        height = size.y;

        game = new GameUtilities(getApplicationContext());

        initializeShipView();

        /* *******************Networking**************************/
        System.out.println("Map");
        playerHost = stats.isPhost();

        System.out.println("pHost: " + playerHost);
        Net = stats.isNet();
        System.out.println("net: " + Net);

        if (!playerHost) {
            ip = stats.getIp();
            System.out.println("Ip: " + ip);
        }


        myHandler = new Myhandler();

        util = new NetworkUtilities(
                playerHost,
                mAcceptThread,
                mServerSocket,
                socket,
                myHandler,
                receiveThreadHost,
                startThread,
                ip,
                receiveThreadClient);

        util.networkbuild();


        util.connection();


        /* *******************Networking**************************/


        //Glide.with(this).load(R.raw.fog).asGif().into(((ImageView)gridView));
        initializeOnClickListenerOnButton();
        //ClickListener gibt die Position #Kachel zurück
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
                        Toast.LENGTH_SHORT).show();

            }
        });
        gridView.setOnDragListener(new AdapterView.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:

                        break;

                    case DragEvent.ACTION_DROP: {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        int position = position(x, y);

                        playerFieldShipContainer.setShipOnPlayerFieldWithDragAndDrop(position, which_ship, degree);

                        draw(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());


                        if (playerFieldShipContainer.getShipLogic().allShipsSetOnPlayerField()) {
                            pulsatorLayout.start();
                            imageMapGoNext.setVisibility(View.VISIBLE);
                        }
                        setShipsVisible();
                        return (true);

                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
                        // setShipsVisible();
                        return (true);

                    }
                    default:
                        break;
                }
                return true;
            }

        });
        // TODO: 16/06/2017 check if only set ship invisible if really on player field is needed

        //ShadowBuilder erzeugt eine Animation, wenn man das schiff los lässt
        ship1.setOnTouchListener(new View.OnTouchListener()

        {

            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship1);
                v.startDrag(data, shadow, null, 0);

                which_ship = playerFieldShipContainer.getShipLogic().SMALL_SHIP_ID;

                ship1.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        ship2.setOnTouchListener(new View.OnTouchListener()

        {

            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship2);
                v.startDrag(data, shadow, null, 0);
                //middle ship
                which_ship = playerFieldShipContainer.getShipLogic().MIDDLE_SHIP_ID;

                ship2.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        ship3.setOnTouchListener(new View.OnTouchListener()

        {

            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship3);
                v.startDrag(data, shadow, null, 0);
                //big ship
                which_ship = playerFieldShipContainer.getShipLogic().BIG_SHIP_ID;
                ship3.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }


    private void setShipsVisible() {
        ship1.setVisibility(View.VISIBLE);
        ship2.setVisibility(View.VISIBLE);
        ship3.setVisibility(View.VISIBLE);
    }

    /**
     * Initializes the ImageViews in the maps activity class when starting the
     * onCreate() method
     */
    private void initializeImageViews() {
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        ship1 = (ImageView) findViewById(R.id.image_ship1);
        ship2 = (ImageView) findViewById(R.id.image_ship2);
        ship3 = (ImageView) findViewById(R.id.image_ship3);
        turn = (ImageView) findViewById(R.id.image_turn);
        textMapWaiting = (TextView) findViewById(R.id.textMapWaiting);
        pulsatorLayout = (PulsatorLayout) findViewById(R.id.pulsatorPlay);
        imageMapGoNext = (ImageView) findViewById(R.id.imageMapGoNext);
        progressWaiting = (ProgressBar) findViewById(R.id.progressBarMapWaiting);
    }


    /**
     * Initializes the ShipViews in the onCreate() method
     */
    private void initializeShipView() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.getLayoutParams().height = (height - 350);
        gridView.getLayoutParams().width = (height - 350);
        gridView.setTranslationY(-60f);
        gridView.setTranslationX(-10f);
        ship1.getLayoutParams().height = (height - 350) / 8;
        ship1.getLayoutParams().width = (height - 350) / 8;
        ship2.getLayoutParams().height = (height - 350) / 8;
        ship2.getLayoutParams().width = (height - 350) / 4;
        ship3.getLayoutParams().height = (height - 350) / 8;
        ship3.getLayoutParams().width = (height - 350) / 3;
    }

    /**
     * Initializes the onClickListeners for the buttons, gridview
     * in the maps activity class
     */
    private void initializeOnClickListenerOnButton() {

        imageMapGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playerFieldShipContainer.getShipLogic().allShipsSetOnPlayerField()) {

                    intent.setClass(Map.this, Gameplay.class);
                    GameUtilities.setPlayerMap(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
                    finish = true;
                    util.messageSend("boolean", playerHost);
                    imageMapGoNext.setVisibility(View.INVISIBLE);
                    pulsatorLayout.stop();
                    progressWaiting.setVisibility(View.VISIBLE);
                    textMapWaiting.setText("Waiting for " + game.getEnemyUsername());
                    textMapWaiting.setVisibility(View.VISIBLE);

                    syncClose();
                }

            }
        });

        gridView.setAdapter(new MapLoad(this, playerFieldShipContainer.getPlayerFieldLogic().getPlayerField()));

        /*
        When this button gets clicked, the ships rotate around the Y axis
         */
        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (degree == 0) {
                    degree = 1;
                    ship1.animate().rotationBy(270).start();
                    ship2.animate().rotationBy(270).start();
                    ship3.animate().rotationBy(270).start();
                } else {
                    degree = 0;
                    ship1.animate().rotationBy(90).start();
                    ship2.animate().rotationBy(90).start();
                    ship3.animate().rotationBy(90).start();
                }
            }
        });
    }

    public int position(int x, int y) {
        int zehner = y * 8 / (height - 350);
        zehner = zehner * 8;
        int einer = x * 8 / (height - 350);
        return zehner + einer;
    }


    public void draw(String[] array) {
        gridView.setAdapter(mapLoad);
    }


    public void syncClose() {
        if (finish && finishEnemy) {

            util.close();
            pulsatorLayout.stop();
            startActivity(intent);

        }

    }


    /********************Netz**************************/

    // There are the Message from other player. We can work with "message" to change our map, uppower and ship.
    class Myhandler extends Handler {


        public void handleMessage(Message msg) {
            message = util.handleMessage(msg);

            if (message.equals("boolean")) {
                finishEnemy = true;
                syncClose();

            }
        }

    }

    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
