package com.tictactoe.hellboy.tictactoe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by anwesh on 9/7/16.
 */
public final class Score{

    private Score(){}

    public class SinglePlayerScoreParams implements BaseColumns {
        public static final int DB_VERSION = 1;
        public static final String DB_NAME = "1Score.db";

        public static final String TABLE_NAME = "SinglePlayer";
        public static final String AI_SCORE = "AI_Score";
        public static final String USER_SCORE = "User_Score";

        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        SinglePlayerScoreParams._ID + " INTEGER PRIMARY KEY," +
                        AI_SCORE + " INTEGER" + COMMA_SEP +
                        USER_SCORE + " INTEGER" + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class SinglePlayerScoreHelper extends SQLiteOpenHelper {

        public SinglePlayerScoreHelper(Context context) {
            super(context, SinglePlayerScoreParams.DB_NAME, null, SinglePlayerScoreParams.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SinglePlayerScoreParams.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SinglePlayerScoreParams.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public class TwoPlayerScoreParams implements BaseColumns {
        public static final int DB_VERSION = 1;
        public static final String DB_NAME = "2Score.db";

        public static final String TABLE_NAME = "TwoPlayer";
        public static final String P1_Name = "P1_Name";
        public static final String P2_Name = "P2_Name";
        public static final String P1_SCORE = "P1_Score";
        public static final String P2_SCORE = "P2_Score";

        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        SinglePlayerScoreParams._ID + " INTEGER PRIMARY KEY," +
                        P1_Name + " Text" + COMMA_SEP +
                        P1_SCORE + " INTEGER" + COMMA_SEP +
                        P2_Name + " Text" + COMMA_SEP +
                        P2_SCORE + " INTEGER" + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }



    public static class TwoPlayerScoreHelper extends SQLiteOpenHelper {

        public TwoPlayerScoreHelper(Context context) {
            super(context, TwoPlayerScoreParams.DB_NAME, null, TwoPlayerScoreParams.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TwoPlayerScoreParams.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(TwoPlayerScoreParams.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
