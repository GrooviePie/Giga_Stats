package com.example.giga_stats.DB.ENTITY;


import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class WorkoutExercises {
    @Embedded
    public Workout workout;

    @Relation(
            parentColumn = "workout_id",
            entity = Exercise.class,
            entityColumn = "exercise_id",
            associateBy = @Junction(WorkoutExerciseSetCrossRef.class)
    )
    public List<Exercise> exercises;
}
