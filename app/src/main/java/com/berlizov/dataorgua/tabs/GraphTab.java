package com.berlizov.dataorgua.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Hapiak on 21.10.2015.
 */
public class GraphTab extends TableReaderFragment {

    HorizontalBarChart barChart;
    PieChart pieChart;

    @Override
    public void setTable(JSONTable table) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        int i = 0;
        for (List<String> row : table.getRows()) {
            try {
                yVals1.add(new Entry(Float.parseFloat(row.get(1).replace(",", ".")), i++));
                xVals.add(row.get(0));
            } catch (NumberFormatException e) {

            }
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Palyvo");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.setDrawHoleEnabled(false);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        //BarDataSet set1 = new BarDataSet(yVals1, "Palyvo");
        //set1.setBarSpacePercent(35f);

        //ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        //dataSets.add(set1);

        //BarData data = new BarData(xVals, dataSets);

        //barChart.setData(data);
        //barChart.invalidate();
    }

    @Override
    public boolean can(JSONTable table) {
        return true;
    }

    @Override
    public int getIdName() {
        return R.string.graph_tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_tab, container, false);
        //barChart = (HorizontalBarChart) view.findViewById(R.id.chart);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        return view;
    }
}
