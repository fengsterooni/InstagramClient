package com.codepath.instagramclient.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.instagramclient.R;
import com.codepath.instagramclient.adapters.InstagramCommentAdapter;
import com.codepath.instagramclient.models.InstagramComment;
import com.codepath.instagramclient.models.InstagramPhoto;

import java.util.ArrayList;

public class PhotoComments extends ActionBarActivity {
    private InstagramCommentAdapter aComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_comments);
        InstagramPhoto photo = (InstagramPhoto) getIntent().getSerializableExtra("photo");
        ArrayList<InstagramComment> comments = photo.getComments();
        aComments = new InstagramCommentAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);
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
