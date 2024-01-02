package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.R;

import java.util.List;

public class AdapterWorkoutRoomExpandableList extends BaseExpandableListAdapter {


    //TODO: Adapter anpassen
    private Context context;
    private List <WorkoutExercises> workoutExercises;

    public AdapterWorkoutRoomExpandableList(Context context, List <WorkoutExercises> workoutExercises) {
        this.context = context;
        this.workoutExercises = workoutExercises;
    }

    @Override
    public int getGroupCount() {
        return workoutExercises.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return workoutExercises.get(i).getExercises().size();
    }

    @Override
    public Object getGroup(int i) {
        return workoutExercises.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return workoutExercises.get(i).getExercises();
    }

    @Override
    public long getGroupId(int i) {
        return workoutExercises.get(i).getWorkout().getWorkout_id();
    }

    @Override
    public long getChildId(int i, int i1) {
        return workoutExercises.get(i).getExercises().get(i1).getExercise_id();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_group_workout, null);
        }
        Workout workout = workoutExercises.get(i).getWorkout();

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
            view = layoutInflater.inflate(R.layout.list_item_workout, null);
        }

        TextView exerciseNameTextView = view.findViewById(R.id.w);
        exerciseNameTextView.setText(workoutExercises.get(i).getExercises().get(i1).getName());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
