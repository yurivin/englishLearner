package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.dao.DbService;
import net.yuvin.dictionarisk.words.WordsService;
import com.google.android.gms.ads.*;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class MainActivity extends BaseActivity implements OnClickListener {

    private AdView adView;
    private static final String MY_AD_UNIT_ID = "ca-app-pub-4177483396427496/5919715969";
    private Intent intent;
    private TextView englishWord, translation, scoreView;
    private Button btnYes, btnNo, btnHelp;
    private FrameLayout container;
    private Toast toast;
    private int scoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (!WordsService.isInitialised()) {
            WordsService.initializeWords();
        }
        scoreNum = 0;

        container = (FrameLayout) findViewById(R.id.container);
        englishWord = (TextView) findViewById(R.id.foreignWord);
        translation = (TextView) findViewById(R.id.translation);
        scoreView = (TextView) findViewById(R.id.score);
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);
        btnHelp = (Button) findViewById(R.id.helpBtn);

        adView = new AdView(this);
        adView.setAdUnitId(MY_AD_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);
        container.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        scoreView.setText("Score: " + scoreNum);

        if (getIntent() != null) {
            intent = getIntent();
            if (intent.getExtras() != null) {
                if (intent.getExtras().containsKey("wordSetTitle")) {
                    String wordsSetTitle = intent.getStringExtra("wordSetTitle");
                    WordsService.initializeWords(DbService.getWordPairs(this, wordsSetTitle));
                }
            }
        }
        setNewWord(WordsService.getNewWord());
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
            case R.id.helpBtn:
                intent = new Intent(this, HowTo.class);
                startActivity(intent);
                finish();
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
                + " " + WordsService.getCurrentWord().getValue());
        toast.show();
    }

    private Map.Entry<String, String> setNewWord(Map.Entry<String, String> word) {
        englishWord.setText(word.getKey());
        translation.setText(WordsService.getRandomValue());

        if (new Random().nextInt(2) + Calendar.getInstance().getTimeInMillis() % 2 == 1 &&
                new Random().nextInt(2) + Calendar.getInstance().getTimeInMillis() % 2 == 1)
            translation.setText(word.getValue());

//        Log.d("UI word", word.toString());
//        Log.d("WordsCollection word", WordsService.getCurrentWord().toString());
        return word;
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}
