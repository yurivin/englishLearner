package com.example.wordslearner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Юрий on 19.01.14.
 */

public class NameWordSetDialog extends Activity implements View.OnClickListener {

    Button btnSave;
    EditText wordSetTitleET;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namewordsetdialog);

        btnSave = (Button) findViewById(R.id.btnSaveTitle);
        wordSetTitleET = (EditText) findViewById(R.id.wordSetTitleET);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSaveTitle :
                Intent intent = new Intent();
                intent.putExtra("wordSetTitle", wordSetTitleET.getText().toString());
                setResult(RESULT_OK , intent);
                finish();
                break;
        }
    }
}
