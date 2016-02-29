package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scottrichards on 2/27/16.
 */
public class TweetsListFragment extends Fragment implements TweetsArrayAdapter.MyCustomObjectListener {
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private long lowestId;
    private int currentPosition = 0;
    private ProgressBar pb;
    // for sending OpenProfile events back to the parent activity
    private MyCustomFragmentListener listener;

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface MyCustomFragmentListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onOpenUserProfile(String username);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomFragmentListener(MyCustomFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MyCustomFragmentListener) {
            listener = (MyCustomFragmentListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        pb = (ProgressBar)v.findViewById(R.id.pbLoading);
        pb.setVisibility(View.VISIBLE);
        lvTweets.setAdapter(aTweets);
        setupScrolling();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        aTweets.setCustomObjectListener(this);
    }

    public void adAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    // setupScrollng
    private void setupScrolling() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //              customLoadMoreDataFromApi(page);
                customLoadMoreDataFromApi();
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    // pass the OpenUserProfile upwards to parent activity
    @Override
    public void onOpenUserProfile(String username) {
        Log.d("TweetsListFragment", "open user name: " + username);
        if (listener != null) {
            listener.onOpenUserProfile(username);
        }
        // Code to handle object ready
    }

    public void customLoadMoreDataFromApi() {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        Log.d("DEBUG", "do something here");
        if (this instanceof HomeTimelineFragment) {
            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment)this;
            homeTimelineFragment.populateTimeline();
        } else if (this instanceof UserTimelineFragment) {
            UserTimelineFragment userTimelineFragment = (UserTimelineFragment)this;
            userTimelineFragment.populateTimeline();
        }
    }

    public void showProgressBar() {
        if (pb != null) {
            pb.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (pb!=null) {
            pb.setVisibility(View.GONE);
        }
    }

    // after adding tweets look up the min id
    public long findMinId() {
        for (int i=currentPosition;i<tweets.size();i++) {
            Tweet tweet = tweets.get(i);
            long id = tweet.getUid();
            if (id < lowestId || lowestId == 0) {
                lowestId = id;
            }
            currentPosition++;
        }
        return lowestId;
    }
}
