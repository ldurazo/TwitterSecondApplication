package com.internship.twittersecondapplication.app;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ldurazo on 5/27/2014.
 */
public class MobileTabActivity extends ListActivity implements ListAdapterUpdater {
    TabActivityCommon mTabActivityCommon;
    DownloadTweetsTask mTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mTabActivityCommon = new TabActivityCommon(this);
        setContentView(R.layout.listlayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTask = new DownloadTweetsTask(this);
        mTask.execute("#Mobile");
    }

    @Override
    public void updateListAdapter(String jsonResponse) {
        mTabActivityCommon.updateListView(jsonResponse);
    }}
