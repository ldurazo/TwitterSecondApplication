package com.internship.twittersecondapplication.app;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldurazo on 5/28/2014.
 */
public class TabActivityCommon {
    private final ListActivity mActivity;

    public TabActivityCommon(ListActivity activity) {
        mActivity = activity;
    }

    public void updateListView(String result){
        try {
            JSONObject parentData = new JSONObject(result);
            List<Tweet> tweetList = new ArrayList<Tweet>();
            JSONArray jTweetList = parentData.getJSONArray("statuses");
            //Aux for the loop
            JSONObject tempObject;
            JSONObject tempUser;
            for(int i=0; i<jTweetList.length();i++){
                Tweet tweet = new Tweet();
                tempObject = jTweetList.getJSONObject(i);
                tweet.setTweetText(tempObject.getString("text"));
                tempUser = tempObject.getJSONObject("user");
                tweet.setAuthor(tempUser.getString("name"));
                tweetList.add(tweet);
            }
            mActivity.setListAdapter(new TweetListAdapter(mActivity, tweetList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}