package com.example.wordslearner.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Юрий on 25.01.14.
 */
class DbUtils {

    static DbHelper dbHelper;
    static SQLiteDatabase db;

    protected static SQLiteDatabase getReadableDb(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
        return db;
    }

    protected static SQLiteDatabase getWritableDb(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return db;
    }

    protected static void closeDb(){
        dbHelper.close();
    }

}
