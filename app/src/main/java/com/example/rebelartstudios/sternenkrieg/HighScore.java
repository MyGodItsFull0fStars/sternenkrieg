package com.example.rebelartstudios.sternenkrieg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

public class HighScore extends AppCompatActivity {

    ListView list_highScore;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> list;
    int counter;
    SharedPreferences settings;
    SharedPreferences namesettings;
    boolean read = true;
    boolean write = true;
    boolean onlyhighscore = true; //für Hauptmenü benötigt
    Button delete_highscore;
    String highscore;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        list_highScore = (ListView) findViewById(R.id.list_highScore);
        delete_highscore = (Button) findViewById(R.id.btn_delete_highscore);

        settings = getApplicationContext().getSharedPreferences("highscoredata", 0);
        namesettings = getApplicationContext().getSharedPreferences("name", 0);
        list = new ArrayList<>();
        String username = namesettings.getString("username", "Unbekannt");
        int levelscore = namesettings.getInt("levelscore",0);
        listAdapter = new ArrayAdapter<String>(this, R.layout.high_score_row);

        intent = getIntent();
        int points = intent.getIntExtra("highScore", 0);
        String highscore = username + " " + points;

        //Calculating level


        //Gespeicherte Werte werden eingelesen
        if (read) {
            list = read_highscore(highscore);
            listAdapter.addAll(list);
            int score = levelscore+points;
            calc(score);
        }

        //Die letzt Highscore wird dauerhaft gespeichert
        if (write) {
            save_highscore(highscore);
        }

        //Highscore wird erzeugt
        list_highScore.setAdapter(listAdapter);

        //Bei Button klick wird alles von "highscoredata" gelöscht, Username bleibt aber
        delete_highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove();
            }
        });

    }

    private void calc(int score) {
        SharedPreferences.Editor editor = namesettings.edit();
        int level=1;
        while(true){
            int levelgrenze=(1000+400*(level-1))*level;
            if(score>=levelgrenze){
                level++;
            }else{
                int differenz= levelgrenze-(1000+400*(level-2))*(level-1);
                int scoreoverlvl = levelgrenze-score;
                double prozent2 = 100-((double)scoreoverlvl/(double)differenz)*100;
                int prozent= (int) prozent2;
                editor.putInt("level",level);
                editor.putInt("prozent",prozent);
                editor.putInt("levelscore", score);
                editor.apply();
                Toast toast = Toast.makeText(getApplicationContext(),"Levelgrenze: "+differenz+" scorelcl: "+scoreoverlvl+" %: "+prozent
                        +" double: "+ prozent2, Toast.LENGTH_LONG);
                toast.show();
                break;
            }

        }


    }

    public ArrayList<String> read_highscore(String highscore) {
        counter = settings.getInt("counter", 0);
        //SharedPreference kann leider nur primitive Datentypen und String speichern
        for (int i = 0; i < counter; i++) {
            list.add(settings.getString("highscore" + i + "", "Unbekannt"));
        }
        onlyhighscore = intent.getBooleanExtra("onlyhighscore", true);

        //Wenn man über das Hauptmenü zur Highscore kommt
        if (onlyhighscore) {
            list.add(highscore);
        }else{
            write=false;
        }
        Collections.sort(list, Collections.<String>reverseOrder());
        read = false;
        return list;

    }

    public void save_highscore(String highscore) {
        SharedPreferences.Editor editor = settings.edit();
        counter = settings.getInt("counter", 0);

        editor.putString("highscore" + counter + "", highscore);
        counter++;

        editor.putInt("counter", counter);
        editor.commit();
        write = false;

    }

    public void remove() {
        this.getSharedPreferences("highscoredata", 0).edit().clear().apply();
        listAdapter.clear();
        list.clear();

    }
}
