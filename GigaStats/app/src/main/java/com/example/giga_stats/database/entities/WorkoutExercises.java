package com.example.giga_stats.database.entities;


import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class WorkoutExercises {
    @Embedded
    private Workout workout;

    @Relation(
            parentColumn = "workout_id",
            entity = Exercise.class,
            entityColumn = "exercise_id",
            associateBy = @Junction(WorkoutExerciseCrossRef.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExercises that = (WorkoutExercises) o;
        return Objects.equals(workout, that.workout) && Objects.equals(exercises, that.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workout, exercises);
    }
}
