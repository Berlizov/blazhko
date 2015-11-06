package com.berlizov.dataorgua.API;

import com.berlizov.dataorgua.Activities.GroupActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 350z6_000 on 05.11.2015.
 */
public class LoadGroups extends API<List<String>> {
    GroupActivity parent;

    public LoadGroups(GroupActivity parent) {
        super(parent.getBaseContext());
        this.parent = parent;
    }

    @Override
    protected List<String> calcResult(String result) throws Exception {
        List<String> list = new ArrayList<>();
        JSONObject obj = new JSONObject(result);
        JSONArray resultObj = obj.getJSONArray("result");
        for(int i=0;i<resultObj.length();i++) {
            list.add(resultObj.getString(i));
        }
        return list;
    }

    @Override
    protected String createQuery(String... params) {
        return "http://" + getSiteURI() + "/api/3/action/group_list?all_fields=true";
    }

    @Override
    protected void successLoad(List<String> s) {
        parent.successLoadGroups(s);
    }

    @Override
    protected void ErrorLoad() {
        parent.setError();
    }
}
