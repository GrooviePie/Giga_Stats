package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.R;

import java.util.List;

public class ExerciseRoomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int itemLayout;
    private List<Exercise> exercises;


    public ExerciseRoomExpandableListAdapter(Context context, List<Exercise> exercises) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.exercises = exercises;
    }

    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return exercises.get(i).getName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return exercises.get(i).getDesc();
    }

    @Override
    public long getGroupId(int i) {
        return exercises.get(i).getExercise_id();
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.exercise_list_group, null);
        }
        Exercise exercise = exercises.get(i);

        if (exercise != null) {
            TextView nameTextView = view.findViewById(R.id.nameExercise);
            TextView categoryTextView = view.findViewById(R.id.categoryExercise);
            TextView idTextView = view.findViewById(R.id.idExercise);

            idTextView.setText(String.valueOf(exercise.getExercise_id()));
            nameTextView.setText(exercise.getName());
            categoryTextView.setText(exercise.getCategory());
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.exercises_list_item, null);
        }

        Exercise exercise = exercises.get(i);

        if(exercise != null) {
            TextView descTextView = view.findViewById(R.id.descExercise);
            TextView repTextView = view.findViewById(R.id.repExercise);
            TextView weightTextView = view.findViewById(R.id.weightExercise);

            repTextView.setText(String.valueOf(exercise.getRep()) + "x");
            weightTextView.setText(String.valueOf(exercise.getWeight()) + "kg");
            descTextView.setText(exercise.getDesc());
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
