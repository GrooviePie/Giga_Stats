package com.example.giga_stats.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.giga_stats.database.entities.WorkoutExercises;

import java.util.List;

@Dao
public interface WorkoutExerciseCrossRefDAO {
    @Transaction
    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutExercises>> getWorkoutsWithExercises();

    @Query("DELETE FROM WorkoutExerciseCrossRef WHERE workout_id = :workoutId")
    void deleteRefsById(int workoutId);
}
