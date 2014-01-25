package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.wordslearner.DbHelper;
import com.example.wordslearner.LogUtils;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.WordSetsDAO;

/**
 * Created by Юрий on 24.01.14.
 */
public class AvailableWordSets extends Activity {

    String[] wordSetNames;
    DbHelper dbHelper;
    SQLiteDatabase db;
    ListView lvWordSets;
    Intent intent;
    int currIndex;
    final int DELETE = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablewordsets);

        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();

        lvWordSets = (ListView) findViewById(R.id.lvWordSets);
        lvWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        registerForContextMenu(lvWordSets);

        intent = new Intent(this, MainActivity.class);
        wordSetNames = WordSetsDAO.getAllWordSetNames(this);
        LogUtils.debugForCycleLog("Word set Names from db: ", wordSetNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordSetNames);
        lvWordSets.setAdapter(adapter);
        lvWordSets.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("wordSetTitle", wordSetNames[position]);
                startActivity(intent);
                dbHelper.close();
                currIndex = position;
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        switch (view.getId()) {
            case R.id.lvWordSets:
                getMenuInflater().inflate(R.menu.wordsetslist, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case DELETE:
                db = dbHelper.getWritableDatabase();
                //           int delCount = db.delete("WordSets", );
                //            Log.d("deleted: ", String.valueOf(delCount));
                dbHelper.close();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
