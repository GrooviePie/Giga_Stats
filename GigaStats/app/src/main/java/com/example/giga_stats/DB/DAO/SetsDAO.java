package com.example.giga_stats.DB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.giga_stats.DB.ENTITY.Sets;

import java.util.List;

@Dao
public interface SetsDAO {
    @Insert
    void insertSet(Sets set);

    @Query("SELECT * FROM Sets WHERE workout_id = :workoutId AND exercise_id = :exerciseId")
    LiveData<List<Sets>> getSetsForExerciseAndWorkout(long workoutId, long exerciseId);

}
