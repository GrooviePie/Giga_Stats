package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;

import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.R;
import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.activityNfragments.FragmentRunningWorkoutBottomSheet;

import java.util.List;

public class AdapterRunningWorkout extends BaseAdapter {

    Context context;
    Workout[] workouts;

    public AdapterRunningWorkout(Context context, Workout[] workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.length;
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

        LinearLayout runningTile = view.findViewById(R.id.runningTile);
        TextView runningNameInsert = view.findViewById(R.id.runningNameInsert);
        TextView runningWorkoutId = view.findViewById(R.id.runningWorkoutId);
        //TextView bottomSheetExerciseNameTextView = view.findViewById(R.id.bottomSheetExerciseNameTextView);

        runningNameInsert.setText(workouts[position].getName());
        runningWorkoutId.setText(String.valueOf(workouts[position].getWorkout_id()));
        //bottomSheetExerciseNameTextView.setText(workouts[position].getName());

        // Füge den Klicklistener zum runningTile hinzu
        runningTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Klick-Callback-Methode, um das Bottom Sheet zu öffnen
                openBottomSheetDialog(workouts[position]);
            }
        });

        return view;
    }

    private void openBottomSheetDialog(Workout workout) {
        FragmentRunningWorkoutBottomSheet bottomSheetFragment = new FragmentRunningWorkoutBottomSheet(workout);
        bottomSheetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}



