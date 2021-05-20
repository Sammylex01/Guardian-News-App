package com.samoye.guardiannewsapp;

public class NewsFeed {
    public final String mTitle;

    public final String mSection;

    public final String mUrl;

    public final int mDate;

    public NewsFeed(String title, String section, int date, String url){
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getDate() {
        return mDate;
    }
}
