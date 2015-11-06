package com.berlizov.dataorgua.graph;

import android.graphics.Color;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Hapiak on 22.10.2015.
 */
public class PieChart extends AChart<com.github.mikephil.charting.charts.PieChart> {


    @Override
    public void setChartView(com.github.mikephil.charting.charts.PieChart view) {
        super.setChartView(view);

        mView.setDrawCenterText(false);
        mView.setDrawSliceText(false);
        mView.setDrawHoleEnabled(false);
        Legend l = mView.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
    }

    @Override
    public void updateRowAndColumn(int row, int column) {
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        int i = 0;
        for (List<String> tableRow : mTable.getRows()) {
            try {
                yValues.add(new Entry(Float.parseFloat(tableRow.get(column).replace(",", ".")), i++));
                String value = tableRow.get(row);
                if(value.length() > 25) {
                    value = value.substring(0, 22) + "...";
                }
                xValues.add(value);
            } catch (NumberFormatException e) {

            }
        }
        if(xValues.size() == 0) {
            mView.clear();
            mView.notifyDataSetChanged();
            mView.invalidate();
            return;
        }


        PieDataSet dataSet = new PieDataSet(yValues, mTable.getHeader(row));
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

        PieData data = new PieData(xValues, dataSet);

        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);

        mView.setData(data);
        mView.notifyDataSetChanged();
        mView.invalidate();
    }
}
