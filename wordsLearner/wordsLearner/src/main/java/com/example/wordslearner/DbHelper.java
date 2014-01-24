package com.example.wordslearner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Юрий on 24.01.14.
 */
class DbHelper extends SQLiteOpenHelper {
    //not use old DB myDB;
    public DbHelper(Context context) {
        super(context, "myDBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE WordSets(");
        sb.append("id integer primary key autoincrement, ");
        sb.append("title text");
        sb.append(");");

        db.execSQL(sb.toString());

        sb = new StringBuilder();
        sb.append("CREATE TABLE WordPairs(");
        sb.append("id integer primary key autoincrement, ");
        sb.append("foreignW text, ");
        sb.append("translation text, ");
        sb.append("wordSetId integer");
        sb.append(");");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
