package com.berlizov.dataorgua.tabs;

import android.content.Context;
import android.util.AttributeSet;
import com.berlizov.dataorgua.JSONTable;
import java.util.List;
import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by 350z6_000 on 18.10.2015.
 * наши кастомная таблица для этого всего
 */
public class JsonTableView extends TableView<List<String>> {
    public JsonTableView(Context context) {
        super(context);
    }

    public JsonTableView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public JsonTableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);
    }
    //настройка таблици, зы тут я думаю что то пабает, ну и ладно
    public void setData(JSONTable table) {
        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), table.getHeaders().toArray(new String[table.getHeaders().size()]) );
        setHeaderAdapter(simpleTableHeaderAdapter);
        setColumnCount(table.getColumnCount());
        setDataAdapter(new JsonDataAdapter(getContext(),table));
    }
}

