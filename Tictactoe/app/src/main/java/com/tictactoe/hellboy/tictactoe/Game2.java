package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game2 extends Activity implements View.OnClickListener {

    String name1,name2;

    // creating button objects
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,newgame;
    Button[] barray;
    Boolean turn = true;   // X= true  O=false
    Boolean match = true;
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    int xscore=0,yscore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        name1= extras.getString("Name1");
        name2= extras.getString("Name2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        TextView t;
        t = (TextView) findViewById(R.id.whoseTurn);
        t.setText(name1+"'s Turn");

        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);


        newgame = (Button) findViewById(R.id.ng);
        newgame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDisableAllButtons(true);

                if(match){
                    turn = false;
                }else{
                    turn = true;
                }
                match = !match;
                turnCount = 0;
                there_is_a_winner=false;


                TextView t;
                t = (TextView) findViewById(R.id.whoseTurn);
                if(match){
                    t.setText(name1+"'s Turn");
                }else {
                    t.setText(name2+"'s Turn");
                }


                TextView r;
                r= (TextView) findViewById(R.id.ng);
                r.setBackgroundColor(Color.parseColor("#ffffff"));
                r.setText("");
                t.setTextColor(Color.parseColor("#0e0834"));
                t.setTypeface(null, Typeface.NORMAL);
            }
        });

        barray = new Button[]{a1,a2,a3,b1,b2,b3,c1,c2,c3}; // it will simplify the process of making event listeners

        for(Button b : barray){
            // means for every button in barray


            b.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        // Toast.makeText(Game2.this, "button clicked",Toast.LENGTH_SHORT).show();
        final MediaPlayer mm = MediaPlayer.create(Game2.this,R.raw.click);
        mm.start();
       Button b = (Button) v;
        buttonClicked(b);

    }

    public void buttonClicked(Button b){

        if(turn){  // X's turn
            b.setText("X");
            if(!match){
                updateWhoseTurn(name2);
            }else{
                updateWhoseTurn(name1);
            }
        }
        else{   // Y's turn
            b.setText("O");
            if(!match){
                updateWhoseTurn(name1);
            }else{
                updateWhoseTurn(name2);
            }
        }
        turnCount++;
        b.setClickable(false);
        turn = !turn;
        checkForWinner();
    }

    private void checkForWinner(){

        // check for horizontals
        if(a1.getText()==a2.getText() && a2.getText()==a3.getText() && !a1.isClickable())
            there_is_a_winner = true;
        if(b1.getText()==b2.getText() && b2.getText()==b3.getText() && !b1.isClickable())
            there_is_a_winner = true;
        if(b1.getText()==b2.getText() && b2.getText()==b3.getText() && !b1.isClickable())
            there_is_a_winner = true;

        // check for verticals

        if(a1.getText()==b1.getText() && b1.getText()==c1.getText() && !a1.isClickable())
            there_is_a_winner = true;
        if(a2.getText()==b2.getText() && b2.getText()==c2.getText() && !a2.isClickable())
            there_is_a_winner = true;
        if(a3.getText()==b3.getText() && b3.getText()==c3.getText() && !a3.isClickable())
            there_is_a_winner = true;

        // for two diagonals
        if(a1.getText()==b2.getText() && b2.getText()==c3.getText() && !a1.isClickable())
            there_is_a_winner = true;
        if(a3.getText()==b2.getText() && b2.getText()==c1.getText() && !a3.isClickable())
            there_is_a_winner = true;

        if(there_is_a_winner){
            if(!turn){
                //Toast.makeText(Game2.this, "X won",Toast.LENGTH_SHORT).show();
                xscore++;
                displayResult(name1);
                updateScorex(xscore);

            }
            else{
                //Toast.makeText(Game2.this, "O won",Toast.LENGTH_SHORT).show();
                yscore++;
                displayResult(name2);
                updateScorey(yscore);
            }

            enableDisableAllButtons(false);
        }
        else if (turnCount == 9){
            //Toast.makeText(Game2.this, "Match Drawn",Toast.LENGTH_SHORT).show();
            displayResult("D");
        }
    }

    private void enableDisableAllButtons(Boolean value){
        for(Button b : barray){
            b.setClickable(value);
            if(value){
                b.setText("");
            }
        }
    }

    private void updateScorex(int x){
        TextView t;
        t = (TextView) findViewById(R.id.scorex);
        String s = "X : ";
        s = s + x;
        t.setText(s);
    }

    private void updateScorey(int y){
        TextView t;
        t = (TextView) findViewById(R.id.scorey);
        String s = "O : ";
        s = s + y;
        t.setText(s);
    }

    private void updateWhoseTurn(String ss){
        TextView t;
        t = (TextView) findViewById(R.id.whoseTurn);
        String s = ss;
        s = s + "'s Turn";
        t.setText(s);
    }

    private void displayResult(String ss){
        TextView t,r;
        t = (TextView) findViewById(R.id.whoseTurn);
        if(ss != "D"){
            String s = ss + " WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#006400"));
            t.setTypeface(null, Typeface.BOLD);
        }
        else{
            t.setText("MAtch Drawn");
            t.setTextColor(Color.parseColor("#006400"));
            t.setTypeface(null, Typeface.BOLD);
        }
        r= (TextView) findViewById(R.id.ng);
        r.setBackgroundColor(Color.parseColor("#006400"));
        r.setText("Next Match");

    }

    @Override
    public void onBackPressed()
    {
        Intent i;
        i = new Intent(Game2.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();  // optional depending on your needs
    }



} // class closed
