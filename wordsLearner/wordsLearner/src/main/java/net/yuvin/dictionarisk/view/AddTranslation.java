package net.yuvin.dictionarisk.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.utils.ValidationUtils;


/**
 * Created by Юрий on 06.04.2014.
 */
public class AddTranslation extends EditWord implements View.OnClickListener{
    TextView tvforeignWord, tvTranslation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foreignET.setVisibility(View.GONE);
        tvforeignWord =  (TextView)findViewById(R.id.foreignWordTV);
        tvforeignWord.setVisibility(View.GONE);
        tvTranslation = (TextView)findViewById(R.id.translationTV);
        tvTranslation.setText(getString(R.string.enterTranslationToDownloadForeignWord));
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(ValidationUtils.checkUserTextEmptiness(translationET)){
            Toast.makeText(this,getString(R.string.incorrect_value), Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.btnSaveEditedWord:
                Intent intent = new Intent();
                intent.putExtra("word", translationET.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}
