package com.example.giga_stats.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sets {
    @PrimaryKey(autoGenerate = true)
    private long set_id;
    private long workout_id;
    private long exercise_id;
    private String date;
    private int repetitions;
    private int weight;

    public Sets() {
    }

    public long getSet_id() {
        return set_id;
    }

    public void setSet_id(long set_id) {
        this.set_id = set_id;
    }

    public long getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(long workout_id) {
        this.workout_id = workout_id;
    }

    public long getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(long exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Sets{" +
                "set_id=" + getSet_id() +
                ", workout_id=" + getWorkout_id() +
                ", exercise_id=" + getExercise_id() +
                ", date='" + getDate() + '\'' +
                ", repetitions=" + getRepetitions() +
                ", weight=" + getWeight() +
                '}';
    }
}
