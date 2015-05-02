package com.codepath.instagramclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.instagramclient.R;
import com.codepath.instagramclient.adapters.InstagramCommentAdapter;
import com.codepath.instagramclient.models.InstagramComment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoComments extends ActionBarActivity {
    public static final String CLIENT_ID = "4f491690c6444624876af2f639876a8a";
    private ArrayList<InstagramComment> comments;
    private InstagramCommentAdapter aComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_comments);

        Intent i = getIntent();
        String photo_id = i.getStringExtra("photoId");
        String profile = i.getStringExtra("profile");
        String caption = i.getStringExtra("caption");
        long time = i.getLongExtra("time", 0);

        if (caption != null) {
            TextView tvCaption = (TextView) findViewById(R.id.tvCaption);
            tvCaption.setText(Html.fromHtml(caption));
        }

        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
        Picasso.with(this).load(profile).into(ivProfile);

        TextView tvTime = (TextView) findViewById(R.id.tvTime);

        if (time != 0) {
            String timeString = DateUtils.getRelativeTimeSpanString(time * 1000,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            tvTime.setText(timeString);
        }

        comments = new ArrayList<>();
        aComments = new InstagramCommentAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        fetchPhotoComments(photo_id);
    }

    private void fetchPhotoComments(String photo_id) {

        String photoCommentsUrl = "https://api.instagram.com/v1/media/" + photo_id + "/comments?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(photoCommentsUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJSON = null;

                try {
                    commentsJSON = response.getJSONArray("data");
                    aComments.addAll(InstagramComment.fromJSONArray(commentsJSON));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
