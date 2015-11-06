package com.berlizov.dataorgua.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.berlizov.dataorgua.API.API;
import com.berlizov.dataorgua.API.LoadTable;
import com.berlizov.dataorgua.Info;
import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;
import com.berlizov.dataorgua.tabs.GraphTab;
import com.berlizov.dataorgua.tabs.MapTab;
import com.berlizov.dataorgua.tabs.TableReaderFragment;
import com.berlizov.dataorgua.tabs.TableTab;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ScrollingActivity extends AppCompatActivity {

    enum Tab {
        TableTab(new TableTab()),
        MapTab(new MapTab()),
        GraphTab(new GraphTab());

        TableReaderFragment tab;

        Tab(TableReaderFragment tab) {
            this.tab = tab;
        }

        public TableReaderFragment getTab() {
            return tab;
        }
    }

    Info mInfo;
    TextView mError;
    ViewPager mViewPager;
    ProgressBar mProgressBar;
    List<TableReaderFragment> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        setupToolbar();
        setupViewPager();

        new LoadTable(this).execute(mInfo.ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable("info",mInfo);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("info",mInfo);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mInfo = (Info) savedInstanceState.getSerializable("info");
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void loaded(JSONTable str) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Tab t : Tab.values()) {
            if (t.getTab().can(str)) {
                adapter.addFrag(t.getTab(), getString(t.getTab().getIdName()));
                mTabs.add(t.getTab());
                t.getTab().setTable(str);
            }
        }
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void setError() {
        mError.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void setupToolbar() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mInfo = (Info) extras.getSerializable(getString(R.string.info_id));
        }
        setTitle(mInfo.name);
    }

    private void setupViewPager() {
        mError = (TextView) findViewById(R.id.errorInfo);
        mViewPager = (ViewPager) findViewById(R.id.containerInfo);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarInfo);
        mViewPager.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(TableReaderFragment tab, String name) {
            mFragmentList.add(tab);
            mFragmentTitleList.add(name);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.size() - 1 < position ? "" : mFragmentTitleList.get(position);
        }
    }
}
