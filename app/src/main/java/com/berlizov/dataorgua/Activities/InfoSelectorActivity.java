package com.berlizov.dataorgua.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.berlizov.dataorgua.Group;
import com.berlizov.dataorgua.Info;
import com.berlizov.dataorgua.InfoArrayAdapter;
import com.berlizov.dataorgua.R;

import java.util.ArrayList;
import java.util.List;

public class InfoSelectorActivity extends AppCompatActivity {

    class Section{
        String name;
        public Section(String name) {
            this.name = name;
        }
    }
    Group mGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_selector);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupActivity();
    }

    protected void setupActivity(){
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mGroup = (Group) extras.getSerializable(getString(R.string.group));
            }
            setTitle(mGroup.getName());
        //TODO Перенести в onStart
        loadInfoList();
    }


    protected void loadInfoList(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarInfos);
        ListView listView = (ListView) findViewById(R.id.listViewInfos);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(new InfoArrayAdapter(this, R.id.listViewInfos, mGroup.getInfos()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                openInfo(position);
            }
        });
    }

    public void openInfo(int index){
        Intent intent = new Intent(this, ScrollingActivity.class);
        intent.putExtra(getString(R.string.info_id),  mGroup.getInfos().get(index));
        startActivity(intent);
    }
}
