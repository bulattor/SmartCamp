package com.toprogrammers.smartcamp.smartcamp.objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class Tasks {

    Context context;

    JSONArray tasks;

    String json;

    public Tasks(Context _context, String _json) {
        this.context = _context;
        this.json = _json;
        try {
            tasks = new JSONObject(_json).getJSONObject("response").getJSONArray("tasks");
        } catch (JSONException e) {
            tasks = new JSONArray();
        }
    }

    public int getTasksCount() {
        return tasks.length();
    }

    public int getId(int pos) {
        try {
            return tasks.getJSONObject(pos).getInt("id");
        } catch (JSONException e) {
            return 0;
        }
    }

    public String getTime(int pos) {
        try {
            return tasks.getJSONObject(pos).getString("time");
        } catch (JSONException e) {
            return "00:00";
        }
    }

    public String getTodo(int pos) {
        try {
            return tasks.getJSONObject(pos).getString("todo");
        } catch (JSONException e) {
            return "null";
        }
    }

    public int getConfirm(int pos) {
        try {
            return tasks.getJSONObject(pos).getInt("confirm");
        } catch (JSONException e) {
            return 0;
        }
    }
}
