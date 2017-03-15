package com.supinfo.gouvinb.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gouvinb on 15/03/2017.
 */

class MyAsyncTask extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "MyAsyncTask";

  @Override
  protected Void doInBackground(Void... params) {
    HttpsURLConnection httpsURLConnection = null;
    try {
      URL url = new URL("https://rec.api.appscho.com/demo/grades");

      httpsURLConnection = (HttpsURLConnection) url.openConnection();

      httpsURLConnection.setRequestMethod("GET");
      httpsURLConnection.setConnectTimeout(150000);

      httpsURLConnection.connect();

      if (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        String result;
        InputStream inputStream = httpsURLConnection.getInputStream();
        result = readStream(inputStream);

        JSONObject jsonObject = new JSONObject(result);
        result = jsonObject.toString(2);
        Log.i(TAG, "doInBackground: " + result);
      }
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    } finally {
      if (httpsURLConnection != null) {
        httpsURLConnection.disconnect();
      }
    }
    return null;
  }

  private String readStream(InputStream inputStream) throws IOException {
    StringBuilder result = new StringBuilder();
    InputStreamReader reader;
    reader = new InputStreamReader(inputStream, "UTF-8");
    char[] buffer = new char[1];
    while (reader.read(buffer) >= 0) {
      result.append(buffer);
      buffer = new char[1];
    }
    if (BuildConfig.DEBUG) {
      Log.d(TAG, result.toString());
    }

    return result.toString();
  }
}
