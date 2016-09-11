package com.tictactoe.hellboy.tictactoe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private ArrayList<scoreSetup> scoreSetups = new ArrayList<>();

    private void getScores() {
        Score.SinglePlayerScoreHelper scoreHelper = new Score.SinglePlayerScoreHelper(this);
        SQLiteDatabase db = scoreHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT * FROM " + Score.SinglePlayerScoreParams.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int a = cursor.getInt(
                    cursor.getColumnIndexOrThrow(Score.SinglePlayerScoreParams.USER_SCORE)
            );
            int b = cursor.getInt(
                    cursor.getColumnIndexOrThrow(Score.SinglePlayerScoreParams.AI_SCORE)
            );
            scoreSetup scoreSetup = new scoreSetup();
            scoreSetup.setPlayer1_name("You");
            scoreSetup.setPlayer2_name("AI");
            scoreSetup.setPlayer1_score(Integer.toString(a));
            scoreSetup.setPlayer2_score(Integer.toString(b));
            scoreSetups.add(scoreSetup);
        }
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getScores();

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView delete_ico = (TextView) findViewById(R.id.del_icon);
        if (delete_ico != null)
            delete_ico.setTypeface(font);

        //System.out.println(scoreSetups.get(0).getPlayer1_score());

        HistoryAdapter historyAdapter = new HistoryAdapter(scoreSetups, History.this);
        ListView listView = (ListView) findViewById(R.id.score_list);
        listView.setAdapter(historyAdapter);

    }

}
