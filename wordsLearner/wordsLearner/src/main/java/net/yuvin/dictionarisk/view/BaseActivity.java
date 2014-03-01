package net.yuvin.dictionarisk.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.yuvin.dictionarisk.MainMenu;
import net.yuvin.dictionarisk.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        MainMenu mainMenu = new MainMenu(this);
        mainMenu.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

}
