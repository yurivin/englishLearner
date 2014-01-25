package com.example.wordslearner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.wordslearner.DbHelper;
import android.content.Context;

/**
 * Created by Юрий on 25.01.14.
 */
public class WordSetsDAO {

    static DbHelper dbHelper;
    static Cursor cursor;

    public static Integer getIdByTitle(Context context, String title) {
        String[] args = new String[1];
        args[0] = title;
        SQLiteDatabase db = getReadableDb(context);
        cursor = db.rawQuery("SELECT id FROM WordSets WHERE title = ?", args);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            return cursor.getInt(idColIndex);
        }
        return null;
    }

    public static long insertNewWordSet(Context context, String title) {
        SQLiteDatabase db = getReadableDb(context);
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        return db.insert("WordSets", null, cv);
    }

    public static String[] getAllWordSetNames(Context context) {
        dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("WordSets", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String[] wordSetNames = new String[cursor.getCount()];
            int i = 0;
            int wordSetNameIndex = cursor.getColumnIndex("title");
            do {
                wordSetNames[i] = cursor.getString(wordSetNameIndex);
                i++;
            } while (cursor.moveToNext());
            return wordSetNames;
        } else {
            return null;
        }
    }

    private static SQLiteDatabase getReadableDb(Context context){
        dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db;
    }

    private static SQLiteDatabase getWritableDb(Context context){
        dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db;
    }
}