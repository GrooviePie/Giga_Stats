package com.example.giga_stats.DB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;

import java.util.List;

@Dao
public interface WorkoutDAO {
    @Insert
    long insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("DELETE FROM workouts  WHERE workout_id = :workout_id")
    void deleteWorkoutById(int workout_id);

    @Query("SELECT * FROM workouts")
    LiveData<List<Workout>> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE workout_id = :id")
    Workout getWorkoutById(long id);

    @Transaction
    @Query("SELECT * FROM workouts")
    LiveData<List<WorkoutExercises>> getWorkoutExercises();
}
