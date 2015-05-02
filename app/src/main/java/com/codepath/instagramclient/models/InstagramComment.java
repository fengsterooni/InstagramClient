package com.codepath.instagramclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class InstagramComment implements Serializable{
    private static final long serialVersionUID = 2765854760081438078L;
    public String userName;
    public String profilePic;
    public String comment;
    public long time;

    public InstagramComment(JSONObject json) {
        try {
            this.userName = "<b><font color='#3F729B'>"
                    + json.getJSONObject("from").getString("username") + "</font></b> ";
            this.profilePic = json.getJSONObject("from").getString("profile_picture");
            this.comment = this.userName + json.getString("text");
            this.time = json.getLong("created_time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<InstagramComment> fromJSONArray(JSONArray array) {
        ArrayList<InstagramComment> results = new ArrayList<InstagramComment>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new InstagramComment(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
