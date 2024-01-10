package com.example.giga_stats.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.giga_stats.database.entities.Sets;

import java.util.List;
/**
 * Das Interface SetsDAO definiert die Datenbankzugriffsmethoden für die Tabelle "Sets" in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Dao
public interface SetsDAO {
    /**
     * Fügt einen Satz zur Datenbank hinzu.
     *
     * @param set Der hinzuzufügende Satz.
     */
    @Insert
    void insertSet(Sets set);

    /**
     * Gibt eine Liste von Sätzen für eine bestimmte Übung und ein bestimmtes Workout aus der Datenbank zurück.
     *
     * @param workoutId  Die ID des Workouts.
     * @param exerciseId Die ID der Übung.
     * @return Eine Liste von Sätzen für die angegebene Übung und das angegebene Workout.
     */
    @Query("SELECT * FROM Sets WHERE workout_id = :workoutId AND exercise_id = :exerciseId")
    List<Sets> getSetsForExerciseAndWorkout(long workoutId, long exerciseId);

    @Query("SELECT * FROM Sets WHERE workout_id = :workoutId AND exercise_id = :exerciseId")
    LiveData<List<Sets>> getSetsForExerciseAndWorkoutLD(long workoutId, long exerciseId);

}
