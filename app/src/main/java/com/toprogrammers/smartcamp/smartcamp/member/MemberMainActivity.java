package com.toprogrammers.smartcamp.smartcamp.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toprogrammers.smartcamp.smartcamp.LoginActivity;
import com.toprogrammers.smartcamp.smartcamp.R;
import com.toprogrammers.smartcamp.smartcamp.TimetableListAdapter;
import com.toprogrammers.smartcamp.smartcamp.objects.InternetConnection;
import com.toprogrammers.smartcamp.smartcamp.objects.Schedule;
import com.toprogrammers.smartcamp.smartcamp.objects.User;

public class MemberMainActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("APP", Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("userIsLoggedIn", false)){
            startActivity(new Intent(MemberMainActivity.this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_member_main);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new FragmentTimeTable();
                case 1 : return new FragmentTasks();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Расписание";
                case 1 :
                    return "Задачи";
            }
            return null;
        }
    }
}
