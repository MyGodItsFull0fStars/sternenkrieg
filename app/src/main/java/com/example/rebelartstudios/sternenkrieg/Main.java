package com.example.rebelartstudios.sternenkrieg;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rebelartstudios.sternenkrieg.gamelogic.GameUtilities;

public class Main extends AppCompatActivity {

    Button socketBtn;
    Button highscoreBtn;
    Button optionsBtn;
    Button aboutBtn;

    ImageView background;
    ImageView logo;
    GameUtilities game;
    TextView txtUsername;
    TextView txtLevel;

    ProgressBar levelProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initializeClasses();
        initializeOnClickListeners();
        initializeBackground();

        game = new GameUtilities(getApplicationContext());
        game.load();

        if (null == game.getUsername())
            generateName();

        txtUsername.setText(game.getUsername());
        txtUsername.setTextColor(Color.WHITE);
        txtLevel.setText("Level:" + game.getLevel());
        txtLevel.setTextColor(Color.WHITE);
        levelProgress.setProgress(0);
        levelProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        levelProgress.setMax(100);
        levelProgress.setProgress(game.getPercent());
        System.out.println("per" + game.getPercent());


        // Background music
        // disabled for now, feel free to enable if you want to check
        //musicStuff();

    }

    /**
     * After pause, when activity is started again, username/background will be initialized again
     */
    @Override
    public void onResume() {
        super.onResume();
        // update username when coming back from Options activity
        txtUsername.setText(game.getUsername());
        initializeBackground();
        levelProgress.setProgress(0);
        levelProgress.setProgress(game.getPercent());
        txtLevel.setText("Level:" + game.getLevel());
        //musicStuff();
    }

    /**
     * If activity is set on pause background will be deleted so memory will be saved.
     */
    @Override
    public void onPause() {
        destroyBackgroundImageView();
        super.onPause();

    }

    /**
     * If activity is destroyed, background will be destroyed.
     */
    @Override
    protected void onDestroy() {
        destroyBackgroundImageView();
        super.onDestroy();
    }

    /**
     * Method to destroy the background image in the memory space
     * Since GIF's are kept in memory for a long time in android, this method will delete the pics in memory
     */
    private void destroyBackgroundImageView() {
        background.destroyDrawingCache();
        background.setImageBitmap(null);
        background.setImageDrawable(null);
        background.setBackgroundResource(0);
        Glide.get(this).clearMemory();
    }


    /**
     * Initialize background and use Android Glide to set background as a GIF
     */
    private void initializeBackground() {
        background = (ImageView) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(0, 0, 0));
        Glide.with(this).load(R.raw.background).asGif().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(background);

    }

    /**
     * Initialization of Buttons, TextViews and ProgressBar for Main
     * Called in the onCreate() method at start of activity
     */
    private void initializeClasses() {
        socketBtn = (Button) findViewById(R.id.socket);
        highscoreBtn = (Button) findViewById(R.id.highscore);
        optionsBtn = (Button) findViewById(R.id.options);
        aboutBtn = (Button) findViewById(R.id.about);

        txtUsername = (TextView) findViewById(R.id.text_username);
        txtLevel = (TextView) findViewById(R.id.txt_level);
        levelProgress = (ProgressBar) findViewById(R.id.progressBar_level);
    }

    /**
     * Sets all OnClickListeners for the buttons in current class using intents
     */
    private void initializeOnClickListeners() {

        // Socket (Game Start)
        socketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, MainSocket.class);
                startActivity(intent);
            }
        });

        // Highscore
        highscoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Highscore.class);
                game.setHighscoreMain(false);
                startActivity(intent);

            }
        });

        // Options
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Options.class);
                startActivity(intent);
            }
        });

        // Options
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, About.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Generates the username which will be shown at the starting screen
     * If no username is already stored, this method will pop up a AlertDialog.Builder to
     * enable the setting of a username
     */
    public void generateName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText one = new EditText(this);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(one);
        builder.setView(lay);
        builder.setMessage("Enter your Name")
                .setTitle("Name")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        game.setUsername(one.getText().toString());
                        txtUsername.setText(game.getUsername());
                        txtUsername.setTextColor(Color.WHITE);
                        // CONFIRM
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                })
                .show();
    }

    private void musicStuff() {
        // TODO not working yet
        ServiceConnection soundConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("SoundConnection", "connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("SoundConnection", "disconnected");
            }
        };

        boolean soundEnabled = game.isSound();
        Intent audioIntent = new Intent(this, PlayAudio.class);
        boolean on = soundEnabled;

        if (soundEnabled) {
            bindService(audioIntent, soundConnection, Context.BIND_AUTO_CREATE);
            startService(audioIntent);
            //on = true;
        } else {
            stopService(audioIntent);
            unbindService(soundConnection);
            //on = false;
        }
    }

}


