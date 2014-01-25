package com.example.wordslearner.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordslearner.DbHelper;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.WordSetsDAO;
import com.example.wordslearner.words.WordsService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {

    Intent intent;
    TextView englishWord, translation, scoreView;
    Button btnYes, btnNo;
    Toast toast;
    int scoreNum;
    DbHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(!WordsService.isInitialised()){
            WordsService.initializeWords();
        }
        scoreNum = 0;

        englishWord = (TextView) findViewById(R.id.foreignWord);
        translation = (TextView) findViewById(R.id.translation);
        scoreView = (TextView) findViewById(R.id.score);
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        scoreView.setText("Score: " + scoreNum);

        if (getIntent() != null) {
            intent = getIntent();
            if (intent.getExtras() != null) {
                if (intent.getExtras().containsKey("wordSetTitle")) {
                    String wordSetName = intent.getStringExtra("wordSetTitle");

                    String[] args = new String[]{WordSetsDAO.getIdByTitle(this, wordSetName).toString()};
                    dbHelper = new DbHelper(this);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    cursor = db.rawQuery("SELECT foreignW, translation FROM WordPairs WHERE wordSetId = ?", args);
                    int foreignWColIndex = cursor.getColumnIndex("foreignW");
                    int translationColIndex = cursor.getColumnIndex("translation");
                    Map<String, String> words = new HashMap<String, String>();
                    if (cursor.moveToFirst()) {
                        do {
                            String foreignW = cursor.getString(foreignWColIndex);
                            String translation = cursor.getString(translationColIndex);
                            words.put(foreignW, translation);
                        } while (cursor.moveToNext());
                        WordsService.initializeWords(words);
                    }
                    dbHelper.close();
                }
            }
        }
        setNewWord(WordsService.getNewWord());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.availableWordSetsMI:
                intent = new Intent(this, AvailableWordSets.class);
                startActivity(intent);
                finish();
                break;
            case R.id.downloadWordSetMI:
                break;
            case R.id.createWordSetMI:
                intent = new Intent(this, NewWordSet.class);
                startActivity(intent);
                finish();
                break;
            case R.id.learningResultsMI:
                break;
            case R.id.optionsMI:
                break;
            case R.id.exitMI:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yes:
                trueOrFalse(true);
                break;
            case R.id.no:
                trueOrFalse(false);
                break;
        }

    }

    private void trueOrFalse(boolean trueOtFalse) {
        if (WordsService.getCurrentWord().getValue().equals(translation.getText()) == trueOtFalse) {
            goodCase();
        } else {
            badCase();
        }
        setNewWord(WordsService.getNewWord());
    }

    private void goodCase() {
        scoreView.setText(getString(R.string.score) + " " + (++scoreNum));
        toast.setText(R.string.excellent_score);
        toast.show();
    }

    private void badCase() {
        scoreView.setText(getString(R.string.score) + " " + (--scoreNum));
        toast.setText(getString(R.string.not_that_case)
                + WordsService.getCurrentWord().getValue());
        toast.show();
    }

    private Map.Entry<String, String> setNewWord(Map.Entry<String, String> word) {
        englishWord.setText(word.getKey());
        translation.setText(word.getValue());

        if (new Random().nextInt(2) + Calendar.getInstance().getTimeInMillis() % 2 == 1)
            translation.setText(WordsService.getRandomValue());

        Log.d("UI word", word.toString());
        Log.d("WordsCollection word", WordsService.getCurrentWord().toString());
        return word;
    }
}