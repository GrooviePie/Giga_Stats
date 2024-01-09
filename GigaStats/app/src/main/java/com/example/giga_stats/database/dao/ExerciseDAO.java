package com.example.giga_stats.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.WorkoutExerciseCrossRef;

import java.util.List;

/**
 * Das Interface ExerciseDAO definiert die Datenbankzugriffsmethoden für die Tabelle "exercises" und die Kreuzverweis-Tabelle "WorkoutExerciseCrossRef" in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Dao
public interface ExerciseDAO {
    /**
     * Fügt eine Übung zur Datenbank hinzu.
     *
     * @param exercise Die hinzuzufügende Übung.
     */
    @Insert
    void insertExercise(Exercise exercise);

    /**
     * Fügt eine Kreuzverweis-Beziehung zwischen Workout und Exercise zur Datenbank hinzu.
     *
     * @param crossRef Die hinzuzufügende Kreuzverweis-Beziehung.
     */
    @Insert
    void insertCrossRef(WorkoutExerciseCrossRef crossRef);

    /**
     * Aktualisiert eine vorhandene Übung in der Datenbank.
     *
     * @param exercise Die zu aktualisierende Übung.
     */
    @Update
    void updateExercise(Exercise exercise);

    /**
     * Löscht eine Übung aus der Datenbank anhand ihrer ID.
     *
     * @param exerciseId Die ID der zu löschenden Übung.
     */
    @Query("DELETE FROM exercises WHERE exercise_id = :exerciseId")
    void deleteExerciseById(int exerciseId);

    /**
     * Löscht eine Übung aus der Datenbank.
     *
     * @param exercise Die zu löschende Übung.
     */
    @Delete
    void deleteExercise(Exercise exercise);

    /**
     * Gibt eine LiveData-Liste aller Übungen aus der Datenbank zurück.
     *
     * @return LiveData-Liste aller Übungen.
     */
    @Query("SELECT * FROM exercises")
    LiveData<List<Exercise>> getAllExercises();

    /**
     * Gibt eine bestimmte Übung anhand ihrer ID aus der Datenbank zurück.
     *
     * @param id Die ID der gesuchten Übung.
     * @return Die gefundene Übung oder null, wenn keine Übung mit der angegebenen ID gefunden wurde.
     */
    @Query("SELECT * FROM exercises WHERE exercise_id = :id")
    Exercise getExerciseById(int id);

    /**
     * Sucht nach einer Kreuzverweis-Beziehung zwischen Workout und Exercise anhand der Exercise-ID.
     *
     * @param exerciseId Die ID der gesuchten Übung.
     * @return Die gefundene Kreuzverweis-Beziehung oder null, wenn keine Beziehung gefunden wurde.
     */
    @Query("SELECT * FROM WorkoutExerciseCrossRef WHERE exercise_id = :exerciseId LIMIT 1")
    WorkoutExerciseCrossRef findWorkoutForExercise(int exerciseId);

    /**
     * Überprüft, ob eine Übung gelöscht werden kann, indem nach einer zugehörigen Kreuzverweis-Beziehung gesucht wird.
     *
     * @param exerciseId Die ID der zu überprüfenden Übung.
     * @return True, wenn die Übung gelöscht werden kann, andernfalls false.
     */
    default boolean canDeleteExercise(int exerciseId) {
        return findWorkoutForExercise(exerciseId) == null;
    }

}
