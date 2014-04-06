package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.dao.DbService;
import net.yuvin.dictionarisk.model.WordSetProperties;
import net.yuvin.dictionarisk.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юрий on 30.03.2014.
 */
public class WordsToDownload extends BaseActivity implements View.OnClickListener {

    private TextView twTitle;
    private Button btnAddWord, btnSend, btnSave;
    private ListView lvWords;
    private SimpleAdapter sAdapter;
    static final String FOREIGN_WORD = "foreign";
    static final String TRANSLATION = "translation";
    private String[] from = {FOREIGN_WORD, TRANSLATION};
    int[] to = {R.id.ewForeignTW, R.id.ewTranslationTW};
    private ArrayList<Map<String, String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordstodownload);

        twTitle = (TextView) findViewById(R.id.tvDownloadWordsTitle);
        btnAddWord = (Button) findViewById(R.id.btnAddWord);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSave = (Button) findViewById(R.id.btnSave);
        lvWords = (ListView) findViewById(R.id.lvDownloadWordsSet);
        lvWords.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btnAddWord.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        WordSetProperties wordSetProperties = new WordSetProperties();
        wordSetProperties.setLanguageFrom(getIntent().getStringExtra("LanguageFrom"));
        wordSetProperties.setLanguageTo(getIntent().getStringExtra("LanguageTo"));
        twTitle.setText(twTitle.getText() + " From: " + wordSetProperties.getLanguageFrom() + " To: " + wordSetProperties.getLanguageTo());
        Log.d("wordSetProperties", wordSetProperties.getLanguageFrom() + " " + wordSetProperties.getLanguageTo());

        dataList = new ArrayList<Map<String, String>>();
        sAdapter = new SimpleAdapter(this, dataList, R.layout.threetextview, from, to);
        lvWords.setAdapter(sAdapter);
        lvWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddWord:
                Intent addTranslation = new Intent(this, AddTranslation.class);
                startActivityForResult(addTranslation, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            Map<String, String> entry = new HashMap<String, String>(2);
            entry.put(TRANSLATION, data.getStringExtra("word"));
            dataList.add(entry);
            sAdapter.notifyDataSetChanged();
        }

    }
}
