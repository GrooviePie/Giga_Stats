package com.example.giga_stats.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.giga_stats.database.entities.WorkoutExercises;

import java.util.List;
/**
 * Das Interface WorkoutExerciseCrossRefDAO definiert die Datenbankzugriffsmethoden für die Tabelle "WorkoutExerciseCrossRef" in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Dao
public interface WorkoutExerciseCrossRefDAO {
    /**
     * Gibt eine LiveData-Liste von WorkoutExercises aus der Datenbank zurück, die Workouts und zugehörige Übungen enthält.
     *
     * @return LiveData-Liste von WorkoutExercises.
     */
    @Transaction
    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutExercises>> getWorkoutsWithExercises();

    /**
     * Löscht alle Verweise auf Übungen in WorkoutExerciseCrossRef anhand der Workout-ID.
     *
     * @param workoutId Die ID des Workouts, für das die Verweise gelöscht werden sollen.
     */
    @Query("DELETE FROM WorkoutExerciseCrossRef WHERE workout_id = :workoutId")
    void deleteRefsById(int workoutId);
}
