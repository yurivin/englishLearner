package com.example.wordslearner;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Юрий on 24.01.14.
 */
public class AvailableWordSets extends Activity {

    Cursor cursor;
    String[] wordSetNames;
    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablewordsets);

        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        cursor = db.query("WordSets", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            wordSetNames = new String[cursor.getCount()];
            int i = 0;
            int wordSetNameIndex = cursor.getColumnIndex("title");
            do {
                wordSetNames[i] = cursor.getString(wordSetNameIndex);
                i++;
            } while (cursor.moveToNext());
            for (String name : wordSetNames) {
                Log.d("Word set Names from db: ", name);
            }
        }
    }
}
