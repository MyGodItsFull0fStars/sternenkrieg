package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class HighScore extends AppCompatActivity {

    ListView list_highScore;
    private ArrayAdapter<String> listAdapter;
    int counter;
    SharedPreferences settings;
    boolean read = true;
    boolean write = true;
    String score[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        list_highScore = (ListView) findViewById(R.id.list_highScore);
        settings = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = settings.getString("username", "Unbekannt");
        listAdapter = new ArrayAdapter<String>(this, R.layout.high_score_row);

        if (read) {
           listAdapter= read_highscore();
        }
        Intent intent = getIntent();
        int points = intent.getIntExtra("highScore", 0);
        String highscore = username + " " + points;
        listAdapter.add(highscore);
        if (write) {
            save_highscore(highscore);
        }

        list_highScore.setAdapter(listAdapter);

    }

    public ArrayAdapter read_highscore() {
        counter = settings.getInt("counter", 0);
        for (int i = 0; i < counter; i++) {
            listAdapter.add(settings.getString("highscore" + i + "", "Unbekannt"));
            Toast toast = Toast.makeText(getApplicationContext(),settings.getString("Read:  "+"highscore" + i + "", null), Toast.LENGTH_SHORT);
            toast.show();
        }
        read=false;
        return listAdapter;

    }

    public void save_highscore(String highscore) {
        SharedPreferences.Editor editor = settings.edit();
        counter = settings.getInt("counter", 0);

        editor.putString("highscore"+ counter+"", highscore);
        Toast toast = Toast.makeText(getApplicationContext(),"Write:  "+highscore+counter+"", Toast.LENGTH_SHORT);
        toast.show();
        counter++;

        editor.putInt("counter",counter);
        editor.commit();
        write=false;

    }
}
