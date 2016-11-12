package com.toprogrammers.smartcamp.smartcamp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatarchaapps.app.tatarcha.R;
import com.tatarchaapps.app.tatarcha.objects.Dictionary;

import java.io.IOException;

public class TimetableListAdapter extends BaseAdapter {

    private final Activity context;

    private static LayoutInflater inflater = null;

    Shedule shedule;
    int dataPosition;

    public TimetableListAdapter(Activity context, Shedule shedule, int dataPosition) {

        this.context = context;
        this.shedule = shedule;
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

        time.setText(shedule.getTaskTime(dataPosition, position));
        event.setText(shedule.getTaskTodo(dataPosition, position));
        return view;

    }

    @Override
    public int getCount() {
        return shedule.getTasksCount(dataPosition);
    }
}