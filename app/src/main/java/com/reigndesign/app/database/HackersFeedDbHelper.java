package com.reigndesign.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HackersFeedDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HackersFeedContract.NewEntry.TABLE_NAME + " (" +
                    HackersFeedContract.NewEntry._ID + " INTEGER PRIMARY KEY," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_ID_STORY + " TEXT," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_TITLE + " TEXT," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_CONTENT + " TEXT," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_URL + " TEXT," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_AUTHOR + " TEXT," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_DATE + " NUMERIC," +
                    HackersFeedContract.NewEntry.COLUMN_NAME_VISIBLE + " INTEGER DEFAULT 1)";//default is visible

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HackersFeedContract.NewEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HackersFeed.db";

    public HackersFeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
