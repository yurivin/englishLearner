package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.wordslearner.LogUtils;
import com.example.wordslearner.MainMenu;
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
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteWordSet:
                DbService.deleteWordSet(this, wordSetNames[info.position]);
                Log.d("deleted wordsSet : ", wordSetNames[info.position]);
                showWordsSets();
                break;
            case R.id.editWordSet:
                Log.d("Words set to change", wordSetNames[info.position]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        MainMenu mainMenu = new MainMenu(this);
        mainMenu.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
