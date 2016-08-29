package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button twoButton,exitButton,oneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer m = MediaPlayer.create(MainActivity.this,R.raw.backm);
        m.start();
        m.setVolume(0.5f,0.5f);


        //MediaPlayer m = MediaPlayer.create(MainActivity.this,R.raw.track);
        //m.start();

        twoButton = (Button) findViewById(R.id.two_id);
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,TwoPlayer.class);
                m.pause();
                startActivity(i);
            }
        });

        exitButton = (Button) findViewById(R.id.exit_id);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        oneButton = (Button) findViewById(R.id.single_id);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Game1.class);
                m.pause();
                startActivity(i);
            }
        });

        Button r = (Button) findViewById(R.id.rules_id);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Rules.class);
                startActivity(i);
            }
        });

    }
}
