package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class TwoPlayerActivity extends Activity{
    // TODO : too many unnecessary global variables
    // FIXME : why are all functions without any arguments, main reason for the above

    String name1,name2;

    // creating button objects
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,newgame;
    Button[] barray;
    Boolean turn = true;   // X= true  O=false

    //Boolean match = true;
    int match = 1;
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    int xscore=0,yscore=0;


    // integer array representing board, -1 = unfilled, 0 = circle, 1 = cross
    Integer[][] board = new Integer[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        name1= extras.getString("Name1");
        name2= extras.getString("Name2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        TextView tmp;
        tmp = (TextView) findViewById(R.id.namex);
        tmp.setText(name1);

        TextView x;
        x = (TextView) findViewById(R.id.namey);
        x.setText(name2);


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

                match ++;
                if(match % 2 ==0){
                    turn = false;
                }else{
                    turn = true;
                }

                turnCount = 0;
                there_is_a_winner=false;


                TextView t;
                t = (TextView) findViewById(R.id.whoseTurn);
                if(turn){
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
            final MediaPlayer mm = MediaPlayer.create(TwoPlayerActivity.this,R.raw.click);

            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mm.start();
                    Button b = (Button) view;
                    buttonClicked(b);
                }
            });
        }

    }

    public void buttonClicked(Button b){
        int idx = Arrays.asList(barray).indexOf(b);
        // 1 for cross
        board[idx/3][idx%3] = 1;

        if(turn){  // X's turn
            b.setBackgroundResource(R.drawable.cross);

            if(match %2 ==0){
                updateWhoseTurn(name1);
            }else{
                updateWhoseTurn(name2);
            }
            updateWhoseTurn(name2);
        }
        else{   // Y's turn
            b.setBackgroundResource(R.drawable.circle);
            // 1 for cross
            board[idx/3][idx%3] = 0;
            if(match %2 == 0){
                updateWhoseTurn(name2);
            }else{
                updateWhoseTurn(name1);
            }
            updateWhoseTurn(name1);
        }
        turnCount++;
        b.setClickable(false);
        turn = !turn;
        checkForWinner();
    }

    private void checkForWinner(){

        // logic seems fine, so didnt change it
        //horizontal
        if(board[0][0].equals(board[0][1]) && board[0][1].equals(board[0][2]) && !board[0][0].equals(-1))
            there_is_a_winner =true;
        if(board[1][0].equals(board[1][1]) && board[1][1].equals(board[1][2]) && !board[1][0].equals(-1))
            there_is_a_winner =true;
        if(board[2][0].equals(board[2][1]) && board[2][1].equals(board[2][2]) && !board[2][0].equals(-1))
            there_is_a_winner =true;

        // vertical
        if(board[0][0].equals(board[1][0]) && board[1][0].equals(board[2][0]) && !board[0][0].equals(-1))
            there_is_a_winner =true;
        if(board[0][1].equals(board[1][1]) && board[1][1].equals(board[2][1]) && !board[0][1].equals(-1))
            there_is_a_winner =true;
        if(board[0][2].equals(board[1][2]) && board[1][2].equals(board[2][2]) && !board[0][2].equals(-1))
            there_is_a_winner =true;

        //diagonals
        if(board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(-1))
            there_is_a_winner =true;
        if(board[2][0].equals(board[1][1]) && board[1][1].equals(board[0][2]) && !board[0][2].equals(-1))
            there_is_a_winner =true;

        if(there_is_a_winner){
            if(!turn){
                //Toast.makeText(TwoPlayerActivity.this, "X won",Toast.LENGTH_SHORT).show();
                xscore++;
                displayResult(name1);
                updateScorex(xscore);

            }
            else{
                //Toast.makeText(TwoPlayerActivity.this, "O won",Toast.LENGTH_SHORT).show();
                yscore++;
                displayResult(name2);
                updateScorey(yscore);
            }

            enableDisableAllButtons(false);
        }
        else if (turnCount == 9){
            //Toast.makeText(TwoPlayerActivity.this, "Match Drawn",Toast.LENGTH_SHORT).show();
            displayResult("D");
        }
    }

    private void enableDisableAllButtons(Boolean value){
        for(Button b : barray){
            b.setClickable(value);
            if(value)
                b.setBackgroundResource(R.drawable.gamebutton);
        }
        // reset board
        if(value) {
            board = new Integer[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
        }
    }

    private void updateScorex(int x){
        TextView t;
        t = (TextView) findViewById(R.id.you_name);
        String s="";
        t.setText(s+x);
    }

    private void updateScorey(int y){
        TextView t;
        t = (TextView) findViewById(R.id.scorey);
        String s = "";;
        t.setText(s+y);
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
        if(! ss.equals("D")){
            String s = ss + " WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#006400"));
            t.setTypeface(null, Typeface.BOLD);
        }
        else{
            t.setText("Match Drawn");
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
        new AlertDialog.Builder(TwoPlayerActivity.this)
            .setTitle("Tic Tac Toe")
            .setMessage("Are you sure you want to quit the game?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i;
                    i = new Intent(TwoPlayerActivity.this,MainActivity.class);
                    startActivity(i);
    //                        super.onBackPressed();
                }

            })
            .setNegativeButton("No", null)
            .show();

    }

} // class closed
