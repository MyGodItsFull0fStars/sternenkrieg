package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HighScore extends AppCompatActivity {

    ListView list_highScore;
    private String[] score_levels = new String[] { "Challenger", "Master", "Platin", "Diamond",
            "Gold", "Silver", "Bronze", "Pls delete game"};
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        list_highScore = (ListView) findViewById(R.id.list_highScore);

        Intent intent = getIntent();
        int points = intent.getIntExtra("highScore",0);

        ArrayList<String> elo = new ArrayList<String>();
        elo.addAll( Arrays.asList(score_levels) );

        listAdapter = new ArrayAdapter<String>(this, R.layout.high_score_row, elo);

        listAdapter.add("points");

        list_highScore.setAdapter(listAdapter);

    }
}
