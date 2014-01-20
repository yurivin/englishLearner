package com.example.wordslearner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Юрий on 19.01.14.
 */
public class NewWordSet extends Activity {


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwordsset);
        intent = new Intent(this, NameWordSetDialog.class);
        startActivity(intent);
    }
}
