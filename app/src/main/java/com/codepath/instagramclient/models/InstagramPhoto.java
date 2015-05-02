package com.codepath.instagramclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class InstagramPhoto implements Serializable {
    private static final long serialVersionUID = 7302706685174970863L;
    public String userName;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public String profilePic;
    public long time;
    public int numComments;
    public ArrayList<InstagramComment> comments;

    public ArrayList<InstagramComment> getComments() {
        return comments;
    }

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

            this.numComments = json.getJSONObject("comments").getInt("count");
            JSONArray commentsJSON = json.getJSONObject("comments").getJSONArray("data");
            comments = new ArrayList<>();

            comments.addAll(InstagramComment.fromJSONArray(commentsJSON));

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
