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
import com.toprogrammers.smartcamp.smartcamp.TasksListAdapter;
import com.toprogrammers.smartcamp.smartcamp.TimetableListAdapter;
import com.toprogrammers.smartcamp.smartcamp.objects.InternetConnection;
import com.toprogrammers.smartcamp.smartcamp.objects.Schedule;
import com.toprogrammers.smartcamp.smartcamp.objects.Tasks;
import com.toprogrammers.smartcamp.smartcamp.objects.User;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class FragmentTasks extends Fragment {

    ListView list;

    InternetConnection ic;
    Tasks tasks;
    User user;

    ProgressDialog progressDialog;

    String response;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_tasks, null);

        ic = new InternetConnection();
        user = new User(getActivity());

        list = (ListView) view.findViewById(R.id.list);

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
                            "method", "tasks.get",
                            "date", "07/11",
                            "tour_id", String.valueOf(user.getTourId())
                    });

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progressDialog.dismiss();
            tasks = new Tasks(getActivity(), response);
            TasksListAdapter timetableListAdapter = new TasksListAdapter(getActivity(), tasks, 0);
            list.setAdapter(timetableListAdapter);
        }
    }
}
