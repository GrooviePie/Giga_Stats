package com.example.giga_stats.database.dto;

import com.example.giga_stats.database.entities.Workout;

import java.util.HashMap;
import java.util.Objects;

/**
 * Repräsentiert die Effizienz eines Workouts pro Übung. Diese Klasse hält Informationen über ein
 * Workout und eine Zuordnung von Übungs-IDs zu deren Effizienzwerten.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutEfficiencyPerExercise that = (WorkoutEfficiencyPerExercise) o;
        return Objects.equals(workout, that.workout) && Objects.equals(efficiencyPerExercise, that.efficiencyPerExercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workout, efficiencyPerExercise);
    }
}
