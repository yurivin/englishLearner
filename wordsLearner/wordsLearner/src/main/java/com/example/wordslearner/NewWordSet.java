package com.example.wordslearner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Юрий on 19.01.14.
 */
public class NewWordSet extends Activity implements View.OnClickListener {

    TextView titleTW;
    EditText foreignET, translationET;
    Button btnSaveWord, btnSaveWordSet;
    Intent intent;
    Map<String, String> wordSet;

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
        translationET = (EditText) findViewById(R.id.translationTW);

        btnSaveWord.setOnClickListener(this);
        btnSaveWordSet.setOnClickListener(this);

        wordSet = new HashMap<String, String>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String wordSetTitle = data.getStringExtra("wordSetTitle");
        titleTW.setText(titleTW.getText() + " " + wordSetTitle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveWord :
                wordSet.put(foreignET.getText().toString(), translationET.getText().toString());
                break;
            case R.id.btnSaveWordSet :
                break;
        }
    }
}
