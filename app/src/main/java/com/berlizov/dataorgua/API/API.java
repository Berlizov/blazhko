package com.berlizov.dataorgua.API;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 350z6_000 on 05.11.2015.
 */
public abstract class API<T> extends AsyncTask<String, String, T> {
    Context context;

    public API(Context context) {
        this.context = context;
    }

    protected String getSiteURI() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("site_uri", context.getString(R.string.site_uri_default));
    }

    public String cyrillicNormalize(String in) {
        StringBuilder out = new StringBuilder();
        while (in.length() > 0) {
            int index = in.indexOf("\\u");
            if (index >= 0) {
                out.append(in.substring(0, index));
                String c = in.substring(index + 2, index + 6);
                int t0 = Integer.parseInt(c, 16);
                out.append(Character.toString((char) t0));
                in = in.substring(index + 6);
            } else {
                out.append(in);
                in = "";
            }
        }
        return out.toString();
    }
    public String normalize(String in) {
        StringBuilder out = new StringBuilder();
        boolean first = true;
        while (in.length() > 0) {
            int index = in.indexOf("\"");
            if (index >= 0) {
                out.append(in.substring(0, index));
                in = in.substring(index + 1);
                char c = in.charAt(0);
                if(first || c==':'||c==','||c==']'||c=='}') {
                    out.append("\"");
                    first=!first;
                }
            } else {
                out.append(in);
                in = "";
            }
        }
        return out.toString();
    }

    @Override
    protected T doInBackground(String... params) {
        String urlString = createQuery(params);
        try {
            String result = readQueryResult(urlString);
            result = cyrillicNormalize(result);
            result = normalize(result);
            return calcResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract T calcResult(String result) throws Exception;

    protected String readQueryResult(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        char[] buffer = new char[1024];

        int bytesRead = 0;
        Reader reader = new BufferedReader(
                new InputStreamReader(in, "UTF-8"));
        Writer writer = new StringWriter();
        while ((bytesRead = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, bytesRead);
        }
        in.close();
        return writer.toString();
    }

    protected abstract String createQuery(String... params);

    @Override
    protected void onPostExecute(T s) {
        super.onPostExecute(s);
        if (s != null) {
            successLoad(s);
        } else {
            ErrorLoad();
        }
    }

    protected abstract void successLoad(T s);

    protected abstract void ErrorLoad();
}
