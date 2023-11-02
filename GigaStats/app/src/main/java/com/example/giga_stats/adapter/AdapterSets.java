package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giga_stats.R;

import java.util.ArrayList;

public class AdapterSets extends BaseAdapter {

    Context context;
    ArrayList<Integer> setCount;

    public AdapterSets(Context context, ArrayList<Integer> setCount){
        this.context = context;
        this.setCount = setCount;
    }

    @Override
    public int getCount() {
        return setCount.size();
    }

    @Override
    public Object getItem(int i) {
        return setCount.get(i);
    }

    @Override
    public long getItemId(int i) {
        return setCount.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bottom_sheet_list_item_item, null);
        }

        TextView rowNumber = view.findViewById(R.id.setCount);
        rowNumber.setText(String.valueOf(setCount.get(i)));

        return view;
    }
}
