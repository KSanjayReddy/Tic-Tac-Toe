package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayerActivity extends Activity implements View.OnClickListener {

    // All variables
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,newgame;
    Button[] barray;
    String[][] array = new String[3][3];
    Boolean turn = true;   // X= true,you  O=false,com
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    int xscore=0,yscore=0;

    // integer array representing board, -1 = unfilled, 0 = circle, 1 = cross
    Integer[][] board = new Integer[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView reload = (TextView) findViewById(R.id.reload);
        if (reload != null)
            reload.setTypeface(font);

        TextView new_game = (TextView) findViewById(R.id.new_game);
        if (new_game != null)
            new_game.setTypeface(font);

        TextView history = (TextView) findViewById(R.id.history);
        if (history != null)
            history.setTypeface(font);

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
        final MiniMax t = new MiniMax();
        for(Button b: barray){
            // means for every button in barray
            final int finalIndex = index;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // not working
                    final MediaPlayer mm = MediaPlayer.create(SinglePlayerActivity.this,R.raw.click);
                    mm.start();

                    Button tmp = (Button)view;
                    tmp.setBackgroundResource(R.drawable.cross);
                    turnCount++;
                    Point best;
                    Point p = new Point(finalIndex/3, finalIndex%3);
                    board[finalIndex/3][finalIndex%3] = 1;
                    if(turnCount != 9){
                        best = t.setUserMove(p);
                        buttonClicked((Button)view, best.x , best.y);
                    }else{
                        checkForWinner();
                    }

                    //Toast.makeText(SinglePlayerActivity.this,"best move"+best.x+" "+best.y,Toast.LENGTH_SHORT).show();
                }
            });
            index++;
        }

//        newgame = (Button) findViewById(R.id.ng);
//        newgame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = getIntent();
//                finish();
//                startActivity(i);
//
//            }
//        });

    }

    public void buttonClicked(Button b, int x,int y){

        //Toast.makeText(SinglePlayerActivity.this, "count"+turnCount,Toast.LENGTH_SHORT).show();
        Button comChoice = a1;
        b.setClickable(false);
        comChoice = findTheButton(x,y);
        comChoice.setBackgroundResource(R.drawable.circle);
        board[x][y] = 0;
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
                displayResult("YOU");
                //updateScorex(xscore);

            }
            else{
                //Toast.makeText(TwoPlayerActivity.this, "O won",Toast.LENGTH_SHORT).show();
                yscore++;
                displayResult("L");
                //updateScorey(yscore);
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
            if(value){
                b.setText("");
            }
        }
    }

    private void displayResult(String ss){
        TextView t,r;
        t = (TextView) findViewById(R.id.whoseTurn);
        if(!ss.equals("D") && !ss.equals("L")){
            String s = ss + " WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#006400"));
            t.setTypeface(null, Typeface.BOLD);
        }else if(ss.equals("L")){
            String s = "YOU LOSE";
            t.setText(s);
            t.setTextColor(Color.parseColor("#ee0000"));
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

}








