package com.codepath.instagramclient.adapters;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagramclient.R;
import com.codepath.instagramclient.models.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.user = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.like = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.profile = (ImageView) convertView.findViewById(R.id.ivProfile);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.tvComments);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (photo.caption != null)
            viewHolder.caption.setText(Html.fromHtml(photo.caption));

        // Clear out the imageview
        viewHolder.image.setImageResource(0);

        viewHolder.like.setText("" + photo.likesCount + " likes");
        viewHolder.user.setText(photo.userName);
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.image);
        Picasso.with(getContext()).load(photo.profilePic).into(viewHolder.profile);

        int count = photo.comments.length;
        if (count > 1)
            viewHolder.comment.setText(Html.fromHtml(photo.comments[count - 1] + "\n" + photo.comments[count - 2]));
        else if (count == 1)
            viewHolder.comment.setText(Html.fromHtml(photo.comments[count-1]));

        // Setup and display time
        String timeString = DateUtils.getRelativeTimeSpanString(photo.time * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        // Log.i("INFO", timeString);
        int index = timeString.indexOf(' ');
        String shortTimeString = timeString.substring(0, index+2);
        // Log.i("INFO", shortTimeString);
        // tvTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.time, 0, 0, 0);
        viewHolder.time.setText(shortTimeString);

        return convertView;
    }

    private static class ViewHolder {
        ImageView profile;
        TextView caption;
        TextView user;
        TextView time;
        ImageView image;
        TextView like;
        TextView comment;
    }
}
