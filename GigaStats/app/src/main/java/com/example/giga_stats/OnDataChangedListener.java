package com.example.giga_stats;

import com.example.giga_stats.DB.DTO.SetDetails;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;

import java.util.ArrayList;
import java.util.HashMap;

public interface OnDataChangedListener {
    void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise, WorkoutExercises workoutExercises);
}
