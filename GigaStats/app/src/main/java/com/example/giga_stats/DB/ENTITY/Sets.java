package com.example.giga_stats.DB.ENTITY;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sets {
    @PrimaryKey(autoGenerate = true)
    public long set_id;
    public long workout_id;
    public long exercise_id;
    public String date;
    public int repetitions;
    public int weight;
}
