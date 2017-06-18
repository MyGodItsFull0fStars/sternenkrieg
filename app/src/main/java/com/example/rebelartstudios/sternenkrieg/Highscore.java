package com.example.rebelartstudios.sternenkrieg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;

import java.util.ArrayList;

public class Highscore extends AppCompatActivity {

    ListView listHighScore;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> list;
    Button deleteHighscore;
    Intent intent;
    GameUtilities game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listHighScore = (ListView) findViewById(R.id.list_highScore);
        deleteHighscore = (Button) findViewById(R.id.btn_delete_highscore);

        list = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, R.layout.high_score_row);

        listHighScore.setBackgroundColor(Color.WHITE);
        game = new GameUtilities(getApplicationContext());
        if (game.isHighscoreMain()) {
            game.level();
            game.setHighscore();
        }
        list = game.getHighscore();
        listAdapter.addAll(list);

        //Highscore wird erzeugt
        listHighScore.setAdapter(listAdapter);
        //Bei Button klick wird alles von "highscoredata" gelöscht, Username bleibt aber
        deleteHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.deleteHighscore();
                listAdapter.clear();
                list.clear();
            }
        });

    }

}
