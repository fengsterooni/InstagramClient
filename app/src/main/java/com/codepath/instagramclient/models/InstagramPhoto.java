package com.codepath.instagramclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstagramPhoto {
    public String userName;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public String profilePic;
    public String[] comments;
    public long time;

    public InstagramPhoto(JSONObject json) {
        try {
            this.userName = json.getJSONObject("user").getString("username");
            this.profilePic = json.getJSONObject("user").getString("profile_picture");

            if (!json.isNull("caption"))
                this.caption = "<b><font color='#3F729B'>" + this.userName + "</font></b> "
                        + json.getJSONObject("caption").getString("text");

            this.imageUrl = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.imageHeight = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            this.likesCount = json.getJSONObject("likes").getInt("count");

            JSONArray commentsJSON = json.getJSONObject("comments").getJSONArray("data");
            int count = commentsJSON.length();
            this.comments = new String[count];
            for (int j = 0; j < count; j++) {
                JSONObject comment = commentsJSON.getJSONObject(j);
                this.comments[j] = "<b><font color='#3F729B'>"
                        + comment.getJSONObject("from").getString("username") + "</font></b> "
                        + comment.getString("text") + "<br>";
            }

            this.time = json.getLong("created_time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<InstagramPhoto> fromJSONArray(JSONArray array) {
        ArrayList<InstagramPhoto> results = new ArrayList<InstagramPhoto>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new InstagramPhoto(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
