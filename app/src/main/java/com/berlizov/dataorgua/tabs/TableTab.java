package com.berlizov.dataorgua.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;

public class TableTab extends TableReaderFragment {

    @Override
    public boolean can(JSONTable table) {
        return true;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_tab, container, false);
        return view;
    }

    @Override
    public int getIdName() {
        return R.string.table_tab;
    }

    @Override
    public void applyTable(View view) {
        JsonTableView tableView = (JsonTableView) view.findViewById(R.id.tableView);
        if(tableView!=null) {
            tableView.setData(mTable);
        }
    }
}
