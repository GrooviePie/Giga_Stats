package com.example.giga_stats.DB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.giga_stats.DB.ENTITY.Workout;

import java.util.List;

@Dao
public interface WorkoutDAO {
    @Insert
    void insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("SELECT * FROM workouts")
    List<Workout> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE workout_id = :id")
    List<Workout> getWorkoutById(int id);

    @Query("SELECT exercise_id FROM workoutexercisesetcrossref r WHERE  r.workout_id = :id")
    List <Integer> getAllExerciseIds(int id);
}
