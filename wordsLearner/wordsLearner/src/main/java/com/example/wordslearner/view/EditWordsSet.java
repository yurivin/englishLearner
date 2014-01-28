package com.example.wordslearner.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.wordslearner.R;
import com.example.wordslearner.dao.DbService;
import com.example.wordslearner.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuriy on 28.01.14.
 */
public class EditWordsSet extends BaseActivity {

    String wordSetTitle;
    Integer wordsSetId;
    ListView lvEditWordSets;
    List<Word> words;
    static final String FOREIGN_WORD = "foreign";
    static final String TRANSLATION = "translation";
    static final String WORD_ID = "word_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editwordsset);
        lvEditWordSets =(ListView)findViewById(R.id.lvEditWordsSet);
        wordSetTitle = getIntent().getStringExtra("wordSetTitle");
        wordsSetId = DbService.getWordsSetId(this, wordSetTitle);
        words = DbService.getWords(this, wordsSetId);
        lvEditWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayList<Map<String, String>> data =
                new ArrayList<Map<String, String>> (words.size());
        Map<String, String> m;
        for(Word word : words) {
            m = new HashMap<String, String>();
            m.put(WORD_ID, String.valueOf(word.getId()));
            m.put(FOREIGN_WORD, word.getForeign());
            m.put(TRANSLATION, word.getTranslation());
            data.add(m);
        }

        String[] from = {WORD_ID, FOREIGN_WORD, TRANSLATION};
        int[] to = {R.id.wordIdTW, R.id.editForeignET, R.id.editTranslationET};
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.twoedittext, from, to);
        lvEditWordSets.setAdapter(sAdapter);
    }

}
