package net.yuvin.dictionarisk.view;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.util.*;

/**
 * Created by Юрий on 15.03.14.
 */
public class DownloadWordsSetProperties extends BaseActivity implements View.OnClickListener {

    private Button btnSelectLangFrom,
            btnSelectLangTo, btnDownloadTranslations,
            btnDownloadCollection, btnDownload;
    WordSetProperties collectionProperties = new WordSetProperties();
    protected Map<String, List<String>> languages = new HashMap<String, List<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new YandexDictionaryJSONGetTask().execute();
        setContentView(R.layout.downloadwordssetproperties);

        btnDownloadTranslations = (Button) findViewById(R.id.downloadTranslations);
        btnDownloadTranslations.setOnClickListener(this);
        btnDownloadCollection = (Button) findViewById(R.id.downloadCollection);
        btnDownloadCollection.setOnClickListener(this);
        btnDownload = (Button) findViewById(R.id.letDowload);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                collectionProperties.getLanguageFrom() + "-" +
                collectionProperties.getLanguageTo();
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
            parseLanguages(result);
            Log.d("Languages in collection", languages.toString());
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
                    languages.put(splitResult[0],values);
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

