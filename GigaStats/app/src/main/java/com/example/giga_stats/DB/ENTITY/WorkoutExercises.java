package com.example.giga_stats.DB.ENTITY;


import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class WorkoutExercises {
    @Embedded
    private Workout workout;

    @Relation(
            parentColumn = "workout_id",
            entity = Exercise.class,
            entityColumn = "exercise_id",
            associateBy = @Junction(WorkoutExerciseSetCrossRef.class)
    )
    private List<Exercise> exercises;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
