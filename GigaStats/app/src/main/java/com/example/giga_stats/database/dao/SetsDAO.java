package com.example.giga_stats.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.giga_stats.database.entities.Sets;

import java.util.List;

@Dao
public interface SetsDAO {
    @Insert
    void insertSet(Sets set);

    @Query("SELECT * FROM Sets WHERE workout_id = :workoutId AND exercise_id = :exerciseId")
    List<Sets> getSetsForExerciseAndWorkout(long workoutId, long exerciseId);

}
