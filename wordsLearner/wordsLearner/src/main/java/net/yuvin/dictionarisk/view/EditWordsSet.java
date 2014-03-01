package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.dao.DbService;
import net.yuvin.dictionarisk.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuriy on 28.01.14.
 */
public class EditWordsSet extends ContextMenuDeleteEditActivity implements OnClickListener {

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
    Button btnNewWord, btnRename;
    TextView title;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editwordsset);

        title = (TextView) findViewById(R.id.editWordsSetTitleTV);
        btnNewWord = (Button) findViewById(R.id.btnNewWord);
        btnRename = (Button) findViewById(R.id.btnRenameSet);
        lvEditWordSets = (ListView) findViewById(R.id.lvEditWordsSet);
        lvEditWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        wordSetTitle = getIntent().getStringExtra("wordSetTitle");
        wordsSetId = DbService.getWordsSetId(this, wordSetTitle);
        data = new ArrayList<Map<String, String>>();
        refreshData();

        title.setText(getString(R.string.edit_words_set) + ": " + wordSetTitle);

        sAdapter = new SimpleAdapter(this, data, R.layout.threetextview, from, to);
        lvEditWordSets.setAdapter(sAdapter);
        registerForContextMenu(lvEditWordSets);

        intent = new Intent(this, EditWord.class);

        btnNewWord.setOnClickListener(this);
        btnRename.setOnClickListener(this);

        lvEditWordSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                editWord(position);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteCM:
                if(data.size() < 3){
                    Toast.makeText(this, R.string.no_delete_two_words, Toast.LENGTH_LONG).show();
                    return super.onContextItemSelected(item);
                }
                Map<String, String> word = data.get(info.position);
                DbService.deleteWord(this, word.get(WORD_ID));
                refreshLV();
                break;
            case R.id.editCM:
                    editWord(info.position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRenameSet:
                Intent renameIntent = new Intent(this, NameWordSetDialog.class);
                startActivityForResult(renameIntent, 3);
                break;
            case R.id.btnNewWord:
                Intent newWordIntent = new Intent(this, EditWord.class);
                newWordIntent.putExtra("wordsSetId", wordsSetId);
                newWordIntent.putExtra("newWord", true);
                startActivityForResult(newWordIntent, 4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && data != null) {
                title.setText(getString(R.string.edit_words_set) + ": " + data.getStringExtra("wordSetTitle"));
                DbService.renameWordsSet(this, wordsSetId, data.getStringExtra("wordSetTitle"));
        }
        refreshLV();
    }

    private void refreshLV() {
        refreshData();
        sAdapter.notifyDataSetChanged();
    }

    private void refreshData() {
        data.clear();
        List<Word> words = DbService.getWords(this, wordsSetId);
        Map<String, String> m;
        for (Word word : words) {
            m = new HashMap<String, String>();
            m.put(WORD_ID, String.valueOf(word.getId()));
            m.put(FOREIGN_WORD, word.getForeign());
            m.put(TRANSLATION, word.getTranslation());
            data.add(m);
        }
    }

    private void editWord(int position) {
        Map<String, String> word = data.get(position);
        intent.putExtra("wordId", word.get(WORD_ID));
        intent.putExtra("foreign", word.get(FOREIGN_WORD));
        intent.putExtra("translation", word.get(TRANSLATION));

        intent.putExtra("newWord", false);
        startActivityForResult(intent, 2);
    }
}
