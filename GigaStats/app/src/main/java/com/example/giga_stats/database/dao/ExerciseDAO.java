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

@Dao
public interface ExerciseDAO {
    @Insert
    void insertExercise(Exercise exercise);

    @Insert
    void insertCrossRef(WorkoutExerciseCrossRef crossRef);

    @Update
    void updateExercise(Exercise exercise);

    @Query("DELETE FROM exercises WHERE exercise_id = :exerciseId")
    void deleteExerciseById(int exerciseId);

    @Delete
    void deleteExercise(Exercise exercise);

    @Query("SELECT * FROM exercises")
    LiveData<List<Exercise>> getAllExercises();

//    @Transaction
//    @Query("SELECT * FROM exercises")
//    List<WorkoutExercises> getWorkoutExercises();

    @Query("SELECT * FROM exercises WHERE exercise_id = :id")
    Exercise getExerciseById(int id);

    @Query("SELECT * FROM WorkoutExerciseCrossRef WHERE exercise_id = :exerciseId LIMIT 1")
    WorkoutExerciseCrossRef findWorkoutForExercise(int exerciseId);

    default boolean canDeleteExercise(int exerciseId) {
        return findWorkoutForExercise(exerciseId) == null;
    }

}
