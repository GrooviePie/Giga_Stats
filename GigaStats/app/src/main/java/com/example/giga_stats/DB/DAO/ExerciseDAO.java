package com.example.giga_stats.DB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.WorkoutExerciseSetCrossRef;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Insert
    void insertExercise(Exercise exercise);

    @Insert
    void insertCrossRef(WorkoutExerciseSetCrossRef crossRef);

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
    List<Exercise> getExerciseById(int id);

}
