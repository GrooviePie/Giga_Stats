package com.example.giga_stats.DB.ENTITY;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {

    public Workout(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    public int workout_id;

    @ColumnInfo(name = "name")
    String name;

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
