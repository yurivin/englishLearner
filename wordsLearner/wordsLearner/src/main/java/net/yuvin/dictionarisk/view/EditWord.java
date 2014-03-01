package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.dao.DbService;
import net.yuvin.dictionarisk.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юрий on 02.02.14.
 */
public class EditWord extends BaseActivity implements View.OnClickListener {

    EditText foreignET, translationET;
    Button saveBtn;
    Intent intent;
    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editword);
        foreignET = (EditText) findViewById(R.id.foreignWordET);
        translationET = (EditText) findViewById(R.id.translationET);
        saveBtn = (Button) findViewById(R.id.btnSaveEditedWord);
        saveBtn.setOnClickListener(this);

        intent = getIntent();
        foreignET.setText(intent.getStringExtra("foreign"));
        translationET.setText(intent.getStringExtra("translation"));
    }

    @Override
    public void onClick(View v) {
        toast = Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        if(ValidationUtils.checkUserTextEmptiness(foreignET) || ValidationUtils.checkUserTextEmptiness(translationET)){
            toast.setText(getString(R.string.incorrect_value));
            toast.show();
            return;
        }
        switch (v.getId()) {
            case R.id.btnSaveEditedWord:
                if (intent.getBooleanExtra("newWord", false) == true) {
                    Map<String, String> wordsSet = new HashMap<String, String>(1);
                    wordsSet.put(foreignET.getText().toString(), translationET.getText().toString());
                    DbService.insertWordPairs(this,  wordsSet, intent.getIntExtra("wordsSetId", -1));
                } else {
                    DbService.updateWord(this, intent.getStringExtra("wordId"), foreignET.getText().toString(), translationET.getText().toString());
                }
                finish();
                break;
        }
    }
}
