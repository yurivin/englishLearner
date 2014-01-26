package com.example.wordslearner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

/**
 * Created by Юрий on 25.01.14.
 */
public class WordSetsDAO {

    static Cursor cursor;
    static SQLiteDatabase db;

    protected static Integer getIdByTitle(Context context, String title) {
        Integer id = null;
        String[] args = {title};
        db = DbUtils.getReadableDb(context);
        cursor = db.rawQuery("SELECT id FROM WordSets WHERE title = ?", args);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            id = cursor.getInt(idColIndex);
        }
        DbUtils.closeDb();
        return id;
    }

    protected static long insertNewWordSet(Context context, String title) {
        db = DbUtils.getWritableDb(context);
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        long id = db.insert("WordSets", null, cv);
        DbUtils.closeDb();
        return id;
    }

    protected static String[] getAllWordSetNames(Context context) {
        String[] wordSetNames = null;
        db = DbUtils.getReadableDb(context);
        cursor = db.query("WordSets", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            wordSetNames = new String[cursor.getCount()];
            int i = 0;
            int wordSetNameIndex = cursor.getColumnIndex("title");
            do {
                wordSetNames[i] = cursor.getString(wordSetNameIndex);
                i++;
            } while (cursor.moveToNext());
        }
        DbUtils.closeDb();
        return wordSetNames;
    }

    protected static void deleteById(Context context, int id){
        db = DbUtils.getWritableDb(context);
        String[] args = {String.valueOf(id)};
        db.delete("WordSets", "id = ?", args);
        DbUtils.closeDb();
    }
}