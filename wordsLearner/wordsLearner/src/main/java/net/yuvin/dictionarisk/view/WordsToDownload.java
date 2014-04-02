package net.yuvin.dictionarisk.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.model.WordSetProperties;

/**
 * Created by Юрий on 30.03.2014.
 */
public class WordsToDownload extends BaseActivity implements View.OnClickListener {

    private TextView twTitle;
    private Button btnAddWord, btnSend, dtnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordstodownload);

        twTitle = (TextView) findViewById(R.id.tvDownloadWordsTitle);
        btnAddWord = (Button)findViewById(R.id.btnNewWord);
        btnSend = (Button)findViewById(R.id.btnSend);
        dtnSave = (Button)findViewById(R.id.btnSave);

        WordSetProperties wordSetProperties = new WordSetProperties();
        wordSetProperties.setLanguageFrom(getIntent().getStringExtra("LanguageFrom"));
        wordSetProperties.setLanguageTo(getIntent().getStringExtra("LanguageTo"));
        Log.d("wordSetProperties", wordSetProperties.getLanguageFrom() + " " + wordSetProperties.getLanguageTo());
    }

    @Override
    public void onClick(View v) {

    }
}
