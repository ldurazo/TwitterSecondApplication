package com.internship.twittersecondapplication.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetListAdapter extends BaseAdapter {
    private static List<Tweet> tweetList;
    private Context mContext;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public TweetListAdapter(Context context, List<Tweet> results) {
        tweetList = results;
        mContext = context;
    }

    public int getCount() {
        return tweetList.size();
    }

    public Object getItem(int position) {
        return tweetList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_row_view, parent, false);
        TextView authorView = (TextView) rowView.findViewById(R.id.author);
        TextView tweetView = (TextView) rowView.findViewById(R.id.tweetText);
        authorView.setText(tweetList.get(position).getAuthor());
        tweetView.setText(tweetList.get(position).getTweetText());
        return rowView;
    }

}
