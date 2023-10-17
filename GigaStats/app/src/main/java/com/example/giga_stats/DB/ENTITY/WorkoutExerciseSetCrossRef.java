package com.example.giga_stats.DB.ENTITY;

import androidx.room.Entity;

@Entity(primaryKeys = {"workout_id", "exercise_id", "set_id"})
public class WorkoutExerciseSetCrossRef {
    public int workout_id;
    public int exercise_id;

    public int set_id;
}
