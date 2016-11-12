package com.toprogrammers.smartcamp.smartcamp.member;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.toprogrammers.smartcamp.smartcamp.R;
import com.toprogrammers.smartcamp.smartcamp.TimetableListAdapter;
import com.toprogrammers.smartcamp.smartcamp.objects.InternetConnection;
import com.toprogrammers.smartcamp.smartcamp.objects.Schedule;
import com.toprogrammers.smartcamp.smartcamp.objects.User;

import java.util.ArrayList;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class FragmentTimeTable  extends Fragment {

    ListView list;

    Spinner spinner;

    ProgressDialog progressDialog;

    String response;

    Schedule schedule;
    InternetConnection ic;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_timetable, null);

        ic = new InternetConnection();
        user = new User(getActivity());

        list = (ListView) view.findViewById(R.id.list);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setPrompt("Выберите день");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //list.removeAllViews();
                TimetableListAdapter timetableListAdapter = new TimetableListAdapter(getActivity(), schedule, i);
                list.setAdapter(timetableListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new GetTask().execute();

        return view;
    }

    public class GetTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Загрузка...");
            progressDialog.setCancelable(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            response = ic.makeGETrequest(
                    new String[] {
                            "method", "schedule.get",
                            "tour_id", String.valueOf(user.getTourId())
                    });

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progressDialog.dismiss();
            schedule = new Schedule(getActivity(), response);
            schedule.updateInfo();
            TimetableListAdapter timetableListAdapter = new TimetableListAdapter(getActivity(), schedule, 0);
            list.setAdapter(timetableListAdapter);

            String[] dates = new String[schedule.getDaysCount()];
            for (int i = 0; i < schedule.getDaysCount(); i++) {
                dates[i] = schedule.getDate(i);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dates);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
        }
    }
}
