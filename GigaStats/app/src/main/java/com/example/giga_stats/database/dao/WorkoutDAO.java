package com.example.giga_stats.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;

import java.util.List;

/**
 * Das Interface WorkoutDAO definiert die Datenbankzugriffsmethoden für die Tabelle "workouts" in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Dao
public interface WorkoutDAO {
    /**
     * Fügt ein Workout zur Datenbank hinzu.
     *
     * @param workout Das hinzuzufügende Workout.
     * @return Die ID des hinzugefügten Workouts.
     */
    @Insert
    long insertWorkout(Workout workout);

    /**
     * Aktualisiert ein vorhandenes Workout in der Datenbank.
     *
     * @param workout Das zu aktualisierende Workout.
     */
    @Update
    void updateWorkout(Workout workout);

    /**
     * Löscht ein Workout aus der Datenbank.
     *
     * @param workout Das zu löschende Workout.
     */
    @Delete
    void deleteWorkout(Workout workout);

    /**
     * Löscht ein Workout aus der Datenbank anhand seiner ID.
     *
     * @param workout_id Die ID des zu löschenden Workouts.
     */
    @Query("DELETE FROM workouts  WHERE workout_id = :workout_id")
    void deleteWorkoutById(int workout_id);

    /**
     * Gibt eine LiveData-Liste aller Workouts aus der Datenbank zurück.
     *
     * @return LiveData-Liste aller Workouts.
     */
    @Query("SELECT * FROM workouts")
    LiveData<List<Workout>> getAllWorkouts();

    /**
     * Gibt ein bestimmtes Workout anhand seiner ID aus der Datenbank zurück.
     *
     * @param id Die ID des gesuchten Workouts.
     * @return Das gefundene Workout oder null, wenn kein Workout mit der angegebenen ID gefunden wurde.
     */
    @Query("SELECT * FROM workouts WHERE workout_id = :id")
    Workout getWorkoutById(long id);

    /**
     * Gibt alle Workouts als LiveData mit ihren jeweiligen Übungen aus der Datenbank zurück.
     *
     * @return Die gefundenen Workouts mit den korrespondierenden Übungen als LiveData-Liste.
     */
    @Transaction
    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutExercises>> getWorkoutExercisesLD();

    /**
     * Gibt alle Workouts mit ihren jeweiligen Übungen aus der Datenbank zurück.
     *
     * @return Die gefundenen Workouts mit den korrespondierenden Übungen.
     */
    @Transaction
    @Query("SELECT * FROM workouts")
    List<WorkoutExercises> getWorkoutExercises();

    /**
     * Gibt eine LiveData-Repräsentation von WorkoutExercises für ein bestimmtes Workout zurück.
     *
     * @param workout_id Die ID des Workouts.
     * @return LiveData-Repräsentation von WorkoutExercises für das angegebene Workout.
     */

    @Transaction
    @Query("SELECT * FROM workouts WHERE workout_id = :workout_id")
    LiveData<WorkoutExercises> getExercisesForWorkoutLD(int workout_id);

    /**
     * Gibt WorkoutExercises für ein bestimmtes Workout zurück.
     *
     * @param workout_id Die ID des Workouts.
     * @return WorkoutExercises für das angegebene Workout.
     */
    @Transaction
    @Query("SELECT * FROM workouts WHERE workout_id = :workout_id")
    WorkoutExercises getExercisesForWorkout(int workout_id);
}
