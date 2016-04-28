package com.codepath.simpletodo;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        tvName.setText(item.getName());
        tvPriority.setText(item.getPriority().name());
        if (item.getStatus() == Status.DONE) {
            tvName.setPaintFlags(tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvPriority.setPaintFlags(tvPriority.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            tvName.setPaintFlags(tvName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            tvPriority.setPaintFlags(tvPriority.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        return convertView;
    }
}

