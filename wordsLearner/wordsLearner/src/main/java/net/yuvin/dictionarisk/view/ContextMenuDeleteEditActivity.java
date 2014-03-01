package net.yuvin.dictionarisk.view;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import net.yuvin.dictionarisk.R;

/**
 * Created by Юрий on 16.02.14.
 */
public class ContextMenuDeleteEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        switch (view.getId()) {
            case R.id.lvWordSets:
            case R.id.lvEditWordsSet:
                getMenuInflater().inflate(R.menu.wordsetslist, menu);
                break;
        }
    }

}
