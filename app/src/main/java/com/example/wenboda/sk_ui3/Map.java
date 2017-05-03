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

import java.util.ArrayList;
import java.util.Arrays;

public class Map extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ImageView ship1, ship2, ship3, turn, play;
    String map[] = new String[64];
    int width;
    int height;
    int oldpos;
    int degree = 0;
    int which_ship;
    int[] old_small = new int[1];
    int[] old_middle = new int[2];
    int[] old_big = new int[3];
    boolean count0, count1, count2 = false;


    public String[] getMap() {
        map[23] = 2+"";
        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_map);

        imageView = (ImageView) findViewById(R.id.grid_item_image);
        ship1 = (ImageView) findViewById(R.id.image_ship1);
        ship2 = (ImageView) findViewById(R.id.image_ship2);
        ship3 = (ImageView) findViewById(R.id.image_ship3);
        turn = (ImageView) findViewById(R.id.image_turn);
        play = (ImageView) findViewById(R.id.play);



        oldpos = 0;

        map = new String[64];
        for (int i = 0; i < 64; i++) {
            map[i] = 0 + "";
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.getLayoutParams().height = height-350;
        gridView.getLayoutParams().width = height-350;
        ship1.getLayoutParams().height = (height-350) / 8;
        ship1.getLayoutParams().width = (height-350)/ 8;
        ship2.getLayoutParams().height = (height-350)/ 8;
        ship2.getLayoutParams().width = (height-350)/ 4;
        ship3.getLayoutParams().height = (height-350)/ 8;
        ship3.getLayoutParams().width = (height-350) / 3;


            play.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                if (count0 && count1 && count2) {
                    Intent intent = new Intent();
                    intent.setClass(Map.this, Spielfeld.class);
                    intent.putExtra("oldmap", map);
                    startActivity(intent);
                }
            }
            });


        gridView.setAdapter(new MapLoad(this, map));

        turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(degree==0) {
                    degree=1;
                    ship1.animate().rotationBy(90).start();
                    ship2.animate().rotationBy(90).start();
                    ship3.animate().rotationBy(90).start();
                } else {
                    degree=0;
                    ship1.animate().rotationBy(270).start();
                    ship2.animate().rotationBy(270).start();
                    ship3.animate().rotationBy(270).start();
                }




            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: " ,
                        Toast.LENGTH_SHORT).show();
               /* map[position] = 1 + "";
                draw(map);*/


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
                            if (!map[pos].equals(2 + "")) {
                                //falls schon mal gesetzt wird die letzte Position gelöscht
                                delete(old_small);
                                //neue Position gesetzt
                                map[pos] = 2 + "";
                                old_small[0] = pos;


                            }
                            count0=true;

                        }
                        //mittleres Schiff
                        if (which_ship == 1) {
                            if (degree == 0) {
                                //check_position schaut dass das schiff nicht auserhalb der map oder vom rechten ende der map auf die linke seite gesetzt wird
                                if (chek_position(pos, which_ship)&&!map[pos].equals(2 + "") && !map[pos + 1].equals(2 + "")) {
                                    delete(old_middle);
                                    // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                                    map[pos - 1] = 2 + "";
                                    old_middle[0] = pos - 1;
                                    map[pos] = 2 + "";
                                    old_middle[1] = pos;
                                }
                            } else if (degree == 1) {
                                if (chek_position(pos, which_ship)&&!map[pos].equals(2 + "") && !map[pos - 8].equals(2 + "")) {
                                    delete(old_middle);
                                    map[pos - 8] = 2 + "";
                                    old_middle[0] = pos - 8;
                                    map[pos] = 2 + "";
                                    old_middle[1] = pos;
                                }

                            }
                            count1=true;

                        }
                        //großes Schiff
                        if (which_ship == 2) {
                            if (degree == 0) {
                                if (chek_position(pos, which_ship)&&!map[pos].equals(2 + "") && !map[pos + 1].equals(2 + "") && !map[pos + 2].equals(2 + "")) {
                                    delete(old_big);
                                    // pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
                                    map[pos - 1] = 2 + "";
                                    old_big[0] = pos - 1;
                                    map[pos] = 2 + "";
                                    old_big[1] = pos;
                                    map[pos + 1] = 2 + "";
                                    old_big[2] = pos + 1;
                                }
                            } else if (degree == 1) {
                                if (chek_position(pos, which_ship)&& !map[pos - 8].equals(2 + "") && !map[pos].equals(2 + "") && !map[pos + 8].equals(2 + "")) {
                                    delete(old_big);
                                    map[pos - 8] = 2 + "";
                                    old_big[0] = pos - 8;
                                    map[pos] = 2 + "";
                                    old_big[1] = pos;
                                    map[pos + 8] = 2 + "";
                                    old_big[2] = pos + 8;
                                }


                            }
                            count2=true;

                        }

                        draw(map);

                        return (true);

                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
                        ship1.setVisibility(View.VISIBLE);
                        ship2.setVisibility(View.VISIBLE);
                        ship3.setVisibility(View.VISIBLE);

                        if(count0&&count1&&count2){
                            play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                        }
                        return (true);

                    }
                    default:
                        break;
                }
                return true;
            }

        });

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
    public boolean chek_position(int pos,int size){
        ArrayList<Integer> failures_right=new ArrayList<Integer>(Arrays.asList(7,15,23,31,39,47,55,63));
        ArrayList<Integer> failures_leftt=new ArrayList<Integer>(Arrays.asList(8,16,24,32,40,48,56));

        String[] length = new String[size];
        if (degree == 180 || degree == 0) {
            if (size == 1) {
                if (failures_right.contains(pos-1)||failures_leftt.contains(pos) || pos < 1 || pos > 62) {
                    return false;
                }
            } else if (size == 2) {
                if (failures_right.contains(pos-1) ||failures_right.contains(pos)|| failures_leftt.contains(pos) || pos < 1 || pos > 62) {
                    return false;
                }
            }
        }
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
        return true;
    }

    public int position(int x, int y) {
        int zehner = y * 8 / (height-350);
        zehner = zehner * 8;
        int einer = x * 8 / (height-350);
        int pos = zehner + einer;
        return pos;
    }

    public void delete(int data[]) {
        if (data.equals(null)) {

        } else {
            for (int x : data) {
                map[x] = 0 + "";
            }

        }
        draw(map);

    }

    public void draw(String[] array) {
        gridView.setAdapter(new MapLoad(this, array));
    }

}
