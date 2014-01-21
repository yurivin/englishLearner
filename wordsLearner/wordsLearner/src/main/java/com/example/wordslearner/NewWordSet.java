package com.example.wordslearner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Юрий on 19.01.14.
 */
public class NewWordSet extends Activity {

    TextView titleTW;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwordsset);
        Intent   intent = new Intent(this, NameWordSetDialog.class) ;
        startActivityForResult(intent, 1);

        titleTW = (TextView) findViewById(R.id.titleTW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){return;}
        String wordSetTitle = data.getStringExtra("wordSetTitle");
        titleTW.setText(titleTW.getText() + " " + wordSetTitle);
    }
}
