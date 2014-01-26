package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.wordslearner.LogUtils;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.DbService;

/**
 * Created by Юрий on 24.01.14.
 */
public class AvailableWordSets extends Activity {

    String[] wordSetNames;
    ListView lvWordSets;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablewordsets);
        lvWordSets = (ListView) findViewById(R.id.lvWordSets);
        lvWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        registerForContextMenu(lvWordSets);
        intent = new Intent(this, MainActivity.class);
        showWordsSets();
        lvWordSets.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("wordSetTitle", wordSetNames[position]);
                startActivity(intent);
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
            case R.id.deleteWordSet:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                DbService.deleteWordSet(this, wordSetNames[info.position]);
                Log.d("deleted wordsSet :", wordSetNames[info.position]);
                showWordsSets();
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void showWordsSets() {
        wordSetNames = DbService.getAllWordSetNames(this);
        LogUtils.debugForCycleLog("Word set Names from db: ", wordSetNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordSetNames);
        lvWordSets.setAdapter(adapter);
    }
}
