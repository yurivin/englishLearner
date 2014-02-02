package com.example.wordslearner.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
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
public class EditWordsSet extends BaseActivity implements OnClickListener{

    String wordSetTitle;
    Integer wordsSetId;
    ListView lvEditWordSets;
    static final String FOREIGN_WORD = "foreign";
    static final String TRANSLATION = "translation";
    static final String WORD_ID = "word_id";
    String[] from = {WORD_ID, FOREIGN_WORD, TRANSLATION};
    int[] to = {R.id.wordIdTW, R.id.ewForeignTW, R.id.ewTranslationTW};
    SimpleAdapter sAdapter;
    ArrayList<Map<String, String>> data;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editwordsset);

        lvEditWordSets =(ListView)findViewById(R.id.lvEditWordsSet);
        lvEditWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        wordSetTitle = getIntent().getStringExtra("wordSetTitle");
        wordsSetId = DbService.getWordsSetId(this, wordSetTitle);
        refreshData();

        sAdapter = new SimpleAdapter(this, data, R.layout.threetextview, from, to);
        lvEditWordSets.setAdapter(sAdapter);

        intent = new Intent(this, EditWord.class);

        lvEditWordSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idView =(TextView) view.findViewById(R.id.wordIdTW);
                TextView foreignTW =(TextView) view.findViewById(R.id.ewForeignTW);
                TextView translationTW =(TextView) view.findViewById(R.id.ewTranslationTW);

                //Insert checking for null and empty string
                //Create a ValidationUtils class to use it here, in
                //wordsSetCreation view and others.

                intent.putExtra("wordId",idView.getText().toString());
                intent.putExtra("foreign",foreignTW.getText().toString());
                intent.putExtra("translation",translationTW.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRestart() {
        refreshData();
        sAdapter.notifyDataSetChanged();
    }

    private void refreshData() {
        List<Word> words = DbService.getWords(this, wordsSetId);
        data =
                new ArrayList<Map<String, String>> (words.size());
        Map<String, String> m;
        for(Word word : words) {
            m = new HashMap<String, String>();
            m.put(WORD_ID, String.valueOf(word.getId()));
            m.put(FOREIGN_WORD, word.getForeign());
            m.put(TRANSLATION, word.getTranslation());
            data.add(m);
        }
    }
}
