package com.example.giga_stats.DB.DTO;

public class SetData {
    public long workout_id;
    public long exercise_id;
    public String date;
    public int repetitions;
    public int weight;

    public SetData() {
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
}
