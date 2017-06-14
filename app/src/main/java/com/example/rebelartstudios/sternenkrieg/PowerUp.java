package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PowerUp extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    final int MAX_POINTS = 10000; // maximum PowerUp points
    int currentPoints = 100; // 100 for testing purpose

    int dicePoints;

    int pu1max = 3; // PowerUp 1 may be used up to 3 times
    int pu2max = 2;
    int pu3max = 10;
    int pu4max = 1;

    int pu1cur = 0;
    int pu2cur = 0;
    int pu3cur = 0;
    int pu4cur = 0;

    Button pu1Btn;
    Button pu2Btn;
    Button pu3Btn;
    Button pu4Btn;
    Button cheatBtn;

    TextView textViewPoints;

    String tag = "PowerUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerups);

        initialize();
        pu1Btn.setText(pu1max-pu1cur + "x PU1");
        pu2Btn.setText(pu2max-pu2cur + "x PU2");
        pu3Btn.setText(pu3max-pu3cur + "x PU3");
        pu4Btn.setText(pu4max-pu4cur + "x PU4");

        updateCurrentPoints();

        sharedPreferences = getSharedPreferences("powerup", Context.MODE_PRIVATE);
        // sound is disabled by default
        currentPoints = sharedPreferences.getInt("currentPoints", 100);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            dicePoints = b.getInt("points");
            if(addPoints(dicePoints)) {
                Log.d(tag, dicePoints + " Points added!");
            } else {
                Log.d(tag, "No points added!");
            }
        }

        pu1Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "PU1 chosen, Current Points: "+getCurrentPoints());

                if(pu1cur < pu1max) {
                    if(remPoints(10)) {
                        Log.d(tag, "PU1: -10 points, Current points: "+getCurrentPoints());
                        pu1cur++;
                        pu1Btn.setText(pu1max-pu1cur + "x PU1");
                    } else {
                        dialog("Zu wenig Punkte");
                        Log.d(tag, "PU1: called dialog");
                    }
                } else {
                    dialog("Maximum erreicht");
                }

            }
        });

        pu2Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "PU2 chosen, Current Points: "+getCurrentPoints());

                if(remPoints(50)) {
                    Log.d(tag, "PU2: -20 points, Current points: "+getCurrentPoints());
                } else {
                    dialog("Zu wenig Punkte");
                    Log.d(tag, "PU2: called dialog");
                }
            }
        });

        pu3Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num = (int) (Math.random()*500+300);
                Log.d(tag, "PU3 Number: "+num);
                pu3cur++;
                if(remPoints(num)) {
                    dialog("Yay");
                } else {
                    dialog("Nay");
                }

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

    public boolean addPoints(int points) {
        if(currentPoints + points <= MAX_POINTS) {
            currentPoints += points;
            updateCurrentPoints();
            savePoints();
            return true;
        }
        return false;
    }

    public boolean remPoints(int points) {
        if(currentPoints - points >= 0) {
            currentPoints -= points;
            updateCurrentPoints();
            savePoints();
            return true;
        }
        return false;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public String getCurrentPointsAsString() {
        return Integer.toString(currentPoints);
    }

    public void updateCurrentPoints() {
        textViewPoints.setText(getCurrentPointsAsString());
    }

    public void savePoints() {
        SharedPreferences sharedPreferences = getSharedPreferences("powerup", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Log.w(tag, sharedPreferences.getAll().toString());

        editor.putInt("currentPoints", getCurrentPoints());
        editor.apply();

        Log.w(tag, sharedPreferences.getAll().toString());
    }

    public int getMaxPoints() {
        return MAX_POINTS;
    }

    public int getPu1max() {
        return pu1max;
    }

    public int getPu2max() {
        return pu2max;
    }

    public int getPu3max() {
        return pu3max;
    }

    public int getPu4max() {
        return pu4max;
    }

    public int getPu1cur() {
        return pu1cur;
    }

    public int getPu2cur() {
        return pu2cur;
    }

    public int getPu3cur() {
        return pu3cur;
    }

    public int getPu4cur() {
        return pu4cur;
    }

    public AlertDialog dialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage(text);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        return builder.create();
    }

}
