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
import com.codepath.instagramclient.models.InstagramComment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramCommentAdapter extends ArrayAdapter<InstagramComment> {
    public InstagramCommentAdapter(Context context, List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final InstagramComment comment = getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder.profile = (ImageView) convertView.findViewById(R.id.ivComProfile);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.tvEachComment);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tvComTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(comment.profilePic).into(viewHolder.profile);
        viewHolder.comment.setText(Html.fromHtml(comment.comment));

        // Setup and display time
        String timeString = DateUtils.getRelativeTimeSpanString(comment.time * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        viewHolder.time.setText(timeString);

        return convertView;
    }

    private static class ViewHolder {
        ImageView profile;
        TextView time;
        TextView comment;
    }
}
