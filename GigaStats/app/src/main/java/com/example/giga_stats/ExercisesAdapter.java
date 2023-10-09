package com.example.giga_stats;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExercisesAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private final int itemLayout;
    private final String[] from;
    private final int[] to;

    public ExercisesAdapter(Context context , int itemLayout, Cursor c, String[] from, int[] to, int flags ) {
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
        String imageName = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
        int imgResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        ImageView img = (ImageView) view.findViewById(to[0]);
        img.setImageResource(imgResource);

        String txt1 = cursor.getString(cursor.getColumnIndexOrThrow(from[1]));
        TextView textView1 = (TextView) view.findViewById(to[1]);
        textView1.setText(txt1);

        String txt2 = cursor.getString(cursor.getColumnIndexOrThrow(from[2]));
        TextView textView2 = (TextView) view.findViewById(to[2]);
        textView2.setText(txt2);
    }
}

