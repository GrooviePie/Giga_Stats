package com.example.giga_stats.database.dto;

import com.example.giga_stats.database.entities.Exercise;

public class SetDetails {
    private int setCount;
    private Exercise exercise;
    private int weight;
    private int reps;

    public SetDetails() {
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        if(getExercise() != null){
        return "SetDetails{" +
                "setCount=" + getSetCount() +
                ", exercise=" + getExercise().toString() +
                ", weight=" + getWeight() +
                ", reps=" + getReps() +
                '}';
        }
        return "Init Set";
    }
}
