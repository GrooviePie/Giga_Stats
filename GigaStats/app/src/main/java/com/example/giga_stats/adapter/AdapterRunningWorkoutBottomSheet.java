package com.example.giga_stats.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giga_stats.R;

import java.util.ArrayList;

public class AdapterRunningWorkoutBottomSheet extends BaseAdapter {

    private Context context;
    private ArrayList<String> exerciseNames;

    public AdapterRunningWorkoutBottomSheet(Context context, ArrayList<String> exerciseNames) {
        this.context = context;
        this.exerciseNames = exerciseNames;
    }

    @Override
    public int getCount() {
        return exerciseNames.size();
    }

    @Override
    public Object getItem(int position) {
        return exerciseNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_layout_bottom_sheet_running_workout, null);
        }

        TextView bottomSheetExerciseNameTextView = convertView.findViewById(R.id.bottomSheetExerciseNameTextView);

        // Setze den Übungsnamen in das TextView
        bottomSheetExerciseNameTextView.setText(exerciseNames.get(position));

        return convertView;
    }
}

