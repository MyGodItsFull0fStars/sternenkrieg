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
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
    boolean smallShipIsSetOnField, middleShipIsSetOnField, bigShipIsSetOnField = false;
    MapLoad mapLoad;

    Socket socket = new Socket();
    ServerSocket mServerSocket = null;
    Handler myhandler;
    boolean Phost = false; // if this is host then Phost is true; if not is false.
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

    //ShipLogic shipLogic;
    PlayerFieldPositionString fieldValues = new PlayerFieldPositionString();
    int degree = fieldValues.HORIZONTAL;
    //PlayerFieldLogic playerFieldLogic;
    PlayerFieldShipContainer playerFieldShipContainer;

    public void initializeMap() {
        mapLoad = new MapLoad(this, playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
    }

    private void initializeLogicClasses() {
//        shipLogic = new ShipLogic();
//        shipLogic.shipsOnFieldInitialize();
//        playerFieldLogic = new PlayerFieldLogic();
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

        initializeShipView();

        /********************Netz**************************/
        System.out.println("Map");
        Phost = stats.isPhost();
        System.out.println("Phost: " + Phost);
        Net = stats.isNet();
        System.out.println("Net: " + Net);
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
               /* playerField[position] = 1 + "";
                draw(playerField);*/


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
                            if (!playerFieldShipContainer.playerFieldPositionContainsString(position, fieldValues.SETFIELDPOSITION_D) &&
                                    !playerFieldShipContainer.playerFieldPositionContainsString(position, fieldValues.SETFIELDPOSITION_F)
                                    ) {
                                //falls schon mal gesetzt wird die letzte Position gelöscht
                                //delete(shipLogic.getSmallShipArray());
                                delete(playerFieldShipContainer.getShipLogic().getSmallShipArray());
                                //neue Position gesetzt
                                //playerFieldLogic.setPFSmallShipPosition(position, fieldValues.SETFIELDPOSITION_D);
                                //shipLogic.setSmallShipPosition(position);
                                //shipLogic.setSmallShipIsSetOnField(true);
                                playerFieldShipContainer.setSmallShipContainer(position, fieldValues.SETFIELDPOSITION_D);
                                playerFieldShipContainer.getShipLogic().setSmallShipIsSetOnField(true);
                            }
                            smallShipIsSetOnField = true;
                        }

                        //mittleres Schiff
                        if (which_ship == playerFieldShipContainer.getShipLogic().MIDDLE_SHIP_ID &&
                                playerFieldShipContainer.checkPosition(position, which_ship, degree)) {
                            //if (degree == fieldValues.HORIZONTAL) {
                            //check_position schaut dass das schiff nicht außerhalb der playerField oder vom rechten ende der playerField auf die linke seite gesetzt wird
                            //if (playerFieldShipContainer.checkPosition(position, which_ship, degree)) {
                                //   check_position(position, which_ship, degree)) {
                                // delete(shipLogic.getMiddleShipArray());
                                delete(playerFieldShipContainer.getShipLogic().getMiddleShipArray());
                                // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                                //playerFieldLogic.setPFMiddleShipPosition(position, degree, fieldValues.SETPLAYERPOSITION_E);
                                //shipLogic.setMiddleShipPosition(position, degree);
                                //shipLogic.setMiddleShipIsSetOnField(true);
                                playerFieldShipContainer.setMiddleShipContainer(position, degree, fieldValues.SETPLAYERPOSITION_E);
                                playerFieldShipContainer.getShipLogic().setMiddleShipIsSetOnField(true);
                            //}
                            //} else if (degree == fieldValues.VERTICAL) {
//                                if (check_position(position, which_ship, degree)) {
//                                    delete(shipLogic.getMiddleShipArray());
//                                    // playerField[position - 8] = setPlayerPositionE;
//                                    // playerField[position] = setPlayerPositionE;
//                                    playerFieldLogic.setPFMiddleShipPosition(position, degree, fieldValues.SETPLAYERPOSITION_E);
//                                    shipLogic.setMiddleShipPosition(position, degree);
//                                    shipLogic.setMiddleShipIsSetOnField(true);
//                                }

                            //}
                            middleShipIsSetOnField = true;

                        }
                        //großes Schiff
                        if (which_ship == playerFieldShipContainer.getShipLogic().BIG_SHIP_ID &&
                                playerFieldShipContainer.checkPosition(position, which_ship, degree)) {
//                            if (degree == fieldValues.HORIZONTAL) {

                            //    check_position(position, which_ship, degree)) {
                            // delete(shipLogic.getBigShipArray());
                            delete(playerFieldShipContainer.getShipLogic().getBigShipArray());

                            // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                            //playerFieldLogic.setPFBigShipPosition(position, degree, fieldValues.SETFIELDPOSITION_F);
                            //shipLogic.setBigShipPosition(position, degree);
                            // shipLogic.setBigShipIsSetOnField(true);
                            playerFieldShipContainer.setBigShipContainer(position, degree, fieldValues.SETFIELDPOSITION_F);
                            playerFieldShipContainer.getShipLogic().setBigShipIsSetOnField(true);

                            //} else if (degree == fieldValues.VERTICAL) {
//                            if (check_position(position, which_ship, degree)) {
//                                delete(shipLogic.getBigShipArray());
//                                // playerField[position - 8] = setPlayerPositionF;
//                                // playerField[position] = setPlayerPositionF;
//                                // playerField[position + 8] = setPlayerPositionF;
//                                playerFieldLogic.setPFBigShipPosition(position, degree, fieldValues.SETFIELDPOSITION_F);
//                                shipLogic.setBigShipPosition(position, degree);
//                                shipLogic.setBigShipIsSetOnField(true);
//                            }
                            //}
                            bigShipIsSetOnField = true;

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

    //schaut das das Schiff nicht über die Map hinaus gesetzt wird
//    public boolean check_position(int pos, int size, int degree) {
//        ArrayList<Integer> failures_right = new ArrayList<Integer>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
//        ArrayList<Integer> failures_left = new ArrayList<>(Arrays.asList(8, 16, 24, 32, 40, 48, 56));
//
//        //linkes und rechtes Ende der Map
//        if (degree == 180 || degree == 0) {
//            if (size == 1) {
//                if (failures_right.contains(pos - 1) || failures_left.contains(pos) || pos < 1 || pos > 62) {
//                    return false;
//                }
//            } else if (size == 2) {
//                if (failures_right.contains(pos - 1) || failures_right.contains(pos) || failures_left.contains(pos) || pos < 1 || pos > 62) {
//                    return false;
//                }
//            }
//        }
//        //Oben und Unten
//        // TODO: 15/06/2017 can't be reached at the moment
//        if (degree == 90 || degree == 270) {
//            if (size == 1) {
//                if (pos < 8 || pos > 63) {
//                    return false;
//                }
//            } else if (size == 2) {
//                if (pos < 8 || pos > 55) {
//                    return false;
//                }
//            }
//        }
//
//        // TODO: 16/06/2017 Add to the container class of ShipLogic and PlayerFieldLogic
//        if (which_ship == shipLogic.BIG_SHIP_ID) {
//            if (degree == fieldValues.HORIZONTAL) {
//                if (playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos + 1].equals(fieldValues.SETFIELDPOSITION_D)
//                        || playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos + 1].equals(fieldValues.SETPLAYERPOSITION_E)) {
//                    return false;
//                }
//            } else {
//                if (playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos + 8].equals(fieldValues.SETFIELDPOSITION_D)
//                        || playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos].equals(fieldValues.SETPLAYERPOSITION_E) || playerFieldLogic.playerField[pos + 8].equals(fieldValues.SETPLAYERPOSITION_E)) {
//                    return false;
//                }
//            }
//        } else if (which_ship == shipLogic.MIDDLE_SHIP_ID) {
//            if (degree == fieldValues.HORIZONTAL) {
//                if (playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D)
//                        || playerFieldLogic.playerField[pos - 1].equals(fieldValues.SETFIELDPOSITION_F) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_F)) {
//                    return false;
//                }
//            } else {
//                if (playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_D) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_D)
//                        || playerFieldLogic.playerField[pos - 8].equals(fieldValues.SETFIELDPOSITION_F) || playerFieldLogic.playerField[pos].equals(fieldValues.SETFIELDPOSITION_F)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }


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

                if (playerFieldShipContainer.getShipLogic().allShipsSetOnPlayerField()){
                       // smallShipIsSetOnField && middleShipIsSetOnField && bigShipIsSetOnField) {

                    intent.setClass(Map.this, Spielfeld.class);
                    NetworkStats.setPlayerMap(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
                    finish = true;
                    util.messageSend("boolean", Phost, true);
                    if (!Phost) {
                        new CountDownTimer(500, 100) {
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
        // TODO: 16/06/2017 reminder


        gridView.setAdapter(new MapLoad(this, playerFieldShipContainer.getPlayerFieldLogic().getPlayerField()));

        /*
        When this button gets clicked, the ships rotate around the Y axis
         */
        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (degree == fieldValues.HORIZONTAL) {
                    degree = fieldValues.VERTICAL;
                    ship1.animate().rotationBy(270).start();
                    ship2.animate().rotationBy(270).start();
                    ship3.animate().rotationBy(270).start();
                } else {
                    degree = fieldValues.HORIZONTAL;
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
//        if (data != null) {
//            for (int x : data) {
//                playerFieldLogic.playerField[x] = fieldValues.SETFIELDPOSITION_ZERO;
//            }
//        }

       // draw(playerFieldLogic.playerField);
        draw(playerFieldShipContainer.getPlayerFieldLogic().getPlayerField());
    }

    // TODO: 16/06/2017 Is it really necessary to call this every time?
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


            switch (msg.what) {
                case 1:
                    message = (String) msg.obj;
                    message = (String) msg.obj;
                    int count = 0;
                    if (message == null) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (count == 3) {
                        util.close();
                    }
                    if (!(message == null)) {
                        if (message.equals("boolean")) {
                            finishEnemy = true;
                            syncClose();

                        }
                    }

                    Log.d(tag, "Message: " + message);

                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
                    displayToast("!");
                    break;
            }
        }

    }

    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}

