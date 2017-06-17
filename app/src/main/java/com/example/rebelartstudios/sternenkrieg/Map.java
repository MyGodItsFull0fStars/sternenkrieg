package com.example.rebelartstudios.sternenkrieg;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.GameLogic.GameUtilities;
import com.example.rebelartstudios.sternenkrieg.GameLogic.NetworkStats;
import com.example.rebelartstudios.sternenkrieg.GameLogic.PlayerFieldPositionString;
import com.example.rebelartstudios.sternenkrieg.GameLogic.PlayerFieldShipContainer;
import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Map Class gets started after clicking the Main class.
 * In this class, the player sets the position of the ships using drag and drop.
 */
public class Map extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ImageView ship1, ship2, ship3, turn, play;

    int width;
    int height;
    int which_ship;

    MapLoad mapLoad;
    GameUtilities game;


    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;

    boolean Phost = false; // if this is host then phost is ture; if not is false.
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

    PlayerFieldPositionString fieldValues = new PlayerFieldPositionString();
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

        game= new GameUtilities(getApplicationContext());

        initializeShipView();

        /********************Netz**************************/
        System.out.println("Map");
        Phost = stats.isPhost();

        System.out.println("phost: " + Phost);
        Net = stats.isNet();
        System.out.println("net: " + Net);

        if (Phost == false) {
            ip = stats.getIp();
            System.out.println("Ip: " + ip);
        }


        myhandler = new Myhandler();

        util = new NetworkUtilities(
                Phost,
                mAcceptThread,
                mServerSocket,
                socket,
                myhandler,
                receiveThreadHost,
                startThread,
                ip,
                receiveThreadClient);

        util.networkbuild();


        util.connection();


        /********************Netz**************************/


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

                        //kleines Schiff
                        if (which_ship == playerFieldShipContainer.getShipLogic().SMALL_SHIP_ID) {
                            if (!playerFieldShipContainer.playerFieldPositionContainsString(position, fieldValues.SETFIELDPOSITION_D) ||
                                    !playerFieldShipContainer.playerFieldPositionContainsString(position, fieldValues.SETFIELDPOSITION_F)
                                    ) {

                                delete(playerFieldShipContainer.getShipLogic().getSmallShipArray());
                                //neue Position gesetzt
                                playerFieldShipContainer.setSmallShipContainer(position, fieldValues.SETFIELDPOSITION_D);
                                playerFieldShipContainer.getShipLogic().setSmallShipIsSetOnField(true);
                            }
                        }

                        //mittleres Schiff
                        if (which_ship == playerFieldShipContainer.getShipLogic().MIDDLE_SHIP_ID &&
                                playerFieldShipContainer.checkPosition(position, which_ship, degree)) {

                            delete(playerFieldShipContainer.getShipLogic().getMiddleShipArray());
                            playerFieldShipContainer.setMiddleShipContainer(position, degree, fieldValues.SETPLAYERPOSITION_E);
                            playerFieldShipContainer.getShipLogic().setMiddleShipIsSetOnField(true);
                        }
                        //großes Schiff
                        if (which_ship == playerFieldShipContainer.getShipLogic().BIG_SHIP_ID &&
                                playerFieldShipContainer.checkPosition(position, which_ship, degree)) {
                            delete(playerFieldShipContainer.getShipLogic().getBigShipArray());

                            playerFieldShipContainer.setBigShipContainer(position, degree, fieldValues.SETFIELDPOSITION_F);
                            playerFieldShipContainer.getShipLogic().setBigShipIsSetOnField(true);
                        }

                        draw(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
                        setShipsVisible();


                        if (playerFieldShipContainer.getShipLogic().allShipsSetOnPlayerField()) {

                            play.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_other));
                        }

                        return (true);

                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
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
                //small ship

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
        play = (ImageView) findViewById(R.id.play);
    }


    /**

     * Initializes the ShipViews in the onCreate() method
     */
    private void initializeShipView() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.getLayoutParams().height = height - 350;
        gridView.getLayoutParams().width = height - 350;
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

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playerFieldShipContainer.getShipLogic().allShipsSetOnPlayerField()) {

                    intent.setClass(Map.this, Spielfeld.class);
                    game.setPlayerMap(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
                    finish = true;
                    util.messageSend("boolean", Phost);
                    if (!Phost) {
                        new CountDownTimer(200, 100) {
                            public void onTick(long millisUntilFinished) {
                                System.out.print(millisUntilFinished);
                            }

                            @Override
                            public void onFinish() {
                                syncClose();
                            }

                        }.start();
                    } else
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

    /**
     * Receives an integer array containing the positions, which will be deleted
     * in the player field
     *
     * @param shipArray used to delete the fields in the player field
     */
    // TODO: 16/06/2017 Refactor to Container class and only call method from container and draw() method
    public void delete(int shipArray[]) {
        playerFieldShipContainer.delete(shipArray);
        draw(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
    }


    public void draw(String[] array) {
        gridView.setAdapter(mapLoad);
    }


    public void syncClose() {
        if (finish && finishEnemy) {
            util.close();
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
