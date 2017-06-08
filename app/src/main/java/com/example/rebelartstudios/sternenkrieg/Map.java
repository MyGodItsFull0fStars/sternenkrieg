package com.example.rebelartstudios.sternenkrieg;

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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Map Class gets started after clicking the Main class.
 * In this class, the player sets the position of the ships using drag and drop.
 */
public class Map extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ImageView ship1, ship2, ship3, turn, play;
    String playerField[] = new String[64];
    int width;
    int height;
    int oldpos;
    int degree = 0;
    int which_ship;
    int[] old_small = new int[1];
    int[] old_middle = new int[2];
    int[] old_big = new int[3];
    boolean count0, count1, count2 = false;
    String setPlayerPositionD = "d";
    String setPlayerPositionE = "e";
    String setPlayerPositionF = "f";
    String setPlayerPositionZERO = "0";
    MapLoad mapLoad;

    public void initializeMap(){
        mapLoad = new MapLoad(this, playerField);
    }




    /**
     * Returns the player field as a String array
     * @return
     */
    public String[] getPlayerField() {
        // playerField[23] = setPlayerPositionTWO;
        return playerField;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_map);
        initializeImageViews();
        initializePlayerField();
        initializeMap();

        oldpos = 0;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        //werden für die größer der Map benötigt
        display.getSize(size);
        width = size.x;
        height = size.y;

        initializeShipView();
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
                        int pos = position(x, y);

                        //kleines Schiff
                        if (which_ship == 0) {
                            if (!playerField[pos].equals(setPlayerPositionE) && !playerField[pos].equals(setPlayerPositionF)) {
                                //falls schon mal gesetzt wird die letzte Position gelöscht
                                delete(old_small);
                                //neue Position gesetzt
                                playerField[pos] = setPlayerPositionD;
                                old_small[0] = pos;


                            }
                            count0 = true;

                        }

                        //mittleres Schiff
                        if (which_ship == 1) {
                            if (degree == 0) {
                                //check_position schaut dass das schiff nicht außerhalb der playerField oder vom rechten ende der playerField auf die linke seite gesetzt wird
                                if (check_position(pos, which_ship, degree)) {
                                    delete(old_middle);
                                    // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                                    playerField[pos - 1] = setPlayerPositionE;
                                    old_middle[0] = pos - 1;
                                    playerField[pos] = setPlayerPositionE;
                                    old_middle[1] = pos;
                                }
                            } else if (degree == 1) {
                                if (check_position(pos, which_ship, degree)) {
                                    delete(old_middle);
                                    playerField[pos - 8] = setPlayerPositionE;
                                    old_middle[0] = pos - 8;
                                    playerField[pos] = setPlayerPositionE;
                                    old_middle[1] = pos;
                                }

                            }
                            count1 = true;

                        }
                        //großes Schiff
                        if (which_ship == 2) {
                            if (degree == 0) {
                                if (check_position(pos, which_ship, degree) ) {
                                    delete(old_big);
                                    // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                                    playerField[pos - 1] = setPlayerPositionF;
                                    old_big[0] = pos - 1;
                                    playerField[pos] = setPlayerPositionF;
                                    old_big[1] = pos;
                                    playerField[pos + 1] = setPlayerPositionF;
                                    old_big[2] = pos + 1;
                                }
                            } else if (degree == 1) {
                                if (check_position(pos, which_ship, degree)) {
                                    delete(old_big);
                                    playerField[pos - 8] = setPlayerPositionF;
                                    old_big[0] = pos - 8;
                                    playerField[pos] = setPlayerPositionF;
                                    old_big[1] = pos;
                                    playerField[pos + 8] = setPlayerPositionF;
                                    old_big[2] = pos + 8;
                                }


                            }
                            count2 = true;

                        }

                        draw(playerField);

                        return (true);

                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
                        ship1.setVisibility(View.VISIBLE);
                        ship2.setVisibility(View.VISIBLE);
                        ship3.setVisibility(View.VISIBLE);

                        if (count0 && count1 && count2) {
                            play.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_other));
                        }
                        return (true);

                    }
                    default:
                        break;
                }
                return true;
            }

        });
        //ShadowBuilder erzeugt eine Animation, wenn man das schiff los lässt
        ship1.setOnTouchListener(new View.OnTouchListener()

        {

            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship1);
                v.startDrag(data, shadow, null, 0);
                //small ship
                which_ship = 0;
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
                which_ship = 1;
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
                which_ship = 2;
                ship3.setVisibility(View.INVISIBLE);
                return false;
            }
        });


    }

    //schaut das das Schiff nicht über die Map hinaus gesetzt wird
    public boolean check_position(int pos, int size, int deg) {
        ArrayList<Integer> failures_right = new ArrayList<Integer>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
        ArrayList<Integer> failures_left = new ArrayList<Integer>(Arrays.asList(8, 16, 24, 32, 40, 48, 56));

        String[] length = new String[size];
        //linkes und rechtes Ende der Map
        if (degree == 180 || degree == 0) {
            if (size == 1) {
                if (failures_right.contains(pos - 1) || failures_left.contains(pos) || pos < 1 || pos > 62) {
                    return false;
                }
            } else if (size == 2) {
                if (failures_right.contains(pos - 1) || failures_right.contains(pos) || failures_left.contains(pos) || pos < 1 || pos > 62) {
                    return false;
                }
            }
        }
        //Oben und Unten
        if (degree == 90 || degree == 270) {
            if (size == 1) {
                if (pos < 8 || pos > 63) {
                    return false;
                }
            } else if (size == 2) {
                if (pos < 8 || pos > 55) {
                    return false;
                }
            }

        }

        if(which_ship==2) {
            if(deg==0){
                if (playerField[pos - 1].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD) || playerField[pos + 1].equals(setPlayerPositionD)
                        || playerField[pos - 1].equals(setPlayerPositionE) || playerField[pos].equals(setPlayerPositionE) || playerField[pos + 1].equals(setPlayerPositionE)) {
                    return false;
                }
            } else {
                if (playerField[pos - 8].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD) || playerField[pos + 8].equals(setPlayerPositionD)
                        || playerField[pos - 8].equals(setPlayerPositionE) || playerField[pos].equals(setPlayerPositionE) || playerField[pos + 8].equals(setPlayerPositionE)) {
                    return false;
                }
            }
        } else if(which_ship==1) {
            if(deg==0){
                if (playerField[pos - 1].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD)
                        || playerField[pos - 1].equals(setPlayerPositionF) || playerField[pos].equals(setPlayerPositionF)) {
                    return false;
                }
            }else {
                if (playerField[pos - 8].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD)
                        || playerField[pos - 8].equals(setPlayerPositionF) || playerField[pos].equals(setPlayerPositionF)) {
                    return false;
                }
            }
        }
        return true;
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
     * Initializes the player field in the onCreate() method
     * player field starts as an "empty" array, containing only zeroes.
     */
    private void initializePlayerField() {
        playerField = new String[64];
        for (int i = 0; i < 64; i++) {
            playerField[i] = setPlayerPositionZERO;
        }
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
                if (count0 && count1 && count2) {
                    Intent intent = new Intent();
                    intent.setClass(Map.this, Spielfeld.class);
                    intent.putExtra("oldmap", playerField);
                    startActivity(intent);
                }
            }
        });

        gridView.setAdapter(new MapLoad(this, playerField));

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
        int pos = zehner + einer;
        return pos;
    }

    /**
     * Receives an integer array containing the positions, which will be deleted
     * in the player field
     * @param data
     */
    public void delete(int data[]) {
        if (data != null) {
            for (int x : data) {
                playerField[x] = setPlayerPositionZERO;
            }
        }
        draw(playerField);

    }

    public void draw(String[] array) {
        gridView.setAdapter(mapLoad);
    }

}
