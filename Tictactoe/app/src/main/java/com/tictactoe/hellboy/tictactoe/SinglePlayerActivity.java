package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SinglePlayerActivity extends Activity {

    // All variables
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3;
    Button[] barray;
    String[][] array = new String[3][3];
    Boolean turn = true;   // X= true,you  O=false,com
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    MiniMax miniMax;
    // difficulty level
    int diff_level;
    double randFactor;

    // integer array representing board, -1 = unfilled, 0 = circle, 1 = cross
    Integer[][] board = new Integer[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        final CharSequence[] difficulty = {"Easy", "Medium", "Hard", "Impossible"};
        new AlertDialog.Builder(SinglePlayerActivity.this)
                .setTitle("Select Difficulty Level")
                .setSingleChoiceItems(difficulty,1, null)
                .setCancelable(false)
                .setIcon(R.drawable.index)
                .setNeutralButton("Done", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diff_level = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                    }
                })
                .show();


        randFactor = Math.random();

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

        TextView score = (TextView) findViewById(R.id.comp_score);
        if(score != null)
            score.setText("0");

        score = (TextView) findViewById(R.id.you_score);
        if(score != null)
            score.setText("0");


        miniMax = new MiniMax();

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

        for(final Button b: barray){
            // means for every button in barray
            final int finalIndex = index;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MediaPlayer mm = MediaPlayer.create(SinglePlayerActivity.this,R.raw.click);
                    mm.start();
                    Button tmp = (Button)view;
                    tmp.setBackgroundResource(R.drawable.cross);
                    tmp.setClickable(false);
                    turnCount++;
                    turn = !turn;
                    Point best = new Point(0, 0);
                    Point p = new Point(finalIndex/3, finalIndex%3);
                    board[finalIndex/3][finalIndex%3] = 1;
                    checkForWinner();

                    if(there_is_a_winner)
                        return;

                    if(turnCount != 9){
                        /*
                        difficulty levels to be implemented here
                         */
                        switch (diff_level){
                            case 0:{
                                best = EasyAI();
                                break;
                            }
                            case 1:{
                                if(randFactor >= 0.5){
                                    best = EasyAI();
                                }
                                else{
                                    best = miniMax.setUserMove(p);
                                }
                                break;
                            }
                            case 2:{
                                if(randFactor > 0.8){
                                    best = EasyAI();
                                }
                                else{
                                    best = miniMax.setUserMove(p);
                                }
                                break;
                            }
                            case 3:{
                                best = miniMax.setUserMove(p);
                                break;
                            }
                        }
                        buttonClicked((Button)view, best.x , best.y);
                    }
                    else{
                        checkForWinner();
                    }

                    //Toast.makeText(SinglePlayerActivity.this,"best move"+best.x+" "+best.y,Toast.LENGTH_SHORT).show();
                }
            });
            index++;
        }

    }

    public Point EasyAI(){
        while(true){
            Random r = new Random();
            int x = r.nextInt(3);
            int y = r.nextInt(3);
            if(board[x][y] == -1){
                return new Point(x, y);
            }

        }
    }

    public void buttonClicked(Button b, int x,int y){

        //Toast.makeText(SinglePlayerActivity.this, "count"+turnCount,Toast.LENGTH_SHORT).show();
        Button comChoice;
        b.setClickable(false);
        comChoice = findTheButton(x,y);
        comChoice.setBackgroundResource(R.drawable.circle);
        board[x][y] = 0;
        turnCount++;
        comChoice.setClickable(false);
        turn = !turn;
        checkForWinner();
    }

    public Button findTheButton(int x,int y){
        return Arrays.asList(barray).get(x* 3 + y);
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
                displayResult("W");
                updateScore(false);
                return;
            }
            else{
                displayResult("L");
                updateScore(true);
            }
            enableDisableAllButtons(false);
            return;

        }
        if (turnCount == 9){
            displayResult("D");
            enableDisableAllButtons(false);

        }
    }


    private void updateScoreDB(){
        Score.SinglePlayerScoreHelper scoreHelper = new Score.SinglePlayerScoreHelper(this);
        SQLiteDatabase db = scoreHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        TextView Cscore = (TextView) findViewById(R.id.comp_score);
        if(Cscore != null)
            contentValues.put(
                    Score.SinglePlayerScoreParams.AI_SCORE,
                    Integer.parseInt(Cscore.getText().toString()));

        TextView Uscore = (TextView) findViewById(R.id.you_score);
        if(Uscore != null)
            contentValues.put(
                    Score.SinglePlayerScoreParams.USER_SCORE,
                    Integer.parseInt(Uscore.getText().toString()));
        // update if only a game has been played
        if(!Uscore.getText().toString().equals("0") || !Cscore.getText().toString().equals("0")) {
            db.insert(Score.SinglePlayerScoreParams.TABLE_NAME, null, contentValues);
        }
    }

    public void reset_game(View view){
        new AlertDialog.Builder(SinglePlayerActivity.this)
            .setTitle("Tic Tac Toe")
            .setMessage("Are you sure you want to reset the game?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateScoreDB();
                    Intent i;
                    i = new Intent(SinglePlayerActivity.this,SinglePlayerActivity.class);
                    startActivity(i);
                }
            })
            .setNegativeButton("No", null)
            .show();
    }

    public void request_more_games(View view){
        // handle result and draw cases
        if(! there_is_a_winner && turnCount != 9){
            new AlertDialog.Builder(SinglePlayerActivity.this)
            .setTitle("Tic Tac Toe")
            .setMessage("Game in progress. Finish it before requesting a new one")
            .setNeutralButton("Ok", null)
            .show();

            return;
        }
        enableDisableAllButtons(true);
        miniMax = new MiniMax();
        randFactor = Math.random();

    }

    public void view_history(View view){
        Intent intent = new Intent(this, History.class);
        intent.putExtra("Game", true);
        startActivity(intent);

    }

    /**
     * @param id
     * true is for computer score
     * false is for human score
     */
    private void updateScore(boolean id){

        if(id){
            TextView score = (TextView) findViewById(R.id.comp_score);
            if(score != null)
                score.setText(Integer.toString(Integer.parseInt(score.getText().toString()) + 1));
        }
        else{
            TextView score = (TextView) findViewById(R.id.you_score);
            if(score != null)
                score.setText(Integer.toString(Integer.parseInt(score.getText().toString()) + 1));
        }
    }

    /**
     * @param value : false when to pause board after a game to make it view only
     *              : true to reset board
     */
    private void enableDisableAllButtons(Boolean value){
        for(Button b : barray){
            b.setClickable(value);
            if(value)
                b.setBackgroundResource(R.drawable.gamebutton);
        }
        // reset board
        if(value) {
            board = new Integer[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
            TextView t = (TextView) findViewById(R.id.win_lose_msg);
            t.setText("");
            turnCount = 0;
            there_is_a_winner = false;
            turn = true;
        }
    }

    private void displayResult(String ss){
        TextView t;
        t = (TextView) findViewById(R.id.win_lose_msg);
        if(ss.equals("W")){
            String s = "YOU WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#096409"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            t.startAnimation(anim);

        }else if(ss.equals("L")){
            String s = "YOU LOSE";
            t.setText(s);
            t.setTextColor(Color.parseColor("#ee0000"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            t.startAnimation(anim);
        }
        else{
            t.setText("Match Drawn");
            t.setTextColor(Color.parseColor("#800000"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            t.startAnimation(anim);
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(SinglePlayerActivity.this)
            .setTitle("Tic Tac Toe")
            .setMessage("Are you sure you want to quit the game?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateScoreDB();
                    Intent i;
                    i = new Intent(SinglePlayerActivity.this,MainActivity.class);
                    startActivity(i);
                }
            })
            .setNegativeButton("No", null)
            .show();

    }

}








