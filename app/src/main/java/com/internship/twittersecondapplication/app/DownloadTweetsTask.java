package com.internship.twittersecondapplication.app;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldurazo on 5/28/2014.
 */
public class DownloadTweetsTask extends AsyncTask<String, Void, String>{
    final ListActivity activity;
    List<Tweet> tweetList=null;
    final static String CONSUMER_KEY = "eBpO9tjcGNZHw9XPT9D44eiFG";
    final static String CONSUMER_SECRET = "469d4t56pGDRRMNnWPUSec6w7RmWOhzhiGuumq3IjZq6lKA2eA";
    //twitter token URL is used to send the credentials
    final static String twitterTokenURL = "https://api.twitter.com/oauth2/token";
    //twitter stream URL is used to get the requested json and info. could be a search, a user, etc.
    final static String twitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?q=";

    public DownloadTweetsTask(ListActivity activity) {
       this.activity = activity;
    }

    @Override
    protected String doInBackground(String... searchName){
        String jsonFile = getTwitterStream(searchName[0]);
        try {
            Bitmap mBitmap;
            JSONObject parentData = new JSONObject(jsonFile);
            tweetList = new ArrayList<Tweet>();
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
                //creating bitmap for imageurl
                URL url = new URL(tempUser.getString("profile_image_url"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mBitmap = BitmapFactory.decodeStream(input);
                tweet.setProfilePictureBitmap(mBitmap);
                //add the tweet object to the list
                tweetList.add(tweet);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFile;
    }


    protected void onPostExecute(String result){
        activity.setListAdapter(new TweetListAdapter(activity, tweetList));
    }


    private String getTwitterStream(String searchName) {
        String results = null;

        try {
            //Step 1: encode key and secret
            //URL encode the consumer key and secret
            String keyEncoded = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String secretEncoded = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

            //Concatenate the encoded key and consumer, separated by a colon.
            String encodedKeySecret = keyEncoded+":"+secretEncoded;

            //Encode the concatenated string in base64
            String base64Encoded = Base64.encodeToString(encodedKeySecret.getBytes(), Base64.NO_WRAP);

            //Obtain a bearer token (this is the authentication)
            //Code explanation: httpPost is created, i give it the right headers, researched at the twitter api.
            //then i continue to execute the getresponse method, which connects to twitter and validates keys
            HttpPost httpPost = new HttpPost(twitterTokenURL);
            httpPost.setHeader("Authorization", "Basic "+base64Encoded);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
            //String of authorization, executes the get response and connects to the client.
            String rawAuthorization = getResponseBody(httpPost);
            //from the string obtained by "get response body" i obtain the token and the access token.
            JSONObject auth = new JSONObject(rawAuthorization);
            String access_token = auth.getString("access_token");

            //time to get the json with the information we actually want.
            HttpGet httpGet = new HttpGet(twitterStreamURL+searchName+"&result_type=popular&count=5");
            httpGet.setHeader("Authorization", "Bearer " + access_token);
            httpGet.setHeader("Content-Type", "application/json");

            //this contains the abomination string with all the data i have to insert into a model
            results = getResponseBody(httpGet);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    private String getResponseBody(HttpRequestBase request) {
        StringBuilder stringBuilder = new StringBuilder();

        try{
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse response = httpClient.execute(request);
            // status code gives us if the connection was successful or so
            int statusCode = response.getStatusLine().getStatusCode();
            // reason tell us the text corresponding to the status obtained, without the int value status.
            String reason = response.getStatusLine().getReasonPhrase();

            if(statusCode==200){
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),8);
                String line = null;
                while ((line=bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
            } else {
                stringBuilder.append(reason);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}