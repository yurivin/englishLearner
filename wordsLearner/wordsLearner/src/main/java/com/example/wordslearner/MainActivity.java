package com.example.wordslearner;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.Random;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    TextView englishWord;
    TextView translation;
    TextView scoreView;
    Button btnYes;
    Button btnNo;
    int scoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WordsCollection.initializeWords();
        scoreNum = 0;

        englishWord = (TextView) findViewById(R.id.englishWord);
        translation = (TextView) findViewById(R.id.translation);
        scoreView = (TextView) findViewById(R.id.score);
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        scoreView.setText("Score: " + scoreNum);
        setNewWord(WordsService.getNewWord());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yes :
                if(WordsCollection.getCurrentWord().getValue().equals(translation.getText())) {
                    goodCase();
                } else {
                    badCase();
                }
                break;
            case R.id.no :
                if(WordsCollection.getCurrentWord().getValue().equals(translation.getText())) {
                    badCase();
                } else {
                    goodCase();
                }
                break;
        }
        setNewWord(WordsService.getNewWord());
    }

    private void goodCase(){
        scoreView.setText("Score: " + (++scoreNum));
    }

    private void badCase(){
        scoreView.setText("Score: " + (--scoreNum));
    }

    private Map.Entry<String, String> setNewWord(Map.Entry<String, String> word) {
        Random randomGen = new Random();

        englishWord.setText(word.getKey());
        translation.setText(word.getValue());

        if(randomGen.nextInt(2) == 1)
             translation.setText(WordsCollection.getRandomValue());

        Log.d("UI word", word.toString());
        Log.d("WordsCollection word", WordsCollection.getCurrentWord().toString());
        return word;
    }
}
