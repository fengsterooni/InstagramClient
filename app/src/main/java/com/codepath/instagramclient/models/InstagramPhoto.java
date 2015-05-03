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
    public String id;
    public ArrayList<InstagramComment> comments;
    public String videoUrl;

    public InstagramPhoto(JSONObject json) {
        try {
            // Photo Id
            this.id = json.getString("id");
            // User Name
            this.userName = json.getJSONObject("user").getString("username");
            // Profile image URL
            this.profilePic = json.getJSONObject("user").getString("profile_picture");
            // Colorize user name
            this.caption = "<b><font color='#3F729B'>" + this.userName + "</font></b> ";
            // Append caption if available
            if (!json.isNull("caption"))
                this.caption += json.getJSONObject("caption").getString("text");
            // Image URL
            this.imageUrl = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            // Image height
            this.imageHeight = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            // Like count
            this.likesCount = json.getJSONObject("likes").getInt("count");
            // Number of comments
            // NOTE:    Only up to 8 comments stored at popular endpoints
            //          For all comments, get it from ../media/{media-id}/comments/
            this.numComments = json.getJSONObject("comments").getInt("count");
            JSONArray commentsJSON = json.getJSONObject("comments").getJSONArray("data");
            comments = new ArrayList<>();
            comments.addAll(InstagramComment.fromJSONArray(commentsJSON));
            // Create time
            this.time = json.getLong("created_time");
            // Media type
            String type = json.getString("type");
            // If it is a video, get the Video URL
            if (type.equals("video")) {
                this.videoUrl = json.getJSONObject("videos").getJSONObject("standard_resolution").getString("url");
            }
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
