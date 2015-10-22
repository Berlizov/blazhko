package com.berlizov.dataorgua.graph;

import com.berlizov.dataorgua.JSONTable;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Hapiak on 22.10.2015.
 */
public class HorizontalBarChart extends AChart<com.github.mikephil.charting.charts.HorizontalBarChart> {

    @Override
    public void updateRowAndColumn(int row, int column) {
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        int i = 0;
        for (List<String> tableRow : mTable.getRows()) {
            try {
                yValues.add(new BarEntry(Float.parseFloat(tableRow.get(column).replace(",", ".")), i++));
                xValues.add(tableRow.get(row));
            } catch (NumberFormatException e) {

            }
        }
        if(xValues.size() == 0) {
            mView.setData(null);
            mView.notifyDataSetChanged();
            mView.invalidate();
            return;
        }

        BarDataSet dataSet = new BarDataSet(yValues, mTable.getHeader(row));
        dataSet.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(dataSet);

        BarData data = new BarData(xValues, dataSets);
        //data.setValueFormatter(new PercentFormatter());
        //data.setValueTextColor(Color.BLACK);
        mView.setData(data);
        mView.notifyDataSetChanged();
        mView.invalidate();
        //pieChart.setDrawHoleEnabled(false);
        //Legend l = pieChart.getLegend();
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
    }
}
