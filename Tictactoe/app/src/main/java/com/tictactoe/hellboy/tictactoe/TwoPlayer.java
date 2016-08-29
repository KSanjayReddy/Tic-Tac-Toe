package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TwoPlayer extends Activity {

    Button b;
    String n1, n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        b = (Button) findViewById(R.id.gotog) ;




        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText e1, e2;
                e1 = (EditText) findViewById(R.id.edit1);
                e2 = (EditText) findViewById(R.id.edit2);


                MediaPlayer m = MediaPlayer.create(TwoPlayer.this, R.raw.click);
                m.start();
                n1 = e1.getText().toString();
                n2 = e2.getText().toString();
                Intent i = new Intent(TwoPlayer.this, Game2.class);

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

//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(n1 != null && n1 != ""){
//                    i.putExtra("Name1",n1);
//                }else{
//                    i.putExtra("Name!","Player1");
//                }
//
//                if(n2 != null && n2 != ""){
//                    i.putExtra("Name2",n2);
//                }else {
//                    i.putExtra("Name2","Player2");
//                }
//                startActivity(i);
//            }
//        });


