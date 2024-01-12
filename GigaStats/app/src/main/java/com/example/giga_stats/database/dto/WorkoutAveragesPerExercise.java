package com.example.giga_stats.database.dto;

import com.example.giga_stats.database.entities.Workout;

import java.util.HashMap;

/**
 * Repräsentiert den Durschnitt eines Workouts pro Übung. Diese Klasse hält Informationen über ein
 * Workout und eine Zuordnung von Übungs-IDs zu deren Durchschnittswerten.
 */
public class WorkoutAveragesPerExercise {
    private Workout workout;
    private HashMap<Integer, SetAverage> setAveragePerExercise;

    public WorkoutAveragesPerExercise() {
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public HashMap<Integer, SetAverage> getSetAveragePerExercise() {
        return setAveragePerExercise;
    }

    public void setSetAveragePerExercise(HashMap<Integer, SetAverage> setAveragePerExercise) {
        this.setAveragePerExercise = setAveragePerExercise;
    }
}
