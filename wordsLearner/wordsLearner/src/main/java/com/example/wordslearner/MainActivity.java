package com.example.wordslearner;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    TextView label;
    TextView englishWord;
    TextView translation;
    TextView scoreView;
    Button btnYes;
    Button btnNo;
    Map.Entry<String, String> word;
    int scoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WordsCollection.initializeWords();
        word = WordsService.setNewWord();

        englishWord = (TextView) findViewById(R.id.englishWord);
        label = (TextView) findViewById(R.id.label);
        translation = (TextView) findViewById(R.id.translation);
        scoreView = (TextView) findViewById(R.id.score);
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        setNewWord(word);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes :
                if(WordsCollection.getCurrentWord().getValue().contentEquals(word.getValue())) {
                    goodCase();
                } else {
                    badCase();
                }
                break;
            case R.id.no :
                if(!WordsCollection.getCurrentWord().getValue().contentEquals(word.getValue())) {
                    goodCase();
                } else {
                    badCase();
                }
                break;
        }
    }

    private void goodCase(){
        scoreView.setText("Score: " + scoreNum++);
        setNewWord(WordsService.setNewWord());
    }

    private void badCase(){
        scoreView.setText("Score: " + scoreNum--);
        setNewWord(WordsService.setNewWord());
    }

    private void setNewWord(Map.Entry<String, String> word) {
        englishWord.setText(word.getKey());
        translation.setText(word.getValue());
    }
}
