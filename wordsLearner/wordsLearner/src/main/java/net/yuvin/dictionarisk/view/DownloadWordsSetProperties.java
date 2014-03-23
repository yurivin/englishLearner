package net.yuvin.dictionarisk.view;

import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Юрий on 15.03.14.
 */
public class DownloadWordsSetProperties extends BaseActivity implements View.OnClickListener{

    private Button btnUpdateLangs, btnSelectLangFrom,
            btnSelectLangTo, btnDownloadTranslations,
            btnDownloadCollection, btnDownload;
    SharedPreferences preferences;
    WordSetProperties collectionProperties = new WordSetProperties();
    private static final String NO_LANGS_AVAILABLE = "";
    private static final String SELECTED_LANGUAGES = "here should be selected languages";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadwordssetproperties);

        btnDownloadTranslations = (Button)findViewById(R.id.downloadTranslations);
        btnDownloadTranslations.setOnClickListener(this);
        btnUpdateLangs = (Button)findViewById(R.id.btnUpdateLanguages);
        btnUpdateLangs.setOnClickListener(this);
        btnSelectLangFrom = (Button)findViewById(R.id.selectLanguageFrom);
        btnSelectLangFrom.setOnClickListener(this);
        btnSelectLangTo = (Button)findViewById(R.id.selectLanguageTo);
        btnSelectLangTo.setOnClickListener(this);
        btnDownloadCollection = (Button)findViewById(R.id.downloadCollection);
        btnDownloadCollection.setOnClickListener(this);
        btnDownload = (Button)findViewById(R.id.letDowload);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateLanguages:
                new YandexDictionaryJSONGetTask().execute();
                break;
            case R.id.downloadTranslations:
                btnDownload.setText(getString(R.string.lets_go) + ": " +
                        getString(R.string.download_Translations) +
                        getSetDetails());
                btnDownload.setVisibility(View.VISIBLE);
                break;
            case R.id.downloadCollection:
                btnDownload.setText(getString(R.string.lets_go) + ": " +
                        getString(R.string.select_Collection) +
                        getSetDetails());
                btnDownload.setVisibility(View.VISIBLE);
        }
    }

    private String getSetDetails() {
        return ". " + getString(R.string.For) +
                collectionProperties.getLanguageFrom().getTitle() + "-" +
                collectionProperties.getLanguageTo().getTitle();
    }

    private List<String> loadYandexLanguages() {
        preferences = getPreferences(MODE_PRIVATE);
        String yandexLanguages = preferences.getString("YandexLanguages", NO_LANGS_AVAILABLE);
        if(yandexLanguages == "") return null;
        return Arrays.asList(yandexLanguages.substring(1, yandexLanguages.length() - 1).split(", "));

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
            if (null != mClient)
                mClient.close();
            preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("YandexLanguages", result.toString());
            editor.commit();
        }

        private class JSONResponseHandler implements ResponseHandler<List<String>> {
            @Override
            public List<String> handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                List<String> result = new ArrayList<String>();
                String JSONResponse = new BasicResponseHandler()
                        .handleResponse(httpResponse);
                try {
//                    Log.d("JSON from Yandex", JSONResponse.toString());
                    JSONArray jsonArray = (JSONArray) new JSONTokener(
                            JSONResponse).nextValue();

                    for(int i = 0; i < jsonArray.length(); i++){
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
