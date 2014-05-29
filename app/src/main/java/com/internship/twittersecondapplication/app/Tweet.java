package com.internship.twittersecondapplication.app;

import android.graphics.Bitmap;

/**
 * Created by ldurazo on 5/26/2014.
 */
public class Tweet {
    private String author = null;
    private String tweetText = null;
    private Bitmap profilePictureBitmap =null;

    public Bitmap getProfilePictureBitmap() {
        return profilePictureBitmap;
    }

    public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
        this.profilePictureBitmap = profilePictureBitmap;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
}
