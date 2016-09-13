package com.tictactoe.hellboy.tictactoe;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class TwoPlayerActivity extends Activity {

    // All variables
    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    Button[] barray;
    String[][] array = new String[3][3];
    Boolean turn = true;   // X= true,you  O=false,com
    int turnCount = 0;
    Boolean there_is_a_winner = false;
    Boolean player_1_turn;

    // integer array representing board, -1 = unfilled, 0 = circle, 1 = cross
    Integer[][] board = new Integer[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        final LinearLayout nameLayout = new LinearLayout(this);
        nameLayout.setOrientation(LinearLayout.VERTICAL);
        nameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        final EditText p1name = new EditText(this);
        p1name.setHint("Player1 Name");
        p1name.setMaxLines(1);
        p1name.setGravity(Gravity.CENTER_HORIZONTAL);
        p1name.setId(R.id.getp1name);

        final EditText p2name = new EditText(this);
        p2name.setHint("Player2 Name");
        p2name.setMaxLines(1);
        p2name.setGravity(Gravity.CENTER_HORIZONTAL);
        p2name.setId(R.id.getp2name);

        final EditText p3name = new EditText(this);
        p3name.setHint("Player1 Name");
        p3name.setMaxLines(1);
        p3name.setGravity(Gravity.CENTER_HORIZONTAL);
        p3name.setVisibility(View.INVISIBLE);

        nameLayout.addView(p1name);
        nameLayout.addView(p3name);
        nameLayout.addView(p2name);

        Intent intent = getIntent();
        player_1_turn = intent.getBooleanExtra("player1_turn", true);

        new AlertDialog.Builder(TwoPlayerActivity.this)
                .setView(nameLayout)
                .setTitle("Enter Player Names")
                .setCancelable(false)
                .setIcon(R.drawable.index)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView player1 = (TextView) findViewById(R.id.Tplayer1_name);
                        if (p1name.getText().toString().length() > 0)
                            player1.setText(p1name.getText().toString());
                        else
                            player1.setText("Player 1");
                        TextView player2 = (TextView) findViewById(R.id.Tplayer2_name);
                        if (p2name.getText().toString().length() > 0)
                            player2.setText(p2name.getText().toString());
                        else
                            player2.setText("Player 2");
                    }
                })
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView player1 = (TextView) findViewById(R.id.Tplayer1_name);
                        player1.setText("Player 1");
                        TextView player2 = (TextView) findViewById(R.id.Tplayer2_name);
                        player2.setText("Player 2");
                    }
                })
                .show();

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

        TextView score = (TextView) findViewById(R.id.Tplayer2_score);
        if (score != null)
            score.setText("0");

        score = (TextView) findViewById(R.id.Tplayer1_score);
        if (score != null)
            score.setText("0");

        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);

        barray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};
        int index = 0;

        for (final Button b : barray) {
            // means for every button in barray
            final int finalIndex = index;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MediaPlayer mm = MediaPlayer.create(TwoPlayerActivity.this, R.raw.click);
                    mm.start();
                    Button tmp = (Button) view;
                    // player 1 starts first
                    if (player_1_turn) {
                        if (turnCount % 2 == 0) {
                            tmp.setBackgroundResource(R.drawable.cross);
                            board[finalIndex / 3][finalIndex % 3] = 1;
                        } else {
                            tmp.setBackgroundResource(R.drawable.circle);
                            board[finalIndex / 3][finalIndex % 3] = 0;
                        }
                        tmp.setClickable(false);
                    }
                    //player 2 starts first
                    else {
                        if (turnCount % 2 == 0) {
                            tmp.setBackgroundResource(R.drawable.cross2);
                            board[finalIndex / 3][finalIndex % 3] = 1;
                        } else {
                            tmp.setBackgroundResource(R.drawable.circle2);
                            board[finalIndex / 3][finalIndex % 3] = 0;
                        }
                    }
                    turnCount++;
                    checkForWinner();
                    turn = !turn;
                    //Toast.makeText(TwoPlayerActivity.this,"best move"+best.x+" "+best.y,Toast.LENGTH_SHORT).show();
                }
            });
            index++;
        }

    }

    public Button findTheButton(int x, int y) {
        return Arrays.asList(barray).get(x * 3 + y);
    }

    private void checkForWinner() {

        if (board[0][0].equals(board[0][1]) && board[0][1].equals(board[0][2]) && !board[0][0].equals(-1))
            there_is_a_winner = true;
        if (board[1][0].equals(board[1][1]) && board[1][1].equals(board[1][2]) && !board[1][0].equals(-1))
            there_is_a_winner = true;
        if (board[2][0].equals(board[2][1]) && board[2][1].equals(board[2][2]) && !board[2][0].equals(-1))
            there_is_a_winner = true;

        // vertical
        if (board[0][0].equals(board[1][0]) && board[1][0].equals(board[2][0]) && !board[0][0].equals(-1))
            there_is_a_winner = true;
        if (board[0][1].equals(board[1][1]) && board[1][1].equals(board[2][1]) && !board[0][1].equals(-1))
            there_is_a_winner = true;
        if (board[0][2].equals(board[1][2]) && board[1][2].equals(board[2][2]) && !board[0][2].equals(-1))
            there_is_a_winner = true;

        //diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(-1))
            there_is_a_winner = true;
        if (board[2][0].equals(board[1][1]) && board[1][1].equals(board[0][2]) && !board[0][2].equals(-1))
            there_is_a_winner = true;

        if (there_is_a_winner) {
            if (player_1_turn) {
                if (!turn) {
                    displayResult("W1");
                    updateScore(false);
                    return;
                } else {
                    displayResult("W2");
                    updateScore(true);
                }
            } else {
                if (!turn) {
                    displayResult("W2");
                    updateScore(true);
                    return;
                } else {
                    displayResult("W1");
                    updateScore(false);
                }
            }
            enableDisableAllButtons(false);
            return;

        }
        if (turnCount == 9) {
            displayResult("D");
            enableDisableAllButtons(false);

        }
    }


    private void updateScoreDB() {
        Score.TwoPlayerScoreHelper scoreHelper = new Score.TwoPlayerScoreHelper(this);
        SQLiteDatabase db = scoreHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        TextView player1name = (TextView) findViewById(R.id.Tplayer1_name);
        if (player1name != null)
            contentValues.put(
                    Score.TwoPlayerScoreParams.P1_Name,
                    player1name.getText().toString());

        TextView player1score = (TextView) findViewById(R.id.Tplayer1_score);
        if (player1score != null)
            contentValues.put(
                    Score.TwoPlayerScoreParams.P1_SCORE,
                    Integer.parseInt(player1score.getText().toString()));

        TextView player2name = (TextView) findViewById(R.id.Tplayer2_name);
        if (player2name != null)
            contentValues.put(
                    Score.TwoPlayerScoreParams.P2_Name,
                    player2name.getText().toString());

        TextView player2score = (TextView) findViewById(R.id.Tplayer2_score);
        if (player2score != null)
            contentValues.put(
                    Score.TwoPlayerScoreParams.P2_SCORE,
                    Integer.parseInt(player2score.getText().toString()));
        // update if only a game has been played
        if (!player1score.getText().toString().equals("0") || !player2score.getText().toString().equals("0")) {
            db.insert(Score.TwoPlayerScoreParams.TABLE_NAME, null, contentValues);
        }
    }

    public void reset_game(View view) {
        new AlertDialog.Builder(TwoPlayerActivity.this)
                .setTitle("Tic Tac Toe")
                .setMessage("Are you sure you want to reset the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateScoreDB();
                        Intent i;
                        i = new Intent(TwoPlayerActivity.this, TwoPlayerActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void request_more_games(View view) {
        // handle result and draw cases
        if (!there_is_a_winner && turnCount != 9) {
            new AlertDialog.Builder(TwoPlayerActivity.this)
                    .setTitle("Tic Tac Toe")
                    .setMessage("Game in progress. Finish it before requesting a new one")
                    .setNeutralButton("Ok", null)
                    .show();

            return;
        }
        enableDisableAllButtons(true);
        player_1_turn = !player_1_turn;

    }

    public void view_history(View view) {
        Intent intent = new Intent(this, History.class);
        intent.putExtra("Game", false);
        startActivity(intent);

    }

    /**
     * @param id true is for computer score
     *           false is for human score
     */
    private void updateScore(boolean id) {

        if (id) {
            TextView score = (TextView) findViewById(R.id.Tplayer2_score);
            if (score != null)
                score.setText(Integer.toString(Integer.parseInt(score.getText().toString()) + 1));
        } else {
            TextView score = (TextView) findViewById(R.id.Tplayer1_score);
            if (score != null)
                score.setText(Integer.toString(Integer.parseInt(score.getText().toString()) + 1));
        }
    }

    /**
     * @param value : false when to pause board after a game to make it view only
     *              : true to reset board
     */
    private void enableDisableAllButtons(Boolean value) {
        for (Button b : barray) {
            b.setClickable(value);
            if (value)
                b.setBackgroundResource(R.drawable.gamebutton);
        }
        // reset board
        if (value) {
            board = new Integer[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
            TextView t = (TextView) findViewById(R.id.win_lose_msg);
            t.setText("");
            turnCount = 0;
            there_is_a_winner = false;
            turn = true;
        }
    }

    private void displayResult(String ss) {
        TextView t;
        t = (TextView) findViewById(R.id.win_lose_msg);
        if (ss.equals("W1")) {
            TextView player1 = (TextView) findViewById(R.id.Tplayer1_name);
            String s = player1.getText().toString() + " WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#096409"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            t.startAnimation(anim);

        } else if (ss.equals("W2")) {
            TextView player2 = (TextView) findViewById(R.id.Tplayer2_name);
            String s = player2.getText().toString() + " WON";
            t.setText(s);
            t.setTextColor(Color.parseColor("#096409"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            t.startAnimation(anim);
        } else {
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

        new AlertDialog.Builder(TwoPlayerActivity.this)
                .setTitle("Tic Tac Toe")
                .setMessage("Are you sure you want to quit the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateScoreDB();
                        Intent i;
                        i = new Intent(TwoPlayerActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

}








