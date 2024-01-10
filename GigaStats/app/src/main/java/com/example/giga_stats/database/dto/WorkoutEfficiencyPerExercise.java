package com.example.giga_stats.database.dto;

import com.example.giga_stats.database.entities.Workout;

import java.util.HashMap;

public class WorkoutEfficiencyPerExercise {
    private Workout workout;
    private HashMap<Integer, Double> efficiencyPerExercise = new HashMap<>();

    public WorkoutEfficiencyPerExercise() {
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public HashMap<Integer, Double> getEfficiencyPerExercise() {
        return efficiencyPerExercise;
    }

    public void setEfficiencyPerExercise(HashMap<Integer, Double> efficiencyPerExercise) {
        this.efficiencyPerExercise = efficiencyPerExercise;
    }
}
