package com.example.giga_stats.DB.DTO;

public class SetAverage {
    double averageWeight;
    double averageReps;

    public SetAverage(double averageWeight, double averageReps) {
        this.averageWeight = averageWeight;
        this.averageReps = averageReps;
    }

    public SetAverage() {

    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public double getAverageReps() {
        return averageReps;
    }

    public void setAverageReps(double averageReps) {
        this.averageReps = averageReps;
    }
}
