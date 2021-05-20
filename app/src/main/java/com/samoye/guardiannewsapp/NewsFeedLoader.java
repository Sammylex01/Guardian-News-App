package com.samoye.guardiannewsapp;

import android.content.Context;

import android.content.AsyncTaskLoader;

import java.util.List;

public class NewsFeedLoader extends AsyncTaskLoader<List<NewsFeed>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsFeedLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsFeedLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsFeed> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<NewsFeed> news = QueryUtils.fetchNewsFeedList(mUrl);
        return news;
    }
}
