package com.berlizov.dataorgua.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.berlizov.dataorgua.API.LoadData;
import com.berlizov.dataorgua.API.LoadGroups;
import com.berlizov.dataorgua.API.LoadImage;
import com.berlizov.dataorgua.Group;
import com.berlizov.dataorgua.GroupArrayAdapter;
import com.berlizov.dataorgua.R;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {
    TextView mError;
    ListView mListView;
    ProgressBar mProgressBar;
    TextView mProgressText;
    int coutOfLoadingGroups=0;
    int coutOfLoadedGroups=0;
    List<Group> groups=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setupView();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(groups.size()>0){
            show();
        } else {
            new LoadGroups(this).execute();
        }
    }

    @Override
     public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(getString(R.string.group), groups.size());
        for(int i=0;i<groups.size();i++) {
            outState.putSerializable(getString(R.string.group) + i, groups.get(i));
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int index = savedInstanceState.getInt(getString(R.string.group), groups.size());
        for(int i=0;i<index;i++) {
            groups.add((Group) savedInstanceState.getSerializable(getString(R.string.group) + i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(this, PrefActivity.class));
                break;
        }
        return true;
    }

    public void setError() {
        mError.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressText.setVisibility(View.INVISIBLE);
    }


    private void setupView() {
        mError = (TextView) findViewById(R.id.errorGroup);
        mListView = (ListView) findViewById(R.id.listViewGroup);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarGroup);
        mProgressText =(TextView) findViewById(R.id.progressTextView);
        mListView.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressText.setVisibility(View.VISIBLE);
    }

    public void successLoadGroups(List<String> s) {
        coutOfLoadedGroups=0;
        coutOfLoadingGroups = s.size();

        groups.clear();
        for (String str : s){
            new LoadData(this).execute(str);
        }
        checkCount();
    }

    public synchronized void successLoadGoupData(Group s) {
        coutOfLoadedGroups++;
        if(s.getName()!=null) {
            groups.add(s);
        }
        checkCount();
    }

    public synchronized void checkCount(){
        mProgressText.setText(coutOfLoadedGroups + "/" + coutOfLoadingGroups);
        if(coutOfLoadingGroups==coutOfLoadedGroups){
           show();
        }
    }
    protected void show(){
        mListView.setVisibility(View.VISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressText.setVisibility(View.INVISIBLE);

        mListView.setAdapter(new GroupArrayAdapter(this, R.id.listViewGroup, groups));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                openInfoSelector(position);
            }
        });
        for(Group g: groups) {
                new LoadImage(g,this).execute(g.getImageDisplayUrl());
        }
    }

    void openInfoSelector(int index){
        Intent intent = new Intent(this, InfoSelectorActivity.class);
        intent.putExtra(getString(R.string.group), groups.get(index));
        startActivity(intent);
    }

    public void imageLoaded(Group group,Bitmap img) {
        int index = groups.indexOf(group);
        View v =mListView.getChildAt(index -
               mListView.getFirstVisiblePosition());
        if(v == null)
            return;
        ImageView imageView = (ImageView) v.findViewById(R.id.groupImageView);
        imageView.setImageBitmap(img);
    }
}
