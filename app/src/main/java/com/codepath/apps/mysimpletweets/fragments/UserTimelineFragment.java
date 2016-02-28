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
 * Created by scottrichards on 2/28/16.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragmentDemo = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        Log.d("DEBUG", "getHomeTimeLine");
        String screenName = getArguments().getString("screenName");
        //       pb.setVisibility(ProgressBar.VISIBLE);
        client.getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
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
