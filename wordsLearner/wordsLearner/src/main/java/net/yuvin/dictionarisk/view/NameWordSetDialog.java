package net.yuvin.dictionarisk.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.utils.StandardOnEditActionListener;
import net.yuvin.dictionarisk.utils.ValidationUtils;

/**
 * Created by Юрий on 19.01.14.
 */

public class NameWordSetDialog extends Activity implements View.OnClickListener {

    Button btnSave;
    EditText wordSetTitleET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namewordsetdialog);

        btnSave = (Button) findViewById(R.id.btnSaveTitle);
        wordSetTitleET = (EditText) findViewById(R.id.wordSetTitleET);
        wordSetTitleET.setOnKeyListener(new StandardOnEditActionListener());

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveTitle:
                Intent intent = new Intent();
                if (!ValidationUtils.checkUserTextEmptiness(wordSetTitleET)) {
                    intent.putExtra("wordSetTitle", wordSetTitleET.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.type_title_here, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
