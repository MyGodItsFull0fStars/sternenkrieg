package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PowerUp extends AppCompatActivity {

    final static int MAX_POINTS = 1000; // maximum PowerUp points
    static int currentPoints = 100; // 100 for testing purpose

    Button pu1Btn;
    Button pu2Btn;
    Button pu3Btn;
    Button pu4Btn;
    Button cheatBtn;

    static TextView textViewPoints;

    String tag = "PowerUps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerups);

        initialize();

        updateCurrentPoints();

        pu1Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "PU1 chosen, Current Points: "+getCurrentPoints());

                if(remPoints(10)) {
                    Log.d(tag, "PU1: -10 points, Current points: "+getCurrentPoints());
                } else {
                    dialog();
                    Log.d(tag, "PU1: called dialog");
                }
            }
        });

        pu2Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "PU2 chosen, Current Points: "+getCurrentPoints());

                if(remPoints(50)) {
                    Log.d(tag, "PU2: -20 points, Current points: "+getCurrentPoints());
                } else {
                    dialog();
                    Log.d(tag, "PU2: called dialog");
                }
            }
        });

        pu3Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        pu4Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        cheatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addPoints(500);
            }
        });
    }

    private void initialize() {
        pu1Btn = (Button) findViewById(R.id.buttonPU1);
        pu2Btn = (Button) findViewById(R.id.buttonPU2);
        pu3Btn = (Button) findViewById(R.id.buttonPU3);
        pu4Btn = (Button) findViewById(R.id.buttonPU4);
        cheatBtn = (Button) findViewById(R.id.buttonPUcheat);

        textViewPoints = (TextView) findViewById(R.id.textViewPoints);
    }

    public static boolean addPoints(int points) {
        if(currentPoints + points <= MAX_POINTS) {
            currentPoints += points;
            updateCurrentPoints();
            return true;
        }
        return false;
    }

    public static boolean remPoints(int points) {
        if(currentPoints - points >= 0) {
            currentPoints -= points;
            updateCurrentPoints();
            return true;
        }
        return false;
    }

    public static int getCurrentPoints() {
        return currentPoints;
    }

    public static String getCurrentPointsAsString() {
        return Integer.toString(currentPoints);
    }

    public static void updateCurrentPoints() {
        textViewPoints.setText(getCurrentPointsAsString());
    }

    public static int getMaxPoints() {
        return MAX_POINTS;
    }

    public AlertDialog dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("Zu wenig Punkte");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
        AlertDialog dialog = builder.create();

        return dialog;
    }

}
