package com.berlizov.dataorgua.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;

/**
 * Created by 350z6_000 on 17.10.2015.
 * таба таблици
 */
public class TableTab extends TableReaderFragment {
    /**
    * табличкая таба отображаеться на любых наборах данных
    */
    @Override
    public boolean can(JSONTable table) {
        return true;
    }
    /**
    * загружаем данных в таблицу
    */
    @Override
    public void setTable(JSONTable table) {
        View view = getView();
        if(view!=null) {
            JsonTableView tableView = (JsonTableView) view.findViewById(R.id.tableView);
            if(tableView!=null) {
                tableView.setData(table);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_tab, container, false);
        return view;
    }

    @Override
    public int getIdName() {
        return R.string.table_tab;
    }
}
