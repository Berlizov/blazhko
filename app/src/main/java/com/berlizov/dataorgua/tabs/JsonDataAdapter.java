package com.berlizov.dataorgua.tabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.berlizov.dataorgua.JSONTable;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by 350z6_000 on 18.10.2015.
 * класс для заполнения таблиц из jsontable
 */
public class JsonDataAdapter extends TableDataAdapter<List<String>> {
    public JsonDataAdapter(Context context, JSONTable table) {
        super(context,table.getRows());

    }
    // формируем яцейку таблици
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        String row = getRowData(rowIndex).get(columnIndex);
        TextView textView = new TextView(getContext());
        textView.setText(row);
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }
}
