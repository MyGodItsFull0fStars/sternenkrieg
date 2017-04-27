package com.example.wenboda.sk_ui3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapLoad extends BaseAdapter{
    private Context context;
    private final String[] mobileValues;


    public MapLoad(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }


    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.square, null);

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            imageView.getLayoutParams().height=height/8 ;
            imageView.getLayoutParams().width=height/8;


            String mobile = mobileValues[position];

            if (mobile.equals("0")) {
                imageView.setBackgroundColor(Color.WHITE);
            } else if (mobile.equals("1")) {
                imageView.setBackgroundColor(Color.MAGENTA);
            } else if (mobile.equals("2")) {
                imageView.setBackgroundColor(Color.RED);
            }else if(mobile.equals("3")) {
                imageView.setBackgroundColor(Color.YELLOW);
            }else if(mobile.equals("4"))
                imageView.setBackgroundColor(Color.GREEN);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
