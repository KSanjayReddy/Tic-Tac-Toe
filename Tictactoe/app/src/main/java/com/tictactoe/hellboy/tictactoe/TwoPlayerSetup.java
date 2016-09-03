package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TwoPlayerSetup extends Activity {

    Button b;
    String n1, n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_setup);

        b = (Button) findViewById(R.id.gotogame) ;
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText e1, e2;
                e1 = (EditText) findViewById(R.id.player1);
                e2 = (EditText) findViewById(R.id.player2);


                MediaPlayer m = MediaPlayer.create(TwoPlayerSetup.this, R.raw.click);
                m.start();
                n1 = e1.getText().toString();
                n2 = e2.getText().toString();
                Intent i = new Intent(TwoPlayerSetup.this, TwoPlayerActivity.class);

                if(n1.length()==0){
                    n1= "Player 1";
                }
                if(n2.length()==0){
                    n2= "Player 2";
                }
                i.putExtra("Name1", n1);
                i.putExtra("Name2", n2);

                startActivity(i);


            }
        });
    }
}



