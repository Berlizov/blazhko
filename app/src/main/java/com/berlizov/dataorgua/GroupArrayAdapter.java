package com.berlizov.dataorgua;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 350z6_000 on 17.10.2015.
 * штука для заполнения итема в списке наборов данных
 */
public class GroupArrayAdapter extends ArrayAdapter<Group> {
    List<Group> mGroups;

    public GroupArrayAdapter(Context context, int resource, List<Group> group) {
        super(context, resource, group);
        this.mGroups= group;
    }
    static class ViewHolder {
        TextView name;
        TextView description;
        TextView count;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = mGroups.get(position);
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.group_list_item, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.groupItemNameTextView);
            holder.description = (TextView) rowView.findViewById(R.id.groupItemCompanyTextView);
            holder.count = (TextView) rowView.findViewById(R.id.groupCount);
            holder.image = (ImageView) rowView.findViewById(R.id.groupImageView);
            rowView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.name.setText(group.getName());
        holder.description.setText(group.getDescription());
        holder.count.setText(getContext().getString(R.string.count)+Integer.toString(group.getInfos().size()));

        return rowView;
    }
    public void updateView(int index){
    }
}
