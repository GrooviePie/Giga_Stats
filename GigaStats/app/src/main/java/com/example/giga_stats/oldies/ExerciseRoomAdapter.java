package com.example.giga_stats.oldies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.R;

public class ExerciseRoomAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private int itemLayout;
    private List<Exercise> exercises;

    public ExerciseRoomAdapter(Context context, int resource, List<Exercise> objects) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itemLayout = resource;
        this.exercises = objects;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int i) {
        return exercises.get(i);
    }

    @Override
    public long getItemId(int i) {
        return exercises.get(i).getExercise_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(itemLayout, parent, false);
        }

        Exercise exercise = exercises.get(position);

        if (exercise != null) {
            TextView nameTextView = convertView.findViewById(R.id.nameExercise);
            TextView categoryTextView = convertView.findViewById(R.id.categoryExercise);
            TextView repTextView = convertView.findViewById(R.id.repExercise);
            TextView weightTextView = convertView.findViewById(R.id.weightExercise);
            TextView idTextView = convertView.findViewById(R.id.idExercise);

            idTextView.setText(String.valueOf(exercise.getExercise_id()));
            nameTextView.setText(exercise.getName());
            categoryTextView.setText(exercise.getCategory());
            repTextView.setText("x" + String.valueOf(exercise.getRep()));
            weightTextView.setText(String.valueOf(exercise.getWeight()) + "kg");

        }

        return convertView;
    }
}
