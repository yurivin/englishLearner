package net.yuvin.dictionarisk.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.dao.DbService;
import net.yuvin.dictionarisk.utils.ValidationUtils;

/**
 * Created by Юрий on 24.01.14.
 */
public class AvailableWordSets extends ContextMenuDeleteEditActivity {

    String[] wordSetNames;
    ListView lvWordSets;
    Intent intent;
    final int DIALOG_EXIT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablewordsets);
        lvWordSets = (ListView) findViewById(R.id.lvWordSets);
        lvWordSets.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        registerForContextMenu(lvWordSets);
        intent = new Intent(this, MainActivity.class);
        showWordsSets();
        lvWordSets.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("wordSetTitle", wordSetNames[position]);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteCM:
                DbService.deleteWordSet(this, wordSetNames[info.position]);
//                Log.d("deleted wordsSet : ", wordSetNames[info.position]);
                showWordsSets();
                break;
            case R.id.editCM:
//                Log.d("Words set to change", wordSetNames[info.position]);
                intent = new Intent(this, EditWordsSet.class);
                intent.putExtra("wordSetTitle", wordSetNames[info.position]);
                startActivity(intent);
                finish();
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void showWordsSets() {
        wordSetNames = DbService.getAllWordSetNames(this);
        if (!checkSetsExistence()) {
//            LogUtils.debugForCycleLog("Word set Names from db: ", wordSetNames);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordSetNames);
            lvWordSets.setAdapter(adapter);
        }
    }

    private boolean checkSetsExistence() {
        if (ValidationUtils.checkCollectionEmptyness(wordSetNames)) {
            intent = new Intent(this, NewWordSet.class);
            showDialog(DIALOG_EXIT);
            return true;
        }
        return false;
    }

    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_EXIT){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(R.string.no_wordsSets);
            adb.setPositiveButton(R.string.yes, dialogOnClickListner);
            adb.setNegativeButton(R.string.no, dialogOnClickListner);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener dialogOnClickListner = new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which){
            switch(which){
                case Dialog.BUTTON_POSITIVE :
                    startActivity(intent);
                    break;
                case Dialog.BUTTON_NEGATIVE :
                    break;
            }
            finish();
        }
    };

}
