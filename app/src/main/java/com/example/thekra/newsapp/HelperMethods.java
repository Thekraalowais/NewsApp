package com.example.thekra.newsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HelperMethods {
    private static final int READ_TIME = 10000;
    private static final int CONNECT_TIME = 15000;

    private HelperMethods() {

    }

    public static List<News> fetchData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e("fetchData", "Error to amke http request", e);
        }
        List<News> news = extractDataFromJson(jsonResponse);
        return news;
    }

    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createURL method", "prblem in build Url" + e);
        }
        return url;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonRequest = "";
        if (url == null) {
            return jsonRequest;
        }
        HttpURLConnection httpConnection = null;
        InputStream inputStream = null;

        try {
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(READ_TIME);
            httpConnection.setConnectTimeout(CONNECT_TIME);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == 200) {
                inputStream = httpConnection.getInputStream();
                jsonRequest = readInputStream(inputStream);
            } else {
                Log.e("makeHTTPRequest method", "Error response code" + httpConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("makeHTTPRequest method", "error retrive json result" + e);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonRequest;
    }

    private static List<News> extractDataFromJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        List<News> news = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONObject newsObject = jsonResponse.getJSONObject("response");
            JSONArray newsArray = newsObject.getJSONArray("results");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.optString("webTitle");
                String section = currentNews.optString("sectionName");
                String url = currentNews.optString("webUrl");
                News newArticle = new News(title, section, url);
                news.add(newArticle);
            }
        } catch (JSONException m) {
            Log.e("extractDataFromJson", "error parsing the json result", m);
        }
        return news;
    }

}
