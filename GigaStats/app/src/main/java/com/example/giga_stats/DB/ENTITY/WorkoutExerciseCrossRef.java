package com.example.giga_stats.DB.ENTITY;

import androidx.room.Entity;

@Entity(primaryKeys = {"workout_id", "exercise_id"})
public class WorkoutExerciseCrossRef {
    public int workout_id;
    public int exercise_id;

    public WorkoutExerciseCrossRef(int workout_id, int exercise_id) {
        this.workout_id = workout_id;
        this.exercise_id = exercise_id;
    }
}
