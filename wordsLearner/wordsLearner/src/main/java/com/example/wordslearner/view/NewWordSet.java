package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordslearner.DbHelper;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.WordSetsDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юрий on 19.01.14.
 */
public class NewWordSet extends Activity implements OnClickListener {

    TextView titleTW;
    EditText foreignET, translationET;
    Button btnSaveWord, btnSaveWordSet;
    Intent intent;
    Map<String, String> wordSet;
    DbHelper dbHelper;
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

        btnSaveWord.setOnClickListener(this);
        btnSaveWordSet.setOnClickListener(this);

        wordSet = new HashMap<String, String>();

        dbHelper = new DbHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        wordSetTitle = data.getStringExtra("wordSetTitle");
        titleTW.setText(titleTW.getText() + " " + wordSetTitle);
    }

    @Override
    public void onClick(View view) {
        toast = Toast.makeText(this, "word pair saved", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        switch (view.getId()) {
            case R.id.btnSaveWord:
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
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] args = new String[1];
                if (WordSetsDAO.getIdByTitle(this, wordSetTitle) != null) {
                    toast.setText("Set with same name already exists. Please, rename set");
                    toast.show();
                    return;
                }
                long wordSetId = WordSetsDAO.insertNewWordSet(this, wordSetTitle);

                ContentValues cv = new ContentValues();
                for (Map.Entry<String, String> entry : wordSet.entrySet()) {
                    cv.put("foreignW", entry.getKey());
                    cv.put("translation", entry.getValue());
                    cv.put("wordSetId", wordSetId);
                    db.insert("WordPairs", null, cv);
                }
                String tag = "myDB";
                Log.d(tag, " - - -   R o w s   i n   m y t a b l e :   - - - ");
                Cursor cursor = db.query("WordPairs", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idColIndex = cursor.getColumnIndex("id");
                    int foreignWColIndex = cursor.getColumnIndex("foreignW");
                    int translationColIndex = cursor.getColumnIndex("translation");
                    int wordSetIdColIndex = cursor.getColumnIndex("wordSetId");

                    do {
                        Log.d(tag,
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", foreignW = " + cursor.getString(foreignWColIndex) +
                                        ", translation = " + cursor.getString(translationColIndex) +
                                        ", wordSetId = " + cursor.getString(wordSetIdColIndex));
                    } while (cursor.moveToNext());
                } else {
                    Log.d(tag, "0 rows");
                }
                dbHelper.close();
                intent = new Intent(this, WordSetCreatedDialog.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
