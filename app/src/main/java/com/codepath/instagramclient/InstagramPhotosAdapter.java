package com.codepath.instagramclient;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        if (photo.caption != null)
            tvCaption.setText(Html.fromHtml(photo.caption));

        // Clear out the imageview
        ivPhoto.setImageResource(0);

        tvLikes.setText("" + photo.likesCount + " likes");
        tvUsername.setText(photo.userName);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profilePic).into(ivProfile);

        int count = photo.comments.length;
        if (count > 1)
            tvComments.setText(Html.fromHtml(photo.comments[count - 1] + "\n" + photo.comments[count - 2]));
        else if (count == 1)
            tvComments.setText(Html.fromHtml(photo.comments[count-1]));

        // Setup and display time
        String timeString = DateUtils.getRelativeTimeSpanString(photo.time * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        // Log.i("INFO", timeString);
        int index;
        int from = 0;
        if (timeString.startsWith("in"))
            from = 2;
        index = timeString.indexOf(' ', from);
        String shortTimeString = timeString.substring(0, from + index+2);
        // Log.i("INFO", shortTimeString);
        // tvTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.time, 0, 0, 0);
        tvTime.setText(shortTimeString);

        return convertView;
    }
}
