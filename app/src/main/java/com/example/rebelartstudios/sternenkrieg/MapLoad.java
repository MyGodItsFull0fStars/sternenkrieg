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

import com.example.rebelartstudios.sternenkrieg.gamelogic.FieldValues;

public class MapLoad extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;
    private ImageView imageView;
    private View gridView;
    private FieldValues fieldValues = new FieldValues();

    /**
     * Constructor for the MapLoad Class
     *
     * @param context
     * @param mobileValues
     */
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


        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.square, null);
            imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            imageView.getLayoutParams().height = (height - 350) / 8;
            imageView.getLayoutParams().width = (height - 350) / 8;
            // TODO change height to width?

            setFieldColor(mobileValues[position]);


        } else {
            gridView = convertView;
        }

        return gridView;
    }

    /**
     * Sets the color of field
     *
     * @param mobile string for deciding which color will be used
     */
    private void setFieldColor(String mobile) {

        // TODO: 20/06/2017 try to make this more readable
        if (
                mobile.equals(fieldValues.SET_FIELD_POSITION_EMPTY) ||
                        mobile.equals(fieldValues.SET_FIELD_POSITION_A) ||
                        mobile.equals(fieldValues.SET_FIELD_POSITION_B) ||
                        mobile.equals(fieldValues.SET_FIELD_POSITION_C)) {
            imageView.setBackgroundColor(Color.WHITE); //no action
            imageView.getBackground().setAlpha(70);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_MISS)) {
            imageView.setBackgroundColor(Color.MAGENTA); //miss for player
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_TWO)) {
            imageView.setBackgroundColor(Color.RED); //your own ships

        } else if (mobile.equals(fieldValues.SET_PLAYER_POSITION_SMALL)) {
            imageView.setBackgroundResource(R.drawable.ship1_small);

        } else if (mobile.equals(fieldValues.SET_PLAYER_POSITION_MIDDLE1)) {
            imageView.setBackgroundResource(R.drawable.ship2_small1);
        } else if (mobile.equals(fieldValues.SET_PLAYER_POSITION_MIDDLE2)) {
            imageView.setBackgroundResource(R.drawable.ship2_small2);
        } else if (mobile.equals(fieldValues.SET_PLAYER_POSITION_MIDDLE1R)) {
            imageView.setBackgroundResource(R.drawable.ship2_small1r);
        } else if (mobile.equals(fieldValues.SET_PLAYER_POSITION_MIDDLE2R)) {
            imageView.setBackgroundResource(R.drawable.ship2_small2r);

        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG1)) {
            imageView.setBackgroundResource(R.drawable.ship3_small1);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG2)) {
            imageView.setBackgroundResource(R.drawable.ship3_small2);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG3)) {
            imageView.setBackgroundResource(R.drawable.ship3_small3);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG1R)) {
            imageView.setBackgroundResource(R.drawable.ship3_small1r);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG2R)) {
            imageView.setBackgroundResource(R.drawable.ship3_small2r);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_BIG3R)) {
            imageView.setBackgroundResource(R.drawable.ship3_small3r);

            /* ships with armour*/
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_J)) {
            imageView.setBackgroundResource(R.drawable.ship1_armour_small);

        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_K1)) {
            imageView.setBackgroundResource(R.drawable.ship2_small1_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_K2)) {
            imageView.setBackgroundResource(R.drawable.ship2_small2_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_K3)) {
            imageView.setBackgroundResource(R.drawable.ship2_small1r_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_K4)) {
            imageView.setBackgroundResource(R.drawable.ship2_small2r_armour);

        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L1)) {
            imageView.setBackgroundResource(R.drawable.ship3_small1_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L2)) {
            imageView.setBackgroundResource(R.drawable.ship3_small2_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L3)) {
            imageView.setBackgroundResource(R.drawable.ship3_small3_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L4)) {
            imageView.setBackgroundResource(R.drawable.ship3_small1r_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L5)) {
            imageView.setBackgroundResource(R.drawable.ship3_small2r_armour);
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_L6)) {
            imageView.setBackgroundResource(R.drawable.ship3_small3r_armour);

            /* --- */

        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_ENEMY_HIT)) {
            imageView.setBackgroundColor(Color.YELLOW); //hit for enemy
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_PLAYER_HIT)) {
            imageView.setBackgroundColor(Color.GREEN); //hit for player
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_ENEMY_MISS)) {
            imageView.setBackgroundColor(Color.BLUE); //miss for enemy
        } else if (mobile.equals(fieldValues.SET_FIELD_POSITION_G) ||
                mobile.equals(fieldValues.SET_FIELD_POSITION_H1) || mobile.equals(fieldValues.SET_FIELD_POSITION_H2) ||
                mobile.equals(fieldValues.SET_FIELD_POSITION_H3) || mobile.equals(fieldValues.SET_FIELD_POSITION_H4) ||
                mobile.equals(fieldValues.SET_FIELD_POSITION_I1) || mobile.equals(fieldValues.SET_FIELD_POSITION_I2) || mobile.equals(fieldValues.SET_FIELD_POSITION_I3) ||
                mobile.equals(fieldValues.SET_FIELD_POSITION_I4) || mobile.equals(fieldValues.SET_FIELD_POSITION_I5) || mobile.equals(fieldValues.SET_FIELD_POSITION_I6))
            imageView.setBackgroundColor(Color.CYAN); //hit for player
    }
}
