package com.example.giga_stats.DB.DTO;

public class ExerciseTotalStats {

    private String exerciseName;
    private SetAverage previousAverage;
    private SetAverage currentAverage;
    private double efficiency;

    public ExerciseTotalStats() {
    }

    public ExerciseTotalStats(String exerciseName, SetAverage previousAverage, SetAverage currentAverage, double efficiency) {
        this.exerciseName = exerciseName;
        this.previousAverage = previousAverage;
        this.currentAverage = currentAverage;
        this.efficiency = efficiency;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public SetAverage getPreviousAverage() {
        return previousAverage;
    }

    public void setPreviousAverage(SetAverage previousAverage) {
        this.previousAverage = previousAverage;
    }

    public SetAverage getCurrentAverage() {
        return currentAverage;
    }

    public void setCurrentAverage(SetAverage currentAverage) {
        this.currentAverage = currentAverage;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }
}
