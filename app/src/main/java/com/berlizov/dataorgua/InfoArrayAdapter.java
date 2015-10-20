package com.berlizov.dataorgua;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 350z6_000 on 17.10.2015.
 * штука для заполнения итема в списке наборов данных
 */
public class InfoArrayAdapter extends ArrayAdapter<Info> {
    List<Info> infos;

    public InfoArrayAdapter(Context context, int resource,  List<Info> infos) {
        super(context, resource, infos);
        this.infos = infos;
    }
    static class ViewHolder {
        TextView name;
        TextView company;
        TextView date;
    }
    /**
    * возвращаем итема набора данных
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info info = infos.get(position);
        View rowView = convertView;
        // если есть свободный итем то используем его, а если нет, то создаем пустой
        if (rowView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.info_list_item, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.infoItemNameTextView);
            holder.company = (TextView) rowView.findViewById(R.id.infoItemCompanyTextView);
            holder.date = (TextView) rowView.findViewById(R.id.infoItemDateTextView);
            rowView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        //заполняем итем именем, копманией и датой
        holder.name.setText(info.name);
        holder.company.setText(info.company);
        holder.date.setText(info.date);

        return rowView;
    }
}
