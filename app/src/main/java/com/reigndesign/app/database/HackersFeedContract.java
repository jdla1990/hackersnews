package com.reigndesign.app.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.reigndesign.app.models.New;

import java.util.ArrayList;
import java.util.List;

public abstract class HackersFeedContract {
    private static final String TAG = "HackersFeedContract";

    private HackersFeedContract() {
    }

    static class NewEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ID_STORY = "id_story";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DATE = "created_at";
        public static final String COLUMN_NAME_VISIBLE = "visibleNew";
    }

    public static List<New> fromDb(SQLiteDatabase db) {
        List<New> newList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String[] projection = {
                    HackersFeedContract.NewEntry._ID,
                    HackersFeedContract.NewEntry.COLUMN_NAME_ID_STORY,
                    HackersFeedContract.NewEntry.COLUMN_NAME_TITLE,
                    HackersFeedContract.NewEntry.COLUMN_NAME_CONTENT,
                    HackersFeedContract.NewEntry.COLUMN_NAME_URL,
                    HackersFeedContract.NewEntry.COLUMN_NAME_AUTHOR,
                    HackersFeedContract.NewEntry.COLUMN_NAME_DATE,
                    HackersFeedContract.NewEntry.COLUMN_NAME_VISIBLE
            };

            String selection = NewEntry.COLUMN_NAME_VISIBLE + " LIKE 1";//solo los visibles

            String sortOrder = NewEntry.COLUMN_NAME_DATE + " DESC";
            cursor = db.query(HackersFeedContract.NewEntry.TABLE_NAME, projection,
                    selection, null, null, null, sortOrder);
            New newToAdd;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NewEntry._ID));
                String storyId = cursor.getString(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_ID_STORY));
                String storyTitle = cursor.getString(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_TITLE));
                String storyContent = cursor.getString(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_CONTENT));
                String storyUrl = cursor.getString(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_URL));
                String storyAuthor = cursor.getString(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_AUTHOR));
                long storyEpoch = cursor.getLong(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_DATE));
                int storyVisible = cursor.getInt(cursor.getColumnIndexOrThrow(NewEntry.COLUMN_NAME_VISIBLE));

                newToAdd = new New(storyEpoch, storyId, id, storyTitle, storyUrl, storyAuthor, storyContent, storyVisible == 1);
                newList.add(newToAdd);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return newList;
    }

    public static List<New> toDb(HackersFeedDbHelper mDbHelper, List<New> newList) {

        SQLiteDatabase writableDatabase = mDbHelper.getWritableDatabase();
        SQLiteDatabase readableDatabase = mDbHelper.getReadableDatabase();
        //se hace una copia de la lista para poder elimitar elementos sin generar excepcion
        //se revisa si la historia ya esta agregada para no dupllicarla
        for (New aNew : new ArrayList<>(newList)) {
            if (storyExist(aNew, readableDatabase)) {
                newList.remove(aNew);
            } else {
                saveNew(aNew, writableDatabase);
            }
        }
        return newList;
    }

    static long saveNew(New mNew, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_ID_STORY, mNew.getStoryId());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_TITLE, mNew.getTitle());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_CONTENT, mNew.getComment());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_URL, mNew.getStoryUrl());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_AUTHOR, mNew.getAuthor());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_DATE, mNew.getCreatedAt());
        values.put(HackersFeedContract.NewEntry.COLUMN_NAME_VISIBLE, 1);

        return db.insert(HackersFeedContract.NewEntry.TABLE_NAME, null, values);
    }

    static boolean storyExist(New aNew, SQLiteDatabase db) {
        Cursor cursor = null;
        boolean exist = false;
        try {
            String[] projection = {
                    HackersFeedContract.NewEntry._ID,
            };

            String selection = NewEntry.COLUMN_NAME_ID_STORY + " = ? AND " + NewEntry.COLUMN_NAME_AUTHOR + " = ?";
            String[] selectionArgs = {aNew.getStoryId(), aNew.getAuthor()};

            cursor = db.query(
                    HackersFeedContract.NewEntry.TABLE_NAME,
                    projection, selection, selectionArgs, null, null, null
            );
            if (cursor.moveToFirst()) {
                exist = true;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
        return exist;

    }

    public static void removeNew(int idDb, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(NewEntry.COLUMN_NAME_VISIBLE, 0);
        String selection = NewEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(idDb)};

        int count = db.update(NewEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        Log.d(TAG, "removeNew: " + count);
    }
}
