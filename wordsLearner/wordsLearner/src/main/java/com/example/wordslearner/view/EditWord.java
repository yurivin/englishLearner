package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.DbService;

/**
 * Created by Юрий on 02.02.14.
 */
public class EditWord extends Activity implements View.OnClickListener{

    EditText foreignET, translationET;
    Button saveBtn;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.editword);
        foreignET = (EditText) findViewById(R.id.foreignWordET);
        translationET = (EditText) findViewById(R.id.translationET);
        saveBtn = (Button) findViewById(R.id.btnSaveEditedWord);
        saveBtn.setOnClickListener(this);

        intent = getIntent();
        foreignET.setText(intent.getStringExtra("foreign"));
        translationET.setText(intent.getStringExtra("translation"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSaveEditedWord :
                DbService.updateWord(this, intent.getStringExtra("wordId"), foreignET.getText().toString(),  translationET.getText().toString());
                finish();
                break;
        }
    }
}