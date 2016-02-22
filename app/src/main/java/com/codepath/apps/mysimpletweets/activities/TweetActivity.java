package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweetActivity extends AppCompatActivity {
    private TwitterClient client;
    private EditText editText;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApplication.getRestClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText)findViewById(R.id.editText);

        // on some click or some loading we need to wait for...
        pb = (ProgressBar) findViewById(R.id.pbLoading);
    }

    public void onTweet(View view) {
        String tweetText = editText.getText().toString();
        Log.d("TweetActivity", "tweet: " + tweetText);
        // show the progress bar
        pb.setVisibility(ProgressBar.VISIBLE);

        client.sendTweet(tweetText, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                callBackToTimeline();
                // hide the progress bar
                pb.setVisibility(ProgressBar.INVISIBLE);
                Log.d("DEBUG", json.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // hide the progress bar
                pb.setVisibility(ProgressBar.INVISIBLE);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void callBackToTimeline() {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
    }
}
