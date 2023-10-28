package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.giga_stats.R;
import com.example.giga_stats.DB.ENTITY.Workout;

public class AdapterRunningWorkout extends BaseAdapter {

    Context context;
    Workout[] workouts;

    public AdapterRunningWorkout(Context context, Workout[] workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.length; // Return the actual number of items in the array
    }

    @Override
    public Object getItem(int position) {
        return workouts[position];
    }

    @Override
    public long getItemId(int position) {
        return workouts[position].workout_id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_running_workout, null);
        }

        TextView runningNameInsert = view.findViewById(R.id.runningNameInsert);
        TextView runningWorkoutId = view.findViewById(R.id.runningWorkoutId);

        runningNameInsert.setText(workouts[position].getName());
        runningWorkoutId.setText(String.valueOf(workouts[position].getWorkout_id()));

        return view;
    }
}
