package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by scottrichards on 2/27/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        Log.d("DEBUG", "getHomeTimeLine");
        //       pb.setVisibility(ProgressBar.VISIBLE);
        client.getHomeTimeLine(new JsonHttpResponseHandler(){
            @Override public  void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                adAll(Tweet.fromJSONArray(json));
//                client.lowest_id_received = findMinId();
                String jsonString = json.toString();
//                pb.setVisibility(ProgressBar.INVISIBLE);
                Log.d("DEBUG", jsonString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                //               pb.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }
}