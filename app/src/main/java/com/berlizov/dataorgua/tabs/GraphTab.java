package com.berlizov.dataorgua.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.berlizov.dataorgua.JSONTable;
import com.berlizov.dataorgua.R;
import com.berlizov.dataorgua.graph.AChart;
import com.berlizov.dataorgua.graph.HorizontalBarChart;
import com.berlizov.dataorgua.graph.PieChart;

/**
 * Created by Viktor Hapiak on 21.10.2015.
 */
public class GraphTab extends TableReaderFragment implements AdapterView.OnItemSelectedListener {

    private enum Chart {
        BAR("Гистограмма", new HorizontalBarChart()),
        PIE("Круговая", new PieChart());

        Chart(String name, AChart chart) {
            mName = name;
            mChart = chart;
        }

        private String mName;
        private AChart mChart;

        public String getName() {
            return mName;
        }

        public AChart getChart() {
            return mChart;
        }
    }

    private AChart mCurrentChart = Chart.BAR.getChart();
    private ArrayAdapter<CharSequence> mHeadersAdapter;
    private int mSelectedRow, mSelectedColumn;

    @Override
    public void setTable(JSONTable table) {
        for(Chart chart : Chart.values()) {
            chart.getChart().setData(table);
        }

        for(String column : table.getHeaders()) {
            mHeadersAdapter.add(column);
        }
        mHeadersAdapter.notifyDataSetChanged();
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

        Chart.BAR.getChart().setChartView(view.findViewById(R.id.bar_chart));
        Chart.PIE.getChart().setChartView(view.findViewById(R.id.pie_chart));

        mHeadersAdapter = new ArrayAdapter<CharSequence>(
                view.getContext(),
                android.R.layout.simple_spinner_item
        );

        Spinner row = (Spinner) view.findViewById(R.id.row);
        row.setAdapter(mHeadersAdapter);
        row.setOnItemSelectedListener(this);

        Spinner column = (Spinner) view.findViewById(R.id.column);
        column.setAdapter(mHeadersAdapter);
        column.setOnItemSelectedListener(this);

        ArrayAdapter<String> chartAdapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_spinner_item
        );
        for(Chart chart : Chart.values()) {
            chartAdapter.add(chart.getName());
        }

        Spinner chart = (Spinner) view.findViewById(R.id.chart_type);
        chart.setAdapter(chartAdapter);
        chart.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.chart_type:
                mCurrentChart.setVisible(false);
                mCurrentChart = Chart.values()[position].getChart();
                mCurrentChart.setVisible(true);
                break;
            case R.id.row:
                mSelectedRow = (int) id;
                break;
            case R.id.column:
                mSelectedColumn = (int) id;
                break;
        }
        mCurrentChart.updateRowAndColumn(mSelectedRow, mSelectedColumn);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
