package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.wordslearner.DbHelper;
import com.example.wordslearner.R;
import com.example.wordslearner.view.MainActivity;

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
            ListView lvWordSets = (ListView) findViewById(R.id.lvWordSets);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordSetNames);
            lvWordSets.setAdapter(adapter);
            lvWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            final Intent intent = new Intent(this, MainActivity.class);

            lvWordSets.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.putExtra("wordSetTitle", wordSetNames[position]);
                    startActivity(intent);
                    dbHelper.close();
                }
            });
        }
    }
}
