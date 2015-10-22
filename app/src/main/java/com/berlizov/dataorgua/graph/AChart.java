package com.berlizov.dataorgua.graph;

import android.view.View;

import com.berlizov.dataorgua.JSONTable;

/**
 * Created by Viktor Hapiak on 22.10.2015.
 */
public abstract class AChart<T extends View> {

    protected T mView;
    protected JSONTable mTable;

    public void setVisible(boolean flag) {
        mView.setVisibility(flag ? View.VISIBLE : View.GONE);
    };

    public void setChartView(T view) {
        mView = view;
    };

    public void setData(JSONTable table) {
        mTable = table;
        updateRowAndColumn(0, 0);
    };

    public abstract void updateRowAndColumn(int row, int column);
}
