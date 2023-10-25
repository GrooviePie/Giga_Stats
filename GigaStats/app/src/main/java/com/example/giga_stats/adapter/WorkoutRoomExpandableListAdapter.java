package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.R;

import java.util.List;

public class WorkoutRoomExpandableListAdapter extends BaseExpandableListAdapter {


    //TODO: Adapter anpassen
    private Context context;
    private List<Workout> workouts;

    public WorkoutRoomExpandableListAdapter(Context context, List<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @Override
    public int getGroupCount() {
        return workouts.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return workouts.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null; // No child items for workouts
    }

    @Override
    public long getGroupId(int i) {
        return workouts.get(i).getWorkout_id();
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0; // No child items
    }

    @Override
    public boolean hasStableIds() {
        return true; // If workout IDs are stable, return true
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.workout_list_group, null);
        }
        Workout workout = workouts.get(i);

        if (workout != null) {
            TextView nameTextView = view.findViewById(R.id.nameWorkout);
            TextView idTextView = view.findViewById(R.id.idWorkout);

            idTextView.setText(String.valueOf(workout.getWorkout_id()));
            nameTextView.setText(workout.getName());
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.workout_list_item, null);
        }

        TextView exerciseNameTextView = view.findViewById(R.id.w);
        exerciseNameTextView.setText("BROOOOOOOOOOOOOOOOT");

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true; // No child items to select
    }
}
