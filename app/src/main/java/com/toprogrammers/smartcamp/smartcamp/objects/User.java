package com.toprogrammers.smartcamp.smartcamp.objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class User {

    Context context;

    JSONObject user;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String json;

    public User(Context _context, String _json) {
        this.context = _context;
        this.json = _json;
        try {
            user = new JSONObject(_json).getJSONObject("response");
        } catch (JSONException e) {
            user = new JSONObject();
        }
    }

    public User(Context _context) {
        this.context = _context;
        sharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        try {
            user = new JSONObject(sharedPreferences.getString("user_info", "{}")).getJSONObject("response");
        } catch (JSONException e) {
            user = new JSONObject();
        }
    }

    public void updateInfo() {
        sharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("user_info", json);
        editor.commit();
    }

    public int getId() {
        try {
            return user.getInt("id");
        } catch (JSONException e) {
            return 0;
        }
    }

    public int getTourId() {
        try {
            return user.getInt("tour_id");
        } catch (JSONException e) {
            return 0;
        }
    }

    public int getLeaderId() {
        try {
            return user.getInt("leader_id");
        } catch (JSONException e) {
            return 0;
        }
    }

    public String getName() {
        try {
            return user.getString("name");
        } catch (JSONException e) {
            return "null";
        }
    }

    public String getSurname() {
        try {
            return user.getString("surname");
        } catch (JSONException e) {
            return "null";
        }
    }

    public int getStatus() {
        try {
            return user.getInt("status");
        } catch (JSONException e) {
            return 0;
        }
    }
}
