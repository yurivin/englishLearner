package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordslearner.MainMenu;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.DbService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юрий on 19.01.14.
 */
public class NewWordSet extends Activity implements OnClickListener {

    TextView titleTW;
    EditText foreignET, translationET;
    Button btnSaveWord, btnSaveWordSet, btnRename;
    Intent intent;
    Map<String, String> wordSet;
    String wordSetTitle;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwordsset);

        intent = new Intent(this, NameWordSetDialog.class);
        startActivityForResult(intent, 1);

        titleTW = (TextView) findViewById(R.id.titleTW);
        btnSaveWord = (Button) findViewById(R.id.btnSaveWord);
        btnSaveWordSet = (Button) findViewById(R.id.btnSaveWordSet);
        foreignET = (EditText) findViewById(R.id.foreignWordET);
        translationET = (EditText) findViewById(R.id.translationET);
        btnRename = (Button) findViewById(R.id.btnRename);

        btnSaveWord.setOnClickListener(this);
        btnSaveWordSet.setOnClickListener(this);
        btnRename.setOnClickListener(this);

        wordSet = new HashMap<String, String>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        wordSetTitle = data.getStringExtra("wordSetTitle");

        titleTW.setText(getString(R.string.creation_of_a_set) + " " + wordSetTitle);
    }

    @Override
    public void onClick(View view) {
        toast = Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        switch (view.getId()) {
            case R.id.btnRename :
                intent = new Intent(this, NameWordSetDialog.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnSaveWord :
                if(foreignET.getText() == null || foreignET.getText().toString().isEmpty() ||
                        translationET.getText() == null || translationET.getText().toString().isEmpty()){
                    toast.setText(getString(R.string.incorrect_value));
                    toast.show();
                    return;
                }
                wordSet.put(foreignET.getText().toString(), translationET.getText().toString());
                foreignET.setText("");
                translationET.setText("");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.btnSaveWordSet:
                if(wordSet.size() == 1){
                    toast.setText("Word set contains 1 element. Please, make more elements");
                    toast.show();
                    return;
                }
                if (DbService.getWordsSetId(this, wordSetTitle) != null) {
                    toast.setText("Set with same name already exists. Please, rename set");
                    toast.show();
                    return;
                }
                long wordSetId = DbService.insertNewWordSet(this, wordSetTitle);
                DbService.insertWordPairs(this, wordSet, wordSetId);

                intent = new Intent(this, WordSetCreatedDialog.class);
                startActivity(intent);
                finish();
                break;
        }

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
