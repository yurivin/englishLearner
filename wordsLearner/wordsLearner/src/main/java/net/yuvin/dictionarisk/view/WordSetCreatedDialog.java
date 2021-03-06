package net.yuvin.dictionarisk.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.yuvin.dictionarisk.R;

/**
 * Created by Yuriy on 23.01.14.
 */
public class WordSetCreatedDialog extends Activity implements View.OnClickListener {

    Button btnWordSetCreated;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordsetcreated);
        btnWordSetCreated = (Button) findViewById(R.id.btnWordSetCreated);
        btnWordSetCreated.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnWordSetCreated :
                intent = new Intent(this, AvailableWordSets.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
