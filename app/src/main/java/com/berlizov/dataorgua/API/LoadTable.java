package com.berlizov.dataorgua.API;

import android.util.Log;

import com.berlizov.dataorgua.Activities.ScrollingActivity;
import com.berlizov.dataorgua.JSONTable;

import org.json.JSONObject;

/**
 * Created by 350z6_000 on 05.11.2015.
 */
public class LoadTable extends API<JSONTable> {
    ScrollingActivity parent;

    public LoadTable(ScrollingActivity parent) {
        super(parent.getBaseContext());
        this.parent = parent;
    }

    @Override
    protected String createQuery(String... params) {
        return "http://" + getSiteURI() + "/uk/api/action/datastore/search.json?resource_id=" + params[0];
    }

    @Override
    protected JSONTable calcResult(String result) throws Exception {
        JSONTable table = new JSONTable();
        if (!new JSONObject(result).getBoolean("success")) {
            Log.e("Success", "" + new JSONObject(result).getBoolean("success"));
            return null;
        }
        table.parseJson(result);
        return table;
    }

    @Override
    protected void successLoad(JSONTable s) {
        parent.loaded(s);
    }

    @Override
    protected void ErrorLoad() {
        parent.setError();
    }
}