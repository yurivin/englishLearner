package com.example.wordslearner;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import com.example.wordslearner.view.AvailableWordSets;
import com.example.wordslearner.view.MainActivity;
import com.example.wordslearner.view.NewWordSet;

/**
 * Created by Юрий on 27.01.14.
 */
public class MainMenu {

    Activity activity;
    Intent intent;


    public MainMenu(Activity activity){
        this.activity = activity;
    }

    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.availableWordSetsMI:
                intent = new Intent(activity, AvailableWordSets.class);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.downloadWordSetMI:
                break;
            case R.id.createWordSetMI:
                intent = new Intent(activity, NewWordSet.class);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.learningResultsMI:
                break;
            case R.id.optionsMI:
                break;
            case R.id.LearnMI:
                intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
                break;
        }
    }
}
