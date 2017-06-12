package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MapLoad extends BaseAdapter {
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
            imageView.getLayoutParams().height = (height - 350) / 8;
            imageView.getLayoutParams().width = (height - 350) / 8;


            String mobile = mobileValues[position];

            if (mobile.equals("0") || mobile.equals("a") || mobile.equals("b") || mobile.equals("c")) {
                imageView.setBackgroundColor(Color.WHITE); //no action
            } else if (mobile.equals("1")) {
                imageView.setBackgroundColor(Color.MAGENTA); //miss for player
            } else if (mobile.equals("2") || mobile.equals("d") || mobile.equals("e") || mobile.equals("f")) {
                imageView.setBackgroundColor(Color.RED); //your own ships
            } else if (mobile.equals("3")) {
                imageView.setBackgroundColor(Color.YELLOW); //hit for enemy
            } else if (mobile.equals("4")) {
                imageView.setBackgroundColor(Color.GREEN); //hit for player
            } else if (mobile.equals("5")) {
                imageView.setBackgroundColor(Color.BLUE); //miss for enemy
            } else if (mobile.equals("g") || mobile.equals("h") || mobile.equals("i"))
                imageView.setBackgroundColor(Color.CYAN); //hit for player


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
