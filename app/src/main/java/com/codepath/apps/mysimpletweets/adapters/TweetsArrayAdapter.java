package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by scottrichards on 11/22/15.
 */
// Turn the Tweet objects into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(Context context,List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1, tweets);
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            relativeDate = relativeDate.replaceFirst("hours ago","h");
            relativeDate = relativeDate.replaceFirst("hour ago","h");
            relativeDate = relativeDate.replaceFirst("minute ago","m");
            relativeDate = relativeDate.replaceFirst("minutes ago","m");
            relativeDate = relativeDate.replaceFirst("days ago","d");
            relativeDate = relativeDate.replaceFirst("day ago","d");
            relativeDate = relativeDate.replaceFirst("seconds ago","s");
            relativeDate = relativeDate.replaceFirst("second ago","s");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView lvProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
        TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView)convertView.findViewById(R.id.timeAgo);
        tvBody.setText(tweet.getBody());
        tvUserName.setText("@" + tweet.getUser().getScreenName());
        String createdAt = tweet.getCreatedAt();
        String relativeTimeAgo = getRelativeTimeAgo(createdAt);
        tvTime.setText(relativeTimeAgo);
        tvName.setText(tweet.getUser().getName());
        lvProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(lvProfileImage);
        return convertView;
    }
}
