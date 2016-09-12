package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button twoButton,exitButton,oneButton;
    MediaPlayer m = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m = MediaPlayer.create(MainActivity.this, R.raw.backm);
        m.start();
        m.setVolume(0.5f, 0.5f);

        //MediaPlayer m = MediaPlayer.create(MainActivity.this,R.raw.track);
        //m.start();

        twoButton = (Button) findViewById(R.id.two_id);
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TwoPlayerSetup.class);
                m.pause();
                startActivity(i);
            }
        });

        exitButton = (Button) findViewById(R.id.exit_id);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.pause();
                //finishAffinity();

                new AlertDialog.Builder(MainActivity.this)
                .setTitle("Tic Tac Toe")
                .setMessage("Are you sure you want to quit the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("No", null)
                .show();

            }
        });

        oneButton = (Button) findViewById(R.id.single_id);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SinglePlayerActivity.class);
                i.putExtra("player1_turn", true);
                m.pause();
                startActivity(i);
            }
        });

        Button r = (Button) findViewById(R.id.rules_id);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Rules.class);
                m.pause();
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        m.pause();

        new AlertDialog.Builder(this)
        .setIcon(R.drawable.wall)
        .setTitle("Tic Tac Toe")
        .setMessage("Are you sure you want to quit the game?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }

        })
        .setNegativeButton("No", null)
        .show();
    }

}
