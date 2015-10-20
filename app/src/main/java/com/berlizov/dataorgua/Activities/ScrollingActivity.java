package com.berlizov.dataorgua.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.berlizov.dataorgua.Info;
import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;
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
    /**
    * Типо сюда дабавляем новые табы если разработали
    */
    enum Tab {
        TableTab(new TableTab()),MapTab(new MapTab());
        TableReaderFragment tab;
        Tab(TableReaderFragment tab){
            this.tab=tab;
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
    /**
    * метод при загр окна
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        setupToolbar();
        setupViewPager();
        new CallAPI(this).execute(mInfo.ID);
    }

    /**
    * метод выполняеться при загрузке инфы из сети
    */
    public void loaded(JSONTable str) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(Tab t: Tab.values()){
            if(t.getTab().can(str)) {
                adapter.addFrag(t.getTab(),getString(t.getTab().getIdName()));
                mTabs.add(t.getTab());
            }
        }
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        for(TableReaderFragment tab: mTabs){
                tab.setTable(str);
        }
    }
    /**
    * метод выполняеться при неудачной загрузке инфы из сети
    * показываем еррор
    */
    public void setError() {
        mError.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
    /**
    * пишем в заголовке окна имя набора данных
    */
    private void setupToolbar(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mInfo =(Info)extras.getSerializable(getString(R.string.info_id));
        }
        setTitle(mInfo.name);
    }
    /**
    * включам крутилку при загрузке
    */
    private void setupViewPager() {
        mError = (TextView) findViewById(R.id.errorInfo);
        mViewPager = (ViewPager) findViewById(R.id.containerInfo);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarInfo);
        mViewPager.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
/**
* штука для работы табов
*/
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

        public void addFrag(TableReaderFragment tab,String name) {
            mFragmentList.add(tab);
            mFragmentTitleList.add(name);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.size()-1<position?"":mFragmentTitleList.get(position);
        }
    }
    /**
    * асинхронная загрузка из сети
    */
    private class CallAPI extends AsyncTask<String, String, JSONTable> {
        ScrollingActivity parent;

        public CallAPI(ScrollingActivity parent) {
            this.parent = parent;
        }

        /**
        * сама загрузка
        */
        @Override
        protected JSONTable doInBackground(String... params) {
            //апи
            String urlString="http://data.ngorg.od.ua/uk/api/action/datastore/search.json?resource_id="+params[0];
            String result = "";
            InputStream in = null;
            // HTTP Get
            // на выход получам фиговую строку json
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                char[] buffer = new char[1024];

                int bytesRead=0;
                Reader reader = new BufferedReader(
                        new InputStreamReader(in, "UTF-8"));
                Writer writer = new StringWriter();
                while ((bytesRead = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, bytesRead);
                }
                in.close();
                result= writer.toString();
            } catch (Exception e ) {
                e.printStackTrace();
            }

            //Code
            // изза криворукости этих сервисов, мы на выход получали строку с  \u**** ,где * - цифра, вместо кириллических букв. там типо криво приброзовывали utf-8 
            // написал транслятор
            // на выход получам норм строку json
            StringBuilder out = new StringBuilder();
            while (result.length() > 0) {
                int index = result.indexOf("\\u");
                if (index >= 0) {
                    out.append(result.substring(0, index));
                    String c = result.substring(index + 2, index + 6);
                    int t0=Integer.parseInt(c, 16);
                    out.append(Character.toString((char)t0));
                    result = result.substring(index + 6);
                } else {
                    out.append(result);
                    result = "";
                }
            }

            //json в обьект JSONTable
            JSONTable table = new JSONTable();
            try {

                //Если что то пошло не так то вернем null
                if(!new JSONObject(out.toString()).getBoolean("success")){
                    Log.e("Success",""+new JSONObject(result).getBoolean("success"));
                    return null;
                }
                table.parseJson(out.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return table;
        }

        @Override
        protected void onPostExecute(JSONTable s) {
            super.onPostExecute(s);
            // если пришел null, по показывае ошибку
            if(s!=null) {
                parent.loaded(s);
            }else{
                parent.setError();
            }
        }
    }
}
