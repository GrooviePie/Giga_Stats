package com.example.giga_stats.database.entities;

import androidx.room.Entity;
/**
 * Die Klasse WorkoutExerciseCrossRef repräsentiert die Verbindungstabelle zwischen Workouts und Exercises in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Entity(primaryKeys = {"workout_id", "exercise_id"})
public class WorkoutExerciseCrossRef {
    public int workout_id;
    public int exercise_id;

    /**
     * Konstruktor für die WorkoutExerciseCrossRef-Klasse.
     *
     * @param workout_id  Die ID des Workouts.
     * @param exercise_id Die ID der Übung.
     */
    public WorkoutExerciseCrossRef(int workout_id, int exercise_id) {
        this.workout_id = workout_id;
        this.exercise_id = exercise_id;
    }
}
