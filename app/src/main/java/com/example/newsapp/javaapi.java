package com.example.newsapp;


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

public class javaapi {
    public static URL CreateUrl(String Stringurl) {
        URL url = null;
        try {
            url = new URL(Stringurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static ArrayList<News> fetch(String requestURl) {
        URL url = CreateUrl(requestURl);
        String jsonresponse = null;

        try {
            jsonresponse = makehttprequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<News> news = extract(jsonresponse);
//        Log.e("hello",news.toString());
        return news;
    }

    public static String makehttprequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.e("responeseee", "coooo");

            } else {
                Log.e("hello", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("helloi", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        Log.e("respone", jsonResponse);
        return jsonResponse;


    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<News> extract(String newsjson) {
        if (TextUtils.isEmpty(newsjson)) {
            return null;
        }
        ArrayList<News> newss = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(newsjson);
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObject1.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject0 = jsonArray.getJSONObject(i);
                String pillarName = jsonObject0.getString("pillarName");
                String sectionName = jsonObject0.getString("sectionName");
                String webPublicationDate = jsonObject0.getString("webPublicationDate");
                String webTitle = jsonObject0.getString("webTitle");
                String url = jsonObject0.getString("webUrl");
                JSONArray jsonArray1 = jsonObject0.getJSONArray("tags");
                String  author = "";
                for(int j = 0; j< jsonArray1.length();j++){
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    author = jsonObject2.getString("firstName") + jsonObject2.getString("lastName");
                }
                if(author != "" ){
                    newss.add(new News(pillarName, webTitle, sectionName, webPublicationDate, url,author));
                }
                else {
                    newss.add(new News(pillarName, webTitle, sectionName, webPublicationDate, url));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newss;
    }

}
