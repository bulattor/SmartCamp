package com.toprogrammers.smartcamp.smartcamp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toprogrammers.smartcamp.smartcamp.objects.Schedule;

public class TimetableListAdapter extends BaseAdapter {

    private final Activity context;

    private static LayoutInflater inflater = null;

    Schedule schedule;
    int dataPosition;

    public TimetableListAdapter(Activity context, Schedule schedule, int dataPosition) {

        this.context = context;
        this.schedule = schedule;
        this.dataPosition = dataPosition;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_timetable, null);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView event = (TextView) view.findViewById(R.id.event);

        time.setText(schedule.getTaskTime(dataPosition, position));
        event.setText(schedule.getTaskTodo(dataPosition, position));
        return view;

    }

    @Override
    public int getCount() {
        return schedule.getTaskCount(dataPosition);
    }
}