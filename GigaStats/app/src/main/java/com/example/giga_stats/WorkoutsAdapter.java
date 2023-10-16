package com.example.giga_stats;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class WorkoutsAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private final int itemLayout;
    private final String[] from;
    private final int[] to;

    public WorkoutsAdapter(Context context , int itemLayout, Cursor c, String[] from, int[] to, int flags) {
        super(context, c, flags);
        layoutInflater = LayoutInflater.from(context);
        this.itemLayout = itemLayout;
        this.from = from;
        this.to = to;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(itemLayout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
