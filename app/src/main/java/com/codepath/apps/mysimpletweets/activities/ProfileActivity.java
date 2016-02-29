package com.codepath.apps.mysimpletweets.activities;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // get the users screen Name
        String screenName = getIntent().getStringExtra("screenName");   // get screen name from Intent
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
        client.getUserInfo(screenName,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                user = User.fromJSON(response);
                getSupportActionBar().setTitle(user.getScreenName());
                populateProfileHeader(user);
            }
        });
        // Display User Fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer,userTimelineFragment);
        fragmentTransaction.commit();
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvTagname = (TextView)findViewById(R.id.tvTagname);
        TextView tvFollowers = (TextView)findViewById(R.id.followersText);
        TextView tvFollowing = (TextView)findViewById(R.id.followingText);
        ImageView imageView = (ImageView) findViewById(R.id.profileImageView);
        tvName.setText(user.getName());
        tvTagname.setText(user.getTagname());
        tvFollowing.setText(String.valueOf(user.getFollowingsCount()));
        tvFollowers.setText(String.valueOf(user.getFollowersCount()));
        Picasso.with(this).load(user.getProfileImageUrl()).into(imageView);
    }

}
