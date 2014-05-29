package com.internship.twittersecondapplication.app;


import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by ldurazo on 5/27/2014.
 */
public class AndroidTabActivity extends ListActivity {
    DownloadTweetsTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listlayout);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // fetch the Android changes

        mTask = new DownloadTweetsTask(this);
        mTask.execute("#Android");
    }

}
