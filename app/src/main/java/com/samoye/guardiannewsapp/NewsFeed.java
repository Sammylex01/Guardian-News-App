package com.samoye.guardiannewsapp;

public class NewsFeed {
    public final String mTitle;

    public final String mSection;

    public final String mUrl;

    public final String mAuthor;

    public final String mDate;

    public NewsFeed(String title, String section, String author, String date, String url){
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
