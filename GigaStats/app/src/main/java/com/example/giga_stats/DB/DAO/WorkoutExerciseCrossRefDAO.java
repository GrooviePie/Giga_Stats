package com.example.giga_stats.DB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.giga_stats.DB.ENTITY.WorkoutExercises;

import java.util.List;

@Dao
public interface WorkoutExerciseCrossRefDAO {
    @Transaction
    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutExercises>> getWorkoutsWithExercises();
}
