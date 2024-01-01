package com.example.giga_stats.database.dto;

public class SetAverage {
    double averageWeight;
    double averageReps;

    public SetAverage(double averageWeight, double averageReps) {
        this.averageWeight = averageWeight;
        this.averageReps = averageReps;
    }

    public SetAverage() {
        averageWeight = 0.0;
        averageReps = 0.0;
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

    @Override
    public String toString() {
        return "SetAverage{" +
                "averageWeight=" + averageWeight +
                ", averageReps=" + averageReps +
                '}';
    }
}
