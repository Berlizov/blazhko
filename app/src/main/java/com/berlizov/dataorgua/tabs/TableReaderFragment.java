package com.berlizov.dataorgua.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;

public abstract class TableReaderFragment extends Fragment {
    protected JSONTable mTable;

    public void setTable(JSONTable table){
            mTable = table;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);
        applyTable(view);
        return view;
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void applyTable(View view);
    public abstract boolean can(JSONTable table);
    public abstract int getIdName();
}
