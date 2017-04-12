package com.example.wenboda.sk_ui3;

import android.content.ClipData;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.DragEvent;

import java.util.Random;

public class Map extends AppCompatActivity {
    GridView gridView;
    ImageView imageView;
    ImageView ship1;
    String map[];
    int width;
    int height;
    int oldpos;
    int amountShips = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_map);

        imageView = (ImageView) findViewById(R.id.grid_item_image);
        ship1 = (ImageView) findViewById(R.id.image_ship1);
        oldpos=0;

        map = new String[64];
        for (int i = 0; i < 64; i++) {
            map[i] = 0+"";
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.getLayoutParams().height=height;
        gridView.getLayoutParams().width=height;
        ship1.getLayoutParams().height=height/8;
        ship1.getLayoutParams().width=height/8;

        gridView.setAdapter(new MapLoad(this, map));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "Pos: "+position+" Id: "+id,
                        Toast.LENGTH_SHORT).show();
                map[position]=1+"";
                draw(map);



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
                        int x= (int)event.getX();
                        int y = (int)event.getY();
                        int pos= position(x,y);
                        if(amountShips<17 && !(map[pos].equals("2"))&& !(map[pos+1].equals("2"))&&!(map[pos+2].equals("2"))){
                        map[pos]=2+"";
                            map[pos+1]=2+"";
                            map[pos+2]=2+"";
                        draw(map);
                            amountShips++;
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

        ship1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship1);
                v.startDrag(data, shadow, null, 0);
                return false;
            }
        });


    }
    public int position(int x, int y){
        int zehner = (int)(y*8)/height;
        zehner = zehner*8;
        int einer = (int) (x*8)/height;
        int pos = zehner+einer;
        return pos;
    }

    public void draw(String[] array){
        gridView.setAdapter(new MapLoad(this, array));
    }
}
