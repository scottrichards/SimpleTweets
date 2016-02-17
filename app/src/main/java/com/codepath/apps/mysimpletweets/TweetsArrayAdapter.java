package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by scottrichards on 11/22/15.
 */
// Turn the Tweet objects into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(Context context,List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1, tweets);
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
        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        lvProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(lvProfileImage);
        return convertView;
    }
}
