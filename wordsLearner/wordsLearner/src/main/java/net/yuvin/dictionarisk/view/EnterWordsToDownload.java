package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import net.yuvin.dictionarisk.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Юрий on 20.03.2014.
 */
public class EnterWordsToDownload extends ContextMenuDeleteEditActivity implements View.OnClickListener{

    ListView lvEditWordSets;
    static final String FOREIGN_WORD = "foreign";
    static final String TRANSLATION = "translation";
    static final String WORD_ID = "word_id";
    String[] from = {WORD_ID, FOREIGN_WORD, TRANSLATION};
    int[] to = {R.id.wordIdTW, R.id.ewForeignTW, R.id.ewTranslationTW};
    SimpleAdapter sAdapter;
    ArrayList<Map<String, String>> data;
    Button btnNewWord;
    Intent intent;

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.enterwordstodownload);

         intent = new Intent(this, NameWordSetDialog.class);
         startActivityForResult(intent, 1);

         btnNewWord = (Button) findViewById(R.id.btnNewWord);
         lvEditWordSets = (ListView) findViewById(R.id.lvEditWordsSet);
         lvEditWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
         data = new ArrayList<Map<String, String>>();

         sAdapter = new SimpleAdapter(this, data, R.layout.threetextview, from, to);
         lvEditWordSets.setAdapter(sAdapter);
         registerForContextMenu(lvEditWordSets);

         btnNewWord.setOnClickListener(this);

         lvEditWordSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                 editWord(position);
             }
         });
    }

    private void editWord(int position){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteCM:

                break;
            case R.id.editCM:
                editWord(info.position);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
