package com.codepath.apps.mysimpletweets.activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;


public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.MyCustomFragmentListener {

 //   private TweetsListFragment fragmentTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapater(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from menu resource (res/menu/main)
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TimelineActivity","Compose Tweet");
        Intent tweetIntent = new Intent(this,TweetActivity.class);
        startActivity(tweetIntent);
        return super.onOptionsItemSelected(item);
    }

    public void onUserNameClick(View view) {
        Log.d("TimelineActivity", "onUserClick");
        TextView userNameView = (TextView)view.findViewById(R.id.tvUserName);
        openUserProfile((String)userNameView.getText());
    }

    @Override
    public void onOpenUserProfile(String username) {
        Log.d("TweetsListFragment", "open user name: " + username);
        openUserProfile(username);
    }

    public void openUserProfile(String username) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("screenName",username);
        startActivity(intent);
    }

    public class TweetsPagerAdapater extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            }
            return null;
        }

        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // return number of tabs
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    public void onProfileView(MenuItem menuItem) {
        Log.d("Timeline","onProfileView");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

}
