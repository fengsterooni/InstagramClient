package com.codepath.instagramclient.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.codepath.instagramclient.R;

public class VideoPlayerActivity extends ActionBarActivity implements MediaPlayer.OnCompletionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();
        VideoView v = (VideoView) findViewById(R.id.vvVideo);

        String url = null;
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("videoUrl");

            if (url != null) {
                v.setMediaController(new MediaController(this));
                v.setOnCompletionListener(this);
                v.setVideoURI(Uri.parse(url));
                v.start();
            }
        }

        if (url == null) {
            throw new IllegalArgumentException("Must set url extra paremeter in intent.");
        }
    }

    @Override
    public void onCompletion(MediaPlayer v) {
        finish();
    }

    //Convenience method to show a video
    public static void showRemoteVideo(Context ctx, String url) {
        Intent i = new Intent(ctx, VideoPlayerActivity.class);

        i.putExtra("url", url);
        ctx.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_player, menu);
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
