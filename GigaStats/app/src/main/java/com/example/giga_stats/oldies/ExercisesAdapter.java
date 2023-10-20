package com.example.giga_stats.oldies;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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
        try {
//            String imageName = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
//            int imgResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
//            ImageView img = (ImageView) view.findViewById(to[0]);
//            img.setImageResource(imgResource);

            String txt1 = cursor.getString(cursor.getColumnIndexOrThrow(from[0]));
            TextView textView1 = (TextView) view.findViewById(to[0]);
            textView1.setText(txt1);

            String txt2 = cursor.getString(cursor.getColumnIndexOrThrow(from[1]));
            TextView textView2 = (TextView) view.findViewById(to[1]);
            textView2.setText(txt2);

            String txt3 = cursor.getString(cursor.getColumnIndexOrThrow(from[2]));
            TextView textView3 = (TextView) view.findViewById(to[2]);
            textView3.setText(txt3);

            String txt4 = cursor.getString(cursor.getColumnIndexOrThrow(from[3]));
            TextView textView4 = (TextView) view.findViewById(to[3]);
            textView4.setText(txt4);

        } catch (ArrayIndexOutOfBoundsException e) {
            // Hier können Sie die Fehlerbehandlung für den ArrayIndexOutOfBoundsException durchführen,
            // wenn 'from' nicht ausreichend viele Elemente enthält.
            Log.e("CHAD", "Fehler beim Binden der Daten an die Ansichtselemente: " + e.getMessage());
        }
    }

}

