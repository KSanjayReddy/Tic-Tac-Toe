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

    Button b1,b2,b3;
    String n1,n2;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        t1 = (TextView) findViewById(R.id.name1);
        t2 = (TextView) findViewById(R.id.name2);

        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3  = (Button) findViewById(R.id.gotog) ;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer m = MediaPlayer.create(TwoPlayer.this,R.raw.click);
                m.start();

                EditText e;
                e = (EditText) findViewById(R.id.edit1);
                n1 = e.getText().toString();
                t1.setText(n1+" ( X )");
                e.setHintTextColor(Color.parseColor("#ffffff"));

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer m = MediaPlayer.create(TwoPlayer.this,R.raw.click);
                m.start();
                EditText e;
                e = (EditText) findViewById(R.id.edit2);
                n2 = e.getText().toString();
                t2.setText(n2+" ( O )");
                e.setHintTextColor(Color.parseColor("#ffffff"));

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer m = MediaPlayer.create(TwoPlayer.this,R.raw.click);
                m.start();
                Intent i = new Intent(TwoPlayer.this,Game2.class);
                i.putExtra("Name1",n1);
                i.putExtra("Name2",n2);
                startActivity(i);
            }
        });
    }
}
