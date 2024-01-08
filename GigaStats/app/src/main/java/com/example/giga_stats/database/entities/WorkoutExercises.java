/**
 * Die Klasse WorkoutExercises repräsentiert eine relationale Ansicht (Relation) zwischen Workouts und Exercises in der Giga Stats-Anwendung.
 * Sie enthält ein eingebettetes Workout-Objekt und eine Liste von Exercises, die mit dem Workout verknüpft sind.
 *
 * @version 1.0
 */
package com.example.giga_stats.database.entities;

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
            associateBy = @Junction(WorkoutExerciseCrossRef.class)
    )
    private List<Exercise> exercises;

    /**
     * Gibt das eingebettete Workout-Objekt zurück.
     *
     * @return Das Workout-Objekt.
     */
    public Workout getWorkout() {
        return workout;
    }

    /**
     * Setzt das eingebettete Workout-Objekt.
     *
     * @param workout Das Workout-Objekt.
     */
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    /**
     * Gibt die Liste von Exercises zurück, die mit dem Workout verknüpft sind.
     *
     * @return Die Liste von Exercises.
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Setzt die Liste von Exercises, die mit dem Workout verknüpft sind.
     *
     * @param exercises Die Liste von Exercises.
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
