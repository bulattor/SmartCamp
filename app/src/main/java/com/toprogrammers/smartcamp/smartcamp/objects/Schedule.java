package com.toprogrammers.smartcamp.smartcamp.objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class Schedule {

    Context context;

    JSONArray schedule;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String json;

    public Schedule(Context _context, String _json) {
        this.context = _context;
        this.json = _json;
        try {
            schedule = new JSONObject(_json).getJSONArray("response");
        } catch (JSONException e) {
            schedule = new JSONArray();
        }
    }

    public Schedule(Context _context) {
        this.context = _context;
        sharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        try {
            schedule = new JSONObject(sharedPreferences.getString("schedule_info", "{}")).getJSONArray("response");
        } catch (JSONException e) {
            schedule = new JSONArray();
        }
    }

    public void updateInfo() {
        sharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("schedule_info", json);
        editor.commit();
    }

    public int getDaysCount() {
        return schedule.length();
    }

    public String getDate(int dayPos) {
        try {
            return schedule.getJSONObject(dayPos).getString("day");
        } catch (JSONException e) {
            return "00/00";
        }
    }

    public int getTaskCount(int dayPos) {
        try {
            return schedule.getJSONObject(dayPos).getJSONArray("tasks").length();
        } catch (JSONException e) {
            return 0;
        }
    }

    public String getTaskTime(int dayPos, int taskPos) {
        try {
            return schedule.getJSONObject(dayPos).getJSONArray("tasks").getJSONObject(taskPos).getString("time");
        } catch (JSONException e) {
            return "00:00";
        }
    }

    public String getTaskTodo(int dayPos, int taskPos) {
        try {
            return schedule.getJSONObject(dayPos).getJSONArray("tasks").getJSONObject(taskPos).getString("todo");
        } catch (JSONException e) {
            return "null";
        }
    }
}
