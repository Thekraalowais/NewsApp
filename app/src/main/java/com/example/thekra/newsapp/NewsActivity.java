package com.example.thekra.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {
    private List<News> news;
    private static final String NEWS_URL = "http://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        news = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        adapter = new NewsAdapter(this, news);
        recyclerView.setAdapter(adapter);
        //the LayoutManager define the type of layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isConnected()) {
            getSupportLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }else{
            adapter.clear();
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String section=sharedPreferences.getString(getString(R.string.settings_section_key),getString(R.string.settings_section_default));
        Uri baseUri=Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder=baseUri.buildUpon();
        uriBuilder.appendQueryParameter("section",section);
        uriBuilder.appendQueryParameter("api-key","test");
        Log.v("SSSSSSSSSSSSss","ss" + uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        if (news != null){
            adapter.clear();
            adapter.AddAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

    public boolean isConnected() {
        ConnectivityManager conectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conectManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu){
            Intent settingIntent=new Intent(this,SettingActivity.class);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
