package net.yuvin.dictionarisk.view;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.yuvin.dictionarisk.R;
import net.yuvin.dictionarisk.model.WordSetProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.*;

/**
 * Created by Юрий on 15.03.14.
 */
public class DownloadWordsSetProperties extends BaseActivity implements View.OnClickListener {

    private enum DownloadActions {
        Translations,
        ReadyCollection;
    }
    private ProgressDialog pDialog;
    private Spinner spLanguageFrom, spLanguageTo;
    private Button btnDownloadTranslations,
            btnDownloadCollection, btnDownload;
    private TextView twSelectLanguageFrom, twLanguageTo;
    WordSetProperties wordSetProperties = new WordSetProperties();
    protected Map<String, List<String>> languages = new HashMap<String, List<String>>();
    private static final String RUISO1 = "ru";
    private  DownloadActions action;
    String[] data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.downloading_languages);
        pDialog.show();
        new YandexDictionaryJSONGetTask().execute();
        setContentView(R.layout.downloadwordssetproperties);

        btnDownloadTranslations = (Button) findViewById(R.id.downloadTranslations);
        btnDownloadTranslations.setOnClickListener(this);
        btnDownloadCollection = (Button) findViewById(R.id.downloadCollection);
        btnDownloadCollection.setOnClickListener(this);
        btnDownload = (Button) findViewById(R.id.letDowload);
        btnDownload.setOnClickListener(this);
        spLanguageFrom = (Spinner) findViewById(R.id.spLanguageFrom);
        spLanguageTo = (Spinner) findViewById(R.id.spLanguageTo);
        spLanguageFrom.setVisibility(View.GONE);
        spLanguageTo.setVisibility(View.GONE);

        twSelectLanguageFrom = (TextView) findViewById(R.id.twSelectYourLang);
        twLanguageTo = (TextView) findViewById(R.id.twselectLanguageTo);
        twSelectLanguageFrom.setText(getString(R.string.downloading_languages));
        twLanguageTo.setVisibility(View.GONE);

        spLanguageFrom.setOnItemSelectedListener(new OnItemSelectedListenerFrom());
        spLanguageTo.setOnItemSelectedListener(new OnItemSelectedListenerTo());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadTranslations:
                action = DownloadActions.Translations;
                checkDownloadConditions();
                break;
            case R.id.downloadCollection:
                action = DownloadActions.ReadyCollection;
                checkDownloadConditions();
        }
    }

    private void setSpinnerLanguages() {
       if(languages.containsKey(RUISO1)) {
        data = new String[languages.get(RUISO1).size()];
        for (int i = 0; i < languages.get(RUISO1).size(); i++) {
            data[i] = languages.get(RUISO1).get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        spLanguageFrom.setAdapter(adapter);
        spLanguageTo.setAdapter(adapter);
        spLanguageFrom.setPrompt(getString(R.string.selectYourLanguage));
        spLanguageTo.setPrompt(getString(R.string.selectLanguageToLearn));
        spLanguageFrom.setVisibility(View.VISIBLE);
        spLanguageTo.setVisibility(View.VISIBLE);
        twLanguageTo.setVisibility(View.VISIBLE);
        twSelectLanguageFrom.setText(R.string.selectYourLanguage);
       } else {
           twSelectLanguageFrom.setText(R.string.error_downloading_languages);
       }
    }

    private void checkDownloadConditions(){
        if(wordSetProperties.getLanguageFrom().equals(WordSetProperties.QUESTION)) {
            return;
        } else if(wordSetProperties.getLanguageTo().equals(WordSetProperties.QUESTION)){
            return;
        } else if( action == null) {
           return;
        }
        if(action == DownloadActions.Translations) {
            btnDownload.setText(getString(R.string.lets_go) + ": " +
                    getString(R.string.download_Translations) +
                    getSetDetails());
        } else if(action == DownloadActions.ReadyCollection) {
            btnDownload.setText(getString(R.string.lets_go) + ": " +
                    getString(R.string.select_Collection) +
                    getSetDetails());
        }
        btnDownload.setVisibility(View.VISIBLE);
    }

    private String getSetDetails() {
        return ". " + getString(R.string.For) +
                wordSetProperties.getLanguageFrom() + "-" +
                wordSetProperties.getLanguageTo();
    }

    private class OnItemSelectedListenerFrom implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            wordSetProperties.setLanguageFrom(data[position]);
            checkDownloadConditions();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    private class OnItemSelectedListenerTo implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            wordSetProperties.setLanguageTo(data[position]);
            checkDownloadConditions();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class YandexDictionaryJSONGetTask extends AsyncTask<Void, Void, List<String>> {

        private static final String YANDEX_DICTIONARY_API_KEY = "dict.1.1.20140314T164415Z.e4c3f91f1f2b3c01.eee844445beea5d33ba6bbd5382b61bdf8614035";

        private static final String URL = "https://dictionary.yandex.net/api/v1/dicservice.json/getLangs?key=" + YANDEX_DICTIONARY_API_KEY;

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

        @Override
        protected List<String> doInBackground(Void... params) {
            HttpGet request = new HttpGet(URL);
            JSONResponseHandler responseHandler = new JSONResponseHandler();
            try {
                return mClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (null != mClient) {
                mClient.close();
            }
            if (result != null) {
                parseLanguages(result);
                setSpinnerLanguages();
            }
            Log.d("Languages in collection", languages.toString());
            pDialog.dismiss();
        }

        private void parseLanguages(List<String> result) {
            String[] splitResult;
            for (String entry : result) {
                splitResult = entry.split("-", 2);
                if (languages.containsKey(splitResult[0])) {
                    languages.get(splitResult[0]).add(splitResult[1]);
                } else {
                    List<String> values = new ArrayList<String>();
                    values.add(splitResult[1]);
                    languages.put(splitResult[0], values);
                }
            }
        }

        private class JSONResponseHandler implements ResponseHandler<List<String>> {
            @Override
            public List<String> handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                List<String> result = new ArrayList<String>();
                String JSONResponse = new BasicResponseHandler()
                        .handleResponse(httpResponse);
                try {
                    Log.d("JSON from Yandex", JSONResponse.toString());
                    JSONArray jsonArray = (JSONArray) new JSONTokener(
                            JSONResponse).nextValue();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        result.add(jsonArray.getString(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
    }
}

