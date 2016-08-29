package com.tictactoe.hellboy.tictactoe;

import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Game1 extends Activity implements View.OnClickListener {

    // All variables
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,newgame;
    Button[] barray;
    String[][] array = new String[3][3];
    Boolean turn = true;   // X= true,you  O=false,com
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    int xscore=0,yscore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);


//
//        TextView wt ;
//        wt = (TextView) findViewById(R.id.whoseTurn);
//        wt.setText("Your Turn");

        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);

        barray = new Button[]{a1,a2,a3,b1,b2,b3,c1,c2,c3};
        int index = 0;
        final ttt t = new ttt();
        for(Button b: barray){
            // means for every button in barray
            final int finalIndex = index;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final MediaPlayer mm = MediaPlayer.create(Game1.this,R.raw.click);
                    mm.start();

                    Button tmp = (Button)view;
                    tmp.setText("X");
                    turnCount++;
//                    TextView wt = (TextView) findViewById(R.id.whoseTurn);
//                    wt.setText("COM'S Turn");
                    int bla = finalIndex;
                    Point best;
                    Point p = new Point(bla/3,bla%3);
                    if(turnCount != 9){
                        best = t.setUserMove(p);
                        buttonClicked((Button)view, best.x , best.y);
                    }else{
                        checkForWinner();
                    }

                    //Toast.makeText(Game1.this,"best move"+best.x+" "+best.y,Toast.LENGTH_SHORT).show();
                }
            });
            index++;
        }

        newgame = (Button) findViewById(R.id.ng);
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                enableDisableAllButtons(true);
//                turnCount = 0;
//                there_is_a_winner=false;
////                final ttt t = new ttt();
////                int index = 0;
////                final int finalIndex = index;
//
//
//                TextView s;
//                s = (TextView) findViewById(R.id.whoseTurn);
//                s.setText("Your Turn");
//                s.setTextColor(Color.parseColor("#0e0834"));
//                s.setTypeface(null, Typeface.NORMAL);
//
//                TextView r;
//                r= (TextView) findViewById(R.id.ng);
//                r.setBackgroundColor(Color.parseColor("#ffffff"));
//                r.setText("");

                Intent i = getIntent();
                finish();
                startActivity(i);



            }
        });

    }


    public void buttonClicked(Button b, int x,int y){

        //Toast.makeText(Game1.this, "count"+turnCount,Toast.LENGTH_SHORT).show();
        Button comChoice = a1;
        b.setClickable(false);
        comChoice = findTheButton(x,y);
        comChoice.setText("O");
        turnCount++;
        comChoice.setClickable(false);
//        whoseTurn.setText("Your Turn");
        //turn = !turn;
        checkForWinner();
    }

    @Override
    public void onClick(View view) {

    }

    public Button findTheButton(int x,int y){
        Button b = null;
        int index=0;
        index = x*3 + y;
        switch (index){
            case 0:
                b= a1;
                break;
            case 1:
                b= a2;
                break;
            case 2:
                b= a3;
                break;
            case 3:
                b= b1;
                break;
            case 4:
                b= b2;
                break;
            case 5:
                b= b3;
                break;
            case 6:
                b= c1;
                break;
            case 7:
                b= c2;
                break;
            case 8:
                b= c3;
                break;
        }
        return b;

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
                displayResult("YOU");
                //updateScorex(xscore);

            }
            else{
                //Toast.makeText(Game2.this, "O won",Toast.LENGTH_SHORT).show();
                yscore++;
                displayResult("COM");
                //updateScorey(yscore);
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

} //  final


//////////////////////////////////////////////////////////////////////////////////////






