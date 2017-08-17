package com.example.thekra.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String Nurl;

    public NewsLoader(Context context, String url) {
        super(context);
        this.Nurl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(Nurl==null){
            return null;
        }
        List<News> news =HelperMethods.fetchData(Nurl);
        return news;
    }

}
