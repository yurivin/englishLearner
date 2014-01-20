package com.example.wordslearner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordslearner.services.WordsService;
import com.example.wordslearner.words.WordsCollection;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {

    Intent intent;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.availableWordSetsMI :
                break;
            case R.id.downloadWordSetMI :
                break;
            case R.id.createWordSetMI :
                intent = new Intent(this, NewWordSet.class);
                startActivity(intent);
                break;
            case R.id.learningResultsMI :
                break;
            case R.id.optionsMI :
                break;
            case R.id.exitMI :
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yes :
                trueOrFalse(true);
                break;
            case R.id.no :
                trueOrFalse(false);
                break;
        }

    }

    private void trueOrFalse(boolean trueOtFalse) {
        if(WordsCollection.getCurrentWord().getValue().equals(translation.getText()) == trueOtFalse) {
            goodCase();
        } else {
            badCase();
        }
        setNewWord(WordsService.getNewWord());
    }

    private void goodCase(){
        scoreView.setText("Score: " + (++scoreNum));
        Toast.makeText(this, "Excellent! Score: +1", Toast.LENGTH_SHORT).show();
    }

    private void badCase(){
        scoreView.setText("Score: " + (--scoreNum));
        Toast.makeText(this, "Not that case. Score: -1 Answer is: "
                + WordsCollection.getCurrentWord().getValue(), Toast.LENGTH_LONG).show();
    }

    private Map.Entry<String, String> setNewWord(Map.Entry<String, String> word) {
        englishWord.setText(word.getKey());
        translation.setText(word.getValue());

        if(new Random().nextInt(2) + Calendar.getInstance().getTimeInMillis() % 2 == 1)
             translation.setText(WordsService.getRandomValue());

        Log.d("UI word", word.toString());
        Log.d("WordsCollection word", WordsService.getCurrentWord().toString());
        return word;
    }
}
