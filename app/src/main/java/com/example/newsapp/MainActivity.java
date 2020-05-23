package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static NewsAdapter newsAdapter;
    TextView textView;

     public static final String URL_REQUEST = "https://content.guardianapis.com/search?q=debates&api-key=test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<News> news = new ArrayList<News>();
        newsAdapter = new NewsAdapter(this,news);
        ListView listView = (ListView)findViewById(R.id.list_view);
        textView = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(textView);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news1 = newsAdapter.getItem(i);
                Uri uirnews = Uri.parse(news1.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,uirnews);
                startActivity(intent);
            }
        });
        if(isNetworkAvailable()){
       LoaderManager.getInstance(this).initLoader(1,null,this);
    }else {
            textView.setText("no internet");
        }
    }


    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(URL_REQUEST);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("tag","politics/politics");
        builder.appendQueryParameter("show-tags","contributor");
        return new NewsLoader(this,builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        textView.setText("no data found");
        newsAdapter.clear();
        if(data != null && !data.isEmpty()){
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;

    }
}
