package com.example.wordslearner.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import com.example.wordslearner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юрий on 18.02.14.
 */
public class HowTo extends BaseActivity {

    ExpandableListView expListView;
    ArrayList<Map<String, String>> groupData;
    ArrayList<Map<String, String>> childDataItem;
    ArrayList<ArrayList<Map<String, String>>> childData;
    Map<String, String> m;
    String[] howToTitles;
    String[] howToContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);
        expListView = (ExpandableListView) findViewById(R.id.howToELV);
        Resources res = getResources();
        howToTitles = res.getStringArray(R.array.howToTitles);
        howToContent = res.getStringArray(R.array.howToContent);
        String[] groupFrom = new String[]{"title"};
        int[] groupTo = new int[]{android.R.id.text1};
        String[] childFrom = new String[]{"content"};
        int[] childTo = new int[]{android.R.id.text1};

        groupData = new ArrayList<Map<String, String>>();
        for (String group : howToTitles) {
            m = new HashMap<String, String>();
            m.put("title", group);
            groupData.add(m);
        }

        childData = new ArrayList<ArrayList<Map<String, String>>>();
        for (String content : howToContent) {
            childDataItem = new ArrayList<Map<String, String>>();
            m = new HashMap<String, String>();
            m.put("content", content);
            childDataItem.add(m);
            childData.add(childDataItem);
        }

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);
        expListView.setAdapter(adapter);
    }


}
