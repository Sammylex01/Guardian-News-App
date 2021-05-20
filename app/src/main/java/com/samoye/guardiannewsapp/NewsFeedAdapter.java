package com.samoye.guardiannewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeed> {

    public NewsFeedAdapter (Context context, List<NewsFeed> newsFeedList){
        super(context, 0, newsFeedList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // check the existing view group is being reused, other wise inflate the view
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.newsfeed_list_item, parent, false);
        }

        //get the{@link word} object located at this position in the list
        NewsFeed currentNewsFeedList = getItem(position);

        TextView newsTitleTextView = (TextView) ListItemView.findViewById(R.id.title);
        newsTitleTextView.setText(currentNewsFeedList.getTitle());

        TextView newsSectionTextView = (TextView) ListItemView.findViewById(R.id.section);
        newsSectionTextView.setText(currentNewsFeedList.getSection());

        TextView newsDateTextView = (TextView) ListItemView.findViewById(R.id.date);
        newsDateTextView.setText(currentNewsFeedList.getDate());

        TextView newsUrlTextView = (TextView) ListItemView.findViewById(R.id.webUrl);
        newsUrlTextView.setText(currentNewsFeedList.getUrl());

        return ListItemView;
    }
}
